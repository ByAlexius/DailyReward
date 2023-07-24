package net.byalexius.rewards.gui;

import net.byalexius.rewards.DailyRewards;
import net.byalexius.rewards.event.ReceiveRewardEvent;
import net.byalexius.rewards.helper.DataHelper;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.logging.Level;

public class RewardsGUI implements Listener {
    private Inventory inv;

    private final String GUI_NAME = DailyRewards.getInstance().getGUI_NAME();

    public RewardsGUI(Player p) {
        inv = Bukkit.createInventory(null, 27, GUI_NAME);

        initItems(p);
    }

    private void initItems(Player p) {
        ItemStack[] items = new ItemStack[7];
        FileConfiguration cfg = DailyRewards.getInstance().getCfg();

        if (p == null)
            return;

        String nameOfDay = DailyRewards.getInstance().getLocalization().getFileConfiguration().getString("nameOfDayInGui");

        items[0] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 1");
        items[1] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 2");
        items[2] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 3");
        items[3] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 4");
        items[4] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 5");
        items[5] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 6");
        items[6] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 7");

        if (cfg.getString("rewards.day1.lore") != null && !cfg.getString("rewards.day1.lore").trim().equals(""))
            items[0] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 1", cfg.getString("rewards.day1.lore"));

        if (cfg.getString("rewards.day2.lore") != null && !cfg.getString("rewards.day2.lore").trim().equals(""))
            items[1] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 2", cfg.getString("rewards.day2.lore"));

        if (cfg.getString("rewards.day3.lore") != null && !cfg.getString("rewards.day3.lore").trim().equals(""))
            items[2] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 3", cfg.getString("rewards.day3.lore"));

        if (cfg.getString("rewards.day4.lore") != null && !cfg.getString("rewards.day4.lore").trim().equals(""))
            items[3] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 4", cfg.getString("rewards.day4.lore"));

        if (cfg.getString("rewards.day5.lore") != null && !cfg.getString("rewards.day5.lore").trim().equals(""))
            items[4] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 5", cfg.getString("rewards.day5.lore"));

        if (cfg.getString("rewards.day6.lore") != null && !cfg.getString("rewards.day6.lore").trim().equals(""))
            items[5] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 6", cfg.getString("rewards.day6.lore"));

        if (cfg.getString("rewards.day7.lore") != null && !cfg.getString("rewards.day7.lore").trim().equals(""))
            items[6] = createGUIItem(Material.CHEST_MINECART, nameOfDay + " 7", cfg.getString("rewards.day7.lore"));

        for (int i = 0; i <= DataHelper.getLastDay(p) - 1; i++) {
            int d = i + 1;

            if (cfg.getString("rewards.day" + d + ".lore") != null && !cfg.getString("rewards.day" + d + ".lore").trim().equals(""))
                items[i] = createGUIItem(Material.MINECART, nameOfDay + " " + d, cfg.getString("rewards.day" + d + ".lore"));
            else
                items[i] = createGUIItem(Material.MINECART, nameOfDay + " " + d);
        }

        ItemStack glass = createGUIItem(Material.BLACK_STAINED_GLASS_PANE, " ");

        inv.setItem(10, items[0]);
        inv.setItem(11, items[1]);
        inv.setItem(12, items[2]);
        inv.setItem(13, items[3]);
        inv.setItem(14, items[4]);
        inv.setItem(15, items[5]);
        inv.setItem(16, items[6]);

        addGlassPlaceholder(glass);
    }

    private void addGlassPlaceholder(ItemStack glass) {
        inv.setItem(0, glass);
        inv.setItem(1, glass);
        inv.setItem(2, glass);
        inv.setItem(3, glass);
        inv.setItem(4, glass);
        inv.setItem(5, glass);
        inv.setItem(6, glass);
        inv.setItem(7, glass);
        inv.setItem(8, glass);
        inv.setItem(9, glass);
        inv.setItem(17, glass);
        inv.setItem(18, glass);
        inv.setItem(19, glass);
        inv.setItem(20, glass);
        inv.setItem(21, glass);
        inv.setItem(22, glass);
        inv.setItem(23, glass);
        inv.setItem(24, glass);
        inv.setItem(25, glass);
        inv.setItem(26, glass);
    }

    public void openGUI(Player p) {
        p.openInventory(inv);
    }

