package malekire.devilrycraft.util.portalutil;

import com.qouteall.immersive_portals.portal.GeometryPortalShape;
import com.qouteall.immersive_portals.portal.Portal;
import malekire.devilrycraft.util.math.MathRef;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static malekire.devilrycraft.Devilrycraft.LOGGER;
import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class PortalFunctionUtil {
    public static Vec3d offsetFromFacing(Vec3d original, Direction facing, float visualAdjustment) {
        return original.subtract(Vec3d.of(facing.getVector()).multiply(visualAdjustment));
    }
    public static void setSize(Portal portal, double width, double height){
        portal.width = width;
        portal.height = height;
    }
    public static boolean makeRoundPortal(Portal portal, int numTriangles) {
        GeometryPortalShape shape = new GeometryPortalShape();
        shape.triangles = IntStream.range(0, numTriangles)
                .mapToObj(i -> new GeometryPortalShape.TriangleInPlane(
                        0.0, 0.0,
                        portal.width * 0.5 * Math.cos(MathRef.TAU * ((double) i) / ((double) numTriangles)),
                        portal.height * 0.5 * Math.sin(MathRef.TAU * ((double) i) / ((double) numTriangles)),
                        portal.width * 0.5 * Math.cos(MathRef.TAU * ((double) (i + 1)) / ((double) numTriangles)),
                        portal.height * 0.5 * Math.sin(MathRef.TAU * ((double) (i + 1)) / ((double) numTriangles))
                )).collect(Collectors.toList());
        portal.specialShape = shape;
        //portal.portalTag = "portal1";
        portal.cullableXStart = portal.cullableXEnd = portal.cullableYStart = portal.cullableYEnd = 0;
        if(!portal.isPortalValid()) {
            portal.specialShape = null;
            return false;
        }
            return true;
    }
}
