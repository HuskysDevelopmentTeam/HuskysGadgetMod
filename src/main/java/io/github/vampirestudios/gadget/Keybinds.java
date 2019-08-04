package io.github.vampirestudios.gadget;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {

    public static KeyBinding bios, leaveBios, startMenu;

    public static void register() {
        bios = new KeyBinding(io.github.vampirestudios.gadget.util.RenderHelper.unlocaliseName("key.bios.description"), Keyboard.KEY_F9, io.github.vampirestudios.gadget.util.RenderHelper.unlocaliseName("key.categories.hgm"));
        leaveBios = new KeyBinding(io.github.vampirestudios.gadget.util.RenderHelper.unlocaliseName("key.leave_bios.description"), Keyboard.KEY_F9, io.github.vampirestudios.gadget.util.RenderHelper.unlocaliseName("key.categories.hgm"));
        startMenu = new KeyBinding(io.github.vampirestudios.gadget.util.RenderHelper.unlocaliseName("key.start_menu.description"), Keyboard.KEY_F9, io.github.vampirestudios.gadget.util.RenderHelper.unlocaliseName("key.categories.hgm"));

        ClientRegistry.registerKeyBinding(bios);
    }
}