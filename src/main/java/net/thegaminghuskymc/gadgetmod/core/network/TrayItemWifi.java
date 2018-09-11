package net.thegaminghuskymc.gadgetmod.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thegaminghuskymc.gadgetmod.DeviceConfig;
import net.thegaminghuskymc.gadgetmod.api.app.Layout;
import net.thegaminghuskymc.gadgetmod.api.app.component.Button;
import net.thegaminghuskymc.gadgetmod.api.app.component.ItemList;
import net.thegaminghuskymc.gadgetmod.api.app.emojie_packs.Icons;
import net.thegaminghuskymc.gadgetmod.api.app.renderer.ListItemRenderer;
import net.thegaminghuskymc.gadgetmod.api.task.TaskManager;
import net.thegaminghuskymc.gadgetmod.api.utils.RenderUtil;
import net.thegaminghuskymc.gadgetmod.core.BaseDevice;
import net.thegaminghuskymc.gadgetmod.core.Device;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskConnect;
import net.thegaminghuskymc.gadgetmod.core.network.task.TaskPing;
import net.thegaminghuskymc.gadgetmod.object.TrayItem;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityDevice;
import net.thegaminghuskymc.gadgetmod.tileentity.TileEntityRouter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrayItemWifi extends TrayItem {

    private int pingTimer;

    public TrayItemWifi()
    {
        super(Icons.WIFI_NONE);
    }

    @Override
    public void init()
    {
        this.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if(BaseDevice.getSystem().hasContext())
            {
                BaseDevice.getSystem().closeContext();
            }
            else
            {
                BaseDevice.getSystem().openContext(createWifiMenu(this), mouseX - 100, mouseY - 100);
            }
        });

        runPingTask();
    }

    @Override
    public void tick()
    {
        if(++pingTimer >= DeviceConfig.getPingRate())
        {
            runPingTask();
            pingTimer = 0;
        }
    }

    private void runPingTask()
    {
        TaskPing task = new TaskPing(BaseDevice.getPos());
        task.setCallback((tagCompound, success) ->
        {
            if(success)
            {
                int strength = tagCompound.getInteger("strength");
                switch(strength)
                {
                    case 2:
                        setIcon(Icons.WIFI_LOW);
                        break;
                    case 1:
                        setIcon(Icons.WIFI_MED);
                        break;
                    case 0:
                        setIcon(Icons.WIFI_HIGH);
                        break;
                    default:
                        setIcon(Icons.WIFI_NONE);
                        break;
                }
            }
            else
            {
                setIcon(Icons.WIFI_NONE);
            }
        });
        TaskManager.sendTask(task);
    }

    private static Layout createWifiMenu(TrayItem item)
    {
        Layout layout = new Layout.Context(100, 100);
        layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
                Gui.drawRect(x, y, x + width, y + height, new Color(0.65F, 0.65F, 0.65F, 0.9F).getRGB()));

        ItemList<Device> itemListRouters = new ItemList<>(5, 5, 90, 4);
        itemListRouters.setItems(getRouters());
        itemListRouters.setListItemRenderer(new ListItemRenderer<Device>(16)
        {
            @Override
            public void render(Device device, Gui gui, Minecraft mc, int x, int y, int width, int height, boolean selected)
            {
                Gui.drawRect(x, y, x + width, y + height, selected ? Color.DARK_GRAY.getRGB() : Color.GRAY.getRGB());
                RenderUtil.drawStringClipped(device.getName(), x + 16, y + 4, 70, Color.WHITE.getRGB(), false);

                if(device.getPos() == null)
                    return;

                BlockPos laptopPos = BaseDevice.getPos();
                double distance = Math.sqrt(device.getPos().distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
                if(distance > 20)
                {
                    Icons.WIFI_LOW.draw(mc, x + 3, y + 3);
                }
                else if(distance > 10)
                {
                    Icons.WIFI_MED.draw(mc, x + 3, y + 3);
                }
                else
                {
                    Icons.WIFI_HIGH.draw(mc, x + 3, y + 3);
                }
            }
        });
        itemListRouters.sortBy((o1, o2) -> {
            BlockPos laptopPos = BaseDevice.getPos();
            double distance1 = Math.sqrt(Objects.requireNonNull(o1.getPos()).distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
            double distance2 = Math.sqrt(Objects.requireNonNull(o2.getPos()).distanceSqToCenter(laptopPos.getX() + 0.5, laptopPos.getY() + 0.5, laptopPos.getZ() + 0.5));
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
                    TaskConnect connect = new TaskConnect(BaseDevice.getPos(), itemListRouters.getSelectedItem().getPos());
                    connect.setCallback((tagCompound, success) ->
                    {
                        if(success)
                        {
                            item.setIcon(Icons.WIFI_HIGH);
                            BaseDevice.getSystem().closeContext();
                        }
                    });
                    TaskManager.sendTask(connect);
                }
            }
        });
        layout.addComponent(buttonConnect);

        return layout;
    }

    public static List<Device> getRouters()
    {
        List<Device> routers = new ArrayList<>();

        World world = Minecraft.getMinecraft().world;
        BlockPos laptopPos = BaseDevice.getPos();
        int range = DeviceConfig.getSignalRange();

        for(int y = -range; y < range + 1; y++)
        {
            for(int z = -range; z < range + 1; z++)
            {
                for(int x = -range; x < range + 1; x++)
                {
                    BlockPos pos = new BlockPos(laptopPos.getX() + x, laptopPos.getY() + y, laptopPos.getZ() + z);
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if(tileEntity instanceof TileEntityRouter)
                    {
                        routers.add(new Device((TileEntityDevice) tileEntity));
                    }
                }
            }
        }
        return routers;
    }
}