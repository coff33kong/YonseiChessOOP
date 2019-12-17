package util;

import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class MoveLogger {

    /**
     * Wrapper class for a pair of moves.
     */
    private static class MoveRound {
        private Move whiteMove;
        private Move blackMove;

        public MoveRound(Move whiteMove, Move blackMove) {
            this.whiteMove = whiteMove;
            this.blackMove = blackMove;
        }

    }

    private static List<MoveRound> moveHistory;
    private static List<Move> moveRoundBuffer;

    private static MoveLogger moveLoggerInstance = new MoveLogger();

    public static MoveLogger getInstance() {
        return moveLoggerInstance;
    }

    private MoveLogger() {
        initialize();
    }

    /**
     * Add a executeMove to the history
     * @param move
     */
    public static void addMove(Move move) {
        moveRoundBuffer.add(move);
        if (moveRoundBuffer.size() == 2) {
            moveHistory.add(new MoveRound(moveRoundBuffer.get(0), moveRoundBuffer.get(1)));
            moveRoundBuffer.clear();
        }
    }

    //TODO UNDO
    public static Move popMove() {
        if (moveRoundBuffer.size() == 0 && moveHistory.size() == 0) {
            return null;
        }
        if (moveRoundBuffer.size()==1) {
            Move lastMove = moveRoundBuffer.get(0);
            moveRoundBuffer.clear();
            return  lastMove;
        } else {
            Move lastMove = moveHistory.get(moveHistory.size() - 1).blackMove;
            moveRoundBuffer.add(moveHistory.get(moveHistory.size() - 1).whiteMove);
            moveHistory.remove(moveHistory.size()-1);
            return lastMove;
        }
    }

    private void initialize() {
        moveHistory = new ArrayList<MoveRound>();
        moveRoundBuffer = new ArrayList<Move>();
    }
}
