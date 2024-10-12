package org.simon.stuff.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import org.simon.stuff.item.StuffItems;

public class CannabisPlantBlock extends CropBlock {
    public static final MapCodec<CannabisPlantBlock> CODEC = createCodec(CannabisPlantBlock::new);
    public static final int MAX_AGE = 15;

    private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);

    public static final BooleanProperty GROWING = BooleanProperty.of("growing");
    public static final BooleanProperty NATURAL = BooleanProperty.of("natural");

    public static final IntProperty AGE = Properties.AGE_15;

    public CannabisPlantBlock(Settings settings) {
        super(settings.ticksRandomly().nonOpaque());
        setDefaultState(getDefaultState().with(NATURAL, false));
    }

    @Override
    public MapCodec<? extends CannabisPlantBlock> getCodec() {
        return CODEC;
    }

    public BlockState getStateForHeight(int y) {
        return getDefaultState();
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty(), GROWING, NATURAL);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    public int getMaxHeight() {
        return 3;
    }

    protected float getRandomGrowthChance() {
        return 0.12F;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return StuffItems.CANNABIS_SEEDS;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos);
    }

    @Override
    public final boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(NATURAL)) {
            BlockState floor = world.getBlockState(pos.down());
            return floor.isOf(this) || floor.isOf(Blocks.GRASS_BLOCK) || floor.isIn(BlockTags.DIRT);
        }
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9 && random.nextFloat() < getRandomGrowthChance()) {
            grow(world, random, pos, state);
        }
    }

    protected int getPlantSize(WorldView world, BlockPos pos) {
        int plantSize = 1;
        BlockPos checkPos = pos.down();
        while (world.getBlockState(checkPos).isOf(this)) {
            plantSize++;
            checkPos = checkPos.down();
        }
        return plantSize;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int currentAge = state.get(getAgeProperty());
        int maxAge = getMaxAge();

        if (currentAge < maxAge) {
            int newAge = Math.min(currentAge + 1 + world.random.nextInt(2), maxAge);
            world.setBlockState(pos, state.with(getAgeProperty(), newAge), Block.NOTIFY_ALL);
            
            if (newAge == maxAge) {
                growUpwards(world, pos, state);
            }
        } else {
            growUpwards(world, pos, state);
        }
    }

    private void growUpwards(ServerWorld world, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.up();
        if (getPlantSize(world, pos) < getMaxHeight()) {
            if (!world.isAir(abovePos)) {
                world.setBlockState(pos, getDefaultState().with(GROWING, false).with(NATURAL, state.get(NATURAL)).with(getAgeProperty(), getMaxAge() - 1), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(abovePos, getDefaultState().with(NATURAL, true), Block.NOTIFY_ALL);
            }
        } else {
            world.setBlockState(pos, getDefaultState().with(GROWING, false).with(NATURAL, state.get(NATURAL)).with(getAgeProperty(), getMaxAge()), Block.NOTIFY_ALL);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            breakEntirePlant(world, pos);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    private void breakEntirePlant(World world, BlockPos pos) {
        // Break blocks above
        BlockPos above = pos.up();
        while (world.getBlockState(above).isOf(this)) {
            world.breakBlock(above, true);
            above = above.up();
        }

        // Break blocks below
        BlockPos below = pos.down();
        while (world.getBlockState(below).isOf(this)) {
            world.breakBlock(below, true);
            below = below.down();
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            return state.with(GROWING, neighborState.isAir() && getPlantSize(world, pos) < getMaxHeight());
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
