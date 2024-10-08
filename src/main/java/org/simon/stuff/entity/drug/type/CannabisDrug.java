package org.simon.stuff.entity.drug.type;

import org.simon.stuff.util.MathUtils;
import org.simon.stuff.entity.drug.DrugProperties;
import org.simon.stuff.entity.drug.DrugType;


public class CannabisDrug extends SimpleDrug {
    public CannabisDrug(double decSpeed, double decSpeedPlus) {
        super(DrugType.CANNABIS, decSpeed, decSpeedPlus);
    }

    @Override
    public void update(DrugProperties drugProperties) {
        super.update(drugProperties);

        if (getActiveValue() > 0) {
            drugProperties.asEntity().addExhaustion(0.03F * (float) getActiveValue());
        }
    }

    @Override
    public float speedModifier() {
        return (1.0F - (float) getActiveValue()) * 0.5F + 0.5F;
    }

    @Override
    public float digSpeedModifier() {
        return (1.0F - (float) getActiveValue()) * 0.5F + 0.5F;
    }

    @Override
    public float superSaturationHallucinationStrength() {
        return MathUtils.inverseLerp((float)getActiveValue(), 0, 0.5f) * 0.3F;
    }

    @Override
    public float colorHallucinationStrength() {
        return MathUtils.inverseLerp((float) getActiveValue() * 1.3F, 0.5F, 1) * 0.1F;
    }

    @Override
    public float movementHallucinationStrength() {
        return MathUtils.inverseLerp((float) getActiveValue() * 1.3F, 0.5f, 1) * 0.1F;
    }

    @Override
    public float contextualHallucinationStrength() {
        return MathUtils.inverseLerp((float) getActiveValue() * 1.3F, 0.5f, 1) * 0.1F;
    }

    @Override
    public float headMotionInertness() {
        return (float)getActiveValue() * 8.0F;
    }

    @Override
    public float viewWobblyness() {
        return (float)getActiveValue() * 0.02F;
    }

    @Override
    public float hungerSuppression() {
        return (float)getActiveValue() * -0.2F;
    }
}
