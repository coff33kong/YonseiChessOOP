package util;

import board.Board;
import board.Square;
import pieces.King;
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
        System.out.println(move.getPiece().getColor()); // black
        System.out.println(currentMoveColor);
        // king's location king 은 move.getPiece().getColor()에 반대되는 색이다.
        char kingF = PieceSet.getOpponentKingFile(move.getPiece().getColor()); // white
        int kingR = PieceSet.getOpponentKingRank(move.getPiece().getColor());
        System.out.println(kingF);
        System.out.println(kingR);
        // king 의 상하 좌우 위치
        int kingRight = 'h' - kingF;
        int kingLeft = kingF - 'a';
        int kingAbove = 8 - kingR;
        int kingBelow = kingR - 1;
        // squre 칸 할당할 변수 두개
        char x;
        int y;
        // 오른쪽 위 대각선 체크하기
        for (int i = 1; i <= Math.min(kingRight,kingAbove); i++) {
            x = (char) (kingF + i);
            y = kingR + i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN)) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    (Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN)) {
                return true;
            }
        }
        //왼쪽 위 대각선 확인하기
        for (int i = 1; i <= Math.min(kingLeft,kingAbove); i++) {
            x = (char) (kingF - i);
            y = kingR + i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN)) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN) {
                return true;
            }
        }
        //오른쪽 아래 대각선 확인하기
        for (int i = 1; i <= Math.min(kingRight,kingBelow); i++) {
            x = (char) (kingF + i);
            y = kingR - i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN)) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN) {
                return true;
            }
        }
        //왼쪽 아래 대각선 확인하기
        for (int i = 1; i <= Math.min(kingLeft,kingBelow); i++) {
            x = (char) (kingF - i);
            y = kingR - i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN)) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN) {
                return true;
            }
        }
        //오른쪽 직선 확인하기
        for (int i = 1; i <= kingRight; i++) {
            x = (char) (kingF + i);
            y = kingR ;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    (Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN))) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN) {
                return true;
            }
        }
        //왼쪽 직선 확인하기
        for (int i = 1; i <= kingLeft; i++) {
            x = (char) (kingF - i);
            y = kingR ;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    (Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN))) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN) {
                return true;
            }
        }
        //위 직선 확인하기
        for (int i = 1; i <= kingAbove; i++) {
            x = kingF;
            y = kingR + i;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    (Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN))) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN) {
                return true;
            }
        }
        //아래 직선 확인하기
        for (int i = 1; i <= kingBelow; i++) {
            x = kingF;
            y = kingR - i;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(Board.getSquare(x,y).getCurrentPiece().getColor() != move.getPiece().getColor() ||
                    (Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                            !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                            || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN))) {
                break;
            }
            if(Board.getSquare(x,y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                    || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN) {
                return true;
            }
        }
        // 나이트 위치 확인하기
        for (int i = 0; i < 8 ; i++) {
            x = 'a';
            y = 1;
            if (i==0) {
                x = (char)(kingF + 2);
                y = kingR + 1;
            } else if (i==1) {
                x = (char)(kingF + 2);
                y = kingR - 1;
            } else if (i==2) {
                x = (char)(kingF + 1);
                y = kingR + 2;
            } else if (i==3) {
                x = (char)(kingF - 1);
                y = kingR + 2;
            } else if (i==4) {
                x = (char)(kingF - 2);
                y = kingR + 1;
            } else if (i==5) {
                x = (char)(kingF - 2);
                y = kingR - 1;
            } else if (i==6) {
                x = (char)(kingF - 1);
                y = kingR + 2;
            } else if (i==7) {
                x = (char)(kingF - 1);
                y = kingR - 2;
            }
            if ( x < 'a' || x > 'h'|| y < 1|| y > 8 )
                continue;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if (Board.getSquare(x, y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                    Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.KNIGHT)
                return true;

        }
        //폰이랑 킹 위치 확인
        x = (char)(kingF + 1);
        y = kingR - 1;
        if ( 'a' <= x && x <= 'h' && 1<= y && y <= 8 ) {
            if ((Board.getSquare(x, y).getCurrentPiece() != null)) {
                if (Board.getSquare(x, y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                        move.getPiece().getColor() == Piece.Color.WHITE &&
                        Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                    return true;
            }
        }
        x = (char)(kingF - 1);
        y = kingR - 1;
        if ( 'a' <= x && x <= 'h' && 1<= y && y <= 8  ) {
            if (Board.getSquare(x, y).getCurrentPiece() != null) {
                if (Board.getSquare(x, y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                        move.getPiece().getColor() == Piece.Color.WHITE &&
                        Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                    return true;
            }
        }
        x = (char)(kingF + 1);
        y = kingR + 1;
        if ( 'a' <= x && x <= 'h' && 1<= y && y <= 8  ) {
            if (Board.getSquare(x, y).getCurrentPiece() != null) {
                if (Board.getSquare(x, y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                        move.getPiece().getColor() == Piece.Color.BLACK &&
                        Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                    return true;
            }
        }
        x = (char)(kingF - 1);
        y = kingR + 1;
        if ( 'a' <= x && x <= 'h' && 1<= y && y <= 8  ) {
            if (Board.getSquare(x, y).getCurrentPiece() != null) {
                if (Board.getSquare(x, y).getCurrentPiece().getColor() == move.getPiece().getColor() &&
                        move.getPiece().getColor() == Piece.Color.BLACK &&
                        Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                    return true;
            }
        }



        return false;
    }

    public static boolean isCheckMate(Move move) {
        // TODO-check 8번 is check 를 확인하고 공격하는 말과 왕 사이에 올 수 있는 아군이 존재하는지 확인
        //공격하고 있는 Piece 의 종류
        Piece thisPiece = Board.getSquare(move.getDestinationFile(), move.getDestinationRank()).getCurrentPiece();
        //왕 위치
        char kingF = PieceSet.getOpponentKingFile(move.getPiece().getColor());
        int kingR = PieceSet.getOpponentKingRank(move.getPiece().getColor());
        Piece.Color opponentKingColor = move.getPiece().getColor();

        if (thisPiece.getType() == Piece.Type.PAWN) {
            if (thisPiece.getColor() == Piece.Color.WHITE) {
                //color is white

            } else {
                //color is black

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

        return false;
    }


    public static boolean longLiveTheKing(Piece.Color color, char kingFile, int kingRank) {

        // king's location king 은 move.getPiece().getColor()에 반대되는 색이다.
        char kingF = kingFile; // white
        int kingR = kingRank;
        System.out.println(kingF);
        System.out.println(kingR);
        // king 의 상하 좌우 위치
        int kingRight = 'h' - kingF;
        int kingLeft = kingF - 'a';
        int kingAbove = 8 - kingR;
        int kingBelow = kingR - 1;
        // squre 칸 할당할 변수 두개
        char x;
        int y;
        // 오른쪽 위 대각선 체크하기
        for (int i = 1; i <= Math.min(kingRight,kingAbove); i++) {
            x = (char) (kingF + i);
            y = kingR + i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(diagonalObstacle(x,y,color)) {
                break;
            }
            if(diagonalOpponent(x,y,color)) {
                return true;
            }
        }
        //왼쪽 위 대각선 확인하기
        for (int i = 1; i <= Math.min(kingLeft,kingAbove); i++) {
            x = (char) (kingF - i);
            y = kingR + i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(diagonalObstacle(x,y,color)) {
                break;
            }
            if(diagonalOpponent(x,y,color)) {
                return true;
            }
        }
        //오른쪽 아래 대각선 확인하기
        for (int i = 1; i <= Math.min(kingRight,kingBelow); i++) {
            x = (char) (kingF + i);
            y = kingR - i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(diagonalObstacle(x,y,color)) {
                break;
            }
            if(diagonalOpponent(x,y,color)) {
                return true;
            }
        }
        //왼쪽 아래 대각선 확인하기
        for (int i = 1; i <= Math.min(kingLeft,kingBelow); i++) {
            x = (char) (kingF - i);
            y = kingR - i;
            // 대각에서 공격가능하면 check
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(diagonalObstacle(x,y,color)) {
                break;
            }
            if(diagonalOpponent(x,y,color)) {
                return true;
            }
        }
        //오른쪽 직선 확인하기
        for (int i = 1; i <= kingRight; i++) {
            x = (char) (kingF + i);
            y = kingR ;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(linearObstacle(x,y,color)) {
                break;
            }
            if(linearOpponent(x,y,color)) {
                return true;
            }
        }
        //왼쪽 직선 확인하기
        for (int i = 1; i <= kingLeft; i++) {
            x = (char) (kingF - i);
            y = kingR ;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(linearObstacle(x,y,color)) {
                break;
            }
            if(linearOpponent(x,y,color)) {
                return true;
            }
        }
        //위 직선 확인하기
        for (int i = 1; i <= kingAbove; i++) {
            x = kingF;
            y = kingR + i;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(linearObstacle(x,y,color)) {
                break;
            }
            if(linearOpponent(x,y,color)) {
                return true;
            }
        }
        //아래 직선 확인하기
        for (int i = 1; i <= kingBelow; i++) {
            x = kingF;
            y = kingR - i;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if(linearObstacle(x,y,color)) {
                break;
            }
            if(linearOpponent(x,y,color)) {
                return true;
            }
        }
        // 나이트 위치 확인하기
        for (int i = 0; i < 8 ; i++) {
            x = 'a';
            y = 1;
            if (i==0) {
                x = (char)(kingF + 2);
                y = kingR + 1;
            } else if (i==1) {
                x = (char)(kingF + 2);
                y = kingR - 1;
            } else if (i==2) {
                x = (char)(kingF + 1);
                y = kingR + 2;
            } else if (i==3) {
                x = (char)(kingF - 1);
                y = kingR + 2;
            } else if (i==4) {
                x = (char)(kingF - 2);
                y = kingR + 1;
            } else if (i==5) {
                x = (char)(kingF - 2);
                y = kingR - 1;
            } else if (i==6) {
                x = (char)(kingF - 1);
                y = kingR + 2;
            } else if (i==7) {
                x = (char)(kingF - 1);
                y = kingR - 2;
            }
            if ( x < 'a' || x > 'h'|| y < 1|| y > 8 )
                continue;
            if (Board.getSquare(x,y).getCurrentPiece() == null)
                continue;
            if (Board.getSquare(x, y).getCurrentPiece().getColor() != color &&
                    Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.KNIGHT)
                return true;

        }
        //폰이랑 킹 위치 확인
        if (color == Piece.Color.BLACK) {
            x = (char) (kingF + 1);
            y = kingR - 1;
            if ('a' <= x && x <= 'h' && 1 <= y && y <= 8) {
                if ((Board.getSquare(x, y).getCurrentPiece() != null)) {
                    if (Board.getSquare(x, y).getCurrentPiece().getColor() == Piece.Color.WHITE &&
                            Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                        return true;
                }
            }
            x = (char) (kingF - 1);
            y = kingR - 1;
            if ('a' <= x && x <= 'h' && 1 <= y && y <= 8) {
                if (Board.getSquare(x, y).getCurrentPiece() != null) {
                    if (Board.getSquare(x, y).getCurrentPiece().getColor() == Piece.Color.WHITE &&
                            Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                        return true;
                }
            }
        } else {
            x = (char) (kingF + 1);
            y = kingR + 1;
            if ('a' <= x && x <= 'h' && 1 <= y && y <= 8) {
                if (Board.getSquare(x, y).getCurrentPiece() != null) {
                    if (Board.getSquare(x, y).getCurrentPiece().getColor() == Piece.Color.BLACK &&
                            Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                        return true;
                }
            }
            x = (char) (kingF - 1);
            y = kingR + 1;
            if ('a' <= x && x <= 'h' && 1 <= y && y <= 8) {
                if (Board.getSquare(x, y).getCurrentPiece() != null) {
                    if (Board.getSquare(x, y).getCurrentPiece().getColor() == Piece.Color.BLACK &&
                            Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.PAWN)
                        return true;
                }
            }
        }

        return false;
    }
    private static boolean diagonalObstacle(char x, int y, Piece.Color color) {
        return Board.getSquare(x,y).getCurrentPiece().getColor() == color ||
                Board.getSquare(x,y).getCurrentPiece().getColor() != color &&
                        !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.BISHOP
                                || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN);
    }
    private static boolean diagonalOpponent(char x, int y, Piece.Color color) {
        return Board.getSquare(x, y).getCurrentPiece().getColor() != color &&
                (Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.BISHOP
                        || Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.QUEEN);
    }
    private static boolean linearObstacle(char x, int y, Piece.Color color) {
        return Board.getSquare(x,y).getCurrentPiece().getColor() == color ||
                (Board.getSquare(x,y).getCurrentPiece().getColor() != color &&
                        !(Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.ROOK
                                || Board.getSquare(x,y).getCurrentPiece().getType() == Piece.Type.QUEEN));
    }
    private static boolean linearOpponent(char x, int y, Piece.Color color) {
        return Board.getSquare(x, y).getCurrentPiece().getColor() != color &&
                Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.ROOK
                || Board.getSquare(x, y).getCurrentPiece().getType() == Piece.Type.QUEEN;
    }

    public static boolean protectedKing(Move move, Piece.Color color) {
        Square originSquare = Board.getSquare(move.getOriginFile(), move.getOriginRank());
        Square destinationSquare = Board.getSquare(move.getDestinationFile(), move.getDestinationRank());
        Piece tmp = originSquare.getCurrentPiece();
        Piece tmp2 = destinationSquare.getCurrentPiece();
        originSquare.setCurrentPiece(null);
        destinationSquare.setCurrentPiece(tmp);
        if(MoveValidator.longLiveTheKing(color, PieceSet.getKingFile(color), PieceSet.getKingRank(color))) {
            originSquare.setCurrentPiece(tmp);
            destinationSquare.setCurrentPiece(tmp2);
            return true;
        }
        originSquare.setCurrentPiece(tmp);
        destinationSquare.setCurrentPiece(tmp2);
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
        System.out.println(currentMoveColor);

        //Square originSquare = Board.getSquare(move.getOriginFile(), move.getOriginRank());
        //originSquare.setCurrentPiece(null);

        if (thisPiece.getType() == Piece.Type.PAWN) {
            if (thisPiece.getColor() == Piece.Color.WHITE) {
                //color is white
                // 이동조건 확인
                if (move.getDestinationRank()-move.getOriginRank() == 2 ){
                    Piece blockPiece =
                            Board.getSquare(move.getOriginFile(), move.getOriginRank()+1).getCurrentPiece();
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
            PieceSet.setKingFile(move.getPiece().getColor(), move.getDestinationFile());
            PieceSet.setKingRank(move.getPiece().getColor(), move.getDestinationRank());
            if (MoveValidator.longLiveTheKing(move.getPiece().getColor(),
                    PieceSet.getKingFile(move.getPiece().getColor()),
                    PieceSet.getKingFile(move.getPiece().getColor()))) {
                PieceSet.setKingFile(move.getPiece().getColor(), move.getOriginFile());
                PieceSet.setKingRank(move.getPiece().getColor(), move.getOriginRank());
                return false;
            }

            return true;
        }

        return false;
    }


    public static boolean isStaleMate(Move move) {
        // TODO-check 추가적으로 할만한거
        return false;
    }


}
