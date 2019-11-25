package pieces;

import util.Move;
import util.MoveValidator;

public class Queen extends Piece {

    public Queen(Color color) {
        super(color);
        this.type = Type.QUEEN;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedPiece() == null)
                || (move.getCapturedPiece() != null
                && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {

            // along file
            if (move.getDestinationFile() == move.getOriginFile()
                    && move.getDestinationRank() != move.getOriginRank()) {
                return true;
            }
            // along rank
            if (move.getDestinationFile() != move.getOriginFile()
                    && move.getDestinationRank() == move.getOriginRank()) {
                return true;
            }
            if (Math.abs(move.getDestinationRank()-move.getOriginRank())
                    == Math.abs(move.getDestinationFile()-move.getOriginFile())){
                return true;
            }

        }

        // all other cases
        return false;
    }

}
