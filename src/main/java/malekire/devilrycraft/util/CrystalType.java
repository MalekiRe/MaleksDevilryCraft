package malekire.devilrycraft.util;

import com.mojang.serialization.Codec;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import net.minecraft.util.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum CrystalType implements StringIdentifiable {
    WATER_TYPE("water_type"),
    FIRE_TYPE("fire_type"),
    EARTH_TYPE("earth_type"),
    AIR_TYPE("air_type"),
    VIS_TYPE("vis_type"),
    TAINT_TYPE("taint_type"),
    NONE("none");


    private final String name;

    CrystalType(String name) {
        this.name = name;
    }





    public String toString() {
        return this.asString();
    }

    public String asString() {
        return name;
    }


}
