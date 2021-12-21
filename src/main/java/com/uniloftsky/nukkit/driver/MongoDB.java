package com.uniloftsky.nukkit.driver;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoDatabase;
import com.uniloftsky.nukkit.driver.connection.Connection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoDB extends PluginBase {

    @Getter
    private static MongoDB INSTANCE;

    private String connectionString;
    private String databaseString;

    @Override
    public void onEnable() {
        INSTANCE = this;
        log("MongoDB plugin is enabled!");
        log("Waiting for a connection...(3000 ms)");
        try {
            Connection.getInstance(connectionString, databaseString).getDatabase().getCollection("test").find().first();
            log(TextFormat.GREEN + "Connection established!");
        } catch (MongoTimeoutException e) {
            this.getLogger().error(TextFormat.RED + "Cannot connect to a specified mongo server!");
            this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin("MongoDB"));
        }
    }

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        connectionString = this.getConfig().getString("connection_uri");
        databaseString = this.getConfig().getString("database_name");
    }

    public static void log(String message) {
        INSTANCE.getLogger().info(message);
    }

    public static MongoDatabase getDatabase() {
        return Connection.getInstance(INSTANCE.connectionString, INSTANCE.databaseString).getDatabase();
    }

}
