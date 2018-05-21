package net.thegaminghuskymc.gadgetmod.api.app.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.thegaminghuskymc.gadgetmod.api.app.Component;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.listener.ChangeListener;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public abstract class ComboBox<T> extends Component {
    protected T value;
    private boolean hovered;
    protected int width = 80;
    protected int height = 14;
    protected Layout layout;
    private ItemRenderer<T> itemRenderer;
    private ChangeListener<T> changeListener;
    private boolean opened = false;

    public ComboBox(int left, int top) {
        super(left, top);
    }

    public ComboBox(int left, int top, int width) {
        super(left, top);
        this.width = width;
    }

    @Override
    public void handleTick() {
        super.handleTick();
        if (opened && !BaseDevice.getSystem().hasContext()) {
            opened = false;
        }
    }

    @Override
    public void init(Layout layout) {
        this.layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> Gui.drawRect(x, y, x + width, y + height, Color.GRAY.getRGB()));
    }

    @Override
    public void render(BaseDevice laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(Component.COMPONENTS_GUI);

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);

            this.hovered = isInside(mouseX, mouseY) && windowActive;
            int i = this.getHoverState(this.hovered);
            int xOffset = width - height;

            /* Corners */
            RenderUtil.drawRectWithTexture(xPosition + xOffset, yPosition, 96 + i * 5, 12, 2, 2, 2, 2);
            RenderUtil.drawRectWithTexture(xPosition + height - 2 + xOffset, yPosition, 99 + i * 5, 12, 2, 2, 2, 2);
            RenderUtil.drawRectWithTexture(xPosition + height - 2 + xOffset, yPosition + height - 2, 99 + i * 5, 15, 2, 2, 2, 2);
            RenderUtil.drawRectWithTexture(xPosition + xOffset, yPosition + height - 2, 96 + i * 5, 15, 2, 2, 2, 2);

            /* Middles */
            RenderUtil.drawRectWithTexture(xPosition + 2 + xOffset, yPosition, 98 + i * 5, 12, height - 4, 2, 1, 2);
            RenderUtil.drawRectWithTexture(xPosition + height - 2 + xOffset, yPosition + 2, 99 + i * 5, 14, 2, height - 4, 2, 1);
            RenderUtil.drawRectWithTexture(xPosition + 2 + xOffset, yPosition + height - 2, 98 + i * 5, 15, height - 4, 2, 1, 2);
            RenderUtil.drawRectWithTexture(xPosition + xOffset, yPosition + 2, 96 + i * 5, 14, 2, height - 4, 2, 1);

            /* Center */
            RenderUtil.drawRectWithTexture(xPosition + 2 + xOffset, yPosition + 2, 98 + i * 5, 14, height - 4, height - 4, 1, 1);

            /* Icons */
            RenderUtil.drawRectWithTexture(xPosition + xOffset + 3, yPosition + 5, 111, 12, 8, 5, 8, 5);

            /* Box */
            drawHorizontalLine(xPosition, xPosition + xOffset, yPosition, Color.BLACK.getRGB());
            drawHorizontalLine(xPosition, xPosition + xOffset, yPosition + height - 1, Color.BLACK.getRGB());
            drawVerticalLine(xPosition, yPosition, yPosition + height - 1, Color.BLACK.getRGB());
            drawRect(xPosition + 1, yPosition + 1, xPosition + xOffset, yPosition + height - 1, Color.DARK_GRAY.getRGB());

            if (itemRenderer != null) {
                itemRenderer.render(value, laptop, mc, x + 1, y + 1, xOffset - 1, height - 2);
            } else if (value != null) {
                RenderUtil.drawStringClipped(value.toString(), xPosition + 3, yPosition + 3, width - 15, Color.WHITE.getRGB(), true);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return;

        if (this.hovered && !this.opened) {
            this.opened = true;
            BaseDevice.getSystem().openContext(this.layout, xPosition, yPosition + 13);
        }
    }

    @Nullable
    public T getValue() {
        return value;
    }

    void updateValue(T newValue) {
        if (newValue != null && value != newValue) {
            if (value != null && changeListener != null) {
                changeListener.onChange(value, newValue);
            }
            value = newValue;
        }
    }

    private boolean isInside(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    protected int getHoverState(boolean mouseOver) {
        int i = 1;
        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }
        return i;
    }

    public void setItemRenderer(ItemRenderer<T> itemRenderer) {
        this.itemRenderer = itemRenderer;
    }

    public void setChangeListener(ChangeListener<T> changeListener) {
        this.changeListener = changeListener;
    }

    public void closeContext() {
        BaseDevice.getSystem().closeContext();
    }

    public static class List<T> extends ComboBox<T> {
        private final ItemList<T> list;
        private T selected;

        public List(int left, int top, T[] items) {
            super(left, top);
            this.list = new ItemList<>(0, 0, width, 6, false);
            this.layout = new Layout(width, getListHeight(list));
            this.setItems(items);
        }

        public List(int left, int top, int width, T[] items) {
            super(left, top, width);
            this.list = new ItemList<>(0, 0, width, 6, false);
            this.layout = new Layout(width, getListHeight(list));
            this.setItems(items);
        }

        public List(int left, int top, int comboBoxWidth, int listWidth, T[] items) {
            super(left, top, comboBoxWidth);
            this.list = new ItemList<>(0, 0, listWidth, 6, false);
            this.layout = new Layout(listWidth, getListHeight(list));
            this.setItems(items);
        }

        private static int getListHeight(ItemList list) {
            int size = Math.max(1, Math.min(list.visibleItems, list.getItems().size()));
            return (list.renderer != null ? list.renderer.getHeight() : 13) * size + size + 1;
        }

        @Override
        public void init(Layout layout) {
            super.init(layout);
            list.setItemClickListener((t, index, mouseButton) ->
            {
                if (mouseButton == 0) {
                    selected = t;
                    updateValue(t);
                    BaseDevice.getSystem().closeContext();
                }
            });
            this.layout.addComponent(list);
        }

        public void setItems(T[] items) {
            if (items == null)
                throw new IllegalArgumentException("Cannot set null items");

            list.removeAll();
            for (T t : items) {
                list.addItem(t);
            }
            if (items.length > 0) {
                selected = list.getItem(0);
                updateValue(selected);
            }
            layout.height = getListHeight(list);
        }

        public T getSelectedItem() {
            return selected;
        }

        public void setListItemRenderer(ListItemRenderer<T> renderer) {
            list.setListItemRenderer(renderer);
            layout.height = getListHeight(list);
        }
    }

    public static class Custom<T> extends ComboBox<T> {
        public Custom(int left, int top, int width, int contextWidth, int contextHeight) {
            super(left, top);
            this.width = width;
            this.layout = new Layout.Context(contextWidth, contextHeight);
        }

        public Layout.Context getLayout() {
            return (Layout.Context) layout;
        }

        public void setValue(@Nonnull T newVal) {
            updateValue(newVal);
        }
    }
}
