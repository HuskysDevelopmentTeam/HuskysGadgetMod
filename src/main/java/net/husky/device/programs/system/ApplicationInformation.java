package net.husky.device.programs.system;

import net.husky.device.api.app.Component;
import net.husky.device.api.app.Layout;
import net.husky.device.api.app.component.ButtonTab;
import net.husky.device.api.app.component.Label;
import net.husky.device.api.app.listener.ClickListener;
import net.husky.device.core.Laptop;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ApplicationInformation extends SystemApplication {

    private Laptop laptop;

    private Label version;
    private Label OSName;

    private Label OS_name;

    private ButtonTab tabs;
    private ButtonTab tabs2;
    private ButtonTab tabs3;
    private ButtonTab tabs4;
    private ButtonTab tabs5;

    private ButtonTab[] tab = new ButtonTab[5];
    private Layout[] layouts = new Layout[5];
    private ItemStack[] stacks = new ItemStack[] {
            new ItemStack(Blocks.BRICK_BLOCK),
            new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta()),
            new ItemStack(Items.REDSTONE),
            new ItemStack(Blocks.GOLDEN_RAIL),
            new ItemStack(Items.LAVA_BUCKET)
    };

    private String[] names = new String[] {
            "Building Blocks",
            "Decoration Blocks",
            "Redstone",
            "Transportation",
            "Miscellaneous"
    };

    private String[] tooltips = new String[] {
            "All the blocks for building",
            "All of the decoration blocks",
            "All of the technical blocks and items",
            "Yay! Transport",
            "All of the strange stuff"
    };

    int activeTab = 0;

    public ApplicationInformation() {
        this.setDefaultWidth(250);
        this.setDefaultHeight(150);
    }

    @Override
    public void init() {
        /*tabs  = new ButtonTab(10, 10, 24, 30, stacks[0], 0);
        tabs.setToolTip(names[0], tooltips[0]);
        super.addComponent(tabs);

        tabs2  = new ButtonTab(38, 10, 24, 30, stacks[1], 1);
        tabs2.setToolTip(names[1], tooltips[1]);
        super.addComponent(tabs2);

        tabs3  = new ButtonTab(66, 10, 24, 30, stacks[2], 2);
        tabs3.setToolTip(names[2], tooltips[2]);
        super.addComponent(tabs3);

        tabs4  = new ButtonTab(94, 10, 24, 30, stacks[3], 3);
        tabs4.setToolTip(names[3], tooltips[3]);
        super.addComponent(tabs4);

        tabs5  = new ButtonTab(124, 10, 24, 30, stacks[4], 4);
        tabs5.setToolTip(names[4], tooltips[4]);
        super.addComponent(tabs5);*/
        ClickListener cl = new ClickListener() {
            @Override
            public void onClick(Component c, int mouseButton) {
                activeTab = ((ButtonTab)c).getTabIndex();
            }
        };
        for(int i = 0; i < 5; i++) {
            layouts[i] = new Layout();
            tab[i] = new ButtonTab(10 + (i * 28), 10, 24, 30, stacks[i], i);
            tab[i].setToolTip(names[i], tooltips[i]);
            tab[i].setClickListener(cl);
            super.addComponent(tab[i]);
        }

    }

    @Override
    public void render(Laptop laptop, Minecraft mc, int x, int y, int mouseX, int mouseY, boolean active, float partialTicks) {
        super.render(laptop, mc, x, y, mouseX, mouseY, active, partialTicks);
        layouts[activeTab].render(laptop, mc, x+10, y+10, mouseX, mouseY, active, partialTicks);
        layouts[activeTab].setTitle("Test");
    }

    @Override
	public void load(NBTTagCompound tagCompound) {

	}

	@Override
	public void save(NBTTagCompound tagCompound) {

	}

}
