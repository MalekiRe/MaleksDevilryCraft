package malekire.devilrycraft.objects.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.objects.blockentities.sealhelpers.AbstractSeal;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SealBlockEntity;
import malekire.devilrycraft.objects.blockentities.sealhelpers.SealTarget;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SealMateWorldComponent implements Component {


    private final World world;

    public Map<SealTarget, BlockPos> potentialSealMates = new HashMap<>();
    public SealMateWorldComponent(World world) {
        this.world = world;
    }
    public static SealMateWorldComponent get(World world) {
        return ComponentInit.SEAL_MATE_WORLD_COMPONENT_COMPONENT_KEY.get(world);
    }
    private AbstractSeal getSealHelper(BlockPos pos) {
        return null;
    }
    public AbstractSeal findMate(AbstractSeal seal) {
        SealTarget lookingForTarget = new SealTarget(seal.id, seal.crystalCombination);
        BlockPos blockPosOfMate = potentialSealMates.get(lookingForTarget);
        if(blockPosOfMate == null)
            return null;
        world.getChunk(blockPosOfMate);
        if(world == null) {
            Devilrycraft.LOGGER.log(Level.FATAL, "WORLD IS NULL");
        }
        if(world.getBlockEntity(blockPosOfMate) == null) {
            Devilrycraft.LOGGER.log(Level.ERROR, "TRIED LOADING BLOCKPOS OF SEALMATE WHERE THERE WAS NO SEAL MATE");
        }
        return ((SealBlockEntity)world.getBlockEntity(blockPosOfMate)).getSeal();
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
        ListTag list = tag.getList("seal_mate_list", NbtType.COMPOUND);
        Map<SealTarget, BlockPos> stuff = list.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).collect(Collectors.toMap(
                compoundTag -> {
                    SealTarget target = new SealTarget();
                    target.fromTag(compoundTag.getCompound("seal_target"));
                    return target;
                },
                compoundTag -> {
                    BlockPos blockPos = BlockPos.CODEC.decode(NbtOps.INSTANCE, compoundTag.get("block_pos")).getOrThrow(false, (string) -> {}).getFirst();
                    return blockPos;
                }
        ));

        potentialSealMates.putAll(stuff);
    }

    /**
     * Writes this component's properties to a {@link CompoundTag}.
     *
     * @param tag a {@code CompoundTag} on which to write this component's serializable data
     */
    @Override
    public void writeToNbt(CompoundTag tag) {
        ListTag listTag;
        listTag = potentialSealMates.entrySet().stream().parallel().map(sealTargetBlockPosEntry -> {
            CompoundTag mappingTag = new CompoundTag();
            mappingTag.put("seal_target", sealTargetBlockPosEntry.getKey().toTag(new CompoundTag()));
            mappingTag.put("block_pos", BlockPos.CODEC.encode(sealTargetBlockPosEntry.getValue(), NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).getOrThrow(false, (string) -> {}));
            return mappingTag;
        }).collect(Collectors.toCollection(ListTag::new));
        tag.put("seal_mate_list", listTag);

    }
}
