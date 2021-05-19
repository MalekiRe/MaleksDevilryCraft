package malekire.devilrycraft.common;

import malekire.devilrycraft.objects.items.SealWrangler;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;

public class DevilryItemModelRegistry {
    private static void registerSealWranglerModel() {
        FabricModelPredicateProviderRegistry.register(DevilryItems.SEAL_WRANGLER, SealWrangler.ACTIVE, (itemStack, clientWorld, livingEntity) -> {

            if(!itemStack.getOrCreateTag().contains(SealWrangler.ACTIVE.toString())
                    || livingEntity == null) {
                return 0.0F;
            }
            return itemStack.getOrCreateTag().getBoolean(SealWrangler.ACTIVE.toString()) ? 1.0F : 0.0F;
        });
    }

    public static void registerItemModels() {
        registerSealWranglerModel();
    }
}
