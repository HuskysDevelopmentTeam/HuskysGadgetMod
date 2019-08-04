/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.vampirestudios.gadget.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

import java.util.Random;

@Mod.EventBusSubscriber
public class PlayerEvents {
    private final String PREFIX = "-> ";

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent e) {
        EntityPlayer player = e.player;
        if (ConfigurationHandler.canDisplay) {
            if (!player.world.isRemote) {
                if (!ConfigurationHandler.hasDisplayedOnce) {
                    TextComponentString prefix = new TextComponentString(TextFormatting.GOLD + "Thank you for downloading Husky's Gadget Mod");
                    prefix.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("You can disable this login message in the config")));
                    player.sendMessage(prefix);

                    TextComponentString url;
                    Random rand = new Random();
                    switch (rand.nextInt(4)) {
                        case 0:
						/*player.sendMessage(new TextComponentString(TextFormatting.GOLD + PREFIX + TextFormatting.GREEN + "Check out all Husky's Mods"));
//						url = new TextComponentString(TextFormatting.GOLD + PREFIX + TextFormatting.RESET + "mrcrayfish.com");
//						url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://mrcrayfish.com/mods"));
						url.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Open URL")));
						player.sendMessage(url);*/
                            break;
                        case 1:
						/*player.sendMessage(new TextComponentString(TextFormatting.GOLD + PREFIX + TextFormatting.GREEN + "Check out the Gadget Mod Wiki"));
//						url = new TextComponentString(TextFormatting.GOLD + PREFIX + TextFormatting.RESET + "mrcrayfishs-furniture-mod.wikia.com");
//						url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mrcrayfishs-furniture-mod.wikia.com/"));
						url.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Open URL")));
						player.sendMessage(url);*/
                            break;
                        case 2:
						/*player.sendMessage(new TextComponentString(TextFormatting.GOLD + PREFIX + TextFormatting.GREEN + "Check out Husky's YouTube"));
						url = new TextComponentString(TextFormatting.GOLD + PREFIX + TextFormatting.RESET + "youtube.com/user/MrCrayfishMinecraft");
						url.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/user/MrCrayfishMinecraft"));
						url.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Open URL")));
						player.sendMessage(url);*/
                            break;
                    }
                }
            }
        }
    }
}
