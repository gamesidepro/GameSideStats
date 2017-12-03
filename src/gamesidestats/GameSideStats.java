/*
 * Плагин статистики для серверов Игровой Стороны
 */
package gamesidestats;

import Events.PlayerChatUse;
import Events.PlayerCrafted;
import Events.PlayerEvents;
import Utils.DataBase;
import Utils.Utils;
import com.google.inject.Inject;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Logger;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.scheduler.Task;

/**
 * @website gameside.pro
 * @author Twelvee
 */
@Plugin(id = "gamesidestats", name = "[GS] Stats", version = "1.2")
public class GameSideStats {
	public static GameSideStats instance;
        private DataBase db;
	public HashMap <Player, Long>blockBroken = new HashMap<Player, Long>();
        public HashMap <Player, Long>blockPlaced = new HashMap<Player, Long>();
        public HashMap <Player, HashMap>items_crafted = new HashMap<Player, HashMap>();
        public long lastsecond = System.currentTimeMillis()/1000;
	@Inject
	Game game;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private File configFile;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	@Inject
	private Logger logger;


	public File getConfigFile() {
		return configFile;
	}

	public ConfigurationLoader<CommentedConfigurationNode> getConfigManager() {
		return configManager;
	}

	public Logger getLogger() {
		return logger;
	}
        
        public DataBase getDb(){
            return db;
        }

	@Listener
	public void onPreInit(GamePreInitializationEvent event) {
	    instance = this;
	    logger.info("Loading GameSide Stats plugin...");
	}
    	@Listener
	public void onInit(GameInitializationEvent event) {
		ConfigurationManager.getInstance().setup(configFile, configManager);
                logger.info("[GS] Stats loaded.");
                game.getEventManager().registerListeners(this, new PlayerEvents());
                game.getEventManager().registerListeners(this, new PlayerCrafted());
                game.getEventManager().registerListeners(this, new PlayerChatUse());
                db = new DataBase();
                db.connect();

        }
        
	
	@Listener
	public void onStart(GameStartedServerEvent event) {
		
	}
}
