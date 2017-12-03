package Utils;

import gamesidestats.ConfigurationManager;
import gamesidestats.GameSideStats;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.statistic.Statistics;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
/**
 * @website gameside.pro
 * @author Twelvee
 */
public class Utils {
    public static void sendMessage(Player player, Object... args) {
		if (args.length != 0) {
			player.sendMessage(Text.of(args));
		}
    }
    
    public static void updateUserStats(Player player){
            Long minutes_played = player.getOrNull(Keys.STATISTICS).get(Statistics.TIME_PLAYED);
            Long distance_walked = player.getOrNull(Keys.STATISTICS).get(Statistics.WALK_ONE_CM);
            Long distance_crouched = player.getOrNull(Keys.STATISTICS).get(Statistics.CROUCH_ONE_CM);
            Long distance_sprinted = player.getOrNull(Keys.STATISTICS).get(Statistics.SPRINT_ONE_CM);
            Long distance_swum = player.getOrNull(Keys.STATISTICS).get(Statistics.SWIM_ONE_CM);
            Long distance_fallen = player.getOrNull(Keys.STATISTICS).get(Statistics.FALL_ONE_CM);
            Long distance_climbed = player.getOrNull(Keys.STATISTICS).get(Statistics.CLIMB_ONE_CM);
            Long distance_dove = player.getOrNull(Keys.STATISTICS).get(Statistics.DIVE_ONE_CM);
            Long distance_by_minecart = player.getOrNull(Keys.STATISTICS).get(Statistics.MINECART_ONE_CM);
            Long distance_by_pig = player.getOrNull(Keys.STATISTICS).get(Statistics.PIG_ONE_CM);
            Long distance_by_horse = player.getOrNull(Keys.STATISTICS).get(Statistics.HORSE_ONE_CM);
            Long distance_by_boat = player.getOrNull(Keys.STATISTICS).get(Statistics.BOAT_ONE_CM);
            Long jumps = player.getOrNull(Keys.STATISTICS).get(Statistics.JUMP);
            Long damage_taken = player.getOrNull(Keys.STATISTICS).get(Statistics.DAMAGE_TAKEN);
            Long number_of_deaths = player.getOrNull(Keys.STATISTICS).get(Statistics.DEATHS);
            Long mob_kills = player.getOrNull(Keys.STATISTICS).get(Statistics.MOB_KILLS);
            Long player_kills = player.getOrNull(Keys.STATISTICS).get(Statistics.PLAYER_KILLS);
            Long items_dropped = player.getOrNull(Keys.STATISTICS).get(Statistics.DROP);
            Long items_enchanted = player.getOrNull(Keys.STATISTICS).get(Statistics.ITEM_ENCHANTED);
            Long fish_caught = player.getOrNull(Keys.STATISTICS).get(Statistics.FISH_CAUGHT);
            Long traded_with_villagers = player.getOrNull(Keys.STATISTICS).get(Statistics.TRADED_WITH_VILLAGER);
            Long cake_eaten = player.getOrNull(Keys.STATISTICS).get(Statistics.CAKE_SLICES_EATEN);
            Long opened_crafting_table = player.getOrNull(Keys.STATISTICS).get(Statistics.CRAFTING_TABLE_INTERACTION);
            Long block_breaked = GameSideStats.instance.blockBroken.get(player);
            Long block_placed = GameSideStats.instance.blockPlaced.get(player);
            int items_crafted = 0;
            if(GameSideStats.instance.items_crafted.containsKey(player)){
                items_crafted = GameSideStats.instance.items_crafted.get(player).size();
            }
            
            if(minutes_played == null) minutes_played = 0L;
            if(distance_walked == null) distance_walked = 0L;
            if(distance_crouched == null) distance_crouched = 0L;
            if(distance_sprinted == null) distance_sprinted = 0L;
            if(distance_swum == null) distance_swum = 0L;
            if(distance_fallen == null) distance_fallen = 0L;
            if(distance_climbed == null) distance_climbed = 0L;
            if(distance_dove == null) distance_dove = 0L;
            if(distance_by_minecart == null) distance_by_minecart = 0L;
            if(distance_by_pig == null) distance_by_pig = 0L;
            if(distance_by_horse == null) distance_by_horse = 0L;
            if(distance_by_boat == null) distance_by_boat = 0L;
            if(jumps == null) jumps = 0L;
            if(damage_taken == null) damage_taken = 0L;
            if(number_of_deaths == null) number_of_deaths = 0L;
            if(mob_kills == null) mob_kills = 0L;
            if(player_kills == null) player_kills = 0L;
            if(items_dropped == null) items_dropped = 0L;
            if(items_enchanted == null) items_enchanted = 0L;
            if(fish_caught == null) fish_caught = 0L;
            if(traded_with_villagers == null) traded_with_villagers = 0L;
            if(cake_eaten == null) cake_eaten = 0L;
            if(opened_crafting_table == null) opened_crafting_table = 0L;
            if(block_breaked == null) block_breaked = 0L;
            if(block_placed == null) block_placed = 0L;
            
            
            
            String table_stats = ConfigurationManager.getInstance().getConfig().getNode("table_stats").getString();
            
            if(!GameSideStats.instance.getDb().checkIfUserExist(table_stats, player.getName()).isEmpty()){
                if(GameSideStats.instance.getDb().getResultSet("UPDATE "+table_stats+" SET `minutes_played`='"+minutes_played+"', `block_broken`=`block_broken`+'"+block_breaked+"',`block_placed`=`block_placed`+'"+block_placed+"',`distance_walked`='"+distance_walked+"',`items_crafted`=`items_crafted`+'"+items_crafted+"',`distance_crouched`='"+distance_crouched+"',`distance_sprinted`='"+distance_sprinted+"',`distance_swum`='"+distance_swum+"',`distance_fallen`='"+distance_fallen+"',`distance_climbed`='"+distance_climbed+"',`distance_dove`='"+distance_dove+"',`distance_by_minecart`='"+distance_by_minecart+"',`distance_by_pig`='"+distance_by_pig+"',`distance_by_horse`='"+distance_by_horse+"',`distance_by_boat`='"+distance_by_boat+"',`jumps`='"+jumps+"',`number_of_deaths`='"+number_of_deaths+"',`damage_taken`='"+damage_taken+"',`player_kills`='"+player_kills+"',`mob_kills`='"+mob_kills+"',`items_dropped`='"+items_dropped+"',`items_enchanted`='"+items_enchanted+"',`fish_caught`='"+fish_caught+"',`traded_with_villagers`='"+traded_with_villagers+"',`cake_eaten`='"+cake_eaten+"',`opened_crafting_table`='"+opened_crafting_table+"' WHERE player_name='"+player.getName()+"'")!=null){
                    //Utils.sendMessage(player, TextColors.AQUA, "[Ваша статистика обновлена]");
                    GameSideStats.instance.blockBroken.remove(player);
                    GameSideStats.instance.blockPlaced.remove(player);
                    GameSideStats.instance.items_crafted.remove(player);
                }else{
                    //Utils.sendMessage(player, TextColors.AQUA, "[Ваша статистика не обновлена]");
                }
            }else{
                    //Utils.sendMessage(player, TextColors.AQUA, "[Вашего пользователя нет в бд]");
                if(GameSideStats.instance.getDb().getResultSet("INSERT INTO `"+table_stats+"` (`player_name`) VALUES ('"+player.getName()+"')")==null){
                    Utils.updateUserStats(player);
                }
            }
        
    }
}
