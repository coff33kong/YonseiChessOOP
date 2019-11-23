package util;

import board.Board;
import board.Square;
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

        // 현재 움직이려는 Color를 바꿔줌
        currentMoveColor = currentMoveColor.equals(Piece.Color.WHITE) ? Piece.Color.BLACK : Piece.Color.WHITE;
        return true;
    }

    public static boolean isCheckMove(Move move) {
        // TODO-check
        // king's location
        PieceSet.getOpponentKingFile(move.getPiece().getColor());
        PieceSet.getOpponentKingRank(move.getPiece().getColor());



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
        System.out.print(move.getDestinationFile() );
        System.out.println(move.getDestinationRank());
        System.out.print(move.getOriginFile() );
        System.out.println(move.getOriginRank());

        if (thisPiece.getType() == Piece.Type.PAWN) {
            if (thisPiece.getColor() == Piece.Color.WHITE) {
                //color is white
                // 이동조건 확인
                if (move.getDestinationRank()-move.getOriginRank() == 2 ){
                    Piece blockPiece = Board.getSquare(move.getOriginFile(), move.getOriginRank()+1).getCurrentPiece();
                    if (blockPiece == null)
                        return true;
                }
                if (move.getDestinationRank()-move.getOriginRank() == 1)
                    return true;
            } else {
                //color is black
                if (move.getDestinationRank()-move.getOriginRank() == -2){
                    Piece blockPiece = Board.getSquare(move.getOriginFile(), move.getOriginRank()-1).getCurrentPiece();
                    if (blockPiece == null)
                        return true;
                }
                if ( move.getDestinationRank()-move.getOriginRank() == -1 ) {
                    return true;
                }
            }
        }
        else if (thisPiece.getType() == Piece.Type.KNIGHT) {
            return true;
        }
        else if (thisPiece.getType() == Piece.Type.BISHOP) {

            for (int i = 1; i < Math.abs(move.getDestinationRank()-move.getOriginRank()); i++) {
                char x = 'a';
                int y = 1;
                if (move.getDestinationRank()-move.getOriginRank() >0) {
                    if (move.getDestinationFile() - move.getOriginFile() > 0) {
                        x = (char) (move.getOriginFile() + i);
                        y = move.getOriginRank() + i;
                    } else if (move.getDestinationFile() - move.getOriginFile() < 0) {
                        x = (char) (move.getOriginFile() - i);
                        y = move.getOriginRank() + i;
                    }
                }else if (move.getDestinationRank()-move.getOriginRank() <0) {
                    if (move.getDestinationFile() - move.getOriginFile() > 0) {
                        x = (char) (move.getOriginFile() + i);
                        y = move.getOriginRank() - i;
                    } else if (move.getDestinationFile() - move.getOriginFile() < 0) {
                        x = (char) (move.getOriginFile() - i);
                        y = move.getOriginRank() - i;
                    }
                }
                // No obstacles
                if(Board.getSquare(x,y).getCurrentPiece()!=null) {
                    return false;
                }
            }
            return true;
        }
        else if (thisPiece.getType() == Piece.Type.ROOK) {
            char x ;
            int y ;
            if (move.getDestinationRank()-move.getOriginRank() >0) {
                for (int i = 1; i < Math.abs(move.getDestinationRank()-move.getOriginRank()); i++) {
                    x = move.getOriginFile();
                    y = move.getOriginRank() + i;
                    if(Board.getSquare(x,y).getCurrentPiece()!=null) {
                        return false;
                    }
                }
            } else if (move.getDestinationRank()-move.getOriginRank() <0) {
                for (int i = 1; i < Math.abs(move.getDestinationRank()-move.getOriginRank()); i++) {
                    x = move.getOriginFile();
                    y = move.getOriginRank() - i;
                    if(Board.getSquare(x,y).getCurrentPiece()!=null) {
                        return false;
                    }
                }
            } else if (move.getDestinationFile() - move.getOriginFile() > 0) {
                for (int i = 1; i < Math.abs(move.getDestinationFile()-move.getOriginFile()); i++) {
                    x = (char)(move.getOriginFile() + i);
                    y = move.getOriginRank();
                    if(Board.getSquare(x,y).getCurrentPiece()!=null) {
                        return false;
                    }
                }
            } else if (move.getDestinationFile() - move.getOriginFile() < 0) {
                for (int i = 1; i < Math.abs(move.getDestinationFile()-move.getOriginFile()); i++) {
                    x = (char)(move.getOriginFile() - i);
                    y = move.getOriginRank();
                    if(Board.getSquare(x,y).getCurrentPiece()!=null) {
                        return false;
                    }
                }
            }
            return true;
        }
        else if (thisPiece.getType() == Piece.Type.QUEEN) {
            for (int i = 1;
                 i < Math.max(Math.abs(move.getDestinationRank() - move.getOriginRank())
                         ,Math.abs(move.getDestinationFile()-move.getOriginFile()));
                 i++) {
                char x = 'a';
                int y = 1;
                if (move.getDestinationRank() - move.getOriginRank() > 0) {
                    if (move.getDestinationFile() - move.getOriginFile() > 0) {
                        x = (char) (move.getOriginFile() + i);
                        y = move.getOriginRank() + i;
                    } else if (move.getDestinationFile() - move.getOriginFile() < 0) {
                        x = (char) (move.getOriginFile() - i);
                        y = move.getOriginRank() + i;
                    } else if (move.getDestinationFile() - move.getOriginFile() == 0) {
                        x = move.getOriginFile();
                        y = move.getOriginRank() + i;
                    }
                } else if (move.getDestinationRank() - move.getOriginRank() < 0) {
                    if (move.getDestinationFile() - move.getOriginFile() > 0) {
                        x = (char) (move.getOriginFile() + i);
                        y = move.getOriginRank() - i;
                    } else if (move.getDestinationFile() - move.getOriginFile() < 0) {
                        x = (char) (move.getOriginFile() - i);
                        y = move.getOriginRank() - i;
                    } else if (move.getDestinationFile() - move.getOriginFile() == 0) {
                        x = move.getOriginFile();
                        y = move.getOriginRank() - i;
                    }
                } else if (move.getDestinationRank() - move.getOriginRank() == 0) {
                    if (move.getDestinationFile() - move.getOriginFile() > 0) {
                        x = (char) (move.getOriginFile() + i);
                        y = move.getOriginRank();
                    } else if (move.getDestinationFile() - move.getOriginFile() < 0) {
                        x = (char) (move.getOriginFile() - i);
                        y = move.getOriginRank();
                    }
                }
                if (Board.getSquare(x, y).getCurrentPiece() != null) {
                    return false;
                }
            }
            return true;
        }

        else if (thisPiece.getType() == Piece.Type.KING) {
            return true;
        }

        return false;
    }

    public static boolean isStaleMate(Move move) {
        // TODO-check 추가적으로 할만한거
        return false;
    }

}
