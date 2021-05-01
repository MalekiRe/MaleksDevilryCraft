package malekire.devilrycraft.vis_system;

public class VisTaint {
    public double taintLevel;
    public double visLevel;

    public static VisTaint EMPTY = new VisTaint(0, 0);

    public VisTaint(double visLevel, double taintLevel) {
        this.taintLevel = taintLevel;
        this.visLevel = visLevel;
    }

}
