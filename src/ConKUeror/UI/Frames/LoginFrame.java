package ConKUeror.UI.Frames;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ConKUeror.domain.controller.BuildHandler;
import ConKUeror.domain.controller.GameHandler;
import ConKUeror.domain.controller.HandlerFactory;
import ConKUeror.domain.controller.SaveLoadHandler;
import ConKUeror.domain.controller.StartHandler;
import ConKUeror.domain.model.Data.GameState;
import ConKUeror.domain.model.Data.PlayerData;
import ConKUeror.domain.model.Modes.BuildMode;
import ConKUeror.domain.model.Modes.StartMode;
import ConKUeror.domain.model.Player.Player;

public class LoginFrame extends JFrame {
    private JButton newGameButton;
    private JButton loadGameButton;
    private GameState gameState;
    private StartMode startMode;
    private SaveLoadHandler saveLoadHandler = new SaveLoadHandler();

    public LoginFrame() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240)); 
        panel.setBorder(new EmptyBorder(50, 80, 50, 80)); //padding icin 

        JLabel titleLabel = new JLabel("ConKuEror");
        Font titleFont = new Font("Arial", Font.BOLD, 30);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.BLUE);

        JLabel madeByLabel = new JLabel("Made by: artbatremix");

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        headerPanel.add(madeByLabel);

        this.add(headerPanel, BorderLayout.NORTH);

        this.newGameButton = createButton("New Game");
        this.loadGameButton = createButton("Load Game");

        
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                try {
                    gameState = loadGameState();
                } catch (ClassNotFoundException | IOException e1) {
                    e1.printStackTrace();
                }

                startLoadedGame(gameState.getPlayerData());
            }
        });

        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(newGameButton);
        panel.add(Box.createHorizontalStrut(10)); 
        panel.add(loadGameButton);

        panel.setBorder(new EmptyBorder(new Insets(50, 80, 50, 80))); // padding
        this.add(panel);

        this.setSize(new Dimension(800, 600)); 
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 50)); 
        button.setFont(new Font("Arial", Font.BOLD, 16)); 
        button.setForeground(Color.BLACK); 
        button.setBackground(new Color(59, 89, 182)); 
        button.setFocusPainted(false); 
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), 
                BorderFactory.createEmptyBorder(5, 15, 5, 15) 
        ));
        return button;
    }

    private void startNewGame() {
        // Implementation for starting a new game
        HandlerFactory controller = HandlerFactory.getInstance();
        BuildHandler buildHandler = controller.giveBuildHandler();
        BuildModeScreen buildModeScreen = new BuildModeScreen(buildHandler);
        this.dispose();
    }

    public void startLoadedGame(List<PlayerData> loadedPlayerDataList) {
        HandlerFactory controller = HandlerFactory.getInstance();
        BuildHandler buildHandler = controller.giveBuildHandler();

        int botCount = 0;
        // Initialize the players using the loaded player data
        for (PlayerData playerData : loadedPlayerDataList) {
            if (playerData.getPlayerType().equals("Computer")) {
                botCount++;
                buildHandler.initalizeBots(botCount); // Initialize bot player.
            } else {
                Player player = playerData.getPlayer();
                // Set the player's inventory and other attributes based on the loaded data
                player.getInventory().setCavalryCount(playerData.getCavalryCount());
                player.getInventory().setInfantryCount(playerData.getInfantryCount());
                player.getInventory().setArtilleryCount(playerData.getArtilleryCount());
                buildHandler.enterNameForRealPlayers(playerData.getPlayerName());
                player.getInventory().setOwnedTerritories(playerData.getTerritories());
                player.setColor(playerData.getPlayerColor());
                // Insert the player back into the system
                //geri sisteme nasıl sokuyoruz onun haricinde bütün verileri topluyo
            }
        }
        StartHandler startHandler = controller.giveStartHandler();
        startHandler.setStartMode();

        // Start the game
        try {
            MapView map = new MapView();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        this.dispose(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }

    public GameState loadGameState() throws ClassNotFoundException, IOException {
        return saveLoadHandler.loadGame("data.txt");
    }
}
