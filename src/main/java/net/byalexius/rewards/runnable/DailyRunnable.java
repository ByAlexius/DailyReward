package net.byalexius.rewards.runnable;

import net.byalexius.rewards.DailyRewards;
import net.byalexius.rewards.helper.DataHelper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class DailyRunnable extends BukkitRunnable {
    @Override
    public void run() {
        ConfigurationSection rewardsSection = DailyRewards.getInstance().getRewardsData().getFileConfiguration().getConfigurationSection("rewards");
        FileConfiguration fc = DailyRewards.getInstance().getRewardsData().getFileConfiguration();

        final String DATA_PREFIX = "rewards.";

        if (rewardsSection == null)
            return;

        for (String uuid : rewardsSection.getKeys(false)) {
            String lastUse = fc.getString(DATA_PREFIX + uuid + ".lastUse");

            if (lastUse == null)
                continue;

            if (DataHelper.daysPassed(lastUse) <= 1) {
                continue;
            }

            fc.set(DATA_PREFIX + uuid + ".lastDay", "0");
        }
    }
}
