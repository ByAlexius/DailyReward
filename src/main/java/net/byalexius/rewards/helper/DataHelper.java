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

    private static final String DATA_PREFIX = "rewards.";
    private static final String LD_DATA_SUFFIX = ".lastDay";
    private static final String LU_DATA_SUFFIX = ".lastUse";

    private static final String PATTERN = "dd/MM/yyyy HH:mm:ss";

    public static void addUserDataToFile(Player p, int day) {
        Config c = DailyRewards.getInstance().getRewardsData();
        FileConfiguration fc_data = DailyRewards.getInstance().getRewardsData().getFileConfiguration();

        c.reload();

        String s = fc_data.getString(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX);

        int lastDay = 0;

        if (s != null) {
            lastDay = Integer.parseInt(s);
        }

        if (day == -1) {
            switch (lastDay) {
                case 0 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 1);
                case 1 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 2);
                case 2 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 3);
                case 3 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 4);
                case 4 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 5);
                case 5 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 6);
                case 6 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 7);
                case 7 -> fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, 0);
            }
        } else {
            fc_data.set(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX, day);
        }

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

        fc_data.set(DATA_PREFIX + p.getUniqueId() + LU_DATA_SUFFIX, now.format(formatter));

        c.save();
        c.reload();
    }

    public static int getLastDay(Player p) {
        Config c = DailyRewards.getInstance().getRewardsData();
        FileConfiguration fc_data = DailyRewards.getInstance().getRewardsData().getFileConfiguration();

        c.reload();

        String s = fc_data.getString(DATA_PREFIX + p.getUniqueId() + LD_DATA_SUFFIX);

        if (s == null || s.trim().equals("")) {
            return -1;
        }

        return Integer.parseInt(s);
    }

    public static Boolean hasItBeenDays(int days, Player p) {
        Config c = DailyRewards.getInstance().getRewardsData();
        FileConfiguration fc_data = DailyRewards.getInstance().getRewardsData().getFileConfiguration();

        c.reload();

        String s = fc_data.getString(DATA_PREFIX + p.getUniqueId() + LU_DATA_SUFFIX);

        if (s == null || s.trim().equals("")) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime storedDate = LocalDateTime.parse(s, formatter);

        LocalDateTime now = LocalDateTime.now();

        long daysDifference = ChronoUnit.DAYS.between(storedDate, now);

        return daysDifference == days;
    }

    public static long daysPassed(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime storedDate = LocalDateTime.parse(date, formatter);

        LocalDateTime now = LocalDateTime.now();

        return ChronoUnit.DAYS.between(storedDate, now);
    }

    public static String getTimeUntilNextDay(LocalDateTime now, int day) {
        FileConfiguration fc_loc = DailyRewards.getInstance().getLocalization().getFileConfiguration();

        String h = fc_loc.getString("timeUntilNextReceiveHours");
        String m = fc_loc.getString("timeUntilNextReceiveMinute");
        String s2 = fc_loc.getString("timeUntilNextReceiveSecond");

        LocalDateTime morning = now.toLocalDate().plusDays(day).atTime(LocalTime.of(0,0));
        long s = ChronoUnit.SECONDS.between(now, morning);

        long hours = s / 3600;
        long minutes = (s % 3600) / 60;
        long seconds = s % 60;

        return hours + " " + h +  " " + minutes + " " + m +  " " + seconds + " " + s2;
    }
}
