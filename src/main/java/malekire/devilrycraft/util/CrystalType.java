package malekire.devilrycraft.util;

import com.mojang.serialization.Codec;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import net.minecraft.util.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum CrystalType implements StringIdentifiable {
    WATER_TYPE,
    FIRE_TYPE,
    EARTH_TYPE,
    AIR_TYPE,
    VIS_TYPE,
    TAINT_TYPE,
    NONE;

    public String toString() {
        return this.asString();
    }

    public String asString() {

        switch (this) {
            case NONE: return "none";
            case AIR_TYPE: return "air_type";
            case VIS_TYPE: return "vis_type";
            case TAINT_TYPE: return "taint_type";
            case FIRE_TYPE: return "fire_type";
            case EARTH_TYPE: return "earth_type";
            case WATER_TYPE: return "water_type";
            default : return "null";
        }
    }
}
