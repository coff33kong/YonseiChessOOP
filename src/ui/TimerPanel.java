package ui;

import util.Core;
import util.GameModel;
import util.Preferences;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.Observable;
import java.util.Observer;

public class TimerPanel extends JPanel implements Observer {

    private GameModel gameModel;
    private Time whiteTime;
    private Time blackTime;
    Preferences preferences = Core.getPreferences();
    private GameFrame gameFrame;


    private JPanel displayPanel;
    private JPanel whiteTimerPanel;
    private JPanel whiteTimerDigitsPanel;
    private JLabel whiteTimerDigitsLabel;
    private JPanel whiteTimerStatusPanel;
    private JPanel blackTimerPanel;
    private JPanel blackTimerDigitsPanel;
    private JLabel blackTimerDigitsLabel;
    private JPanel blackTimerStatusPanel;


    public static long wTime ;
    public static long bTime ;
    private String timeLimit = "00:00:00";

    public TimerPanel(GameModel gameModel) {
        super(new BorderLayout());
        this.gameModel = gameModel;
        if (preferences.getTimerMode() == Preferences.TimerMode.COUNTDOWN) {
            int getTime = preferences.getTimeLimit();
            int getTimeHour = getTime / 60;
            int getTimeMinute = getTime % 60;
            timeLimit = getTimeHour + ":" + getTimeMinute + ":00";
        }
        whiteTime = Time.valueOf(timeLimit);
        blackTime = Time.valueOf(timeLimit);
        initialize();
        gameModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (preferences.getTimerMode() == Preferences.TimerMode.STOPWATCH) {
            if (arg == whiteTime) {
                wTime += 1000;
            } else if (arg == blackTime) {
                bTime += 1000;
            }
            //TODO COUNTDOWN
        } else if (preferences.getTimerMode() == Preferences.TimerMode.COUNTDOWN) {
            if (arg == whiteTime) {
                wTime -= 1000;
                gameModel.stopTimer2();
            } else if (arg == blackTime) {
                bTime -= 1000;
                gameModel.stopTimer2();
            }
        }
    }

    public void whiteTimerTikTok() {
        /*
        TODO-timer
            Update whiteTime
            Update whiteDigitsLabel
            Show whiteTimerStatusPanel
            Blind blackTimerStatusPanel
         */
        update(gameModel, whiteTime);
        whiteTime.setTime(wTime);
        whiteTimerDigitsLabel.setText(whiteTime.toString());
        setBackgroundColor(Color.WHITE);
    }

    public void blackTimerTikTok() {
        // TODO-timer: same with whiteTimerTikTok
        update(gameModel,blackTime);
        blackTime.setTime(bTime);
        blackTimerDigitsLabel.setText(blackTime.toString());
        setBackgroundColor(Color.BLACK);

    }

    private void setBackgroundColor(Color color) {
        if (Color.WHITE.equals(color)) {
            whiteTimerStatusPanel.setVisible(true);
            blackTimerStatusPanel.setVisible(false);
        } else {
            blackTimerStatusPanel.setVisible(true);
            whiteTimerStatusPanel.setVisible(false);
        }
    }

    private void initialize() {
        Preferences preferences = Core.getPreferences();

        whiteTimerDigitsLabel = new JLabel(whiteTime.toString());
        whiteTimerDigitsLabel.setFont(whiteTimerDigitsLabel.getFont().deriveFont(48f));
        whiteTimerDigitsPanel = new JPanel();
        whiteTimerDigitsPanel.add(whiteTimerDigitsLabel);
        whiteTimerStatusPanel = new JPanel();
        whiteTimerStatusPanel.setBackground(Color.WHITE);
        whiteTimerPanel = new JPanel(new BorderLayout());
        whiteTimerPanel.add(whiteTimerDigitsPanel, BorderLayout.LINE_START);
        whiteTimerPanel.add(whiteTimerStatusPanel, BorderLayout.CENTER);
        whiteTimerPanel.setBorder(BorderFactory.createTitledBorder("White"));

        blackTimerDigitsLabel = new JLabel(blackTime.toString());
        blackTimerDigitsLabel.setFont(blackTimerDigitsLabel.getFont().deriveFont(48f));
        blackTimerDigitsPanel = new JPanel();
        blackTimerDigitsPanel.add(blackTimerDigitsLabel);
        blackTimerStatusPanel = new JPanel();
        blackTimerStatusPanel.setBackground(Color.BLACK);
        blackTimerPanel = new JPanel(new BorderLayout());
        blackTimerPanel.add(blackTimerDigitsPanel, BorderLayout.LINE_START);
        blackTimerPanel.add(blackTimerStatusPanel, BorderLayout.CENTER);
        blackTimerPanel.setBorder(BorderFactory.createTitledBorder("Black"));

        displayPanel = new JPanel(new GridLayout(2, 1));
        displayPanel.add(whiteTimerPanel);
        displayPanel.add(blackTimerPanel);

        this.add(displayPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 200));

        switch (preferences.getTimerMode()) {
            case STOPWATCH:
                wTime = -32400000L;
                bTime = -32400000L;
                break;
            //TODO COUNTDOWN
            case COUNTDOWN:

                int getTime = preferences.getTimeLimit();
                wTime = -32400000L + 60000*getTime;
                bTime = -32400000L + 60000*getTime;
                break;
        }
    }

}
