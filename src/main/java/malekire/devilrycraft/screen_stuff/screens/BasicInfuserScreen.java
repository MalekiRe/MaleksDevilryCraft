package malekire.devilrycraft.screen_stuff.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import malekire.devilrycraft.Devilrycraft;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class BasicInfuserScreen extends HandledScreen<ScreenHandler> {
    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final int DOWN_OFFSET_GRAPHIC = 50;
    private static final Identifier BASIC_INFUSER_GUI = new Identifier(MOD_ID, "textures/gui/basic_infuser_gui.png");
    public BasicInfuserScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight += DOWN_OFFSET_GRAPHIC;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(BASIC_INFUSER_GUI);
        int x = (width - backgroundWidth) / 2;
        int y = (height - (backgroundHeight)) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        //titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2 - 50;
        //titleY = (20);
        playerInventoryTitleY += DOWN_OFFSET_GRAPHIC - 5;

    }
}