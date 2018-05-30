package planB.mc.val.pbUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import planB.mc.val.barrier.pbBarrier;
import planB.mc.val.lightSense.pbLighter;
import planB.mc.val.pbAdvancements.Block;
import planB.mc.val.shulker.pbShulker;
import planB.mc.val.unreleased.pbChests.pbChests;

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
                    case "lights": {
                        return pblights(args, sender);
                    }
                    case "adv": {
                        return pbadvancements(args, sender);
                    }
                    case "chest": {
                        return pbChest(args, sender);
                    }
                    default: {
                        sender.sendMessage(ChatColor.RED + "command not found!!");
                        return true;
                    }
                }
            } else if (cmd.getName().equalsIgnoreCase("blame")) {
                return blame(args, sender);
            }
            return false;
        }
        return false;
    }

    private boolean pbChest(String[] args, CommandSender sender) {
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Usage includes: save | load");
            return true;
        }
        switch (args[1]) {
            case "load": {
                if (sender.hasPermission("planb.opped")) {
                  //  pbChests.loadChests();
                    return true;
                } else noPermMsg(sender);
                break;
            }
            case "save": {
                if (sender.hasPermission("planb.opped")) {
                  //  pbChests.saveChests();
                    return true;
                } else noPermMsg(sender);
                break;
            }
        }
        return false;
    }

    private boolean pbadvancements(String[] args, CommandSender sender) {
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Usage includes: reload | reset | toggle[true|false] | print[done|todo]");
            return true;
        }
        switch (args[1]) {
            case "reset": {
                if (sender.hasPermission("planb.opped")) {
                   // Block.resetConfig();
                    return true;
                } else noPermMsg(sender);
                break;
            }
            case "reload": {
                if (sender.hasPermission("planb.opped")) {
                   // Block.reloadConfig();
                    return true;
                } else noPermMsg(sender);
                break;
            }
            case "toggle": {
                if (sender.hasPermission("planb.opped")) {
                    switch (args[2]) {
                        case "true": {
                           // Block.toggle(true);
                            return true;
                        }
                        case "false": {
                            //Block.toggle(false);
                            return true;
                        }
                        default: {
                            sender.sendMessage(ChatColor.RED + "Use /planb adv toggle [true|false]");
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

    private boolean pblights(String[] args, CommandSender sender) {
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Usage includes: true | false");
            return true;
        }
        try {
            switch (args[1]) {
                case "off":
                case "on": {
                    if (sender.hasPermission("planb.all")) {
                        Boolean val = args[1].equalsIgnoreCase("on") ? true : false;
                        pbLighter.toggle(sender, val);
                        sender.sendMessage(ChatColor.GOLD + "lights are now switched " + args[1]);
                        return true;
                    } else noPermMsg(sender);
                    break;
                }
                case "print": {
                    if (sender.hasPermission("planb.opped")) {
                        pbLighter.printAll();
                        return true;
                    } else noPermMsg(sender);
                    break;
                }
                case "settime": {
                    if (sender.hasPermission("planb.opped")) {
                        if (args.length < 3) throw new ArrayIndexOutOfBoundsException();
                        else try {
                            pbLighter.setCooldown(Integer.parseInt(args[2]));
                        } catch (NumberFormatException e){
                            sender.sendMessage(ChatColor.RED + "Please enter a valid number!");
                        } catch (ArrayIndexOutOfBoundsException e){
                            sender.sendMessage(ChatColor.RED + "Not enough parameters!");
                        }
                        return true;
                    } else noPermMsg(sender);
                    break;
                }
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Usage includes: true | false");
            return false;
        }
        return false;
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
