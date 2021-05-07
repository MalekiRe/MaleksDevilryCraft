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
