package net.mcexpanded.alchemy.station;

import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class StationScreen extends AbstractContainerScreen<StationMenu>
{
    private static final Identifier TEXTURE = Alchemy.rl("textures/gui/station/station.png");

    public StationScreen(StationMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, Component.empty());
        inventoryLabelY = 2314234;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick)
    {
        minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 67);
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
    {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }
}
