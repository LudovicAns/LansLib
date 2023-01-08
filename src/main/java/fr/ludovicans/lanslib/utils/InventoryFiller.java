package fr.ludovicans.lanslib.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Prototype.
 */
@SuppressWarnings("unused")
public class InventoryFiller {

    @NotNull private final Map<ItemStack, Integer> itemFillMap;

    @NotNull private final Inventory inventory;

    private final int roll;

    public InventoryFiller(@NotNull final Map<ItemStack, Integer> itemFillMap,
                           @NotNull final Inventory inventory,
                           final int roll) {
        this.itemFillMap = itemFillMap;
        this.inventory = inventory;
        this.roll = roll;
    }

    /**
     * Fill inventory following {@link InventoryFiller#itemFillMap}.
     * Items will be added to the first free slots.
     */
    public void fill() {
        for (int i = roll; i > 0; i--) {
            // By making the sum of all percentage we can now randomly generate a number to get a random item inside itemFillMap.
            final int randomNumberRange = itemFillMap
                    .values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            // Random number that represent a cursor for the itemFillMap.
            final int mapCursor = (int) Math.floor(Math.random() * randomNumberRange);

            // Looking for correspondance.
            boolean added = false;
            int count = 0;
            for (Map.Entry<ItemStack, Integer> entry : itemFillMap.entrySet()) {
                if (entry.getValue() + count >= mapCursor && !added) {
                    // Add item in inventory.
                    inventory.addItem(entry.getKey());
                    added = true;
                } else {
                    count += entry.getValue();
                }
            }
        }
    }

    /**
     * Fill inventory following {@link InventoryFiller#itemFillMap}.
     * Items will be added randomly in free slots
     */
    public void fillRandomly() {
        for (int i = roll; i > 0; i--) {
            // By making the sum of all percentage we can now randomly generate a number to get a random item inside itemFillMap.
            final int randomNumberRange = itemFillMap
                    .values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            // Random number that represent a cursor for the itemFillMap.
            final int mapCursor = (int) Math.floor(Math.random() * randomNumberRange);

            // Get free slots that can be filled.
            final List<Integer> freeSlots = freeSlots();

            // Stop filling if no free slots left.
            if (freeSlots.isEmpty()) break;

            // Looking for correspondance.
            boolean added = false;
            int count = 0;
            for (Map.Entry<ItemStack, Integer> entry : itemFillMap.entrySet()) {
                if (entry.getValue() + count >= mapCursor && !added) {
                    // Add item in random slot of the inventory.
                    final int randomIndex = (int) Math.floor(Math.random() * freeSlots.size());

                    inventory.setItem(freeSlots.get(randomIndex), entry.getKey());
                    added = true;
                } else {
                    count += entry.getValue();
                }
            }
        }
    }

    /**
     * Get the list of free slots index in {@link InventoryFiller#inventory}.
     *
     * @return list of free slots in {@link InventoryFiller#inventory}.
     */
    @NotNull private List<Integer> freeSlots() {
        final List<Integer> freeSlots = new ArrayList<>();

        for (int slot=0; slot < this.inventory.getSize(); slot++) {
            @Nullable final ItemStack item = this.inventory.getItem(slot);

            if (item == null || item.getType() == Material.AIR) {
                freeSlots.add(slot);
            }
        }

        return freeSlots;
    }

}
