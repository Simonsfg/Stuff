package org.simon.stuff.entity.drug.type;

import java.util.Optional;

import org.simon.stuff.StuffDamageTypes;
import org.simon.stuff.entity.drug.*;
import org.simon.stuff.util.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class SimpleDrug implements Drug {
    protected double effect;
    protected double effectActive;
    protected boolean locked = false;

    private final double decreaseSpeed;
    private final double decreaseSpeedPlus;
    private final boolean invisible;

    private final DrugType type;

    private int ticksActive;

    public SimpleDrug(DrugType type, double decSpeed, double decSpeedPlus) {
        this(type, decSpeed, decSpeedPlus, false);
    }

    public SimpleDrug(DrugType type, double decSpeed, double decSpeedPlus, boolean invisible) {
        this.type = type;
        decreaseSpeed = decSpeed;
        decreaseSpeedPlus = decSpeedPlus;

        this.invisible = invisible;
    }

    @Override
    public final DrugType getType() {
        return type;
    }

    public void setActiveValue(double value) {
        effectActive = value;
    }

    @Override
    public double getActiveValue() {
        return effectActive;
    }

    public double getDesiredValue() {
        return effect;
    }

    public int getTicksActive() {
        return ticksActive;
    }

    @Override
    public void setDesiredValue(double value) {
        effect = value;
    }

    @Override
    public void addToDesiredValue(double value) {
        if (!locked) {
            effect += value;
        }
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean isVisible() {
        return !invisible;
    }

    @Override
    public void update(DrugProperties drugProperties) {
        if (getActiveValue() > 0) {
            ticksActive++;

            if (heartbeatSpeed() > 3) {
                drugProperties.asEntity().damage(drugProperties.damageOf(StuffDamageTypes.HEART_ATTACK), Integer.MAX_VALUE);
                reset(drugProperties);
            }
        } else {
            ticksActive = 0;
        }

        if (!locked) {
            effect *= decreaseSpeed;
            effect -= decreaseSpeedPlus;
        }

        effect = MathHelper.clamp(effect, 0, 1);
        setActiveValue(MathUtils.nearValue(effectActive, effect, 0.05, 0.005));
    }

    @Override
    public void onWakeUp(DrugProperties drugProperties) {
        reset(drugProperties);
    }

    @Override
    public void reset(DrugProperties drugProperties) {
        if (!locked) {
            effect = 0;
        }
    }

    @Override
    public void fromNbt(NbtCompound compound) {
        setDesiredValue(compound.getDouble("effect"));
        setActiveValue(compound.getDouble("effectActive"));
        setLocked(compound.getBoolean("locked"));
        ticksActive = compound.getInt("ticksActive");
    }

    @Override
    public void toNbt(NbtCompound compound) {
        compound.putDouble("effect", getDesiredValue());
        compound.putDouble("effectActive", getActiveValue());
        compound.putBoolean("locked", isLocked());
        compound.putInt("ticksActive", ticksActive);
    }

    @Override
    public Optional<Text> trySleep(BlockPos pos) {
        return Optional.empty();
    }

    @Override
    public void applyContrastColorization(float[] rgba) {

    }

    @Override
    public void applyColorBloom(float[] rgba) {

    }

    protected static void rotateEntityPitch(Entity entity, double amount) {
        entity.setPitch((float)MathHelper.clamp(entity.getPitch() + amount, -90, 90));
    }

    protected static void rotateEntityYaw(Entity entity, double amount) {
        entity.setYaw(entity.getYaw() + (float)amount);
    }
}
