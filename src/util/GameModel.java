package util;

import board.Board;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceSet;
import ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class GameModel extends Observable {

    private GameFrame gameFrame;
    private BoardPanel boardPanel;
    private TimerPanel timerPanel;
    private ControlPanel controlPanel;
    private MoveHistoryPanel moveHistoryPanel;
    private Board board;
    private MoveValidator moveValidator;

    private Timer whiteTimer;
    private Timer blackTimer;

    public GameModel() {
        initialize();
    }

    private void initialize() {
        initializeTimers();
        initializeUIComponents();
    }

    public void onMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        onLocalMoveRequest(originFile, originRank, destinationFile, destinationRank);
    }

    private void onLocalMoveRequest(char originFile, int originRank, char destinationFile, int destinationRank) {
        Move move = new Move(originFile, originRank, destinationFile, destinationRank);
        if (MoveValidator.validateMove(move)) {
            executeMove(move);
        } else {
            //
        }
    }

    private void executeMove(Move move) {
        MoveLogger.addMove(move);
        Board.executeMove(move);
        moveHistoryPanel.printMove(move);
        //TODO UNDO
        boardPanel.executeMove(move);
        switchTimer(move);

        // promotion
        if (Pawn.class.equals(move.getPiece().getClass())) {
            switch (move.getDestinationRank()) {
                case 1:
                    if (Piece.Color.BLACK.equals(move.getPiece().getColor())) {
                        Core.getPromotionFrame().setMove(move);
                        Core.getPromotionFrame().setVisible(true);
                    }
                    break;
                case 8:
                    if (Piece.Color.WHITE.equals(move.getPiece().getColor())) {
                        Core.getPromotionFrame().setMove(move);
                        Core.getPromotionFrame().setVisible(true);
                    }
                    break;
            }
        }

        if (MoveValidator.isCheckMove(move,
                PieceSet.getOpponentKingFile(move.getPiece().getColor()),
                PieceSet.getOpponentKingRank(move.getPiece().getColor()))) {
            if (MoveValidator.isCheckMate(move)) {
                stopTimer();
                gameFrame.showCheckmateDialog();
            } else {
                gameFrame.showCheckDialog();
            }
        }
//        else if (MoveValidator.isStaleMate(move.getPiece().getColor(),
//                PieceSet.getOpponentKingFile(move.getPiece().getColor()),
//                PieceSet.getOpponentKingRank(move.getPiece().getColor()))) {
//            stopTimer();
//            gameFrame.showStaleMateDialog();
//        }
        move.getPiece().setMoved(true);
    }


    //TODO UNDO
    public void undoMove() {
        Move lastMove = MoveLogger.popMove();
        boardPanel.undoPiece(lastMove);
        board.undoMove(lastMove);
        moveHistoryPanel.printUndo();
        moveValidator.currentMoveChange();
        switchTimer2(lastMove);
        if (lastMove.getPiece().getColor() == Piece.Color.WHITE) {
            TimerPanel.wTime = TimerPanel.lastwTime;
        } else {
            TimerPanel.bTime = TimerPanel.lastbTime;
        }
    }

    public Piece queryPiece(char file, int rank) {
        return Board.getSquare(file, rank).getCurrentPiece();
    }

    private void initializeUIComponents() {
        boardPanel = new BoardPanel(this);
        timerPanel = new TimerPanel(this);
        controlPanel = new ControlPanel(this);
        moveHistoryPanel = new MoveHistoryPanel(this);
        gameFrame = new GameFrame(this);
    }

    private void initializeTimers() {
        // 밀리세컨드
        whiteTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerPanel.whiteTimerTikTok();
            }
        });
        blackTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerPanel.blackTimerTikTok();
            }
        });
    }

    private void switchTimer(Move move) {
        /*
        TODO-timer
            start and stop whiteTimer and blackTimer
         */
        if (move.getPiece().getColor() == Piece.Color.WHITE) {
            whiteTimer.stop();
            blackTimer.start();
        } else {
            blackTimer.stop();
            whiteTimer.start();

        }

    }

    private void switchTimer2(Move move) {
        /*
        TODO-timer
            start and stop whiteTimer and blackTimer
         */
        if (move.getPiece().getColor() != Piece.Color.WHITE) {
            whiteTimer.stop();
            blackTimer.start();
        } else {
            blackTimer.stop();
            whiteTimer.start();

        }

    }

    private void stopTimer() {
        // TODO-timer: stop timers
        whiteTimer.stop();
        blackTimer.stop();
    }

    public void stopTimer2() {
        if (TimerPanel.wTime <= -32400000L || TimerPanel.bTime <= -32400000L ) {
            stopTimer();
            gameFrame.showTimeOutDialog();
        }
    }


    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public TimerPanel getTimerPanel() {
        return timerPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public MoveHistoryPanel getMoveHistoryPanel() {
        return moveHistoryPanel;
    }

}
