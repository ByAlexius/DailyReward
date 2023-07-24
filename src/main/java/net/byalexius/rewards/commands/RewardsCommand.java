package net.byalexius.rewards.commands;

import net.byalexius.rewards.DailyRewards;
import net.byalexius.rewards.gui.RewardsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RewardsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            try {
                sender.sendMessage(DailyRewards.getInstance().getPREFIX() +  " " + Objects.requireNonNull(DailyRewards.getInstance().getLocalization().getFileConfiguration().getString("mustBeAPlayer")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        Player p = (Player) sender;
        
        if (!p.hasPermission("daily.use")) {
            p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_ERROR() + DailyRewards.getInstance().getLocalization().getFileConfiguration().getString("noPerms"));
            return false;
        }

        RewardsGUI gui = new RewardsGUI(p);

        gui.openGUI(p);

        DailyRewards.getInstance().getRewardsData().getFileConfiguration().set("rewards." + p.getUniqueId() + ".lastDay", "0");

        return true;
    }
}
