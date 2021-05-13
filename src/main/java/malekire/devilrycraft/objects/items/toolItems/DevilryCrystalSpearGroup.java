package malekire.devilrycraft.objects.items.toolItems;

import malekire.devilrycraft.common.DevilryItems;
import malekire.devilrycraft.util.CrystalType;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class DevilryCrystalSpearGroup implements ToolMaterial {

    public static final DevilryCrystalSpearGroup INSTANCE = new DevilryCrystalSpearGroup();

    @Override
    public int getDurability() {
        return 500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0;
    }

    @Override
    public float getAttackDamage() {
        return 3.0F;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(DevilryItems.VIS_FLASK);
    }

}

