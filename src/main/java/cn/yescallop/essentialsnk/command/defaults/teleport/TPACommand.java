package cn.yescallop.essentialsnk.command.defaults.teleport;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;

public class TPACommand extends CommandBase {

    public TPACommand(EssentialsAPI api) {
        super("tpa", api);
        this.setAliases(new String[]{"call", "tpask"});
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.ingame"));
            return false;
        }
        if (args.length != 1) {
            this.sendUsage(sender);
            return false;
        }
        Player player = api.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.player.notfound", args[0]));
            return false;
        }
        if (player.getName().equals(((Player) sender).getName())) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.tpa.self"));
            return false;
        }
        if (api.hasTPRequestBetween((Player) sender, player)) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.tpa.exists"));
            return false;
        }
        api.requestTP((Player) sender, player, true);
        player.sendMessage(lang.translateString("commands.tpa.invite"));
        sender.sendMessage(lang.translateString("commands.tpa.sent"));
        return true;
    }
}