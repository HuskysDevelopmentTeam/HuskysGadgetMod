package io.github.vampirestudios.gadget.init;

import io.github.vampirestudios.gadget.block.*;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;

import static io.github.vampirestudios.gadget.Reference.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class GadgetBlocks {

    public static Block[] gaming_chairs = new Block[16];
    public static BlockLaptop[] laptops = new BlockLaptop[16];
    public static Block[] routers = new Block[16];
    public static Block[] printers = new Block[16];
    public static Block[] monitors = new Block[16];
    public static Block[] ethernet_wall_outlets = new Block[16];
    public static Block[] robots = new Block[16];
    public static Block[] gaming_desks = new Block[16];
    public static Block[] benchmark_stations = new Block[16];
    public static Block[] external_harddrives = new Block[16];
    public static Block[] playstation_4_pros = new Block[16];
    public static Block[] threede_printers = new Block[16];
    public static Block[] desktops = new Block[16];
    public static Block[] drawing_tablets = new Block[16];

    // Needed but boring stuff
    public static final Block paper, screen;

    // Server Stuff
    public static final Block server, serverRack, serverTerminal;

    // Lights
    public static final Block rgbLights, roofLights;

    // Fun Stuff
    public static final Block easter_egg;

    // Other computer stuff
    private static final Block desktopCase;

    // Security Stuff
    private static final Block securityFence, securityGate, cardScanner, securityLaser, securityCamera;

    static {

        for (EnumDyeColor color : EnumDyeColor.values()) {
            gaming_chairs[color.getMetadata()] = new BlockOfficeChair(color);
            laptops[color.getMetadata()] = new BlockLaptop(color);
            routers[color.getMetadata()] = new BlockRouter(color);
            printers[color.getMetadata()] = new BlockPrinter(color);
            monitors[color.getMetadata()] = new BlockMonitor(color);
            ethernet_wall_outlets[color.getMetadata()] = new BlockEthernetWallOutlet(color);
            robots[color.getMetadata()] = new BlockRobot(color);
            gaming_desks[color.getMetadata()] = new BlockGamingDesk(color);
//            benchmark_stations[color.getMetadata()] = new BlockBenchmarkStation(color);
            external_harddrives[color.getMetadata()] = new BlockExternalHarddrive(color);
//            playstation_4_pros[color.getMetadata()] = new BlockPlaystation4Pro(color);
//            threede_printers[color.getMetadata()] = new Block3DPrinter(color);
            desktops[color.getMetadata()] = new BlockDesktop(color);
//            drawing_tablets[color.getMetadata()] = new BlockDrawingTablet(color);
        }

        paper = new BlockPaper();
        screen = new BlockScreen();
        server = new BlockServer();
        serverRack = new BlockServerRack();
        serverTerminal = new BlockServerTerminal();
        rgbLights = new BlockRGBLights();
        roofLights = new BlockRoofLights();
        securityCamera = new BlockSecurityCamera();
        easter_egg = new BlockEasterEgg();
        securityFence = new BlockElectricSecurityFence();
        securityGate = new BlockElectricSecurityGate();
        cardScanner = new BlockCardScanner();
        securityLaser = new BlockSecurityLaser();
        desktopCase = new BlockDesktopCase();
    }

    public static void register() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            register(gaming_chairs[color.getMetadata()]);
            register(laptops[color.getMetadata()]);
            register(routers[color.getMetadata()]);
            register(printers[color.getMetadata()]);
            register(monitors[color.getMetadata()]);
            register(ethernet_wall_outlets[color.getMetadata()]);
            register(robots[color.getMetadata()]);
            register(gaming_desks[color.getMetadata()]);
            register(external_harddrives[color.getMetadata()]);
            register(desktops[color.getMetadata()]);
        }

        register(paper);
        register(screen);
        register(server);
        register(serverRack);
        register(serverTerminal);
        register(rgbLights);
        register(roofLights);
        register(securityCamera);
        register(easter_egg);
        register(securityFence);
        register(securityGate);
        register(cardScanner);
        register(securityLaser);
        register(desktopCase);
    }

    private static void register(Block block) {
        RegistrationHandler.Blocks.add(block);
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        itemBlock.setTranslationKey(block.getRegistryName().getNamespace() + "." + block.getRegistryName().getPath());
        RegistrationHandler.Items.add(itemBlock);
    }

}