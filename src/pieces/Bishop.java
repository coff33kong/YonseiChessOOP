package pieces;

import board.Board;
import board.Square;
import util.Move;
import util.MoveValidator;

public class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
        this.type = Type.BISHOP;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedPiece() == null)
                || (move.getCapturedPiece() != null
                && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {

            if(MoveValidator.protectedKing(move,color))
                return false;

            if (Math.abs(move.getDestinationRank()-move.getOriginRank())
                    == Math.abs(move.getDestinationFile()-move.getOriginFile())){
                return true;
            }


        }

        // all other cases
        return false;
    }

}
