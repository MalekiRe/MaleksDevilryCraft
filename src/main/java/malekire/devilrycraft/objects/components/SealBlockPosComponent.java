package malekire.devilrycraft.objects.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SealBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.math.BlockPos;

public class SealBlockPosComponent implements BlockPosComponent, AutoSyncedComponent {
    private BlockPos blockPos;
    private BlockPos matePos;
    private SealBlockEntity sealBlockEntity;
    @Override
    public BlockPos getBlockPos() {
        return ComponentInit.SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY.get(sealBlockEntity).blockPos;
    }


    @Override
    public void setBlockPos(BlockPos blockPos) {
        ComponentInit.SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY.get(sealBlockEntity).blockPos = blockPos;
    }

    @Override
    public BlockPos getMateBlockPos() {
        return ComponentInit.SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY.get(sealBlockEntity).matePos;
    }

    @Override
    public void setMateBlockPos(BlockPos blockPos) {
        ComponentInit.SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY.get(sealBlockEntity).matePos = blockPos;
    }

    public SealBlockPosComponent(SealBlockEntity sealBlockEntity) {
        this.sealBlockEntity = sealBlockEntity;
        ComponentInit.SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY.get(sealBlockEntity).setBlockPos(sealBlockEntity.getPos());
        if(sealBlockEntity.getSeal().isMateable) {
            if(sealBlockEntity.getSeal().hasMate) {
                ComponentInit.SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY.get(sealBlockEntity).setMateBlockPos(sealBlockEntity.getSeal().matePos);
            }
        }
    }

    /**
     * Reads this component's properties from a {@link CompoundTag}.
     *
     * @param tag a {@code CompoundTag} on which this component's serializable data has been written
     * @implNote implementations should not assert that the data written on the tag corresponds to any
     * specific scheme, as saved data is susceptible to external tempering, and may come from an earlier
     * version.
     */
    @Override
    public void readFromNbt(CompoundTag tag) {
        this.blockPos = BlockPos.CODEC.decode(NbtOps.INSTANCE, tag.get("block_pos")).getOrThrow(false, (string) -> {}).getFirst();
        this.matePos = BlockPos.CODEC.decode(NbtOps.INSTANCE, tag.get("mate_pos")).getOrThrow(false, (string) -> {}).getFirst();
    }

    /**
     * Writes this component's properties to a {@link CompoundTag}.
     *
     * @param tag a {@code CompoundTag} on which to write this component's serializable data
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.put("block_pos", BlockPos.CODEC.encode(this.blockPos, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {}));
        tag.put("mate_pos", BlockPos.CODEC.encode(this.matePos, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {}));
    }
}
