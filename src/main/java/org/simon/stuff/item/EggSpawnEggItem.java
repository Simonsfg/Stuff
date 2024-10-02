package org.simon.stuff.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import org.simon.stuff.Stuff;
import org.simon.stuff.entity.EggEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

public class EggSpawnEggItem extends Item {
    public static final EggSpawnEggItem EGG_SPAWN_EGG = new EggSpawnEggItem(new FabricItemSettings());

    public EggSpawnEggItem(Settings settings) {
        super(settings);
    }

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(Stuff.MOD_ID, "egg_spawn_egg"), EGG_SPAWN_EGG);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BlockPos pos = context.getBlockPos().up();
            EggEntity eggEntity = new EggEntity(EggEntity.getEntityType(), world);
            eggEntity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            world.spawnEntity(eggEntity);
            if (!context.getPlayer().isCreative()) {
                context.getStack().decrement(1);
            }
        }
        return ActionResult.success(world.isClient);
    }
}