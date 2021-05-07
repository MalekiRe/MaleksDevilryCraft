package malekire.devilrycraft.common;

import malekire.devilrycraft.screen_stuff.screen_handlers.BasicInfuserScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilryScreens {
    public static final ScreenHandlerType<BasicInfuserScreenHandler> BASIC_INFUSER_SCREEN_HANDLER;

    static {
        BASIC_INFUSER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "basic_infuser"), BasicInfuserScreenHandler::new);
    }
}
