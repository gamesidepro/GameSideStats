/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;

import gamesidestats.ConfigurationManager;
import gamesidestats.GameSideStats;
import java.sql.Array;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.AffectSlotEvent;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

/**
 *
 * @author Twelvee
 */
public class PlayerChatUse {
    @Listener
    public void onGlobalOrLocalsay(MessageChannelEvent.Chat event, @Root Player player){
        String message = event.getRawMessage().toPlain();
        String fc = message.substring(0,1);
        boolean somebodywastoldme = false;
        if(fc.equals("!")){
            String body = event.getMessage().toPlain();
            boolean messageclear = false;
            String newbody = event.getMessage().toString();
            Text first = null;
            int i = 0;
            for(Text sd : event.getMessage().getChildren()){
                if(i==1){
                    message = sd.toPlain().replaceFirst("!", "");
                    if(message.isEmpty()){
                        messageclear = true;
                    }
                }else if(i==0){
                    first=sd;
                }
                i++;
            }
            if(!messageclear){
                Text toMessage = first.toBuilder().append(Text.builder(message).color(TextColors.WHITE).build()).build();
                for (Player pla : Sponge.getServer().getOnlinePlayers()){
                    Utils.Utils.sendMessage(pla, TextColors.GOLD, "[Г] ", toMessage);
                }
                
            }
            event.setCancelled(true);   
            //event.setMessageCancelled(true);
            //Sponge.getServer().getConsole().sendMessage(mes);
        }else{
            Text ms = event.getMessage();
            for(Player pla : Sponge.getServer().getOnlinePlayers()){
                
                if(pla.getNearbyEntities(700).contains(player)){
                    if(!pla.equals(player)){
                        pla.sendMessage(ms);
                        somebodywastoldme = true;   
                    }
                }
                
            }event.setCancelled(true);
                if(!somebodywastoldme){
                    Utils.Utils.sendMessage(player, TextColors.DARK_GRAY, "Вас никто не слышит...");
                }else{
                    player.sendMessage(ms);
                }
            
        }
    }
    
    @Listener
    public void onCommandUse(SendCommandEvent e, @Root Player player){
        String command = e.getCommand();
        //String message = e.getCommand();
        String args_st = e.getArguments();
        //Utils.Utils.sendMessage(player, args_st);
        String args[] = args_st.split(" ");
        String table_bans = ConfigurationManager.getInstance().getConfig().getNode("table_bans").getString();
        String server = ConfigurationManager.getInstance().getConfig().getNode("serverBans").getString();
        String target = "";
        String reason = "";
        long duration = 0;
        if(command.equals("ban")){
            if(player.hasPermission("nucleus.ban.base")){
                target = args[0];
                reason = args_st.replaceAll(target, "");
                long create = System.currentTimeMillis()/1000L;
                GameSideStats.instance.getDb().getResultSet("INSERT INTO `"+table_bans+"` (`created`,`server`,`player_name`,`type`,`reason`,`admin`,`until`) VALUES ('"+create+"','"+server+"','"+target+"','ban','"+reason+"','"+player.getName()+"','none')");
            }
        }else if(command.equals("mute")){
            if(player.hasPermission("nucleus.mute.base")){
                target = args[0];
                reason = args_st.replaceAll(target, "");
                long create = System.currentTimeMillis()/1000L;
                GameSideStats.instance.getDb().getResultSet("INSERT INTO `"+table_bans+"` (`created`,`server`,`player_name`,`type`,`reason`,`admin`,`until`) VALUES ('"+create+"','"+server+"','"+target+"','mute','"+reason+"','"+player.getName()+"','none')");
            }
        }else if(command.equals("tempban")){
            if(player.hasPermission("nucleus.tempban.base")){
                target = args[0];
                duration = Long.parseLong(args[1]);
                reason = args_st.replaceAll(target+" "+duration, "");
                long until = (System.currentTimeMillis()+(duration*60*60*1000))/1000L;
                long create = System.currentTimeMillis()/1000L;
                GameSideStats.instance.getDb().getResultSet("INSERT INTO `"+table_bans+"` (`created`,`server`,`player_name`,`type`,`reason`,`admin`,`until`) VALUES ('"+create+"','"+server+"','"+target+"','ban','"+reason+"','"+player.getName()+"','"+until+"')");
            }
        }else if(command.equals("kick")){
            if(player.hasPermission("nucleus.kick.base")){
                target = args[0];
                reason = args_st.replaceAll(target, "");
                long create = System.currentTimeMillis()/1000L;
                GameSideStats.instance.getDb().getResultSet("INSERT INTO `"+table_bans+"` (`created`,`server`,`player_name`,`type`,`reason`,`admin`,`until`) VALUES ('"+create+"','"+server+"','"+target+"','kick','"+reason+"','"+player.getName()+"','none')");
            }
        }else if(command.equals("warn")){
            if(player.hasPermission("nucleus.warn.base")){
                target = args[0];
                duration = Long.parseLong(args[1]);
                reason = args_st.replaceAll(target+" "+duration, "");
                long until = (System.currentTimeMillis()+(duration*60*60*1000))/1000L;
                long create = System.currentTimeMillis()/1000L;
                GameSideStats.instance.getDb().getResultSet("INSERT INTO `"+table_bans+"` (`created`,`server`,`player_name`,`type`,`reason`,`admin`,`until`) VALUES ('"+create+"','"+server+"','"+target+"','warn','"+reason+"','"+player.getName()+"','"+until+"')");
            }
        }
    }
}
