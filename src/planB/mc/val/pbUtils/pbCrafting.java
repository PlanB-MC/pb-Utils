package planB.mc.val.pbUtils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import planB.mc.val.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class pbCrafting {
    private int count = 0;
    private Main plugin;

    public pbCrafting(Main plugin) {
        this.plugin = plugin;
        //initialize Stairs
        List<Recipe> restore = new ArrayList<>();
        HashMap<Material, ItemStack> stairs = new HashMap<>();
        stairs.put(Material.ACACIA_PLANKS, new ItemStack(Material.ACACIA_STAIRS, 8));
        stairs.put(Material.BIRCH_PLANKS, new ItemStack(Material.BIRCH_STAIRS, 8));
        stairs.put(Material.BRICK, new ItemStack(Material.BRICK_STAIRS, 8));
        stairs.put(Material.COBBLESTONE, new ItemStack(Material.COBBLESTONE_STAIRS, 8));
        stairs.put(Material.DARK_OAK_PLANKS, new ItemStack(Material.DARK_OAK_STAIRS, 8));
        stairs.put(Material.DARK_PRISMARINE, new ItemStack(Material.DARK_PRISMARINE_STAIRS, 8));
        stairs.put(Material.JUNGLE_PLANKS, new ItemStack(Material.JUNGLE_STAIRS, 8));
        stairs.put(Material.NETHER_BRICK, new ItemStack(Material.NETHER_BRICK_STAIRS, 8));
        stairs.put(Material.OAK_PLANKS, new ItemStack(Material.OAK_STAIRS, 8));
        stairs.put(Material.PRISMARINE_BRICKS, new ItemStack(Material.PRISMARINE_BRICK_STAIRS, 8));
        stairs.put(Material.PURPUR_BLOCK, new ItemStack(Material.PURPUR_STAIRS, 8));
        stairs.put(Material.RED_SANDSTONE, new ItemStack(Material.RED_SANDSTONE_STAIRS, 8));
        stairs.put(Material.QUARTZ, new ItemStack(Material.QUARTZ_STAIRS, 8));
        stairs.put(Material.SANDSTONE, new ItemStack(Material.SANDSTONE_STAIRS, 8));
        stairs.put(Material.SPRUCE_PLANKS, new ItemStack(Material.SPRUCE_STAIRS, 8));
        stairs.put(Material.STONE_BRICKS, new ItemStack(Material.STONE_BRICK_STAIRS, 8));
        stairs.put(Material.PRISMARINE, new ItemStack(Material.PRISMARINE_STAIRS, 8));

        //initialize Wool
        HashMap<Material, ItemStack> wool = new HashMap<>();
        stairs.put(Material.INK_SAC, new ItemStack(Material.BLACK_WOOL, 8));
        stairs.put(Material.LAPIS_LAZULI, new ItemStack(Material.BLUE_WOOL, 8));
        stairs.put(Material.COCOA_BEANS, new ItemStack(Material.BROWN_WOOL, 8));
        stairs.put(Material.CYAN_DYE, new ItemStack(Material.CYAN_WOOL, 8));
        stairs.put(Material.GRAY_DYE, new ItemStack(Material.GRAY_WOOL, 8));
        stairs.put(Material.CACTUS_GREEN, new ItemStack(Material.GREEN_WOOL, 8));
        stairs.put(Material.LIGHT_BLUE_DYE, new ItemStack(Material.LIGHT_BLUE_WOOL, 8));
        stairs.put(Material.LIGHT_GRAY_DYE, new ItemStack(Material.LIGHT_GRAY_WOOL, 8));
        stairs.put(Material.LIME_DYE, new ItemStack(Material.LIME_WOOL, 8));
        stairs.put(Material.MAGENTA_DYE, new ItemStack(Material.MAGENTA_WOOL, 8));
        stairs.put(Material.ORANGE_DYE, new ItemStack(Material.ORANGE_WOOL, 8));
        stairs.put(Material.PINK_DYE, new ItemStack(Material.PINK_WOOL, 8));
        stairs.put(Material.PURPLE_DYE, new ItemStack(Material.PURPLE_WOOL, 8));
        stairs.put(Material.ROSE_RED, new ItemStack(Material.RED_WOOL, 8));
        stairs.put(Material.DANDELION_YELLOW, new ItemStack(Material.YELLOW_WOOL, 8));

        //remove all recipes
        plugin.getServer().recipeIterator().forEachRemaining(cur -> {
            ItemStack result = cur.getResult();
            result.setAmount(8);

            boolean found = false;
            for (ItemStack curItem : stairs.values()) {
                if (result.isSimilar(curItem)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                /*for (ItemStack curItem : wool.values()) {
                    if (result.getType().equals(curItem.getType())) {
                        found = true;
                        break;
                    }
                }*/
                if (!found) {
                    restore.add(cur);
                    return;
                }
            }
        });
        plugin.getServer().clearRecipes();
        //set new recipes

        //  Crafting recipe for Stair
        stairs.forEach((curMat, curStair) -> {
            ShapedRecipe newRecipeR = new ShapedRecipe(getKey(), curStair);
            ShapedRecipe newRecipeL = new ShapedRecipe(getKey(), curStair);
            newRecipeR.shape("**b", "*bb", "bbb");
            newRecipeR.setIngredient('b', curMat);
            newRecipeL.shape("b**", "bb*", "bbb");
            newRecipeL.setIngredient('b', curMat);
            plugin.getServer().addRecipe(newRecipeR);
            plugin.getServer().addRecipe(newRecipeL);
        });
        //  Crafting recipe for Wool
       /* wool.forEach((curMat, curWool) -> {
            ShapedRecipe newRecipe = new ShapedRecipe(getKey(), curWool);
            newRecipe.shape("bbb", "b*b", "bbb");
            newRecipe.setIngredient('*', curMat);
            newRecipe.setIngredient('b', Material.WHITE_WOOL);
            plugin.getServer().addRecipe(newRecipe);
        });*/

        //restore recipes
        for (Recipe cur : restore) {
            try {
                plugin.getServer().addRecipe(cur);
            } catch (IllegalStateException e) {
            }
        }

        //New recipes
        //Crafting recipe for Barrier Block
        ItemStack barrierStack = new ItemStack(Material.BARRIER, 32);
        ShapedRecipe barrierR = new ShapedRecipe(getKey(), barrierStack);
        barrierR.shape("GSG", "GBG", "GPG");
        barrierR.setIngredient('G', Material.GLASS);
        barrierR.setIngredient('B', Material.BUCKET);
        barrierR.setIngredient('S', Material.GHAST_TEAR);
        barrierR.setIngredient('P', Material.BLAZE_POWDER);
        plugin.getServer().addRecipe(barrierR);

        //Crafting recipes for slabs
        HashMap<Material, ItemStack> slabs = new HashMap<>();
        slabs.put(Material.ACACIA_SLAB, new ItemStack(Material.ACACIA_PLANKS));
        slabs.put(Material.BIRCH_SLAB, new ItemStack(Material.BIRCH_PLANKS));
        slabs.put(Material.BRICK_SLAB, new ItemStack(Material.BRICKS));
        slabs.put(Material.SANDSTONE_SLAB, new ItemStack(Material.SANDSTONE));
        slabs.put(Material.SPRUCE_SLAB, new ItemStack(Material.SPRUCE_PLANKS));
        slabs.put(Material.COBBLESTONE_SLAB, new ItemStack(Material.COBBLESTONE));
        slabs.put(Material.DARK_OAK_SLAB, new ItemStack(Material.DARK_OAK_PLANKS));
        slabs.put(Material.DARK_PRISMARINE_SLAB, new ItemStack(Material.DARK_PRISMARINE));
        slabs.put(Material.JUNGLE_SLAB, new ItemStack(Material.JUNGLE_PLANKS));
        slabs.put(Material.NETHER_BRICK_SLAB, new ItemStack(Material.NETHER_BRICK));
        slabs.put(Material.OAK_SLAB, new ItemStack(Material.OAK_PLANKS));
        slabs.put(Material.STONE_SLAB, new ItemStack(Material.STONE));
        slabs.put(Material.STONE_BRICK_SLAB, new ItemStack(Material.STONE_BRICKS));
        slabs.put(Material.PURPUR_SLAB, new ItemStack(Material.PURPUR_BLOCK));
        slabs.put(Material.RED_SANDSTONE_SLAB, new ItemStack(Material.RED_SANDSTONE));
        slabs.put(Material.QUARTZ_SLAB, new ItemStack(Material.QUARTZ_BLOCK));
        slabs.put(Material.PRISMARINE_SLAB, new ItemStack(Material.PRISMARINE));
        slabs.put(Material.PRISMARINE_BRICK_SLAB, new ItemStack(Material.PRISMARINE_BRICKS));

        slabs.forEach((curMat, curBlock) -> {
            ShapelessRecipe newOne = new ShapelessRecipe(getKey(), curBlock);
            newOne.addIngredient(2, curMat);
            plugin.getServer().addRecipe(newOne);
        });


        pbUtils.log("[pbCrafting]", "Added a total of " + count + " Recipe(s)");
    }

    private NamespacedKey getKey() {
        NamespacedKey key = new NamespacedKey(plugin, "key_val_" + count);
        System.out.println("[pbUtils]@recipe " + count);
        count++;
        return key;
    }

}
