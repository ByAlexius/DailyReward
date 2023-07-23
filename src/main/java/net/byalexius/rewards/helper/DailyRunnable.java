package net.byalexius.rewards.helper;

import net.byalexius.rewards.DailyRewards;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

public class DailyRunnable extends BukkitRunnable {
    @Override
    public void run() {
        ConfigurationSection rewardsSection = DailyRewards.getInstance().getRewardsData().getFileConfiguration().getConfigurationSection("rewards");

        if (rewardsSection == null)
            return;

        for (String uuid : rewardsSection.getKeys(false)) {
            String lastUse = DailyRewards.getInstance().getRewardsData().getFileConfiguration().getString("rewards." + uuid + ".lastUse");

            if (lastUse == null)
                return;

            if (DataHelper.daysPassed(lastUse) <= 1) {
                continue;
            }

            DailyRewards.getInstance().getRewardsData().getFileConfiguration().set("rewards." + uuid + ".lastDay", "0");
        }
    }
}
