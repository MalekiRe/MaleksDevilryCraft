package malekire.devilrycraft.objects.entities.entityrenderers;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;

public class TaintSpearPearlEntityRenderer extends FlyingItemEntityRenderer {
    public TaintSpearPearlEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, ItemRenderer itemRenderer){
        super(entityRenderDispatcher, itemRenderer);
    }
}
