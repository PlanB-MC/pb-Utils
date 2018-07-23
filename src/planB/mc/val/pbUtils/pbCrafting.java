package planB.mc.val.pbUtils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import planB.mc.val.Main;

public class pbCrafting {

    public pbCrafting(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, plugin.getDescription().getName());
        //Crafting recipe for Barrier Block
        ItemStack barrierStack = new ItemStack(Material.BARRIER, 32);
        ShapedRecipe barrierR = new ShapedRecipe(key, barrierStack);
        barrierR.shape("GSG", "GBG", "GPG");
        barrierR.setIngredient('G', Material.GLASS);
        barrierR.setIngredient('B', Material.BUCKET);
        barrierR.setIngredient('S', Material.GHAST_TEAR);
        barrierR.setIngredient('P', Material.BLAZE_POWDER);
        plugin.getServer().addRecipe(barrierR);


   /*     //Stairs
        List<Recipe> restore = new ArrayList<>();
        List<ItemStack> stairs = new ArrayList<>();
        ItemStack ACACIA_STAIRSStack = new ItemStack(Material.ACACIA_STAIRS, 8);
        ItemStack BIRCH_STAIRSStack = new ItemStack(Material.BIRCH_STAIRS, 8);
        ItemStack BRICK_STAIRSStack = new ItemStack(Material.BRICK_STAIRS, 8);
        ItemStack COBBLESTONE_STAIRSStack = new ItemStack(Material.COBBLESTONE_STAIRS, 8);
        ItemStack DARK_OAK_STAIRSStack = new ItemStack(Material.DARK_OAK_STAIRS, 8);
        ItemStack DARK_PRISMARINE_STAIRSStack = new ItemStack(Material.DARK_PRISMARINE_STAIRS, 8);
        ItemStack JUNGLE_STAIRSStack = new ItemStack(Material.JUNGLE_STAIRS, 8);
        ItemStack NETHER_BRICK_STAIRSStack = new ItemStack(Material.NETHER_BRICK_STAIRS, 8);
        ItemStack OAK_STAIRSStack = new ItemStack(Material.OAK_STAIRS, 8);
        ItemStack PRISMARINE_BRICK_STAIRSStack = new ItemStack(Material.PRISMARINE_BRICK_STAIRS, 8);
        ItemStack PURPUR_STAIRSStack = new ItemStack(Material.PURPUR_STAIRS, 8);
        ItemStack RED_SANDSTONE_STAIRSStack = new ItemStack(Material.RED_SANDSTONE_STAIRS, 8);
        ItemStack QUARTZ_STAIRSStack = new ItemStack(Material.QUARTZ_STAIRS, 8);
        ItemStack SANDSTONE_STAIRSStack = new ItemStack(Material.SANDSTONE_STAIRS, 8);
        ItemStack SPRUCE_STAIRSStack = new ItemStack(Material.SPRUCE_STAIRS, 8);
        ItemStack STONE_BRICK_STAIRSStack = new ItemStack(Material.STONE_BRICK_STAIRS, 8);
        ItemStack PRISMARINE_STAIRSStack = new ItemStack(Material.PRISMARINE_STAIRS, 8);
        stairs.addAll(Arrays.asList(
                ACACIA_STAIRSStack,
                BIRCH_STAIRSStack,
                BRICK_STAIRSStack,
                COBBLESTONE_STAIRSStack,
                DARK_OAK_STAIRSStack,
                DARK_PRISMARINE_STAIRSStack,
                JUNGLE_STAIRSStack,
                NETHER_BRICK_STAIRSStack,
                OAK_STAIRSStack,
                PRISMARINE_BRICK_STAIRSStack,
                PURPUR_STAIRSStack,
                RED_SANDSTONE_STAIRSStack,
                QUARTZ_STAIRSStack,
                SANDSTONE_STAIRSStack,
                SPRUCE_STAIRSStack,
                STONE_BRICK_STAIRSStack,
                PRISMARINE_STAIRSStack
        ));


        plugin.getServer().recipeIterator().forEachRemaining(cur -> {
            ItemStack result = cur.getResult();
            if (!result.isSimilar(acaciaStairStack)) {
                restore.add(cur);
            }
        });

        plugin.getServer().clearRecipes();


        //Crafting recipe for Stair
        //ACACIA_STAIRS
        //ItemStack acaciaStairStack = new ItemStack(Material.ACACIA_STAIRS);
    /*    ShapedRecipe acaciaStairR = new ShapedRecipe(key, acaciaStairStack);
        acaciaStairR.shape("**b", "*bb", "bbb");
        // acaciaStairR.setIngredient('*', Material.AIR);
        acaciaStairR.setIngredient('b', Material.ACACIA_WOOD);
        System.out.println("test");
        plugin.getServer().addRecipe(acaciaStairR);

        restore.forEach(cur -> {
            try {
                plugin.getServer().addRecipe(cur);
            } catch (IllegalStateException e) {
                //ignore
            }
        });

  /*      //BIRCH_STAIRS
        ItemStack Stack = new ItemStack(Material.BIRCH_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //BRICK_STAIRS
        ItemStack Stack = new ItemStack(Material.BRICK_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //COBBLESTONE_STAIRS
        ItemStack Stack = new ItemStack(Material.COBBLESTONE_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //DARK_OAK_STAIRS
        ItemStack Stack = new ItemStack(Material.DARK_OAK_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //DARK_PRISMARINE_STAIRS
        ItemStack Stack = new ItemStack(Material.DARK_PRISMARINE_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //JUNGLE_STAIRS
        ItemStack Stack = new ItemStack(Material.JUNGLE_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //NETHER_BRICK_STAIRS
        ItemStack Stack = new ItemStack(Material.NETHER_BRICK_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //OAK_STAIRS
        ItemStack Stack = new ItemStack(Material.OAK_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //PRISMARINE_BRICK_STAIRS
        ItemStack Stack = new ItemStack(Material.PRISMARINE_BRICK_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //PRISMARINE_STAIRS
        ItemStack Stack = new ItemStack(Material.PRISMARINE_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //PURPUR_STAIRS
        ItemStack Stack = new ItemStack(Material.PURPUR_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //QUARTZ_STAIRS
        ItemStack Stack = new ItemStack(Material.QUARTZ_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //RED_SANDSTONE_STAIRS
        ItemStack Stack = new ItemStack(Material.RED_SANDSTONE_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //SANDSTONE_STAIRS
        ItemStack Stack = new ItemStack(Material.SANDSTONE_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //SPRUCE_STAIRS
        ItemStack Stack = new ItemStack(Material.SPRUCE_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);
        //STONE_BRICK_STAIRS
        ItemStack Stack = new ItemStack(Material.STONE_BRICK_STAIRS);
        ShapedRecipe R = new ShapedRecipe(key, Stack);
        R.shape("**b", "*bb", "bbb");
        R.setIngredient('b', Material.);
        plugin.getServer().addRecipe(R);*/


        pbUtils.log("[pbCrafting]", "Added a total of 3 Recipe(s)");
    }


}
