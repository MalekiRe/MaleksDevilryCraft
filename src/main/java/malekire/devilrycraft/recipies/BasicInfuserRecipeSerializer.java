package malekire.devilrycraft.recipies;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import malekire.devilrycraft.magic.Vis;
import malekire.devilrycraft.magic.VisTaint;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class BasicInfuserRecipeSerializer implements RecipeSerializer<BasicInfuserRecipe> {
    public static final BasicInfuserRecipeSerializer INSTANCE = new BasicInfuserRecipeSerializer();

    public static final Identifier ID = new Identifier(MOD_ID, "basic_infuser_recipe");


    @Override
    public BasicInfuserRecipe read(Identifier id, JsonObject json) {
        BasicInfuserRecipeJsonFormat recipeJson = new Gson().fromJson(json, BasicInfuserRecipeJsonFormat.class);
        JsonArray ingredients = recipeJson.ingredients;
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : ingredients)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem)).get();
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);
        VisTaint visTaint = new VisTaint(recipeJson.vis, recipeJson.taint);

        return new BasicInfuserRecipe(myIngredients, visTaint, output, recipeJson.ticks, id);
    }

    @Override
    public BasicInfuserRecipe read(Identifier id, PacketByteBuf buf) {
        JsonArray array = new JsonArray();
        ArrayList<Ingredient> myIngredients = new ArrayList<>();
        for(JsonElement jsonElement : array)
        {
            myIngredients.add(Ingredient.fromJson(jsonElement));
        }
        ItemStack stack = buf.readItemStack();
        VisTaint visTaint = new VisTaint(buf.readDouble(), buf.readDouble());
        int ticks = buf.readInt();
        return new BasicInfuserRecipe(myIngredients, visTaint, stack, ticks, id);
    }

    @Override
    public void write(PacketByteBuf buf, BasicInfuserRecipe recipe) {
        JsonArray array = new JsonArray();
        for(int i = 0; i < recipe.ingredients.size(); i++)
        {
            recipe.ingredients.get(i).write(buf);
        }
        buf.writeItemStack(recipe.getOutput());
        buf.writeDouble(recipe.visTaint.visLevel);
        buf.writeDouble(recipe.visTaint.taintLevel);
        buf.writeInt(recipe.TICKS);
    }
}
