package malekire.devilrycraft.magic;

public interface Vis {
    double GetLevel(VisType type);
    boolean Insert(VisType type, double amount);
    boolean Extract(VisType type, double amount);
    boolean IsEmpty(VisType type);
    boolean IsFull(VisType type);
    boolean CanContainVisType(VisType type);
    double ExtractionRate(VisType type);
    double InsertionRate(VisType type);

}
