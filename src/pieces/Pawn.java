package pieces;

import util.Move;
import util.MoveValidator;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
        this.type = Type.PAWN;
    }

    @Override
    public boolean validateMove(Move move) {
        // executeMove or capture
        if (move.getCapturedPiece() == null
            && move.getDestinationFile() == move.getOriginFile()) {

            if(MoveValidator.protectedKing(move,color))
                return false;

            //can move two squares
            if (move.getPiece().getMoved() == false
                    && Math.abs(move.getDestinationRank() - move.getOriginRank()) == 2) {

                return true;

            } else if (Math.abs(move.getDestinationRank() - move.getOriginRank()) == 1) {

                return true;

            }

        } else if ((move.getCapturedPiece() != null
                && !move.getPiece().getColor().equals(move.getCapturedPiece().getColor()))) {

            if ( Math.abs(move.getDestinationFile() - move.getOriginFile()) == 1
                    && Math.abs(move.getDestinationRank() - move.getOriginRank()) == 1){
                // 앞 대각선으로 한칸 이동 확인

                return true;

            }
        }
        // all other cases
        return false;
    }

}
