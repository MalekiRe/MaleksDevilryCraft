package malekire.devilrycraft.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class DevilryProperties {
    public static final IntProperty TAINTED_PERCENT;
    public static final BooleanProperty NORTH_CONNECTED;
    public static final BooleanProperty SOUTH_CONNECTED;
    public static final BooleanProperty EAST_CONNECTED;
    public static final BooleanProperty WEST_CONNECTED;
    public static final BooleanProperty UP_CONNECTED;
    public static final BooleanProperty DOWN_CONNECTED;

    static {
        TAINTED_PERCENT = IntProperty.of("tainted_percent", 1, 5);
        NORTH_CONNECTED = BooleanProperty.of("north_connected");
        SOUTH_CONNECTED = BooleanProperty.of("north_connected");
        EAST_CONNECTED = BooleanProperty.of("north_connected");
        WEST_CONNECTED = BooleanProperty.of("north_connected");
        UP_CONNECTED = BooleanProperty.of("north_connected");
        DOWN_CONNECTED = BooleanProperty.of("north_connected");
    }
}
