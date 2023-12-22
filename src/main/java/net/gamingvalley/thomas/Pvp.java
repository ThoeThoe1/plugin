package net.gamingvalley.thomas;

import net.gamingvalley.thomas.db.DataBase;
import net.gamingvalley.thomas.listeners.listeners;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Pvp extends JavaPlugin {

    private DataBase database;
    @Override
    public void onEnable() {

        try{
            this.database = new DataBase();
            database.initializeDatabase();

        }catch (SQLException ex){
            System.out.println("Unable to connect to database and create tables.");
            ex.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new listeners(this), this);

        System.out.println("The PVP Plugin has started!");

    }

    public DataBase getDatabase() {
        return database;
    }
    @Override
    public void onDisable() {
        System.out.println("The PVP Plugin has stopped!");
    }
}
