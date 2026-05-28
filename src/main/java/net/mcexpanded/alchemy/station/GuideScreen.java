package net.mcexpanded.alchemy.station;

import com.mojang.blaze3d.platform.InputConstants;
import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.AlchemyConfig;
import net.mcexpanded.alchemy.alchemy.PotionEffectProperties;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitRequirement;
import net.mcexpanded.alchemy.potion.PotionData;
import net.mcexpanded.alchemy.registry.AlchemyDataAttachments;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.mcexpanded.alchemy.registry.AlchemyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.List;

public class GuideScreen extends Screen
{

    private static final Image BACKGROUND_INDEX_FIRST = new Image(Alchemy.rl("textures/gui/guide/background.png"), 420, 260);

    private static final int SOFT_TEXT_COLOR = 0xff9c897c;
    private static final int HARD_TEXT_COLOR = 0xff635040;

    int centerX;
    int centerY;

    private EditBox searchBoxReagents;
    private EditBox searchBoxPotions;

    Map<Item, List<String>> knownReagentTraits;
    List<Triple<ItemStack, Holder<MobEffect>, List<TraitRequirement>>> potionsImmutable;
    List<Triple<ItemStack, Holder<MobEffect>, List<TraitRequirement>>> potions;

    List<ItemStack> reagentsImmutable;
    List<ItemStack> reagents;

    ItemStack hoveredReagent;
    Triple<ItemStack, Holder<MobEffect>, List<TraitRequirement>> hoveredPotion;

    @Override
    protected void init()
    {
        super.init();
        centerX = width / 2;
        centerY = height / 2;

        this.searchBoxReagents = new EditBox(this.font, centerX + 23, centerY + 64, 100, 12, Component.literal("Search"));
        this.searchBoxReagents.setCentered(true);
        this.searchBoxReagents.setCanLoseFocus(true);
        this.searchBoxReagents.setTextColor(HARD_TEXT_COLOR);
        this.searchBoxReagents.setInvertHighlightedTextColor(false);
        this.searchBoxReagents.setBordered(false);
        this.searchBoxReagents.setMaxLength(26);
        this.searchBoxReagents.setResponder(this::onSearchUpdatedReagents);
        this.searchBoxReagents.setValue("");
        this.searchBoxReagents.setTextShadow(false);
        this.addRenderableWidget(searchBoxReagents);


        this.searchBoxPotions = new EditBox(this.font, centerX - 134, centerY + 64, 90, 12, Component.literal("Search"));
        this.searchBoxPotions.setCentered(true);
        this.searchBoxPotions.setCanLoseFocus(true);
        this.searchBoxPotions.setTextColor(HARD_TEXT_COLOR);
        this.searchBoxPotions.setInvertHighlightedTextColor(false);
        this.searchBoxPotions.setBordered(false);
        this.searchBoxPotions.setMaxLength(26);
        this.searchBoxPotions.setResponder(this::onSearchUpdatedPotions);
        this.searchBoxPotions.setValue("");
        this.searchBoxPotions.setTextShadow(false);
        this.addRenderableWidget(searchBoxPotions);
    }

    private void onSearchUpdatedReagents(String text)
    {
        String searchText = text.toLowerCase(Locale.ROOT);

        reagents = reagentsImmutable.stream().filter(stack ->
        {
            Item reagent = stack.getItem();

            //if name of item matches search
            if (reagent.getName(stack).getString().toLowerCase(Locale.ROOT).contains(searchText)) return true;

            //if known trait groups of reagent matches search, return true
            if (knownReagentTraits.getOrDefault(reagent, List.of()).stream().anyMatch(string -> string.contains(searchText)))
                return true;

            //if known trait name (not group) matches search
            if (AlchemyDataMaps.get(stack).traits().stream().anyMatch(trait ->
            {
                List<String> knownTraits = knownReagentTraits.getOrDefault(reagent, List.of());
                if (!knownTraits.contains(trait.value().group()) && AlchemyConfig.HIDE_REAGENT_TRAITS_UNTIL_FOUND.get()) return false;

                String s = I18n.get("alchemy.trait." + trait.getRegisteredName().replace(":", ".")).toLowerCase(Locale.ROOT);

                return s.contains(searchText);

            })) return true;


            return false;

        }).toList();

    }

