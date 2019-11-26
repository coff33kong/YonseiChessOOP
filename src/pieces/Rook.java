package pieces;

import util.Move;
import util.MoveValidator;

public class Rook extends Piece {

    public Rook(Color color) {
        super(color);
        this.type = Type.ROOK;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if ((move.getCapturedPiece() == null)
                || (move.getCapturedPiece() != null
                    && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {

            if(MoveValidator.protectedKing(move,color))
                return false;

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
        }

        return false;
    }

}
