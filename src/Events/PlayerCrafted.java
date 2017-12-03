package Events;

import gamesidestats.GameSideStats;
import java.util.HashMap;
import java.util.Iterator;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.AffectSlotEvent;

import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.crafting.CraftingOutput;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.text.format.TextColors;

public class PlayerCrafted {
    /*
    @Listener
    public void onItemCraft(ClickInventoryEvent event, @Root Player player, @Getter("getTargetInventory") Inventory inventory) {
        int countitems = 0;
        CraftingOutput slot = inventory.query(CraftingOutput.class);
        
        if (inventory.getArchetype() == InventoryArchetypes.PLAYER || inventory.getArchetype() == InventoryArchetypes.WORKBENCH) {
            for (Iterator<SlotTransaction> i = event.getTransactions().iterator(); i.hasNext();) {
                SlotTransaction item = i.next();
                if(item.getSlot().equals(slot)){
                    Utils.Utils.sendMessage(player, TextColors.BLUE, "Вы скрафтили: ", TextColors.WHITE, slot.getName());
                }
            }
        }
    }
    */
    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event, @Root Player player){
        //Utils.Utils.sendMessage(player, "Вы сломали что-то");
        if(!GameSideStats.instance.blockBroken.containsKey(player)){
            GameSideStats.instance.blockBroken.put(player, 1L);
        }else{
            Long numbr = GameSideStats.instance.blockBroken.get(player);
            GameSideStats.instance.blockBroken.replace(player, numbr, numbr+1);
        }
    }
    
    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event, @Root Player player){
        //Utils.Utils.sendMessage(player, "Вы поставили что-то");
        if(!GameSideStats.instance.blockPlaced.containsKey(player)){
            GameSideStats.instance.blockPlaced.put(player, 1L);
        }else{
            Long numbr = GameSideStats.instance.blockPlaced.get(player);
            GameSideStats.instance.blockPlaced.replace(player, numbr, numbr+1);   
        }
    }
}
