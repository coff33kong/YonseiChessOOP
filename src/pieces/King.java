package pieces;

import ui.BoardPanel;
import util.Move;
import  board.Board;
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

        //TODO Castling
        if (move.getDestinationFile() == 'c' && move.getOriginFile() == 'e'
                && !MoveValidator.longLiveTheKing(this.color,move.getOriginFile(),move.getOriginRank())
                && !MoveValidator.longLiveTheKing(this.color,move.getDestinationFile(),move.getDestinationRank())) {
            if (color == Color.WHITE && moved == false) {
                if (Board.getSquare('b',1).getCurrentPiece() == null
                        && Board.getSquare('c',1).getCurrentPiece() == null
                        && Board.getSquare('d',1).getCurrentPiece() == null
                        && Board.getSquare('a',1).getCurrentPiece() != null) {
                    if (Board.getSquare('a',1).getCurrentPiece().getType() == Type.ROOK
                            && Board.getSquare('a',1).getCurrentPiece().moved == false) {
                        // TODO change Piece
                        BoardPanel.changeRook('a',1,'d',1);
                        return true;
                    }
                }
            } else if(color == Color.BLACK && moved == false) {
                if (Board.getSquare('b',8).getCurrentPiece() == null
                        && Board.getSquare('c',8).getCurrentPiece() == null
                        && Board.getSquare('d',8).getCurrentPiece() == null
                        && Board.getSquare('a',8).getCurrentPiece() != null) {
                    if (Board.getSquare('a',8).getCurrentPiece().getType() == Type.ROOK
                            && Board.getSquare('a',8).getCurrentPiece().moved == false) {
                        BoardPanel.changeRook('a',8,'d',8);
                        return true;
                    }
                }
            }
        } else if (move.getDestinationFile() == 'g' && move.getOriginFile() == 'e'
                && !MoveValidator.longLiveTheKing(this.color,move.getOriginFile(),move.getOriginRank())
                && !MoveValidator.longLiveTheKing(this.color,move.getDestinationFile(),move.getDestinationRank())){
            if (color == Color.WHITE && moved == false) {
                if (Board.getSquare('f',1).getCurrentPiece() == null
                        && Board.getSquare('g',1).getCurrentPiece() == null
                        && Board.getSquare('h',1).getCurrentPiece() != null) {
                    if (Board.getSquare('h',1).getCurrentPiece().getType() == Type.ROOK
                            && Board.getSquare('h',1).getCurrentPiece().moved == false) {
                        BoardPanel.changeRook('h',1,'f',1);
                        return true;
                    }
                }
            } else if (color == Color.BLACK && moved == false) {
                if (Board.getSquare('f',8).getCurrentPiece() == null
                        && Board.getSquare('g',8).getCurrentPiece() == null
                        && Board.getSquare('h',8).getCurrentPiece() != null) {
                    if (Board.getSquare('h',8).getCurrentPiece().getType() == Type.ROOK
                            && Board.getSquare('h',8).getCurrentPiece().moved == false) {
                        BoardPanel.changeRook('h',8,'f',8);
                        return true;
                    }
                }
            }
        }

        // all other cases
        return false;
    }

}
