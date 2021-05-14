package malekire.devilrycraft.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DevilryProperties {
    public static final IntProperty TAINTED_PERCENT;
    public static final BooleanProperty NORTH_CONNECTED;
    public static final BooleanProperty SOUTH_CONNECTED;
    public static final BooleanProperty EAST_CONNECTED;
    public static final BooleanProperty WEST_CONNECTED;
    public static final BooleanProperty UP_CONNECTED;
    public static final BooleanProperty DOWN_CONNECTED;
    public static final Set<BooleanProperty> CONNECTED_DIRECTIONS;
    public static final EnumProperty<CrystalType> FIRST_LAYER;
    public static final EnumProperty<CrystalType> SECOND_LAYER;
    public static final EnumProperty<CrystalType> THIRD_LAYER;
    public static final EnumProperty<CrystalType> FOURTH_LAYER;
    public static final IntProperty HACKY_RENDERING_CHEAT;
    static {
        TAINTED_PERCENT = IntProperty.of("tainted_percent", 1, 5);
        NORTH_CONNECTED = BooleanProperty.of("north_connected");
        SOUTH_CONNECTED = BooleanProperty.of("south_connected");
        EAST_CONNECTED = BooleanProperty.of("east_connected");
        WEST_CONNECTED = BooleanProperty.of("west_connected");
        UP_CONNECTED = BooleanProperty.of("up_connected");
        DOWN_CONNECTED = BooleanProperty.of("down_connected");
        HashSet<BooleanProperty> temp = new HashSet<>();
        setConnectedDirections(temp);
        CONNECTED_DIRECTIONS = Collections.unmodifiableSet(temp);

        FIRST_LAYER = EnumProperty.of("first_layer", CrystalType.class);
        SECOND_LAYER = EnumProperty.of("second_layer", CrystalType.class);
        THIRD_LAYER = EnumProperty.of("third_layer", CrystalType.class);
        FOURTH_LAYER = EnumProperty.of("fourth_layer", CrystalType.class);

        HACKY_RENDERING_CHEAT = IntProperty.of("hacky_rendering_cheat", 0, 10);
    }
    private static void setConnectedDirections(HashSet<BooleanProperty> temp) {
        temp.add(NORTH_CONNECTED);
        temp.add(SOUTH_CONNECTED);
        temp.add(EAST_CONNECTED);
        temp.add(WEST_CONNECTED);
        temp.add(UP_CONNECTED);
        temp.add(DOWN_CONNECTED);
    }
    public static Direction getConnectedDirection(BooleanProperty connectedDirection) throws Exception {
        switch(connectedDirection.getName())
        {
            case "north_connected" : return Direction.NORTH;
            case "south_connected" : return Direction.SOUTH;
            case "east_connected" : return Direction.EAST;
            case "west_connected" : return Direction.WEST;
            case "up_connected" : return Direction.UP;
            case "down_connected" : return Direction.DOWN;
            default:
                throw new Exception("Devilry encountered and Error, attempted to pass a BooleanProperty that is not a direction");

        }

    }
}
