package malekire.devilrycraft.recipies;

import malekire.devilrycraft.blockentities.BasicInfuserBlockEntity;
import malekire.devilrycraft.inventory.BasicInfuserInventory;
import malekire.devilrycraft.vis_system.VisTaint;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BasicInfuserRecipe implements Recipe<BasicInfuserInventory> {
    public final ArrayList<Ingredient> ingredients;
    public final VisTaint visTaint;
    public final ItemStack result;
    public final int TICKS;
    private final Identifier id;

    public BasicInfuserRecipe(ArrayList<Ingredient> ingredients, VisTaint visTaint, ItemStack result, int ticks, Identifier id) {
        this.ingredients = ingredients;
        this.visTaint = visTaint;
        this.result = result;
        this.TICKS = ticks;
        this.id = id;
    }

    @Override
    public boolean matches(BasicInfuserInventory inv, World world) {
        for(int i = 0; i < inv.size(); i++)
        {
            if(!ingredients.get(i).test(inv.getStack(i)))
            {
                return false;
            }
        }
        if(inv.getVis() < visTaint.visLevel || inv.getTaint() < visTaint.taintLevel)
        {
            return false;
        }


        return true;
    }

    @Override
    public ItemStack craft(BasicInfuserInventory inv) {
        ((BasicInfuserBlockEntity)inv).removeTaint(visTaint.taintLevel);
        ((BasicInfuserBlockEntity)inv).removeVis(visTaint.visLevel);
        return getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BasicInfuserRecipeSerializer.INSTANCE;
    }


    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
}
