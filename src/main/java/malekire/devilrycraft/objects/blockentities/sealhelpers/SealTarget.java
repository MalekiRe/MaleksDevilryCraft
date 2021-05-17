package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.util.CrystalType;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class SealTarget {
    private Identifier id;
    private ArrayList<CrystalType> crystalCombination;

    public SealTarget(Identifier id, ArrayList<CrystalType> crystalCombination) {
        this.id = id;
        this.crystalCombination = crystalCombination;
    }
    public SealTarget() {
    }

    public static SealTarget of(AbstractSeal sealHelper) {
        return new SealTarget(sealHelper.id, sealHelper.crystalCombination);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SealTarget)) return false;
        SealTarget that = (SealTarget) o;
        return Objects.equals(id, that.id) && Objects.equals(crystalCombination, that.crystalCombination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, crystalCombination);
    }
    public void fromTag(CompoundTag tag) {
        this.id = new Identifier(tag.getString("seal_id"));
        ListTag list = tag.getList("crystal_combos", NbtType.STRING);
        crystalCombination = list.stream()
                .filter(StringTag.class::isInstance)
                .map(StringTag.class::cast)
                .map(StringTag::asString)
                .map(CrystalType::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("seal_id", this.id.toString());
        ListTag listTag = crystalCombination.stream()
                .map(CrystalType::name)
                .map(StringTag::of)
                .collect(Collectors.toCollection(ListTag::new));
        tag.put("crystal_combos", listTag);
        return tag;
    }
}
