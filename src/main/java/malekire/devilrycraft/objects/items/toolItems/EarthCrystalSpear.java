package malekire.devilrycraft.objects.items.toolItems;

import com.mojang.datafixers.types.templates.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.util.math.Box;

import javax.swing.*;

public class EarthCrystalSpear extends SwordItem implements Vanishable {
    public EarthCrystalSpear(DevilryCrystalSpearGroup instance, int i, float v, Settings settings){
        super(new DevilryCrystalSpearGroup(), 8, -1.4F, settings);
    }
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }
    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 16 && !(i >= 128)) {
                long posX = (long) playerEntity.getX();
                long posY = (long) playerEntity.getY();
                long posZ = (long) playerEntity.getZ();
                Direction facing = playerEntity.getHorizontalFacing();

//                Box upFacing = new Box(posX-1, posY+2, posZ-1, posX+1, posY+4, posZ+1);
                Box southFacing = new Box(posX, posY, posZ, posX-2, posY+2, posZ+2);
                Box northFacing = new Box(posX-2, posY, posZ-2, posX, posY+2, posZ-4);
                Box eastFacing = new Box(posX, posY, posZ-2, posX+2, posY+2, posZ);
                Box westFacing = new Box(posX-2, posY, posZ, posX-4, posY+2, posZ-2);
//                Box downFacing = new Box(posX-1, posY-1, posZ-1, posX+1, posY-3, posZ+1);

                if(facing == Direction.SOUTH){
                    for (int boxx = (int) southFacing.minX; boxx <= southFacing.maxX; ++boxx) {
                        for (int boxy = (int) southFacing.minY; boxy <= southFacing.maxY; ++boxy) {
                            for (int boxz = (int) southFacing.minZ; boxz <= southFacing.maxZ; ++boxz) {
                                BlockPos blockPos = new BlockPos(boxx, boxy, boxz);
                                Block block = world.getBlockState(blockPos).getBlock();
                                if(block != Blocks.BEDROCK || block != Blocks.COMMAND_BLOCK || block != Blocks.REPEATING_COMMAND_BLOCK || block != Blocks.CHAIN_COMMAND_BLOCK || block != Blocks.END_PORTAL_FRAME || block != Blocks.END_PORTAL || block != Blocks.END_GATEWAY || block !=Blocks.NETHER_PORTAL) {



                                    Item item = block.asItem();
                                    playerEntity.dropItem(item);
                                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }else if(facing == Direction.NORTH){
                    for (int boxx = (int) northFacing.minX; boxx <= northFacing.maxX; ++boxx) {
                        for (int boxy = (int) northFacing.minY; boxy <= northFacing.maxY; ++boxy) {
                            for (int boxz = (int) northFacing.minZ; boxz <= northFacing.maxZ; ++boxz) {
                                BlockPos blockPos = new BlockPos(boxx, boxy, boxz);
                                Block block = world.getBlockState(blockPos).getBlock();
                                if(block != Blocks.BEDROCK || block != Blocks.COMMAND_BLOCK || block != Blocks.REPEATING_COMMAND_BLOCK || block != Blocks.CHAIN_COMMAND_BLOCK || block != Blocks.END_PORTAL_FRAME || block != Blocks.END_PORTAL || block != Blocks.END_GATEWAY || block !=Blocks.NETHER_PORTAL) {



                                    Item item = block.asItem();
                                    playerEntity.dropItem(item);
                                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }else if(facing == Direction.EAST){
                    for (int boxx = (int) eastFacing.minX; boxx <= eastFacing.maxX; ++boxx) {
                        for (int boxy = (int) eastFacing.minY; boxy <= eastFacing.maxY; ++boxy) {
                            for (int boxz = (int) eastFacing.minZ; boxz <= eastFacing.maxZ; ++boxz) {
                                BlockPos blockPos = new BlockPos(boxx, boxy, boxz);
                                Block block = world.getBlockState(blockPos).getBlock();
                                if(block != Blocks.BEDROCK || block != Blocks.COMMAND_BLOCK || block != Blocks.REPEATING_COMMAND_BLOCK || block != Blocks.CHAIN_COMMAND_BLOCK || block != Blocks.END_PORTAL_FRAME || block != Blocks.END_PORTAL || block != Blocks.END_GATEWAY || block !=Blocks.NETHER_PORTAL) {



                                    Item item = block.asItem();
                                    playerEntity.dropItem(item);
                                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }else if(facing == Direction.WEST){
                    for (int boxx = (int) westFacing.minX; boxx <= westFacing.maxX; ++boxx) {
                        for (int boxy = (int) westFacing.minY; boxy <= westFacing.maxY; ++boxy) {
                            for (int boxz = (int) westFacing.minZ; boxz <= westFacing.maxZ; ++boxz) {
                                BlockPos blockPos = new BlockPos(boxx, boxy, boxz);
                                Block block = world.getBlockState(blockPos).getBlock();
                                if(block != Blocks.BEDROCK || block != Blocks.COMMAND_BLOCK || block != Blocks.REPEATING_COMMAND_BLOCK || block != Blocks.CHAIN_COMMAND_BLOCK || block != Blocks.END_PORTAL_FRAME || block != Blocks.END_PORTAL || block != Blocks.END_GATEWAY || block !=Blocks.NETHER_PORTAL) {



                                    Item item = block.asItem();
                                    playerEntity.dropItem(item);
                                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

