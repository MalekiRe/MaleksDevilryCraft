package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.util.render.DRenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.Level;

import static com.qouteall.immersive_portals.render.context_management.RenderStates.tickDelta;
import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.*;

public class SealItemTransferer extends AbstractSealHelper {
    public SealItemTransferer() {
        super(ItemTransferSealID, ItemTransferSealID.sealCombinations);
        this.isMateable = true;
    }
    public boolean isOriginMate() {
        return matePos != null;
    }
    ItemStack renderStack = new ItemStack(Items.LAPIS_BLOCK, 1);
    BlockPos offsetPos = new BlockPos(3, 3, 3);
    Vec3d origin = new Vec3d(0, 0, 0);
    @Override
    public void render(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light, int overlay) {
        if(!isOriginMate()) {
            return;
        }
        float time = (this.blockEntity.getWorld().getTime() + tickDelta)*2;
        time = (time%100)/100;
        //System.out.println((time%100)/100);
        /*
        if(this.getMate() != null) {
            Devilrycraft.LOGGER.log(Level.INFO, "rendering seal itemstack");
            Vec3d myPos = DRenderUtil.interpolatePositionsThroughTime(Vec3d.of(this.getPos()), Vec3d.of(getMate().getPos()), time);
            matrixStack.push();
            matrixStack.translate(myPos.x, myPos.y, myPos.z);
            MinecraftClient.getInstance().getItemRenderer().renderItem(renderStack, ModelTransformation.Mode.GROUND, light, 1, matrixStack, vertexConsumerProvider);
            matrixStack.pop();
        }
         */
        //Devilrycraft.LOGGER.log(Level.INFO, "rendering seal itemstack");
        System.out.println(getPos());
        System.out.println(matePos);
        offsetPos = (matePos.subtract(getPos()));
        Vec3d myPos = DRenderUtil.interpolatePositionsThroughTime(origin, Vec3d.of((offsetPos)), time);
        //System.out.println(myPos);
        matrixStack.push();
        matrixStack.translate(myPos.x, myPos.y, myPos.z);
        MinecraftClient.getInstance().getItemRenderer().renderItem(renderStack, ModelTransformation.Mode.GROUND, light, overlay, matrixStack, vertexConsumerProvider);
        matrixStack.pop();
    }

    @Override
    public void tick() {

    }

    /**
     * finds and attaches the first itemTransferable in the sealCombinations list that has the same sealSignature.
     */
    @Override
    public void oneOffTick() {


    }



    @Override
    public AbstractSealHelper getNewInstance() {
        return new SealItemTransferer();
    }
}
