package net.gamingvalley.thomas.listeners;

import net.gamingvalley.thomas.Pvp;
import net.gamingvalley.thomas.model.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class listeners implements Listener {

    private final Pvp plugin;

    public listeners(Pvp plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void OnPlayerConnect(PlayerJoinEvent e){
        Player p = e.getPlayer();
        try{
            String firstloginuuid = p.getUniqueId().toString();
            PlayerStats playerInfo = getPlayerStatsFromDatabase(p);

            if (firstloginuuid == null){

                playerInfo.setUuid(p.getUniqueId().toString());
                playerInfo.setUsername(p.getDisplayName());
                this.plugin.getDatabase().UpdatePlayerStats(playerInfo);
            }
            else{

                playerInfo.setUsername(p.getDisplayName());
                this.plugin.getDatabase().UpdatePlayerStats(playerInfo);

            }

        } catch (SQLException ex1) {
            ex1.printStackTrace();
        }
    }



    @EventHandler
    public void OnDeath(PlayerDeathEvent e){

        Player killer = e.getEntity().getKiller();
        Player p = e.getEntity();

        try {
            PlayerStats pStats = getPlayerStatsFromDatabase(p);
            pStats.setDeaths(pStats.getDeaths() + 1);

            this.plugin.getDatabase().UpdatePlayerStats(pStats);

            if (killer == null){
                return;

            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }


        try {
            PlayerStats KillerStats = getPlayerStatsFromDatabase(killer);
            KillerStats.setKills(KillerStats.getKills() + 1);


            this.plugin.getDatabase().UpdatePlayerStats(KillerStats);


            }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    private PlayerStats getPlayerStatsFromDatabase(Player p) throws SQLException{

        PlayerStats stats = this.plugin.getDatabase().findPlayerStatsByUUID(p.getUniqueId().toString());

        if(stats == null){

            stats = new PlayerStats(p.getUniqueId().toString(), p.getDisplayName(), 0, 0);
            this.plugin.getDatabase().CreatePlayerStats(stats);
            return stats;
        }

        return stats;
    }

}
