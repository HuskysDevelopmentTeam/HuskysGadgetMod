package io.github.vampirestudios.gadget.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import io.github.vampirestudios.gadget.HuskyGadgetMod;
import io.github.vampirestudios.gadget.Reference;
import io.github.vampirestudios.gadget.item.SubItems;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RegistrationHandler {

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Blocks {
        private static final List<Block> BLOCKS = new LinkedList<>();

        static void add(Block block) {
            BLOCKS.add(block);
        }

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<Block> event) {
            HuskyGadgetMod.logger().info("Registering blocks");
            BLOCKS.forEach(block -> {
                HuskyGadgetMod.logger().info(String.format("We just registered a block called: %s", block.getRegistryName()));
                event.getRegistry().register(block);
            });
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Items {
        private static final List<Item> ITEMS = new LinkedList<>();

        static void add(Item item) {
            ITEMS.add(item);
        }

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<Item> event) {
            HuskyGadgetMod.logger().info("Registering items");
            ITEMS.forEach(item -> {
                HuskyGadgetMod.logger().info(String.format("We just registered an item called: %s | %s", item.getRegistryName(), item.getClass().getCanonicalName()));
                event.getRegistry().register(item);
            });
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class Recipes {
        private static final List<IRecipe> RECIPES = new LinkedList<>();

        static void add(IRecipe recipe) {
            RECIPES.add(recipe);
        }

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<IRecipe> event) {
            HuskyGadgetMod.logger().info("Registering recipes");
            RECIPES.forEach(recipe -> event.getRegistry().register(recipe));
            //System.out.println(event.getRegistry().getEntries());
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Side.CLIENT)
    public static class Models {
        @SubscribeEvent
        public static void register(ModelRegistryEvent event) {
            HuskyGadgetMod.logger().info("Registering models");
            Items.ITEMS.forEach(Models::registerRender);
        }

        public static void registerRender(Item item) {
            if (item instanceof SubItems) {
                NonNullList<ResourceLocation> modelLocations = ((SubItems) item).getModels();
                for (int i = 0; i < modelLocations.size(); i++) {
                    ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(modelLocations.get(i), "inventory"));
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
            }
        }
    }
}
