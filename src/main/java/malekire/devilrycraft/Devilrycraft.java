package malekire.devilrycraft;

import malekire.devilrycraft.common.generation.DevilryOreGeneration;
import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import malekire.devilrycraft.common.*;
import malekire.devilrycraft.objects.entities.SmallDirectionalLightningEntity;
import malekire.devilrycraft.common.DevilryFluidRegistry;
import malekire.devilrycraft.screen_stuff.screen_handlers.BasicInfuserScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Devilrycraft implements ModInitializer {
    public static final String MOD_ID = "devilry_craft";
    public static final Logger LOGGER = LogManager.getLogger("Devilrycraft");



    public static final ScreenHandlerType<BasicInfuserScreenHandler> BASIC_INFUSER_SCREEN_HANDLER;
    public static final Identifier BASIC_INFUSER_GUI;
    static {

        BASIC_INFUSER_GUI = new Identifier(MOD_ID, "textures/gui/basic_infuser_gui.png");
        BASIC_INFUSER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "basic_infuser"), BasicInfuserScreenHandler::new);
    }




    public static final EntityType<SmallDirectionalLightningEntity> SMALL_DIRECTIONAL_LIGHTNING_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("devilry_craft", "small_directional_lightning"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SmallDirectionalLightningEntity::new).dimensions(EntityDimensions.fixed(0f, 0f)).trackRangeBlocks(16).trackedUpdateRate(Integer.MAX_VALUE).build()
    );
    @Override
    public void onInitialize() {
        DevilryOreGeneration.RegisterFeatures();
        DevilryTreeGeneration.RegisterFeatures();
        DevilryRecipes.registerRecipies();
        DevilryFluidRegistry.registerFluids();
        DevilryBlockEntities.registerBlockEntities();
        DevilrySounds.registerSounds();
        DevilryBlocks.registerBlocks();
        DevilryItems.registerItems();
        DevilryBlockItems.registerBlockItems();
    }






}
