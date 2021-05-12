package malekire.devilrycraft.objects.blockentities.sealhelpers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.SuctionSealID;

public class SuctionSealHelper extends AbstractSealHelper {
    public SuctionSealHelper() {
        super(SuctionSealID, SuctionSealID.sealCombinations);
        typesOfStorageBlockEntities.add(Blocks.CHEST);

    }

    List<ItemEntity> itemEntities;
    public Box box;
    public static final int RANGE = 5;
    public static final int PICKUP_CHANCE = 10;
    Random random = new Random();
    BlockEntity nearbyStorageBlockEntity;
    public static final int STORAGE_DISTANCE = 2;
    public void findNearbyStorageBlockEntity() {
        Optional<BlockPos> nearestStoragePos = BlockPos.findClosest(pos, STORAGE_DISTANCE, STORAGE_DISTANCE, this::isStorageBlockEntity);
        if(nearestStoragePos.isPresent())
        {
            nearbyStorageBlockEntity = world.getBlockEntity(nearestStoragePos.get());
        }
    }
    ArrayList<Block> typesOfStorageBlockEntities = new ArrayList<>();
    public boolean isStorageBlockEntity(BlockPos pos) {
        for(Block storageCheck : typesOfStorageBlockEntities)
            if(world.getBlockState(pos).getBlock() == storageCheck)
                return true;
        return false;
    }

    @Override
    public void tick() {
        itemEntities = world.getEntitiesByType(EntityType.ITEM, box, this::isAcceptableItem);
        if(itemEntities.size() > 0)
        {
            if(nearbyStorageBlockEntity == null)
            {
                findNearbyStorageBlockEntity();
                System.out.println("Null storage block");
            }
            else
            {
                ChestBlockEntity chest = ((ChestBlockEntity)nearbyStorageBlockEntity);
                for(int i = 0; i < chest.size(); i++)
                {
                    if(!chest.isEmpty())
                    {
                        int i2 = -1;
                        boolean runOnce = true;
                        while(runOnce || (chest.getStack(i2).getItem() != itemEntities.get(0).getStack().getItem() && chest.getStack(i2).getCount() <= 64))
                        {
                            runOnce = false;
                            i2++;
                            System.out.println("checking item stack");
                        }
                        if(i2 == -1)
                            return;
                        if(chest.getStack(i2).getCount()+itemEntities.get(0).getStack().getCount() > 64)
                        {
                            int remainder = 64 - (chest.getStack(i2).getCount()+itemEntities.get(0).getStack().getCount());
                            itemEntities.get(0).getStack().decrement(remainder);
                            chest.getStack(i2).setCount(64);
                        }
                        chest.getStack(i2).increment(itemEntities.get(0).getStack().getCount());
                        itemEntities.get(0).getStack().setCount(0);
                        itemEntities.get(0).remove();

                    }
                }

            }
        }
    }
    public boolean isAcceptableItem(Entity entity) {
        return true;
    }
    @Override
    public void oneOffTick() {
        box = new Box(pos.getX()-RANGE, pos.getY()-RANGE, pos.getZ()-RANGE, pos.getX()+RANGE, pos.getY()+RANGE, pos.getZ()+RANGE);
    }


    @Override
    public AbstractSealHelper getNewInstance() {
        return new SuctionSealHelper();
    }
}
