package net.byalexius.rewards;

import lombok.Getter;
import net.byalexius.rewards.commands.RewardsCommand;
import net.byalexius.rewards.config.Config;
import net.byalexius.rewards.gui.RewardsGUI;
import net.byalexius.rewards.helper.ColorHelper;
import net.byalexius.rewards.helper.DailyRunnable;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DailyRewards extends JavaPlugin {

    @Getter
    private static DailyRewards Instance;

    @Getter
    private final FileConfiguration cfg = this.getConfig();

    @Getter
    private Config rewardsData;

    @Getter
    private Config localization;

    @Getter
    private String PREFIX;

    @Getter
    private String GUI_NAME;

    @Getter
    private ChatColor PREFIX_CHATCOLOR;

    @Getter
    private ChatColor MESSAGE_CHATCOLOR_REDEEMED;

    @Getter
    private ChatColor MESSAGE_CHATCOLOR_ERROR;

    @Override
    public void onEnable() {
        Instance = this;
        saveDefaultConfig();
        rewardsData = new Config("data.yml", getDataFolder());

        localization = new Config("messages.yml", getDataFolder());
        addDefaultForLocalization();

        PREFIX = localization.getFileConfiguration().getString("pluginPrefix");

        String cc_string = cfg.getString("colorOfMessagePrefix");

        initCustomColors(cc_string);

        initCustomName();

        if (!getCfg().getBoolean("enabled")) {
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        registerListeners();
        registerCommands();

        runDailyTask();
    }

    private void initCustomName() {
        String CGui = cfg.getString("guiName");

        if (CGui == null || CGui.trim().equals(""))
            GUI_NAME = "Daily Rewards";
        else
            GUI_NAME = CGui.trim();
    }

    private void initCustomColors(String cc_string) {

        // PREFIX COLOR
        if (cc_string == null || cc_string.trim().equals(""))
            PREFIX_CHATCOLOR = ChatColor.GREEN;
        else
            PREFIX_CHATCOLOR = ColorHelper.stringToChatColor(cc_string);

        // REDEEMED MESSAGE COLOR
        String mcc_string = cfg.getString("colorOfRedeemedMessage");

        if (mcc_string == null || mcc_string.trim().equals(""))
            MESSAGE_CHATCOLOR_REDEEMED = ChatColor.GOLD;
        else
            MESSAGE_CHATCOLOR_REDEEMED = ColorHelper.stringToChatColor(mcc_string);

        // ERROR MESSAGE COLOR
        String mcce_string = cfg.getString("colorOfErrorMessage");

        if (mcce_string == null || mcce_string.trim().equals(""))
            MESSAGE_CHATCOLOR_ERROR = ChatColor.RED;
        else
            MESSAGE_CHATCOLOR_ERROR = ColorHelper.stringToChatColor(mcce_string);
    }

    private void addDefaultForLocalization() {
        localization.getFileConfiguration().addDefault("pluginPrefix", "[Rewards]");
        localization.getFileConfiguration().addDefault("alreadyRedeemed", "You have already redeemed your daily reward!");
        localization.getFileConfiguration().addDefault("noPerms", "You do not have enough permissions to execute this command!");
        localization.getFileConfiguration().addDefault("mustBeAPlayer", "You must be a player to execute this command!");
        localization.getFileConfiguration().addDefault("internalError", "An internal error occurred, please contact a Server Administrator!");
        localization.getFileConfiguration().addDefault("cancelledByOtherPlugin", "The reward was canceled by another plugin!");
        localization.getFileConfiguration().addDefault("reusable", "You can redeem your next reward in %remainingTime%.");
        localization.getFileConfiguration().addDefault("redeemed", "You've redeemed your daily reward!");
        localization.getFileConfiguration().addDefault("timeUntilNextReceiveHours", "Hour/s");
        localization.getFileConfiguration().addDefault("timeUntilNextReceiveMinute", "Minute/s");
        localization.getFileConfiguration().addDefault("timeUntilNextReceiveSecond", "Second/s");
        localization.getFileConfiguration().addDefault("nameOfDayInGui", "Day");
        localization.getFileConfiguration().options().copyDefaults(true);
        localization.save();
    }

    private void runDailyTask() {
        long delayTicks = 0;
        long periodTicks = 20L * 60L * 60L * 24L;

        DailyRunnable runnable = new DailyRunnable();
        runnable.runTaskTimer(this, delayTicks, periodTicks);
    }

    private void registerListeners() {
        PluginManager pl = this.getServer().getPluginManager();

        pl.registerEvents(new RewardsGUI(null), this);
    }

    private void registerCommands() {
        this.getCommand("daily").setExecutor(new RewardsCommand());
    }
}
