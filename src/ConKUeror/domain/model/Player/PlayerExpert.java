package src.ConKUeror.domain.model.Player;
import src.ConKUeror.domain.controller.PlayerExpertListener;
import src.ConKUeror.domain.model.Army.Army;
import src.ConKUeror.domain.model.Modes.DeployMode;
import src.ConKUeror.domain.model.Player.Player;
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;

public class PlayerExpert {


    private static PlayerExpert playerExpertInstance = null;
    private static List<Player> players = new ArrayList<Player>();
    private static List<PlayerExpertListener> listeners = new ArrayList<>();



    private PlayerExpert() {

    }


    public static void addPlayerPanelAsListener(PlayerExpertListener l) {
        listeners.add(l);
    }

    public static void updatePlayerCount(int index) {

        Player player = players.get(index);
        Army army = player.getInventory().getArmy();
        int totalUnit = army.getTotalArmyUnit();
        publishArmyCount(index, totalUnit);


    }

    public static void publishArmyCount(int index, int armyUnit) {
    for(PlayerExpertListener l: listeners){
        l.updatePanelCounts(index ,armyUnit);
        }
    }





    public static void publishPlayerInfoEvent(int oldIndex,int nexIndex, Color color) {
    for(PlayerExpertListener l: listeners){
        l.changePlayerPanelColor(oldIndex,nexIndex,color);

    }

    }


    public static PlayerExpert getPlayerExpert() {
        if (playerExpertInstance == null) {
            playerExpertInstance = new PlayerExpert();
        }

        return playerExpertInstance;
    }


    public void addPlayersList(Player player) {
        players.add(player);
        System.out.println(player.getName() +  " was " + "HERE");
    }

    public static List<Player> getPlayersList() {
        return players;
    }

    public static void setPlayersList(List<Player> orderedPlayers) {
        players = orderedPlayers;

    }

    public static int getPlayersListSize() {
        return players.size();
    }




}
