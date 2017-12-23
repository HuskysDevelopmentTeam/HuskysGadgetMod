package net.thegaminghuskymc.gadgetmod.core.images;

import com.mrcrayfish.device.init.DeviceBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.DeviceConfig;
import net.thegaminghuskymc.gadgetmod.api.app.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.ItemList;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.core.Laptop;
import net.thegaminghuskymc.gadgetmod.core.images.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.core.images.task.TaskPing;
import net.thegaminghuskymc.gadgetmod.init.GadgetBlocks;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrayItemMonitor extends TrayItem {

    public static TrayItem trayItem;

    public TrayItemMonitor()
    {
        super(Icons.COMPUTER);
    }

    @Override
    public void init()
    {
        this.setClickListener((mouseX, mouseY, mouseButton) ->
        {
        	if(Laptop.getSystem().hasContext())
            {
                Laptop.getSystem().closeContext();
            }
            else
            {
            	Laptop.getSystem().openContext(createMonitorMenu(this), mouseX - 100, mouseY - 100);
            }
        });
    }

    private static Layout createMonitorMenu(TrayItem item)
    {
        trayItem = item;

        Layout layout = new Layout.Context(100, 100);
        layout.yPosition = 40;
        layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
                Gui.drawRect(x, y, x + width, y + height, new Color(0.65F, 0.65F, 0.65F, 0.9F).getRGB()));

        ItemList<BlockPos> itemListRouters = new ItemList<>(5, 5, 90, 4);
        itemListRouters.setItems(getMonitors());
        itemListRouters.setListItemRenderer(new ListItemRenderer<BlockPos>(16)
        {
            @Override
            public void render(BlockPos blockPos, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected)
            {
                Gui.drawRect(x, y, x + width, y + height, selected ? Color.DARK_GRAY.getRGB() : Color.GRAY.getRGB());
                gui.drawString(mc.fontRenderer, "Monitor", x + 16, y + 4, Color.WHITE.getRGB());
            }
        });
        itemListRouters.sortBy((o1, o2) -> {
            BlockPos laptopPos = Laptop.getPos();
            double distance1 = Math.sqrt(o1.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            double distance2 = Math.sqrt(o2.distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            return Double.compare(distance1, distance2);
        });
        layout.addComponent(itemListRouters);

        Button buttonConnect = new Button(79, 79, Icons.CHECK);
        buttonConnect.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(mouseButton == 0)
            {
                if(itemListRouters.getSelectedItem() != null)
                {
                    TaskConnect connect = new TaskConnect(Laptop.getPos(), itemListRouters.getSelectedItem());
                    connect.setCallback((tagCompound, success) ->
                    {
                        if(success)
                        {
                            item.setIcon(Icons.COMPUTER);
                            Laptop.getSystem().closeContext();
                        }
                    });
                    TaskManager.sendTask(connect);
                }
            }
        });
        layout.addComponent(buttonConnect);

        return layout;
    }

    public static List<BlockPos> getMonitors()
    {
        List<BlockPos> routers = new ArrayList<>();

        World world = Minecraft.getMinecraft().world;
        BlockPos laptopPos = Laptop.getPos();
        int range = DeviceConfig.getSignalRange();

        for(int y = -range; y < range + 1; y++)
        {
            for(int z = -range; z < range + 1; z++)
            {
                for(int x = -range; x < range + 1; x++)
                {
                    BlockPos pos = new BlockPos(laptopPos.getX() + x, laptopPos.getY() + y, laptopPos.getZ() + z);
                    IBlockState state = world.getBlockState(pos);
                    if(state.getBlock() == GadgetBlocks.MONITOR)
                    {
                        routers.add(pos);
                    }
                }
            }
        }
        return routers;
    }
}