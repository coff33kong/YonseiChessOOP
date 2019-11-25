package pieces;

import util.Move;
import util.MoveValidator;

public class King extends Piece {

    public King(Color color) {
        super(color);
        this.type = Type.KING;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedPiece() == null)
                || (move.getCapturedPiece() != null
                && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {


            // along file
            if (move.getDestinationFile() == move.getOriginFile()
                    && Math.abs(move.getDestinationRank() - move.getOriginRank())==1) {
                return true;
            }
            // along rank
            if (Math.abs(move.getDestinationFile() - move.getOriginFile())==1
                    && move.getDestinationRank() == move.getOriginRank()) {
                return true;
            }
            if ((Math.abs(move.getDestinationRank()-move.getOriginRank())
                    == Math.abs(move.getDestinationFile()-move.getOriginFile()))
                    && Math.abs(move.getDestinationFile()-move.getOriginFile()) == 1){
                return true;
            }
        }

        // all other cases
        return false;
    }

}
