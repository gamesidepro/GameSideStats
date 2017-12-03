package Events;

import Utils.DataBase;
import Utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gamesidestats.ConfigurationManager;
import gamesidestats.GameSideStats;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.command.args.GenericArguments.plugin;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.statistic.ChangeStatisticEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


/**
 * @website gameside.pro
 * @author Twelvee
 */
public class PlayerEvents {
        
        Task.Builder taskBuilder = Task.builder();
        
        @Listener
        public void onPlayerJoined(ClientConnectionEvent.Join event) throws SQLException{
            String prems = GameSideStats.instance.getDb().checkIfPremOur(event.getTargetEntity());
            String group = GameSideStats.instance.getDb().getCurrentUserGroup(event.getTargetEntity());
            if(prems!=null){
                    Date currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                    long n1 = currentTimestamp.getTime()/1000L;
                    long n2 = Long.parseLong(prems);
                    //Utils.sendMessage(event.getTargetEntity(), n1);
                    //Utils.sendMessage(event.getTargetEntity(), n2);
                    if(n1>=n2){
                        GameSideStats.instance.getDb().getResultSet("DELETE FROM `gs_prems` WHERE `username`='"+event.getTargetEntity().getName()+"' AND `until`='"+n2+"'");
                        Sponge.getServer().getConsole().sendMessage(Text.of("pex user "+event.getTargetEntity().getName()+" parent remove group "+group));
                        Utils.sendMessage(event.getTargetEntity(), TextColors.GOLD, "[Игровая Сторона] ", TextColors.AQUA, "Ваша группа истекла.");
                    }
            }
            //GameSideStats.instance.getLogger().info("Player "+event.getTargetEntity().getName()+" JOINED BITH");
            taskBuilder.execute(
                task -> {
                    Utils.updateUserStats(event.getTargetEntity());
                    //Utils.sendMessage(event.getTargetEntity(), "Таск.");
                }
            ).async().delay(100, TimeUnit.MILLISECONDS).interval(120, TimeUnit.SECONDS).submit(GameSideStats.instance);
        }
        
        @Listener
        public void onPlayerDisconnected(ClientConnectionEvent.Disconnect event){
            //GameSideStats.instance.getLogger().info("Player "+event.getTargetEntity().getName()+" JOINED BITH");
            Utils.updateUserStats(event.getTargetEntity());
        }
}
