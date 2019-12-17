package ui;

import board.Board;
import pieces.*;
import util.Core;
import util.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class PromotionFrame extends JFrame {

    class promRook implements ActionListener {
        Move move;
        promRook(Move move) { this.move = move; }
        void setMove(Move move) { this.move = move; }

        @Override
        public void actionPerformed(ActionEvent a) {
            Piece p = new Rook(move.getPiece().getColor());
            Board.promote(move, p);
            Core.getGameModel().getBoardPanel().promote(move, p);
            setVisible(false);
        }
    }

    class promKnight implements ActionListener {
        Move move;
        promKnight(Move move) { this.move = move; }
        void setMove(Move move) { this.move = move; }

        @Override
        public void actionPerformed(ActionEvent a) {
            Piece p = new Knight(move.getPiece().getColor());
            Board.promote(move, p);
            Core.getGameModel().getBoardPanel().promote(move, p);
            setVisible(false);
        }
    }

    class promBishop implements ActionListener {
        Move move;
        promBishop(Move move) { this.move = move; }
        void setMove(Move move) { this.move = move; }

        @Override
        public void actionPerformed(ActionEvent a) {
            Piece p = new Bishop(move.getPiece().getColor());
            Board.promote(move, p);
            Core.getGameModel().getBoardPanel().promote(move, p);
            setVisible(false);
        }
    }

    class promQueen implements ActionListener {
        Move move;
        promQueen(Move move) { this.move = move; }
        void setMove(Move move) { this.move = move; }

        @Override
        public void actionPerformed(ActionEvent a) {
            Piece p = new Queen(move.getPiece().getColor());
            Board.promote(move, p);
            Core.getGameModel().getBoardPanel().promote(move, p);
            setVisible(false);
        }
    }

    Move move;

    promRook pRook;
    promKnight pKnight;
    promBishop pBishop;
    promQueen pQueen;

    public PromotionFrame() {
        setLayout(new GridLayout(1,4));
        setSize(300, 100);

        JButton promRookButton = new JButton("Rook");
        JButton promKnightButton = new JButton("Knight");
        JButton promBishopButton = new JButton("Bishop");
        JButton promQueenButton = new JButton("Queen");

        pRook = new promRook(move);
        pKnight = new promKnight(move);
        pBishop = new promBishop(move);
        pQueen = new promQueen(move);

        promRookButton.addActionListener(pRook);
        promKnightButton.addActionListener(pKnight);
        promBishopButton.addActionListener(pBishop);
        promQueenButton.addActionListener(pQueen);

        add(promRookButton);
        add(promKnightButton);
        add(promBishopButton);
        add(promQueenButton);
    }

    public void setMove(Move move) {
        this.move = move;
        pRook.setMove(move);
        pKnight.setMove(move);
        pBishop.setMove(move);
        pQueen.setMove(move);
    }
}
