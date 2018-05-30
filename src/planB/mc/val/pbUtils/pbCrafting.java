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
        ShapedRecipe barrier = new ShapedRecipe(key, barrierStack);
        barrier.shape("GSG", "GBG", "GPG");
        barrier.setIngredient('G', Material.GLASS);
        barrier.setIngredient('B', Material.BUCKET);
        barrier.setIngredient('S', Material.GHAST_TEAR);
        barrier.setIngredient('P', Material.BLAZE_POWDER);
        plugin.getServer().addRecipe(barrier);
        pbUtils.log("[pbCrafting]","Added a total of 1 Recipe(s)");
    }
}
