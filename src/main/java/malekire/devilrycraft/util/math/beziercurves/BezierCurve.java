package malekire.devilrycraft.util.math.beziercurves;

import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class BezierCurve {
    ArrayList<Point> points = new ArrayList<>();
    public void addPoint(Point point){
        points.add(point);
    }
    public double getX(double time){
        return (Math.pow(1-time, 3)*points.get(0).x) + (3*Math.pow(1-time, 2)*time*points.get(1).x) + (3*(1-time)*Math.pow(time, 2)*points.get(2).x) +
                Math.pow(time, 3)*points.get(3).x;
    }
    public double getY(double time){

        return (Math.pow(1-time, 3)*points.get(0).y) + (3*Math.pow(1-time, 2)*time*points.get(1).y) + (3*(1-time)*Math.pow(time, 2)*points.get(2).y) +
                Math.pow(time, 3)*points.get(3).y;


    }

    Vec3d GetPointOnBezierCurve(Vec3d p0, Vec3d p1, Vec3d p2, Vec3d p3, float t)
    {
        float u = 1f - t;
        float t2 = t * t;
        float u2 = u * u;
        float u3 = u2 * u;
        float t3 = t2 * t;

        return p0.multiply(u3).add(p0.multiply(3f * u2 * t)).add(p2.multiply(3f * u * t2)).add(p3.multiply(t3));
    }

}
