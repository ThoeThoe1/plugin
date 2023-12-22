package net.gamingvalley.thomas.model;

public class PlayerStats {

    private String uuid;
    private String username;
    private int deaths;
    private int kills;


    public PlayerStats(String uuid, String username, int deaths, int kills){
        this.uuid = uuid;
        this.username = username;
        this.deaths = deaths;
        this.kills = kills;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) { this.uuid = uuid; }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) { this.username = username; }


    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    public int getKills() { return kills; }
    public void setKills(int kills) { this.kills = kills; }
}
