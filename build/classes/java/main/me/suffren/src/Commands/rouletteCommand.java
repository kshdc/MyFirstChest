package me.suffren.src.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.FireworkEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class rouletteCommand implements CommandExecutor, Listener {
    private final Map<UUID, Long> lastCommandUse = new HashMap<>();
    private final long cooldownTime = 6 * 60 * 60 * 1000; // 6 heures en millisecondes
    private final JavaPlugin plugin;
    private Inventory rouletteGUI;
    private ItemStack grayGlassPane;
    private ItemStack winItem;
    private ItemStack steakItem;
    private ItemStack waterBucketItem;
    private ItemStack witherHeadItem;
    private ItemStack enchantedBookItem;
    private ItemStack IRON_INGOT;
    private ItemStack BLOC_DIRT;
    private ItemStack Sword_Diamond;
    private ItemStack GOLD_INGOT;
    private final Random random;


    public rouletteCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        rouletteGUI = Bukkit.createInventory(null, 27, "Roulette by Zhq_");

        winItem = new ItemStack(Material.DIAMOND);
        ItemMeta winMeta = winItem.getItemMeta();
        if (winMeta != null) {
            winMeta.setDisplayName("Gain");
            winItem.setItemMeta(winMeta);
        }

        steakItem = new ItemStack(Material.COOKED_BEEF);
        ItemMeta steakMeta = steakItem.getItemMeta();
        if (steakMeta != null) {
            steakMeta.setDisplayName("Steak Cuit");
            steakItem.setItemMeta(steakMeta);
        }

        GOLD_INGOT = new ItemStack(Material.GOLD_INGOT);
        ItemMeta goldMeta = GOLD_INGOT.getItemMeta();
        if (goldMeta != null) {
            goldMeta.setDisplayName("Gain");
            GOLD_INGOT.setItemMeta(goldMeta);
        }

        IRON_INGOT = new ItemStack(Material.IRON_INGOT);
        ItemMeta ironMeta = IRON_INGOT.getItemMeta();
        if (ironMeta != null) {
            ironMeta.setDisplayName("Lingot de Fer");
            IRON_INGOT.setItemMeta(ironMeta);
        }

        Sword_Diamond = new ItemStack(Material.LEGACY_DIAMOND_SWORD);
        ItemMeta swordMeta = Sword_Diamond.getItemMeta();
        if (swordMeta != null) {
            swordMeta.setDisplayName("Sword Diamond");
        }

        waterBucketItem = new ItemStack(Material.WATER_BUCKET);
        ItemMeta waterBucketMeta = waterBucketItem.getItemMeta();
        if (waterBucketMeta != null) {
            waterBucketMeta.setDisplayName("Sceau d'eau");
            waterBucketItem.setItemMeta(waterBucketMeta);
        }

        witherHeadItem = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta witherHeadMeta = witherHeadItem.getItemMeta();
        if (witherHeadMeta != null) {
            witherHeadMeta.setDisplayName("Wither Head");
            witherHeadItem.setItemMeta(witherHeadMeta);
        }

        BLOC_DIRT = new ItemStack(Material.DIRT);
        ItemMeta BLOC_DIRTMeta = BLOC_DIRT.getItemMeta();
        if (BLOC_DIRTMeta != null) {
            BLOC_DIRTMeta.setDisplayName("Block Dirt");
        }


        enchantedBookItem = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBookItem.getItemMeta();
        if (bookMeta != null) {
            bookMeta.addStoredEnchant(Enchantment.SHARPNESS, 2, true);
            bookMeta.setDisplayName("Livre Enchanté");
            enchantedBookItem.setItemMeta(bookMeta);
        }

        grayGlassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = grayGlassPane.getItemMeta();
        if (glassMeta != null) {
            glassMeta.setDisplayName("§7.");
            glassMeta.setCustomModelData(1);
            grayGlassPane.setItemMeta(glassMeta);
        }

        int size = rouletteGUI.getSize();
        for (int i = 0; i < size; i++) {
            if (rouletteGUI.getItem(i) == null) {
                rouletteGUI.setItem(i, grayGlassPane);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            UUID playerId = player.getUniqueId();
            long currentTime = System.currentTimeMillis();
            if (lastCommandUse.containsKey(playerId)) {
                long lastUseTime = lastCommandUse.get(playerId);
                long timeElapsed = currentTime - lastUseTime;

                if (timeElapsed < cooldownTime) {
                    long timeLeft = cooldownTime - timeElapsed;
                    long hoursLeft = timeLeft / (60 * 60 * 1000);
                    long minutesLeft = (timeLeft / (60 * 1000)) % 60;
                    long secondsLeft = (timeLeft / 1000) % 60;
                    player.sendMessage("§cVous devez attendre encore " + hoursLeft + " heures, " + minutesLeft + " minutes, et " + secondsLeft + " secondes.");
                    return true;
                }
            }

            player.openInventory(rouletteGUI);
            startAnimation(player);

            lastCommandUse.put(playerId, currentTime);
            return true;
        }

        sender.sendMessage("§cCette commande ne peut être exécutée que par un joueur.");
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(rouletteGUI)) {
            event.setCancelled(true);
        }
    }

    private void startAnimation(Player player) {
        new BukkitRunnable() {
            int ticksPassed = 0;
            final ItemStack[] items = new ItemStack[]{winItem, steakItem, witherHeadItem, GOLD_INGOT, waterBucketItem, Sword_Diamond, enchantedBookItem, IRON_INGOT, BLOC_DIRT};

            @Override
            public void run() {
                if (ticksPassed >= 10) {
                    giveRandomPrize(player);
                    cancel();
                    return;
                }

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f + (ticksPassed * 0.1f));

                rouletteGUI.setItem(13, items[ticksPassed % items.length]);
                ticksPassed++;
            }
        }.runTaskTimer(plugin, 0L, 5L);
    }
    private void giveRandomPrize(Player player) {
        int totalRewards = 9;
        int randomNumber = random.nextInt(totalRewards);

        ItemStack prize;

        switch (randomNumber) {
            case 0:
                prize = winItem;
                break;
            case 1:
                prize = steakItem;
                break;
            case 2:
                prize = waterBucketItem;
                break;
            case 3:
                prize = witherHeadItem;
                break;
            case 4:
                prize = enchantedBookItem;
                break;
            case 5:
                prize = IRON_INGOT;
                break;
            case 6:
                prize = BLOC_DIRT;
                break;
            case 7:
                prize = Sword_Diamond;
                break;
            case 8:
                prize = GOLD_INGOT;
                break;
            default:
                prize = null;
                break;
        }

        rouletteGUI.setItem(13, prize);
        player.updateInventory();

        Sound victorySound = Sound.ENTITY_PLAYER_LEVELUP;
        Sound LooseSound = Sound.ENTITY_PLAYER_TELEPORT;
        String announcementMessage = null;

        if (prize.isSimilar(winItem)) {
            player.getInventory().addItem(new ItemStack(Material.DIAMOND, 4));
            player.sendMessage("§aVous avez gagné 4 diamants!");
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
            announcementMessage = "§a" + player.getName() + " a gagné 4 diamants!";
        } else if (prize.isSimilar(steakItem)) {
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 10));
            player.sendMessage("§aVous avez gagné 10 steaks cuits!");
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
        } else if (prize.isSimilar(waterBucketItem)) {
            player.teleport(player.getLocation().add(0, 200, 0));
            player.sendTitle(player.getName(), "§cVous avez perdu..");
            player.playSound(player.getLocation(), LooseSound, 1.0f, 1.0f);
        } else if (prize.isSimilar(witherHeadItem)) {
            player.giveExp(160);
            player.sendMessage("§aVous avez gagné 10 points d'XP");
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
            announcementMessage = "§a" + player.getName() + " a gagné 10 points d'XP!";
        } else if (prize.isSimilar(enchantedBookItem)) {
            ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta enchantedMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
            announcementMessage = "§a" + player.getName() + " a gagné un livre enchanté avec Sharpness II";
            if (enchantedMeta != null) {
                enchantedMeta.addStoredEnchant(Enchantment.SHARPNESS, 2, true);
                enchantedMeta.setDisplayName("Livre Enchanté");
                enchantedBook.setItemMeta(enchantedMeta);
            }
            player.getInventory().addItem(enchantedBook);
            player.sendMessage("§aVous avez gagné un livre enchanté avec Sharpness II");
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
        } else if (prize.isSimilar(IRON_INGOT)) {
            player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 16));
            player.sendMessage("§aVous avez gagné 16 lingots de fer!");
            announcementMessage = "§a" + player.getName() + " a gagné 16 lingots de fer!";
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
        } else if (prize.isSimilar(BLOC_DIRT)) {
            player.getInventory().addItem(new ItemStack(Material.DIRT, 64));
            player.sendMessage("§aVous avez gagné 64 bloc de terre!");
            announcementMessage = "§a" + player.getName() + " a gagné 64 bloc de terre!";
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
        }
        else if (prize.isSimilar(Sword_Diamond)) {
            player.setHealth(0.0);
            player.sendMessage("§cVous avez été tué par la roulette!");
        } else if (prize.isSimilar(GOLD_INGOT)) {
            player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 16));
            player.sendMessage("§aVous avez gagné 16 lingots de fer!");
            announcementMessage = "§a" + player.getName() + " a gagné 16 lingots d'or!";
            player.playSound(player.getLocation(), victorySound, 1.0f, 1.0f);
        }

        if (announcementMessage != null) {
            Bukkit.broadcastMessage(announcementMessage);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.closeInventory();
            launchFireworks(player);
        }, 40L);
    }
    private void launchFireworks(Player player) {
        for (int i = 0; i < 10; i++) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                FireworkEffect effect = FireworkEffect.builder()
                        .withColor(Color.RED, Color.BLUE, Color.GREEN)
                        .with(FireworkEffect.Type.BALL)
                        .withFlicker()
                        .withTrail()
                        .build();

                fireworkMeta.addEffect(effect);
                fireworkMeta.setPower(1);
                firework.setFireworkMeta(fireworkMeta);
            }, i * 10L);
        }
    }
}
