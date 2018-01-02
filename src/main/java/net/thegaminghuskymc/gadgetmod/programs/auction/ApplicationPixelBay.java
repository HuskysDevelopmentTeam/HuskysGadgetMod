package net.thegaminghuskymc.gadgetmod.programs.auction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.thegaminghuskymc.gadgetmod.Reference;
import net.thegaminghuskymc.gadgetmod.api.app.Application;
import net.thegaminghuskymc.gadgetmod.api.app.Dialog;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.*;
import net.thegaminghuskymc.gadgetmod.api.app.component.Label;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.api.utils.BankUtil;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.programs.auction.object.AuctionItem;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskAddAuction;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskBuyItem;
import net.thegaminghuskymc.gadgetmod.programs.auction.task.TaskGetAuctions;
import net.thegaminghuskymc.gadgetmod.util.TimeUtil;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class ApplicationPixelBay extends Application {

    private static final ResourceLocation MINEBAY_ASSESTS = new ResourceLocation(Reference.MOD_ID, "textures/gui/pixel_bay.png");

    private static final ItemStack EMERALD = new ItemStack(Items.EMERALD);

    private String[] categories = {"Building", "Combat", "Tools", "Food", "Materials", "Redstone", "Alchemy", "Rare", "Misc"};

    private ItemList<AuctionItem> items;

    /* Add Item Layout */
    private Layout layoutSelectItem;
    private Inventory inventory;
    private Button buttonAddNext;

    /* Set Amount and Price Layout */
    private Layout layoutAmountAndPrice;
    private NumberSelector selectorAmount;
    private NumberSelector selectorPrice;

    /* Set Duration Layout */
    private Layout layoutDuration;
    private NumberSelector selectorHours;
    private NumberSelector selectorMinutes;
    private NumberSelector selectorSeconds;

    public ApplicationPixelBay() {
        this.setDefaultWidth(330);
        this.setDefaultHeight(140);
    }

    @Override
    public void onTick() {
        super.onTick();
        AuctionManager.INSTANCE.tick();
    }

    @Override
    public void init() {

        Button btnAddItem = new Button(70, 5, "Add Item");
        btnAddItem.setSize(60, 15);
        btnAddItem.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutSelectItem));
        super.addComponent(btnAddItem);

        Button btnViewItem = new Button(135, 5, "Your Auctions");
        btnViewItem.setSize(80, 15);
        btnViewItem.setClickListener((mouseX, mouseY, mouseButton) -> {
            TaskGetAuctions task = new TaskGetAuctions(Minecraft.getMinecraft().player.getUniqueID());
            task.setCallback((nbt, success) -> {
                items.removeAll();
                for (AuctionItem item : AuctionManager.INSTANCE.getItems()) {
                    items.addItem(item);
                }
            });
            TaskManager.sendTask(task);
        });
        super.addComponent(btnViewItem);

        Label labelBalance = new Label("Balance", 295, 3);
        labelBalance.setAlignment(Label.ALIGN_RIGHT);
        super.addComponent(labelBalance);

        Label labelMoney = new Label("$" + EMERALD.getCount(), 295, 13);
        labelMoney.setAlignment(Label.ALIGN_RIGHT);
        labelMoney.setScale(1);
        labelMoney.setShadow(false);
        super.addComponent(labelMoney);

        Label labelCategories = new Label("Categories", 5, 29);
        labelCategories.setShadow(false);
        super.addComponent(labelCategories);

        ItemList<String> categories = new ItemList<>(5, 40, 70, 7);
        for (String category : this.categories) {
            categories.addItem(category);
        }
        super.addComponent(categories);

        Label labelItems = new Label("Items", 100, 29);
        labelItems.setShadow(false);
        super.addComponent(labelItems);

        items = new ItemList<>(100, 40, 180, 4);
        items.setListItemRenderer(new ListItemRenderer<AuctionItem>(20) {
            @Override
            public void render(AuctionItem e, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected) {
                if (selected) {
                    Gui.drawRect(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
                } else {
                    Gui.drawRect(x, y, x + width, y + height, Color.GRAY.getRGB());
                }

                RenderUtil.renderItem(x + 2, y + 2, e.getStack(), true);

                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(x + 24, y + 4, 0);
                    GlStateManager.scale(0.666, 0.666, 0);
                    mc.fontRenderer.drawString(e.getStack().getDisplayName(), 0, 0, Color.WHITE.getRGB(), false);
                    mc.fontRenderer.drawString(TimeUtil.getTotalRealTime(e.getTimeLeft()), 0, 11, Color.LIGHT_GRAY.getRGB(), false);
                }
                GlStateManager.popMatrix();

                String price = "$" + e.getPrice();
                mc.fontRenderer.drawString(price, x - mc.fontRenderer.getStringWidth(price) + width - 5, y + 6, Color.YELLOW.getRGB());
            }
        });
        super.addComponent(items);

        Button btnBuy = new Button(100, 127, "Buy");
        btnBuy.setSize(50, 15);
        btnBuy.setClickListener((mouseX, mouseY, mouseButton) -> {
            final Dialog.Confirmation dialog = new Dialog.Confirmation();
            dialog.setPositiveText("Buy");
            dialog.setPositiveListener((mouseX13, mouseY13, mouseButton13) -> {
                final int index = items.getSelectedIndex();
                if (index == -1) return;

                AuctionItem item = items.getItem(index);
                if (item != null) {
                    TaskBuyItem task = new TaskBuyItem(item.getId());
                    task.setCallback((nbt, success) ->
                    {
                        if (success) {
                            items.removeItem(index);
                        }
                    });
                    TaskManager.sendTask(task);
                }
            });
            dialog.setNegativeText("Cancel");
            dialog.setNegativeListener((mouseX12, mouseY12, mouseButton12) -> dialog.close());
            ApplicationPixelBay.this.openDialog(dialog);
        });
        super.addComponent(btnBuy);

        /* Select Item Layout */

        layoutSelectItem = new Layout(172, 87);
        layoutSelectItem.setTitle("Add Item");
        layoutSelectItem.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y, x + width, y + 22, Color.LIGHT_GRAY.getRGB());
            Gui.drawRect(x, y + 22, x + width, y + 23, Color.DARK_GRAY.getRGB());
            mc.fontRenderer.drawString("Select an Item...", x + 5, y + 7, Color.WHITE.getRGB(), true);
        });

        inventory = new Inventory(5, 28);
        inventory.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (inventory.getSelectedSlotIndex() != -1) {
                ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(inventory.getSelectedSlotIndex());
                if (!stack.isEmpty()) {
                    buttonAddNext.setEnabled(true);
                    selectorAmount.setMax(stack.getCount());
                    selectorAmount.setNumber(stack.getCount());
                } else {
                    buttonAddNext.setEnabled(false);
                }
            }
        });
        layoutSelectItem.addComponent(inventory);

        Button buttonAddCancel = new Button(138, 4, MINEBAY_ASSESTS, 0, 12, 8, 8);
        buttonAddCancel.setToolTip("Cancel", "Go back to main page");
        buttonAddCancel.setClickListener((mouseX, mouseY, mouseButton) -> restoreDefaultLayout());
        layoutSelectItem.addComponent(buttonAddCancel);

        buttonAddNext = new Button(154, 4, MINEBAY_ASSESTS, 16, 12, 8, 8);
        buttonAddNext.setToolTip("Next Page", "Set price and amount");
        buttonAddNext.setEnabled(false);
        buttonAddNext.setClickListener((mouseX, mouseY, mouseButton) -> {
            selectorAmount.updateButtons();
            selectorPrice.updateButtons();
            setCurrentLayout(layoutAmountAndPrice);
        });
        layoutSelectItem.addComponent(buttonAddNext);


        /* Set Amount and Price */

        layoutAmountAndPrice = new Layout(172, 87);
        layoutAmountAndPrice.setTitle("Add Item");
        layoutAmountAndPrice.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y, x + width, y + 22, Color.LIGHT_GRAY.getRGB());
            Gui.drawRect(x, y + 22, x + width, y + 23, Color.DARK_GRAY.getRGB());
            mc.fontRenderer.drawString("Set amount and price...", x + 5, y + 7, Color.WHITE.getRGB(), true);

            int offsetX = 14;
            int offsetY = 40;
            Gui.drawRect(x + offsetX, y + offsetY, x + offsetX + 38, y + offsetY + 38, Color.BLACK.getRGB());
            Gui.drawRect(x + offsetX + 1, y + offsetY + 1, x + offsetX + 37, y + offsetY + 37, Color.DARK_GRAY.getRGB());

            offsetX = 90;
            Gui.drawRect(x + offsetX, y + offsetY, x + offsetX + 38, y + offsetY + 38, Color.BLACK.getRGB());
            Gui.drawRect(x + offsetX + 1, y + offsetY + 1, x + offsetX + 37, y + offsetY + 37, Color.DARK_GRAY.getRGB());

            if (inventory.getSelectedSlotIndex() != -1) {
                ItemStack stack = mc.player.inventory.getStackInSlot(inventory.getSelectedSlotIndex());
                if (!stack.isEmpty()) {
                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(x + 17, y + 43, 0);
                        GlStateManager.scale(2, 2, 0);
                        RenderUtil.renderItem(0, 0, stack, false);
                    }
                    GlStateManager.popMatrix();
                }
            }

            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(x + 92, y + 43, 0);
                GlStateManager.scale(2, 2, 0);
                RenderUtil.renderItem(0, 0, EMERALD, false);
            }
            GlStateManager.popMatrix();
        });

        Button buttonAmountAndPriceBack = new Button(122, 4, MINEBAY_ASSESTS, 8, 12, 8, 8);
        buttonAmountAndPriceBack.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutSelectItem));
        layoutAmountAndPrice.addComponent(buttonAmountAndPriceBack);

        Button buttonAmountAndPriceCancel = new Button(138, 4, MINEBAY_ASSESTS, 0, 12, 8, 8);
        buttonAmountAndPriceCancel.setClickListener((mouseX, mouseY, mouseButton) -> restoreDefaultLayout());
        layoutAmountAndPrice.addComponent(buttonAmountAndPriceCancel);

        Button buttonAmountAndPriceNext = new Button(154, 4, MINEBAY_ASSESTS, 16, 12, 8, 8);
        buttonAmountAndPriceNext.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutDuration));
        layoutAmountAndPrice.addComponent(buttonAmountAndPriceNext);

        Label labelAmount = new Label("Amount", 16, 30);
        layoutAmountAndPrice.addComponent(labelAmount);

        selectorAmount = new NumberSelector(55, 42, 18);
        selectorAmount.setMax(64);
        layoutAmountAndPrice.addComponent(selectorAmount);

        Label labelPrice = new Label("Price", 96, 30);
        layoutAmountAndPrice.addComponent(labelPrice);

        selectorPrice = new NumberSelector(131, 42, 24);
        selectorPrice.setMax(999);
        layoutAmountAndPrice.addComponent(selectorPrice);


        /* Duration Layout */
        layoutDuration = new Layout(172, 87);
        layoutDuration.setTitle("Add Item");
        layoutDuration.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Gui.drawRect(x, y, x + width, y + 22, Color.LIGHT_GRAY.getRGB());
            Gui.drawRect(x, y + 22, x + width, y + 23, Color.DARK_GRAY.getRGB());
            mc.fontRenderer.drawString("Set duration...", x + 5, y + 7, Color.WHITE.getRGB(), true);
        });

        Button buttonDurationBack = new Button(122, 4, MINEBAY_ASSESTS, 8, 12, 8, 8);
        buttonDurationBack.setClickListener((mouseX, mouseY, mouseButton) -> setCurrentLayout(layoutAmountAndPrice));
        layoutDuration.addComponent(buttonDurationBack);

        Button buttonDurationCancel = new Button(138, 4, MINEBAY_ASSESTS, 0, 12, 8, 8);
        buttonDurationCancel.setClickListener((mouseX, mouseY, mouseButton) -> restoreDefaultLayout());
        layoutDuration.addComponent(buttonDurationCancel);

        Button buttonDurationAdd = new Button(154, 4, MINEBAY_ASSESTS, 24, 12, 8, 8);
        buttonDurationAdd.setClickListener((mouseX, mouseY, mouseButton) -> {
            final Dialog.Confirmation dialog = new Dialog.Confirmation();
            dialog.setMessageText("Are you sure you want to auction this item?");
            dialog.setPositiveText("Yes");
            dialog.setPositiveListener((mouseX1, mouseY1, mouseButton1) ->
            {
                int ticks = (int) TimeUtil.getRealTimeToTicks(selectorHours.getNumber(), selectorMinutes.getNumber(), selectorSeconds.getNumber());
                TaskAddAuction task = new TaskAddAuction(inventory.getSelectedSlotIndex(), selectorAmount.getNumber(), selectorPrice.getNumber(), ticks);
                task.setCallback((nbt, success) ->
                {
                    if (success) {
                        List<AuctionItem> auctionItems = AuctionManager.INSTANCE.getItems();
                        items.addItem(auctionItems.get(auctionItems.size() - 1));
                    }
                });
                TaskManager.sendTask(task);
                dialog.close();
                restoreDefaultLayout();
            });
            openDialog(dialog);
        });
        layoutDuration.addComponent(buttonDurationAdd);

        Label labelHours = new Label("Hrs", 45, 30);
        layoutDuration.addComponent(labelHours);

        Label labelMinutes = new Label("Mins", 76, 30);
        layoutDuration.addComponent(labelMinutes);

        Label labelSeconds = new Label("Secs", 105, 30);
        layoutDuration.addComponent(labelSeconds);

        DecimalFormat format = new DecimalFormat("00");

        selectorHours = new NumberSelector(45, 42, 20);
        selectorHours.setMax(23);
        selectorHours.setMin(0);
        selectorHours.setFormat(format);
        layoutDuration.addComponent(selectorHours);

        selectorMinutes = new NumberSelector(76, 42, 20);
        selectorMinutes.setMax(59);
        selectorMinutes.setMin(0);
        selectorMinutes.setFormat(format);
        layoutDuration.addComponent(selectorMinutes);

        selectorSeconds = new NumberSelector(107, 42, 20);
        selectorSeconds.setMax(59);
        selectorSeconds.setMin(1);
        selectorSeconds.setFormat(format);
        layoutDuration.addComponent(selectorSeconds);

        BankUtil.getBalance((nbt, success) ->
        {
            if (success) {
                labelMoney.setText("$" + Objects.requireNonNull(nbt).getInteger("balance"));
            }
        });

        TaskGetAuctions task = new TaskGetAuctions();
        task.setCallback((nbt, success) ->
        {
            items.removeAll();
            for (AuctionItem item : AuctionManager.INSTANCE.getItems()) {
                items.addItem(item);
            }
        });
        TaskManager.sendTask(task);
    }

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        Gui.drawRect(x, y, x + this.getWidth(), y + 15, Color.GRAY.getRGB());
        Gui.drawRect(x, y + 24, x + this.getWidth(), y + 25, Color.DARK_GRAY.getRGB());
        Gui.drawRect(x, y + 25, x + 95, y + this.getHeight(), Color.LIGHT_GRAY.getRGB());
        Gui.drawRect(x + 94, y + 25, x + 95, y + this.getHeight(), Color.GRAY.getRGB());

        mc.getTextureManager().bindTexture(MINEBAY_ASSESTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtil.drawRectWithTexture(x + 5, y + 6, 0, 0, 61, 11, 61, 12);
        super.render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
    }

    @Override
    public void load(NBTTagCompound tagCompound) {

    }

    @Override
    public void save(NBTTagCompound tagCompound) {

    }
}
