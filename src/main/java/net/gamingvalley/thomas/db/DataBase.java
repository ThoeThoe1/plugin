package net.gamingvalley.thomas.db;

import net.gamingvalley.thomas.model.PlayerStats;

import java.sql.*;

public class DataBase {

    private Connection connection;
    public Connection getConnection() throws SQLException{

        if(connection != null){
            return connection;
        }

        String url = "no";
        String user = "no";
        String password = "no";


        this.connection = DriverManager.getConnection(url, user, password);
        System.out.println("[PVP] Connected to the database");

        return this.connection;
    }

    public void initializeDatabase() throws SQLException{

        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS pvp_stats(uuid varchar(36) primary key, username varchar(16), deaths int, kills int)";
        statement.execute(sql);

        statement.close();

        System.out.println("[PVP] No table called pvp_stats, creating now");
    }

    public PlayerStats findPlayerStatsByUUID(String uuid) throws SQLException{
        Statement statement = getConnection().createStatement();
        String sql = "SELECT * FROM pvp_stats WHERE uuid = " + uuid;
        ResultSet results = statement.executeQuery(sql);

        if(results.next()){

            String username = results.getString("username");
            int deaths = results.getInt("deaths");
            int kills = results.getInt("Kills");

            PlayerStats playerStats = new PlayerStats(uuid, username, deaths, kills);

            statement.close();

            return playerStats;
        }

        statement.close();
        return null;
    }

    public void CreatePlayerStats(PlayerStats stats) throws SQLException{

        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO pvp_stats(uuid, username, deaths, kills) VALUES (?, ?, ?, ?)");

        statement.setString(1, stats.getUuid());
        statement.setString(2, stats.getUsername());
        statement.setInt(3, stats.getDeaths());
        statement.setInt(4, stats.getKills());

        statement.executeUpdate();

        statement.close();

    }

    public void UpdatePlayerStats(PlayerStats stats) throws SQLException{

        PreparedStatement statement = getConnection().prepareStatement("UPDATE pvp_stats SET username = ?, deaths = ?, kills = ? WHERE uuid = ?");

        statement.setString(1, stats.getUsername());
        statement.setInt(2, stats.getDeaths());
        statement.setInt(3, stats.getKills());
        statement.setString(4, stats.getUuid());

        statement.executeUpdate();

        statement.close();

    }
}
