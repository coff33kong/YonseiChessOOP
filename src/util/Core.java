package util;

import ui.LaunchFrame;
import ui.PreferencesFrame;
import ui.PromotionFrame;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Core {

    private static Core coreInstance = new Core();
    private static boolean inGame;

    private static GameModel gameModel;
    private static Preferences preferences;

    private static LaunchFrame launchFrame;
    private static PreferencesFrame preferencesFrame;
    private static PromotionFrame promotionFrame;

    private Core() {
    }

    public static Core getInstance() {
        return coreInstance;
    }

    public static void launch() {
        inGame = false;
        preferences = new Preferences();
        launchFrame = new LaunchFrame();
        promotionFrame = new PromotionFrame();
    }

    public static void startGame() {
        inGame = true;
        gameModel = new GameModel();
    }

    public static String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    public static Preferences getPreferences() {
        return preferences;
    }

    public static LaunchFrame getLaunchFrame() {
        return launchFrame;
    }

    public static PreferencesFrame getPreferencesFrame() {
        return preferencesFrame;
    }

    public static boolean isInGame() {
        return inGame;
    }

    // gameModel
    public static GameModel getGameModel() { return gameModel; }

    // promotion
    public static PromotionFrame getPromotionFrame() { return promotionFrame; }
}
