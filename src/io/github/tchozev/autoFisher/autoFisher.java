package io.github.tchozev.autoFisher;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class autoFisher extends JavaPlugin implements Listener{

	public void onDisable() {	
	}
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	// Check to see if an inventory closed
	@EventHandler
	public void openInventory(InventoryCloseEvent event) {
		
		getLogger().info("Inventory Close Event");
		
		// Check to see if it was a furnace
		if (event.getInventory().getType() == InventoryType.FURNACE) {
			
			getLogger().info("Furnace Found");

			Inventory inven = event.getInventory();
			// Check contents for Fishing Rod and the bait
			if (inven.contains(Material.FISHING_ROD, 1)) {
				if (inven.contains(Material.SPIDER_EYE)) {
					
					getLogger().info("Furnace Contents OK");
					
					
					
					
					// Find the furnace and check to see if water is nearby
					Location loc = event.getPlayer().getLocation();
					boolean fisherReady = false;
					int range = 4;
					
					getLogger().info("Locating Furnace...");
					
					// By searching over nearby blocks within range:
					for (int i = loc.getBlockX() - range; i < loc.getBlockX() + range; i++) {
						for (int j = loc.getBlockY() - range; j < loc.getBlockY() + range; j++) {
							for (int k = loc.getBlockZ() - range; k < loc.getBlockZ() + range; k++) {
								// Set the coordinates with the test:
								loc.setX(i);
								loc.setY(j);
								loc.setZ(k);
								// Check that location for furnace:
								World w = loc.getWorld();
								// Check if it's a furnace block
								if (w.getBlockAt(loc).getTypeId() == 61) {
									
									getLogger().info("... Found Furnace!");
									
									// Search nearby for water:
									loc.setY(loc.getBlockY() - 1);
									// Within this range:
									int waterRange = 2;
									// Search within the area
									
									getLogger().info("Looking around Furnace for water...");
									
									for (int iWater = loc.getBlockX() - range; iWater < loc.getBlockX() + waterRange; iWater++) {
										for (int kWater = loc.getBlockZ() - range; kWater < loc.getBlockZ() + waterRange; kWater++) {
											loc.setX(iWater);
											loc.setZ(kWater);
											// Check that location for Water:
											World wWater = loc.getWorld();
											// Check if it's a water block
											if (wWater.getBlockAt(loc).isLiquid()) {
												
												getLogger().info("... Furnace is close to water, ready to fish!");
												fisherReady = true;	
											}
										}
									}
								}	
							}
						}
					}
					
					
					getLogger().info("Acceptable Furnace");
					
					if (fisherReady) {
						
						getLogger().info("LET THE FISHING COMMENCE");

						// All conditions have been met, so let's FISH!
						while (inven.contains(Material.SPIDER_EYE)) {
	
							// Remove a spider eye
							inven.remove(Material.SPIDER_EYE);
							inven.addItem(new ItemStack(Material.RAW_FISH));
							// WOOHOO
						}
					}
					
					
				}
			}	
		}	
	}
}