    private void onSearchUpdatedPotions(String text)
    {
        String searchText = text.toLowerCase(Locale.ROOT);

        potions = potionsImmutable.stream().filter(triple ->
        {

            //if effect name matches search text
            if (I18n.get("effect." + triple.getMiddle().getRegisteredName().replace(":", "."))
                    .toLowerCase(Locale.ROOT).contains(searchText)) return true;

            //if any of the requirements group names match search text
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) return false;
            List<Holder<MobEffect>> data = player.getData(AlchemyDataAttachments.KNOWN_EFFECTS);
            if (data.contains(triple.getMiddle()))
                if (triple.getRight().stream().map(TraitRequirement::group).anyMatch(o -> o.contains(searchText)))
                    return true;

            return false;
        }).toList();

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick)
    {
        searchBoxReagents.setFocused(searchBoxReagents.mouseClicked(event, doubleClick));
        searchBoxPotions.setFocused(searchBoxPotions.mouseClicked(event, doubleClick));
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a)
    {
        super.extractRenderState(guiGraphics, mouseX, mouseY, a);

        //render background
        renderCenteredImage(guiGraphics, BACKGROUND_INDEX_FIRST, 0, 0);

        renderPotions(guiGraphics, mouseX, mouseY);
        renderReagents(guiGraphics, mouseX, mouseY);

        if (hoveredReagent != null)
        {
            guiGraphics.setTooltipForNextFrame(font, hoveredReagent, mouseX, mouseY);
            hoveredReagent = null;
        }

        if (hoveredPotion != null)
        {
            MutableComponent name = Component.translatable("effect." + hoveredPotion.getMiddle().getRegisteredName().replace(":", "."));
            guiGraphics.setTooltipForNextFrame(font, List.of(name), Optional.empty(), mouseX, mouseY);

            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) return;
            List<Holder<MobEffect>> data = player.getData(AlchemyDataAttachments.KNOWN_EFFECTS);
            boolean potionKnown = data.contains(hoveredPotion.getMiddle());

            for (int i = 0; i < hoveredPotion.getRight().size(); i++)
            {
                TraitRequirement traitRequirement = hoveredPotion.getRight().get(i);

                MutableComponent group;
                if (potionKnown)
                    group = Component.translatable("alchemy.group." + traitRequirement.group());
                else
                    group = Component.literal("???");

                //level suffix
                if (Minecraft.getInstance().hasShiftDown())
                    group.append(Component.literal(" lvl." + traitRequirement.level()));
                else if (traitRequirement.level() > 1)
                    group.append(Component.literal(" lvl." + traitRequirement.level()));


                group.withStyle(ChatFormatting.GRAY);

                guiGraphics.tooltip(font, List.of(ClientTooltipComponent.create(group.getVisualOrderText())), mouseX, mouseY + 13 * i + 17,
                        DefaultTooltipPositioner.INSTANCE, ItemStack.EMPTY.get(DataComponents.TOOLTIP_STYLE));
            }


            hoveredPotion = null;
        }
    }

    public void renderPotions(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY)
    {
        //Potions
        renderCenteredString(guiGraphics, Component.literal("Potions"), centerX - 91, centerY - 44, HARD_TEXT_COLOR, false);

        //render items
        int topLeftX = centerX - 157;
        int topLeftY = centerY - 32;
        for (int i = 0; i < Math.min(potions.size(), 24); i++)
        {
            int x = topLeftX + (i % 6) * 24;
            int y = topLeftY + (i / 6) * 24;

            if (mouseX > x - 3 && mouseX < x + 18 && mouseY > y - 3 && mouseY < y + 19)
                hoveredPotion = potions.get(i);

            guiGraphics.fill(x - 2, y - 2, x + 18, y + 18, 0xffb4a697);

            guiGraphics.item(potions.get(i).getLeft(), x, y);
        }

        //render search box
        searchBoxPotions.extractRenderState(guiGraphics, mouseX, mouseY, 0);
        guiGraphics.horizontalLine(centerX - 135, centerX - 43, centerY + 73, SOFT_TEXT_COLOR);
        renderCenteredString(guiGraphics, Component.literal("Search"), centerX - 90, centerY + 75, SOFT_TEXT_COLOR, false);
    }

    public void renderReagents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY)
    {
        //Reagents
        renderCenteredString(guiGraphics, Component.literal("Reagents"), centerX + 73, centerY - 92, HARD_TEXT_COLOR, false);

        //render items
        int topLeftX = centerX + 4;
        int topLeftY = centerY - 80;
        for (int i = 0; i < Math.min(reagents.size(), 36); i++)
        {
            int x = topLeftX + (i % 6) * 24;
            int y = topLeftY + (i / 6) * 24;

            if (mouseX > x - 3 && mouseX < x + 18 && mouseY > y - 3 && mouseY < y + 19)
                hoveredReagent = reagents.get(i);

            guiGraphics.fill(x - 2, y - 2, x + 18, y + 18, 0xffb4a697);

            guiGraphics.item(reagents.get(i), x, y);
        }

        //render search box
        searchBoxReagents.extractRenderState(guiGraphics, mouseX, mouseY, 0);
        guiGraphics.horizontalLine(centerX + 23, centerX + 121, centerY + 73, SOFT_TEXT_COLOR);
        renderCenteredString(guiGraphics, Component.literal("Search"), centerX + 73, centerY + 75, SOFT_TEXT_COLOR, false);
    }


    public void renderCenteredString(GuiGraphicsExtractor guiGraphics, Component text, int x, int y, int color, boolean shadow)
    {
        FormattedCharSequence formattedcharsequence = text.getVisualOrderText();
        guiGraphics.text(font, formattedcharsequence, x - font.width(formattedcharsequence) / 2, y, color, shadow);
    }

    public void renderCenteredImage(GuiGraphicsExtractor guiGraphics, Image image, int x, int y)
    {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, image.rl, width / 2 + x - image.sizeX / 2, height / 2 + y - image.sizeY / 2, 0, 0, image.sizeX, image.sizeY, image.sizeX, image.sizeY, image.sizeX, image.sizeY);
    }

    @Override
    public boolean keyPressed(KeyEvent event)
    {
        if (event.isEscape())
        {
            onClose();
            return true;
        }
        //close on pressing inventory key
        InputConstants.Key key = InputConstants.getKey(event);
        if (this.minecraft.options.keyInventory.isActiveAndMatches(key) && !searchBoxReagents.canConsumeInput() && !searchBoxPotions.canConsumeInput())
        {
            onClose();
            return true;
        }

        return
                this.searchBoxReagents.keyPressed(event) || this.searchBoxReagents.canConsumeInput() ||
                        this.searchBoxPotions.keyPressed(event) || this.searchBoxPotions.canConsumeInput() ||
                        super.keyPressed(event);
    }

    public GuideScreen()
    {
        super(Component.empty());

        knownReagentTraits = Minecraft.getInstance().player.getData(AlchemyDataAttachments.KNOWN_TRAITS_MAP);

        List<ItemStack> reag = new ArrayList<>();
        BuiltInRegistries.ITEM.forEach(o ->
        {
            ReagentProperties rp = AlchemyDataMaps.get(o);
            if (rp != null)
            {
                reag.add(o.getDefaultInstance());
            }
        });

        reagentsImmutable = List.copyOf(reag);
        reagents = new ArrayList<>(reag);

        List<Triple<ItemStack, Holder<MobEffect>, List<TraitRequirement>>> effects = new ArrayList<>();
        BuiltInRegistries.MOB_EFFECT.forEach(o ->
        {
            Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(o);
            PotionEffectProperties pep = AlchemyDataMaps.get(holder);
            if (pep != null)
            {
                ItemStack potion = AlchemyItems.ROUND_FLASK.toStack();

                potion.set(AlchemyDataComponents.POTION_DATA, List.of(new PotionData(holder, pep.duration(), pep.level())));

                effects.add(Triple.of(potion, holder, pep.requirements()));
            }
        });

        potionsImmutable = List.copyOf(effects);
        potions = new ArrayList<>(effects);
    }

    public record Image(Identifier rl, int sizeX, int sizeY)
    {

    }
}
