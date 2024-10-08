package org.simon.stuff.entity.drug;

import org.simon.stuff.*;
import org.simon.stuff.advancement.StuffCriteria;
import org.simon.stuff.entity.*;
import org.simon.stuff.entity.drug.hallucination.HallucinationManager;
import org.simon.stuff.entity.drug.influence.DrugInfluence;
import org.simon.stuff.entity.drug.sound.DrugMusicManager;
import org.simon.stuff.item.StuffItems;
import org.simon.stuff.mixin.MixinLivingEntity;
import org.simon.stuff.network.Channel;
import org.simon.stuff.network.MsgDrugProperties;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.simon.stuff.util.NbtSerializable;

public class DrugProperties implements NbtSerializable {
    public static final UUID DRUG_EFFECT_UUID = UUID.fromString("2da054e7-0fe0-4fb4-bf2c-a185a5f72aa1");

    private final Map<DrugType, Drug> drugs = DrugType.REGISTRY.stream().collect(Collectors.toMap(Function.identity(), DrugType::create));
    private final List<DrugInfluence> influences = new ArrayList<>();

    private boolean dirty;

    private final HallucinationManager hallucinations = new HallucinationManager(this);
    private final DrugMusicManager soundManager = new DrugMusicManager(this);

    private int timeBreathingSmoke;
    @Nullable
    private Vector3f breathSmokeColor;

    private final PlayerEntity entity;


    public DrugProperties(PlayerEntity entity) {
        this.entity = entity;
    }

    public static DrugProperties of(PlayerEntity player) {
        return ((DrugPropertiesContainer)player).getDrugProperties();
    }

    public static Stream<DrugProperties> stream(Entity entity) {
        if (entity instanceof DrugPropertiesContainer c) {
            return Stream.of(c.getDrugProperties());
        }
        return Stream.empty();
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
        dirty = true;
    }

    public HallucinationManager getHallucinations() {
        return hallucinations;
    }

    public DrugMusicManager getMusicManager() {
        return soundManager;
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

    public boolean isTripping() {
        float f = getModifier(Drug.MOVEMENT_HALLUCINATION_STRENGTH)
                + getModifier(Drug.CONTEXTUAL_HALLUCINATION_STRENGTH)
                + getModifier(Drug.COLOR_HALLUCINATION_STRENGTH);
        return f > 0.7F;
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

    public void addToDrug(DrugInfluence influence) {
        influences.add(influence);
        markDirty();
    }

    public void addAll(Iterable<DrugInfluence> influences) {
        influences.forEach(influence -> this.influences.add(influence.clone()));
        markDirty();
    }

    public Collection<Drug> getAllDrugs() {
        return drugs.values();
    }

    public Set<DrugType> getAllDrugNames() {
        return drugs.keySet();
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

    public int getAge() {
        return entity.age;
    }

    public void onTick() {
        if (entity.age % 5 == 0) { //4 times / sec is enough
            influences.removeIf(influence -> {
                if (influence.update(this)) {
                    markDirty();
                    return true;
                }
                return false;
            });
        }

        drugs.values().forEach(drug -> drug.update(this));

        soundManager.update();

        Random random = entity.getRandom();

        if (entity.getWorld().isClient) {
            hallucinations.update();



            if (entity.isOnGround() && random.nextFloat() < getModifier(Drug.JUMP_CHANCE)) {
                ((MixinLivingEntity)entity).invokeJump();
            }

            if (!entity.handSwinging && random.nextFloat() < getModifier(Drug.PUNCH_CHANCE)) {
                entity.swingHand(Hand.MAIN_HAND);
            }
        } else {
            if (random.nextFloat() < getModifier(Drug.DROWSYNESS)) {
                entity.addExhaustion(0.05F);
            }
        }

        if (isBreathingSmoke()) {
            timeBreathingSmoke--;

            if (timeBreathingSmoke > 10 && entity.getWorld().isClient) {
                Vec3d look = entity.getRotationVec(1);

                if (random.nextInt(2) == 0) {
                    float s = random.nextFloat() * 0.05f + 0.1f;
                    ParticleHelper.spawnColoredParticle(entity, breathSmokeColor, look, s, 1.0f);
                }

                if (random.nextInt(5) == 0) {
                    float s = random.nextFloat() * 0.05f + 0.1f;
                    ParticleHelper.spawnColoredParticle(entity, breathSmokeColor, look, s, 2.5f);
                }
            }
        }

        changeDrugModifierMultiply(entity, EntityAttributes.GENERIC_MOVEMENT_SPEED, getModifier(Drug.SPEED));
        changeDrugModifierMultiply(entity, EntityAttributes.GENERIC_ATTACK_SPEED, getModifier(Drug.SPEED));

        if (dirty) {
            dirty = false;
            sendCapabilities();
        }

        if (!entity.getWorld().isClient && Stuff.getConfig().balancing.randomTicksUntilRiftSpawn > 0) {
            if (random.nextInt(Stuff.getConfig().balancing.randomTicksUntilRiftSpawn) == 0) {
                RealityRiftEntity.spawn(entity);
            }
        }
    }

    public void sendCapabilities() {
        if (!entity.getWorld().isClient) {
            Channel.UPDATE_DRUG_PROPERTIES.sendToSurroundingPlayers(new MsgDrugProperties(this), entity);
            Channel.UPDATE_DRUG_PROPERTIES.sendToPlayer(new MsgDrugProperties(this), (ServerPlayerEntity)entity);
        }
    }

    @Override
    public void fromNbt(NbtCompound tagCompound) {
        NbtCompound drugData = tagCompound.getCompound("Drugs");
        drugs.clear();
        drugData.getKeys().forEach(key -> {
            DrugType.REGISTRY.getOrEmpty(Identifier.tryParse(key)).ifPresent(type -> {
                getDrug(type).fromNbt(drugData.getCompound(key));
            });
        });
        influences.clear();
        tagCompound.getList("drugInfluences", NbtElement.COMPOUND_TYPE).forEach(tag -> {
            DrugInfluence.loadFromNbt((NbtCompound)tag).ifPresent(this::addToDrug);
        });
        dirty = false;
    }

    @Override
    public void toNbt(NbtCompound compound) {
        NbtCompound drugsComp = new NbtCompound();
        drugs.forEach((key, drug) -> {
            drugsComp.put(key.id().toString(), drug.toNbt());
        });
        compound.put("Drugs", drugsComp);

        NbtList influenceTagList = new NbtList();
        for (DrugInfluence influence : influences) {
            influenceTagList.add(influence.toNbt());
        }
        compound.put("drugInfluences", influenceTagList);
    }

    public void copyFrom(DrugProperties old, boolean alive) {
        if (alive) {
            influences.clear();
            influences.addAll(old.influences);
            drugs.clear();
            drugs.putAll(old.drugs);
            timeBreathingSmoke = old.timeBreathingSmoke;
            breathSmokeColor = old.breathSmokeColor;
            dirty = true;
        }
    }

    public boolean onAwoken() {
        drugs.values().forEach(drug -> drug.onWakeUp(this));
        influences.clear();
        dirty = true;

        // TODO: (Sollace) Implement longer sleeping/comas
        return true;
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
}