    protected ItemStack createGUIItem(final Material material, final String name, final String... lore) {
        final ItemStack itemStack = new ItemStack(material, 1);
        final ItemMeta meta = itemStack.getItemMeta();

        if (meta == null)
            return null;

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(GUI_NAME))
            return;

        e.setCancelled(true);

        FileConfiguration fc_loc = DailyRewards.getInstance().getLocalization().getFileConfiguration();

        final ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().isAir()) {
            return;
        }
        Player p = (Player) e.getWhoClicked();

        switch (clickedItem.getType()) {
            case MINECART -> {
                p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_ERROR() + fc_loc.getString("alreadyRedeemed"));
                e.getView().close();
            }
            case CHEST_MINECART -> {
                ItemMeta meta = clickedItem.getItemMeta();

                int day;

                if (meta == null) {
                    Bukkit.getLogger().log(Level.SEVERE, "The Item Meta on the Item that the user " + p.getName() + " (" + p.getUniqueId() + ")" + " selected was null!");
                    p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_ERROR() + fc_loc.getString("internalError"));
                    e.getView().close();
                    return;
                }

                String displayName = meta.getDisplayName();

                switch (displayName) {
                    case "Day 1" -> day = 1;
                    case "Day 2" -> day = 2;
                    case "Day 3" -> day = 3;
                    case "Day 4" -> day = 4;
                    case "Day 5" -> day = 5;
                    case "Day 6" -> day = 6;
                    case "Day 7" -> day = 7;
                    default -> day = -1;
                }

                if (day == -1) {
                    Bukkit.getLogger().log(Level.SEVERE, "The Item that the user " + p.getName() + " (" + p.getUniqueId() + ")" + " selected had a DisplayName that the Plugin did not recognize!");
                    p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_ERROR() + fc_loc.getString("internalError"));
                    e.getView().close();
                    return;
                }

                int lastDay = DataHelper.getLastDay(p);

                if (day <= lastDay) {
                    p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_ERROR() + fc_loc.getString("alreadyRedeemed"));
                    e.getView().close();
                    return;
                }

                if (lastDay != -1) {
                    if (DataHelper.hasItBeenDays(1, p) != null && Boolean.FALSE.equals(DataHelper.hasItBeenDays(1, p))) {
                        p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_ERROR() + fc_loc.getString("reusable").replace("%remainingTime%", DataHelper.getTimeUntilNextDay(LocalDateTime.now(), 1)));
                        e.getView().close();
                        return;
                    }
                }

                ReceiveRewardEvent receiveRewardEvent = null;

                switch (day) {
                    case 1 -> receiveRewardEvent = new ReceiveRewardEvent(p, 1);
                    case 2 -> receiveRewardEvent = new ReceiveRewardEvent(p, 2);
                    case 3 -> receiveRewardEvent = new ReceiveRewardEvent(p, 3);
                    case 4 -> receiveRewardEvent = new ReceiveRewardEvent(p, 4);
                    case 5 -> receiveRewardEvent = new ReceiveRewardEvent(p, 5);
                    case 6 -> receiveRewardEvent = new ReceiveRewardEvent(p, 6);
                    case 7 -> receiveRewardEvent = new ReceiveRewardEvent(p, 7);
                }

                Bukkit.getPluginManager().callEvent(receiveRewardEvent);

                if (!receiveRewardEvent.isCancelled()) {
                    DataHelper.addUserDataToFile(p, -1);
                } else {
                    p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_ERROR() + fc_loc.getString("cancelledByOtherPlugin"));
                    Bukkit.getLogger().info(DailyRewards.getInstance().getPREFIX() +  " " + fc_loc.getString("cancelledByOtherPlugin"));
                }

                e.getView().close();
                p.playNote(p.getLocation(), Instrument.PLING, Note.natural(1, Note.Tone.A));
                p.sendMessage(DailyRewards.getInstance().getPREFIX_CHATCOLOR() + DailyRewards.getInstance().getPREFIX() +  " " + DailyRewards.getInstance().getMESSAGE_CHATCOLOR_REDEEMED() + fc_loc.getString("redeemed"));

                // Used to block the User from using SHIFT + CLICK to take an item from custom inventory
                p.updateInventory();
            }
        }
    }


    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (!e.getView().getTitle().equals(GUI_NAME))
            e.setCancelled(true);
    }
}
