package planB.mc.val.lightSense.HeadsLights;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Chunk;
import net.minecraft.server.v1_12_R1.PacketPlayOutMapChunk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftMagicNumbers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import planB.mc.val.pbUtils.pbUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class onHeadLights implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        //Checking if the item held in has is the correct head
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack itemHead = event.getItemInHand();
        pbUtils.log("[onHeadLights]", api.getItemID(itemHead));
        try {
            if (api.getItemID(itemHead).equals("3297")) {
                pbUtils.log("[onHeadsLights]","light placed");
                //Getting the location of the block where its placed
                Location loc = event.getBlockPlaced().getLocation();
                Chunk chunk = ((CraftChunk) loc.getChunk()).getHandle();
                BlockPosition blockPos = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

                //Using reflection to access and call the method used to set the light level
                //  Note it is also possible (given the current approach) to set 'protected int o;' directly
                Class clazz = Block.class;
                Block block = CraftMagicNumbers.getBlock(event.getBlockPlaced());
                Method mSetLightLevel = clazz.getDeclaredMethod("a", float.class);
                mSetLightLevel.setAccessible(true);
                Block litBlock = (Block) mSetLightLevel.invoke(block, 1F);
                //^^^^ the method wants a light percentage as input, thus any value from 0F to 1F (e.g. 0.84445F)

                //Updates the lights sever side
                chunk.getWorld().m(blockPos);

                //the sendPacket and forLoops are used to update the lights client side

                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++) {
                        org.bukkit.Chunk chunk1 = event.getBlockPlaced().getWorld().getChunkAt(loc.getChunk().getX() + x, loc.getChunk().getZ() + z);
                        chunk1.unload();
                        chunk1.load();
                    }



                /*Bukkit.getOnlinePlayers().forEach(p ->
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(
                                new PacketPlayOutMapChunk(chunk, 20)
                        ));
                for (int x = -1; x <= 1; x++)
                    for (int z = -1; z <= 1; z++)
                        event.getPlayer().getWorld().refreshChunk(loc.getChunk().getX() + x, loc.getChunk().getZ() + z);
*/
                pbUtils.log("[onHeadLights]", "added head light to block");
            }
        } catch (NullPointerException e) {
            //pbUtils.log("[onHeadLights]", "2");
        } catch (NoSuchMethodException e) {
            pbUtils.log("[onHeadLights]", "couldnt get method!");
        } catch (IllegalAccessException e) {
            pbUtils.log("[onHeadLights]", "couldnt call method!");
        } catch (InvocationTargetException e) {
            pbUtils.log("[onHeadLights]", "couldnt use method!");
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        //Update lights when the head is broken
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        Location loc = event.getBlock().getLocation();
        if (event.getBlock().getType().equals(Material.SKULL)) {
            for (int x = -1; x <= 1; x++)
                for (int z = -1; z <= 1; z++) {
                    org.bukkit.Chunk chunk1 = event.getBlock().getWorld().getChunkAt(loc.getChunk().getX() + x, loc.getChunk().getZ() + z);
                    chunk1.unload();
                    chunk1.load();
                }
            /*Bukkit.getOnlinePlayers().forEach(p ->
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(
                            new PacketPlayOutMapChunk(((CraftChunk) loc.getChunk()).getHandle(), 20)
                    ));
            for (int x = -1; x <= 1; x++)
                for (int z = -1; z <= 1; z++)
                    event.getPlayer().getWorld().refreshChunk(loc.getChunk().getX() + x, loc.getChunk().getZ() + z);
        */
        }
    }
}
