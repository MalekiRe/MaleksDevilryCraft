package malekire.devilrycraft.objects.blockentities.seals;

import malekire.devilrycraft.objects.items.SealWrangler;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static malekire.devilrycraft.objects.blockentities.seals.SealUtilities.DestroySealID;

public class DestroySeal extends AbstractSeal{
    public DestroySeal() {
        super(DestroySealID, DestroySealID.sealCombinations);
    }


    /**
     * Override and implement any custom rendering for the seal.
     *
     * @param vertexConsumerProvider
     * @param matrixStack
     * @param light
     * @param overlay
     */
    @Override
    public void render(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light, int overlay) {

    }
    public static int DESTROY_TICKS = 20;
    public int ticks = 0;
    /**
     * Override and implement any functions you want to run every tick.
     * Is called after oneOffTick()
     */
    public BlockPos targetBlock;
    public void normalModeSealWranglerFunction(ItemStack itemStack) {
        targetBlock = SealWrangler.getPosFromItemStack(itemStack, "selected_block_pos");
    }
    public void DestroyBlock() {
        if(targetBlock == null)
            return;
        getWorld().breakBlock(targetBlock, true);
    }
    @Override
    public void tick() {
        if(ticks == 20) {
            DestroyBlock();
            ticks = 0;
        }
        ticks++;
    }

    /**
     * Override and implement any functions you want to run once, when a seal is finished/created in {@link SealBlockEntity}.
     */
    @Override
    public void oneOffTick() {

    }

    /**
     * The function you override with a new instance of your class.
     *
     * @return A new instance of your class
     */
    @Override
    protected AbstractSeal getNewInstance() {
        return new DestroySeal();
    }
}
