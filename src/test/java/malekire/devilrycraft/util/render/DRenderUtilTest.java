package malekire.devilrycraft.util.render;

import net.minecraft.util.math.Vec3d;
import org.junit.Test;
import org.lwjgl.system.CallbackI;

import static malekire.devilrycraft.util.render.DRenderUtil.interpolatePositionsThroughTime;
import static org.junit.Assert.*;
public class DRenderUtilTest {

    @Test
    public void Test() {
        Vec3d origin = new Vec3d(0, 0, 0);
        Vec3d dest = new Vec3d(1, 1, 1);
        Vec3d output = interpolatePositionsThroughTime(origin, dest, 0.5F);
        Vec3d expectedOutput = new Vec3d(0.5, 0.5, 0.5);
        //assertEquals(origin, dest);
        assertEquals(output, expectedOutput);
        assertEquals(interpolatePositionsThroughTime(origin, new Vec3d(4, 4, 4), 0.5F), new Vec3d(2, 2, 2));
    }
}
