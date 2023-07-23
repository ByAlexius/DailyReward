package net.byalexius.rewards.helper;

import net.byalexius.rewards.DailyRewards;
import net.byalexius.rewards.config.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DataHelper {

    public static final String DATA_PREFIX = "rewards.";

    public static void addUserDataToFile(Player p, int day) {
        Config c = DailyRewards.getInstance().getRewardsData();
        FileConfiguration fc = DailyRewards.getInstance().getRewardsData().getFileConfiguration();

        c.reload();

        String s = fc.getString(DATA_PREFIX + p.getUniqueId() + ".lastDay");

        int lastDay = 0;

        if (s != null) {
            lastDay = Integer.parseInt(s);
        }

        if (day == -1) {
            switch (lastDay) {
                case 0 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 1);
                case 1 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 2);
                case 2 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 3);
                case 3 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 4);
                case 4 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 5);
                case 5 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 6);
                case 6 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 7);
                case 7 -> fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", 0);
            }
        } else {
            fc.set(DATA_PREFIX + p.getUniqueId() + ".lastDay", day);
        }

        LocalDateTime now = LocalDateTime.now();

        String pattern = "dd/M/yyyy H:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        fc.set(DATA_PREFIX + p.getUniqueId() + ".lastUse", now.format(formatter));

        c.save();
        c.reload();
    }

    public static int getLastDay(Player p) {
        Config c = DailyRewards.getInstance().getRewardsData();
        FileConfiguration fc = DailyRewards.getInstance().getRewardsData().getFileConfiguration();

        c.reload();

        String s = fc.getString(DATA_PREFIX + p.getUniqueId() + ".lastDay");

        if (s == null || s.trim().equals("")) {
            return -1;
        }

        return Integer.parseInt(s);
    }

    public static Boolean hasItBeenDays(int days, Player p) {
        Config c = DailyRewards.getInstance().getRewardsData();
        FileConfiguration fc = DailyRewards.getInstance().getRewardsData().getFileConfiguration();

        c.reload();

        String s = fc.getString(DATA_PREFIX + p.getUniqueId() + ".lastUse");

        if (s == null || s.trim().equals("")) {
            return null;
        }

        String pattern = "dd/M/yyyy H:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime storedDate = LocalDateTime.parse(s, formatter);

        LocalDateTime now = LocalDateTime.now();

        long daysDifference = ChronoUnit.DAYS.between(storedDate, now);

        return daysDifference == days;
    }

    public static String getTimeUntilNextDay(LocalDateTime now, int day) {

        String h = DailyRewards.getInstance().getLocalization().getFileConfiguration().getString("timeUntilNextReceiveHours");
        String m = DailyRewards.getInstance().getLocalization().getFileConfiguration().getString("timeUntilNextReceiveMinute");
        String s2 = DailyRewards.getInstance().getLocalization().getFileConfiguration().getString("timeUntilNextReceiveSecond");

        LocalDateTime morning = now.toLocalDate().plusDays(day).atTime(LocalTime.of(0,0));
        long s = ChronoUnit.SECONDS.between(now, morning);

        long hours = s / 3600;
        long minutes = (s % 3600) / 60;
        long seconds = s % 60;

        return hours + " " + h +  " " + minutes + " " + m +  " " + seconds + " " + s2;
    }
}
