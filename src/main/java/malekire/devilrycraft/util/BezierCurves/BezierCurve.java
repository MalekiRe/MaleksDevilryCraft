package malekire.devilrycraft.util.BezierCurves;

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

}
