package malekire.devilrycraft.blocks.blockmodels;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;

public class DevilryCraftModelProvider implements ModelResourceProvider {
    public static final Identifier VIS_CRYSTAL_BLOCK_MODEL = new Identifier("devilry_craft:block/vis_crystal_block");
    @Override
    public UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext modelProviderContext) throws ModelProviderException {
        if(identifier.equals(VIS_CRYSTAL_BLOCK_MODEL)) {
            return new SingleCrystalModel();
        } else {
            return null;
        }
    }
}
