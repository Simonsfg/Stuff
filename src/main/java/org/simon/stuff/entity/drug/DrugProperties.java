package org.simon.stuff.entity.drug;


import org.simon.stuff.*;
import org.simon.stuff.advancement.StuffCriteria;
import org.simon.stuff.entity.drug.sound.DrugMusicManager;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.simon.stuff.util.NbtSerializable;

public class DrugProperties implements NbtSerializable {
    public static final UUID DRUG_EFFECT_UUID = UUID.fromString("2da054e7-0fe0-4fb4-bf2c-a185a5f72aa1");

    private final Map<DrugType, Drug> drugs = new HashMap<>();

    private final DrugMusicManager soundManager = new DrugMusicManager(this);

    private int tripDuration = 0;

    private int timeBreathingSmoke;
    @Nullable
    private Vector3f breathSmokeColor;

    private final PlayerEntity entity;

    public DrugProperties() {
        this.entity = null;  // Default constructor
    }

    public DrugProperties(PlayerEntity entity) {
        this.entity = entity;
        // Initialize with known drug types
        drugs.put(DrugType.CANNABIS, DrugType.CANNABIS.create());
    }

    public static DrugProperties of(PlayerEntity player) {
        if (player instanceof DrugPropertiesContainer) {
            return ((DrugPropertiesContainer) player).getDrugProperties();
        } else {
            System.out.println("Player is not a DrugPropertiesContainer");
            return new DrugProperties(player); // Return a new instance instead of null
        }
    }

    public static Optional<DrugProperties> of(Entity entity) {
        if (entity instanceof DrugPropertiesContainer c) {
            return Optional.of(c.getDrugProperties());
        }
        return Optional.empty();
    }

    public PlayerEntity asEntity() {
        return entity;
    }

    public DamageSource damageOf(RegistryKey<DamageType> type) {
        return StuffDamageTypes.create(entity.getWorld(), type);
    }

    public void markDirty() {
    }

    public Drug getDrug(DrugType type) {
        return drugs.computeIfAbsent(type, DrugType::create);
    }

    public float getDrugValue(DrugType type) {
        if (!drugs.containsKey(type)) {
            return 0F;
        }
        return (float) getDrug(type).getActiveValue();
    }

    public boolean isDrugActive(DrugType type) {
        return drugs.containsKey(type) && getDrugValue(type) > MathHelper.EPSILON;
    }

    public void addToDrug(DrugType type, double effect) {
        getDrug(type).addToDesiredValue(effect);
        StuffCriteria.DRUG_EFFECTS_CHANGED.trigger(this);
        markDirty();
    }

    public void setDrugValue(DrugType type, double effect) {
        getDrug(type).setDesiredValue(effect);
        StuffCriteria.DRUG_EFFECTS_CHANGED.trigger(this);
        markDirty();
    }

    public Collection<Drug> getAllDrugs() {
        return drugs.values();
    }

    public void startBreathingSmoke(int time, Vector3f color) {
        this.breathSmokeColor = color;
        this.timeBreathingSmoke = time + 10; //10 is the time spent breathing in
        markDirty();

        entity.getWorld().playSoundFromEntity(entity, entity, StuffSounds.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS, 0.02F, 1.5F);
    }

    public boolean isBreathingSmoke() {
        return timeBreathingSmoke > 0;
    }

    @Override
    public void fromNbt(NbtCompound tagCompound) {
        NbtCompound drugData = tagCompound.getCompound("Drugs");
        drugs.clear();
        for (String key : drugData.getKeys()) {
            DrugType type = DrugType.REGISTRY.get(new Identifier(key));
            if (type != null) {
                Drug drug = type.create();
                drug.fromNbt(drugData.getCompound(key));
                drugs.put(type, drug);
            }
        }
    }

    @Override
    public void toNbt(NbtCompound compound) {
        NbtCompound drugsComp = new NbtCompound();
        drugs.forEach((key, drug) -> {
            drugsComp.put(key.id().toString(), drug.toNbt());
        });
        compound.put("Drugs", drugsComp);
    }

    public Optional<Text> trySleep(BlockPos pos) {
        return getAllDrugs().stream().flatMap(drug -> drug.trySleep(pos).stream()).findFirst();
    }

    public float getModifier(Drug.AggregateModifier modifier) {
        return modifier.get(this);
    }

    private void changeDrugModifierMultiply(LivingEntity entity, EntityAttribute attribute, double value) {
        // 2: ret *= 1.0 + value
        changeDrugModifier(entity, attribute, value - 1.0, Operation.MULTIPLY_TOTAL);
    }

    private void changeDrugModifier(LivingEntity entity, EntityAttribute attribute, double value, Operation operation) {
        EntityAttributeInstance speedInstance = entity.getAttributeInstance(attribute);
        speedInstance.removeModifier(DrugProperties.DRUG_EFFECT_UUID);
        speedInstance.addTemporaryModifier(new EntityAttributeModifier(DrugProperties.DRUG_EFFECT_UUID, "Drug Effects", value, operation));
    }

    public int getTimeBreathingSmoke() {
        return timeBreathingSmoke;
    }

    @Nullable
    public Vector3f getBreathSmokeColor() {
        return breathSmokeColor;
    }

    public void startTrip(int duration) {
        this.tripDuration = duration;
    }

    public boolean isTripping() {
        return this.tripDuration > 0;
    }

    public float getTripIntensity() {
        return Math.min(1.0f, this.tripDuration / 1200f);
    }

    public void tick() {
        if (this.tripDuration > 0) {
            this.tripDuration--;
        }
    }
}
