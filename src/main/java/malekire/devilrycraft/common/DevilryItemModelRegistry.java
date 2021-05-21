package malekire.devilrycraft.common;

import malekire.devilrycraft.objects.items.SealWrangler;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class DevilryItemModelRegistry {
    private static void registerSealWranglerModel() {
        FabricModelPredicateProviderRegistry.register(DevilryItems.SEAL_WRANGLER, new Identifier(SealWrangler.ACTIVE), (itemStack, clientWorld, livingEntity) -> {
            if(livingEntity == null) {
                return 0.0F;
            }
            return itemStack.getOrCreateTag().getBoolean(SealWrangler.ACTIVE) ? 1.0F : 0.0F;
        });
        FabricModelPredicateProviderRegistry.register(DevilryItems.SEAL_WRANGLER, new Identifier(SealWrangler.CHEST_MODE), (itemStack, clientWorld, livingEntity) -> {
            if(livingEntity == null) {
                return 0.0F;
            }
            return itemStack.getOrCreateTag().getBoolean(SealWrangler.CHEST_MODE) ? 1.0F : 0.0F;
        });
    }

    public static void registerItemModels() {
        registerSealWranglerModel();
    }
}
