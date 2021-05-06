package malekire.devilrycraft.common;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilrySounds {
    public static ArrayList<SoundHelper> sounds = new ArrayList<>();

    public static Identifier CHAOS_PORTAL_ID = new Identifier(MOD_ID, "chaos_portal");
    public static SoundEvent CHAOS_PORTAL = new SoundEvent(CHAOS_PORTAL_ID);

    public static Identifier CAULDRON_BUBBLING_ID = new Identifier(MOD_ID, "cauldron_bubbling");
    public static SoundEvent CAULDRON_BUBBLING = new SoundEvent(CAULDRON_BUBBLING_ID);
    static {
        add(CHAOS_PORTAL_ID, CHAOS_PORTAL);
        add(CAULDRON_BUBBLING_ID, CAULDRON_BUBBLING);
    }
    public static void registerSounds() {
        for(SoundHelper soundHelper : sounds)
            Registry.register(Registry.SOUND_EVENT, soundHelper.NAME, soundHelper.SOUND_EVENT);
    }

    public static void add(Identifier name, SoundEvent soundEvent) {
        sounds.add(new SoundHelper(name, soundEvent));
    }
}
class SoundHelper {
    final Identifier NAME;
    final SoundEvent SOUND_EVENT;
    public SoundHelper(Identifier name, SoundEvent sound_event)
    {
        NAME = name;
        SOUND_EVENT = sound_event;
    }
}