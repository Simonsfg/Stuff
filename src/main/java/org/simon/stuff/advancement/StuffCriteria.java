package org.simon.stuff.advancement;

import org.simon.stuff.Stuff;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

public interface StuffCriteria {
    DrugEffectsChangedCriterion DRUG_EFFECTS_CHANGED = register("drug_effects_changed", new DrugEffectsChangedCriterion());

    private static <T extends Criterion<?>> T register(String id, T criterion) {
        return Criteria.register(Stuff.id(id).toString(), criterion);
    }

    static void bootstrap() { }
}
