package malekire.devilrycraft.common;

import malekire.devilrycraft.recipies.BasicInfuserRecipeSerializer;
import malekire.devilrycraft.recipies.Type;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class DevilryRecipes {

    public static void registerRecipies() {
        Registry.register(Registry.RECIPE_SERIALIZER, BasicInfuserRecipeSerializer.ID, BasicInfuserRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, "basic_infuser_recipe"), Type.INSTANCE);
    }
}
