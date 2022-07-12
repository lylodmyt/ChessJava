package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;

import java.util.List;

public class Rook extends Piece {


    public Rook(boolean white, Board board, String image) {
        super(white, board, image);
    }

    public List<Position> getPossibleMoves() {
        return getLinearMoves();
    }

    @Override
    public String toString() {
        return isWhite() ? "R" : "r";
    }
}
