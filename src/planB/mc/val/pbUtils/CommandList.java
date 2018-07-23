package planB.mc.val.pbUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import planB.mc.val.barrier.pbBarrier;
import planB.mc.val.pbAdvancements.Block;
import planB.mc.val.pbUtils.pbPlayerLogger.pbGriefLogger;
import planB.mc.val.shulker.pbShulker;

public class CommandList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("planb")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.GOLD + "Usage is /planb shulker | barrier | lights | adv");
                    return true;
                }
                switch (args[0]) {
                    case "shulker": {
                        return pbshulkers(args, sender);
                    }
                    case "barrier": {
                        return pbbarrier(args, sender);
                    }
                    case "adv": {
                        return pbadvancements(args, sender);
                    }
                    case "grief": {
                        return pbGrief(args, sender);
                    }
                    default: {
                        sender.sendMessage(ChatColor.RED + "command not found!!");
                        return true;
                    }
                }
            } else if (cmd.getName().equalsIgnoreCase("blame")) {
                return blame(args, sender);
            } else if (cmd.getName().equalsIgnoreCase("poke")) {
                return poke(args, sender);
            }
            return false;
        }
        return false;
    }

    private boolean pbGrief(String[] args, CommandSender sender) {
        if (!sender.hasPermission("planb.opped")) {
            noPermMsg(sender);
            return false;
        }
        if (args.length == 0)
            return true;
        pbGriefLogger.updateTime(args[1], sender);
        return true;
    }

    private boolean poke(String[] args, CommandSender sender) {
        if (!sender.hasPermission("planb.opped")) {
            noPermMsg(sender);
            return false;
        }
        if (args.length == 0)
            return true;
        pbUtils.doPoke(args[0], sender);
        return true;
    }

    private boolean pbadvancements(String[] args, CommandSender sender) {
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Usage print[done|todo]");
            return true;
        }
        switch (args[1]) {
            case "give":{
                if (sender.hasPermission("planb.opped")) {
                    if (args.length == 2) {
                        sender.sendMessage(ChatColor.RED + "Use /planb give [all|done|todo]");
                        return true;
                    }
                    switch (args[2]) {
                        case "done": {
                            Block.print(sender, "done");
                            return true;
                        }
                        case "todo": {
                            Block.print(sender, "todo");
                            return true;
                        }
                        case "all": {
                            Block.print(sender, "all");
                            return true;
                        }
                        default: {
                            sender.sendMessage(ChatColor.RED + "Use /planb give [all|done|todo]");
                            return true;
                        }
                    }
                } else noPermMsg(sender);
                break;
            }
            case "print": {
                if (sender.hasPermission("planb.all")) {
                    if (args.length == 2) {
                        sender.sendMessage(ChatColor.RED + "Use /planb print [done|todo]");
                        return true;
                    }
                    switch (args[2]) {
                        case "done": {
                            Block.print(sender, "done");
                            return true;
                        }
                        case "todo": {
                            Block.print(sender, "todo");
                            return true;
                        }
                        default: {
                            sender.sendMessage(ChatColor.RED + "Use /pta print [done|todo]");
                            return true;
                        }
                    }
                } else noPermMsg(sender);
                break;
            }
        }
        return false;
    }

    private boolean blame(String[] args, CommandSender sender) {
        if (!sender.hasPermission("planb.all")) {
            noPermMsg(sender);
            return false;
        }
        if (args.length == 0)
            return true;
        pbUtils.doBlame(args[0]);
        return true;
    }

    private boolean pbbarrier(String[] args, CommandSender sender) {
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Usage includes: settime | magic");
            return true;
        }

        switch (args[1]) {
            case "settime": {
                if (sender.hasPermission("planb.opped")) {
                    try {
                        pbBarrier.setEffectTime(Integer.parseInt(args[2]));
                        sender.sendMessage(ChatColor.GOLD + "Time is set!");
                    } catch (Exception e) {
                        sender.sendMessage(ChatColor.RED + "Invalid number");
                    }
                    return true;
                } else noPermMsg(sender);
                break;
            }
            case "magic": {
                if (sender.hasPermission("planb.all")) {
                    pbBarrier.setPrefTool(sender);
                    return true;
                } else noPermMsg(sender);
                break;
            }
        }
        return false;
    }

    private boolean pbshulkers(String[] args, CommandSender sender) {
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Usage includes: stats | setmax | reset");
            return true;
        }
        switch (args[1]) {
            case "stats": {
                if (sender.hasPermission("planb.all")) {
                    sender.sendMessage(ChatColor.GOLD + pbShulker.getRemainder());
                    return true;
                } else noPermMsg(sender);
                break;
            }
            case "reset": {
                if (sender.hasPermission("planb.opped")) {
                    sender.sendMessage(ChatColor.GOLD + "Resseting Shulker stats!");
                    pbShulker.reset();
                    return true;
                } else noPermMsg(sender);
                break;
            }
            case "setmax": {
                if (sender.hasPermission("planb.opped")) {
                    System.out.println("doing reset");
                    try {
                        pbShulker.setMax(Integer.parseInt(args[2]));
                        sender.sendMessage(ChatColor.GOLD + pbShulker.getRemainder());
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Invalid number :\\");
                    }
                    return true;
                } else noPermMsg(sender);
                break;
            }
        }
        return false;
    }

    private void noPermMsg(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You dont have the permission to use this planB command!");
    }
}
