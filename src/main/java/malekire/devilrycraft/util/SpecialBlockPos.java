package malekire.devilrycraft.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class SpecialBlockPos extends BlockPos {
    public SpecialBlockPos(int i, int j, int k) {
        super(i, j, k);
    }

    public SpecialBlockPos(double d, double e, double f) {
        super(d, e, f);
    }

    public SpecialBlockPos(Vec3d pos) {
        super(pos);
    }

    public SpecialBlockPos(Position pos) {
        super(pos);
    }

    public SpecialBlockPos(Vec3i pos) {
        super(pos);
    }

    public void setY(int x)
    {
        this.setY(x);
    }
}
