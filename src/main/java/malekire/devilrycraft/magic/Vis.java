package malekire.devilrycraft.magic;

public interface Vis {
    double GetLevel(VisType type);
    boolean Insert(VisType type, double amount, Vis vis);
    boolean Extract(VisType type, double amount, Vis vis);
    boolean IsEmpty(VisType type);
    boolean IsFull(VisType type);
    boolean CanContainVisType(VisType type);
    double ExtractionRate(VisType type);
    double InsertionRate(VisType type);
    boolean IsFull();
    double MaxLevel(VisType type);

    double Insert(VisType type, double amount);
    double Extract(VisType type, double amount);

    boolean CanInsert(VisType type, double amount);

}
