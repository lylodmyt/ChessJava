package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{
    public Queen(boolean white, Board board, String image) {
        super(white, board, image);
    }

    public List<Position> getPossibleMoves() {
        List<Position> moves = new ArrayList<>();
        moves.addAll(getLinearMoves());
        moves.addAll(getDiagonalMoves());
        return moves;
    }

    @Override
    public String toString() {
        return isWhite() ? "Q" : "q";
    }
}
