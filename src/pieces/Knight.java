package pieces;

import util.Move;
import util.MoveValidator;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.type = Type.KNIGHT;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedPiece() == null)
                || (move.getCapturedPiece() != null
                && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {

            // along file
            if (Math.abs(move.getDestinationFile() - move.getOriginFile()) == 2
                    && Math.abs(move.getDestinationRank() - move.getOriginRank()) == 1) {
                return true;
            }
            // along rank
            if (Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1
                    && Math.abs(move.getDestinationRank() - move.getOriginRank()) == 2) {
                return true;
            }
        }

        // all other cases
        return false;
    }

}
