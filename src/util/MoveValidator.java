package util;

import board.Board;
import board.Square;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceSet;

import java.util.List;

public class MoveValidator {

    private static MoveValidator ourInstance = new MoveValidator();

    public static MoveValidator getInstance() {
        return ourInstance;
    }

    private MoveValidator() {
        currentMoveColor = Piece.Color.WHITE;
    }

    private static Piece.Color currentMoveColor;

    public static boolean validateMove(Move move) {
        return validateMove(move, false);
    }

    public static boolean validateMove(Move move, boolean ignoreColorCheck) {
        // check for out of bounds
        if (move.getDestinationFile() < 'a' || move.getDestinationFile() > 'h'
                || move.getDestinationRank() < 1 || move.getDestinationRank() > 8) {
            return false;
        }

        // check for valid origin
        if (move.getPiece() == null) {
            return false;
        }

        // check for valid color
        if (!move.getPiece().getColor().equals(currentMoveColor) && !ignoreColorCheck) {
            return false;
        }

        // check for valid destination
        if (move.getCapturedPiece() != null) {
            if (move.getPiece().getColor().equals(move.getCapturedPiece().getColor())) {
                return false;
            }
        }

        // check for piece rule
        if (!move.getPiece().validateMove(move)) {
            return false;
        }

        // check for clear path
        if (!validateClearPath(move)) {
            return false;
        }

        currentMoveColor = currentMoveColor.equals(Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        return true;
    }

    public static boolean isCheckMove(Move move) {
        // TODO-check
        return false;
    }

    public static boolean isCheckMate(Move move) {
        // TODO-check
        return false;
    }

    private static boolean validateClearPath(Move move) {
        // TODO-movement
        // check the piece
        Piece thisPiece = Board.getSquare(move.getOriginFile(), move.getOriginRank()).getCurrentPiece();
        Piece destPiece = Board.getSquare(move.getDestinationFile(), move.getDestinationRank()).getCurrentPiece();
        System.out.print(move.getDestinationFile() );
        System.out.println(move.getDestinationRank());
        System.out.print(move.getOriginFile() );
        System.out.println(move.getOriginRank());

        if (thisPiece.getType() == Piece.Type.PAWN) {
            if (thisPiece.getColor() == Piece.Color.WHITE) {
                //color is white
                // 이동조건 확인
                if (move.getDestinationFile() == move.getOriginFile()) {
                    //can move two squares
                    if (thisPiece.getMoved() == false
                        && move.getDestinationRank()-move.getOriginRank() == 2) {
                        //앞에 적이 없으면 이동가능
                        if (destPiece == null) {
                            thisPiece.setMoved(true);
                            return true;
                        }
                    } else if (move.getDestinationRank()-move.getOriginRank() == 1){
                        if (destPiece == null) {
                            thisPiece.setMoved(true);
                            return true;
                        }
                    }
                } else if( Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1
                        && move.getDestinationRank() - move.getOriginRank() == 1){
                    // 앞 대각선으로 한칸 이동 확인
                    // 적을 공격하는지 확인
                    if (destPiece != null){
                        thisPiece.setMoved(true);
                        return true;
                    } else {
                        return true;
                    }
                }
            } else {
                //color is black
                // 이동조건 확인
                if (move.getDestinationFile() == move.getOriginFile()) {
                    //can move two squares
                    if (thisPiece.getMoved() == false
                            && move.getDestinationRank()-move.getOriginRank() == -2) {
                        //앞에 적이 없으면 이동가능
                        if (destPiece == null) {
                            thisPiece.setMoved(true);
                            return true;
                        }
                    } else if (move.getDestinationRank()-move.getOriginRank() == -1){
                        if (destPiece == null) {
                            thisPiece.setMoved(true);
                            return true;
                        }
                    }
                } else if(Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1
                        && move.getDestinationRank() - move.getOriginRank() == -1){
                    // 앞 대각선으로 한칸 이동 확인
                    // 적을 공격하는지 확인
                    if (destPiece != null){
                        thisPiece.setMoved(true);
                        return true;
                    } else {
                        return true;
                    }
                }
            }
        }
        else if (thisPiece.getType() == Piece.Type.KNIGHT) {

        }
        else if (thisPiece.getType() == Piece.Type.BISHOP) {

        }
        else if (thisPiece.getType() == Piece.Type.ROOK) {

        }
        else if (thisPiece.getType() == Piece.Type.QUEEN) {

        }
        else if (thisPiece.getType() == Piece.Type.KING) {

        }

        return false;
    }

    public static boolean isStaleMate(Move move) {
        // TODO-check 추가적으로 할만한거
        return false;
    }

}
