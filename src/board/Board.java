package board;

import pieces.*;
import util.Core;
import util.Move;
import util.MoveValidator;

import java.util.Iterator;

public class Board {

    public static final int DIMENSION = 8;

    private static Square[][] grid = new Square[DIMENSION][DIMENSION];

    private static Board boardInstance = new Board();

    public static Board getInstance() {
        return boardInstance;
    }

    private Board() {
        initialize();
    }

    public static void initialize() {
        initializeSquares();
        initializePieces();
    }

    public static Square getSquare(char file, int rank) {
        file = Character.toLowerCase(file);
        if (file < 'a' || file > 'h' || rank < 1 || rank > 8) {
            return null;
        } else {
            return grid[file - 'a'][rank - 1];
        }
    }

    public static void executeMove(Move move) {
        Square originSquare = getSquare(move.getOriginFile(), move.getOriginRank());
        Square destinationSquare = getSquare(move.getDestinationFile(), move.getDestinationRank());
        Piece originPiece = originSquare.getCurrentPiece();
        Piece destPiece = destinationSquare.getCurrentPiece();
        if (destinationSquare.getCurrentPiece() != null) {
            destinationSquare.getCurrentPiece().setCapture(true);
            PieceSet.addCapturedPiece(destinationSquare.getCurrentPiece());
        }
        destinationSquare.setCurrentPiece(originSquare.getCurrentPiece());
        originSquare.setCurrentPiece(null);

    }

    //TODO undo
    public static void undoMove(Move lastmove) {
        Square originSquare = getSquare(lastmove.getOriginFile(), lastmove.getOriginRank());
        Square destinationSquare = getSquare(lastmove.getDestinationFile(), lastmove.getDestinationRank());
        //만약 죽인 말이 있었다면 부활시켜야함
        if (lastmove.getCapturedPiece() != null ){
            PieceSet.removeCapturedPiece(lastmove.getCapturedPiece());
        }
        // 옮긴자리는 무조건 null 만약 직업이 바뀌지 않았다면 프로모션이 안된것
        if (destinationSquare.getCurrentPiece().getClass() == lastmove.getPiece().getClass()) {
            originSquare.setCurrentPiece(destinationSquare.getCurrentPiece());
            destinationSquare.setCurrentPiece(lastmove.getCapturedPiece());
        } else if (originSquare.getCurrentPiece() != null){
            originSquare.setCurrentPiece(new Pawn(lastmove.getPiece().getColor()));
            destinationSquare.setCurrentPiece(lastmove.getCapturedPiece());
        }
    }



    private static void initializeSquares() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                grid[i][j] = new Square();
            }
        }
    }

    private static void initializePieces() {
        /*
        TODO-piece
            Initialize pieces on board
            Check following code to implement other pieces
            Highly recommended to use same template!
         */
        // pawns
        Iterator<Piece> whitePawnIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.PAWN).iterator();
        Iterator<Piece> blackPawnIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.PAWN).iterator();
        char[] li = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (char c: li) {
            getSquare(c, 2).setCurrentPiece(whitePawnIterator.next());
            getSquare(c, 7).setCurrentPiece(blackPawnIterator.next());
        }

        // knights
        Iterator<Piece> whiteKnightIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.KNIGHT).iterator();
        Iterator<Piece> blackKnightIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.KNIGHT).iterator();
        getSquare('b',1).setCurrentPiece(whiteKnightIterator.next());
        getSquare('g',1).setCurrentPiece(whiteKnightIterator.next());
        getSquare('b',8).setCurrentPiece(blackKnightIterator.next());
        getSquare('g',8).setCurrentPiece(blackKnightIterator.next());

        // bishops
        Iterator<Piece> whiteBishopIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.BISHOP).iterator();
        Iterator<Piece> blackBishopIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.BISHOP).iterator();
        getSquare('c',1).setCurrentPiece(whiteBishopIterator.next());
        getSquare('f',1).setCurrentPiece(whiteBishopIterator.next());
        getSquare('c',8).setCurrentPiece(blackBishopIterator.next());
        getSquare('f',8).setCurrentPiece(blackBishopIterator.next());


        // rooks
        Iterator<Piece> whiteRooksIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.ROOK).iterator();
        Iterator<Piece> blackRooksIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.ROOK).iterator();
        getSquare('a', 1).setCurrentPiece(whiteRooksIterator.next());
        getSquare('h', 1).setCurrentPiece(whiteRooksIterator.next());
        getSquare('a', 8).setCurrentPiece(blackRooksIterator.next());
        getSquare('h', 8).setCurrentPiece(blackRooksIterator.next());

        //Queens
        Iterator<Piece> whiteQueenIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.QUEEN).iterator();
        Iterator<Piece> blackQueenIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.QUEEN).iterator();
        getSquare('d', 1).setCurrentPiece(whiteQueenIterator.next());
        getSquare('d', 8).setCurrentPiece(blackQueenIterator.next());

        // Kings
        Iterator<Piece> whiteKingIterator = PieceSet.getPieces(Piece.Color.WHITE, Piece.Type.KING).iterator();
        Iterator<Piece> blackKingIterator = PieceSet.getPieces(Piece.Color.BLACK, Piece.Type.KING).iterator();
        getSquare('e', 1).setCurrentPiece(whiteKingIterator.next());
        getSquare('e', 8).setCurrentPiece(blackKingIterator.next());
    }

    // promotion
    public static void promote(Move move, Piece piece) {
        Square destSquare = getSquare(move.getDestinationFile(), move.getDestinationRank());
        destSquare.setCurrentPiece(piece);
    }
}
