package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{
    public King(boolean white, Board board, String image) {
        super(white, board, image);
    }

    /**
     * Check if position is valid and add it to list of moves
     * @param p position to move
     * @param moves list of possible moves
     */
    public void addPosition(Position p, List<Position> moves){
        if (getBoard().positionExist(p)){
            if (!getBoard().isFreePosition(p)){
                if (getBoard().getPiece(p).isWhite() != isWhite()){
                    moves.add(new Position(p.getCol(), p.getRow()));
                }
            }else {
                moves.add(new Position(p.getCol(), p.getRow()));
            }
        }
    }
    public List<Position> getPossibleMoves() {
        List<Position> moves = new ArrayList<>();
        Position p = new Position(0, 0);

        p.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() + 1);
        addPosition(p, moves);
        p.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() + 1);
        addPosition(p, moves);
        p.setValues(this.getPosition().getCol(), this.getPosition().getRow() + 1);
        addPosition(p, moves);
        p.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() - 1);
        addPosition(p, moves);
        p.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() - 1);
        addPosition(p, moves);
        p.setValues(this.getPosition().getCol(), this.getPosition().getRow() - 1);
        addPosition(p, moves);
        p.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow());
        addPosition(p, moves);
        p.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow());
        addPosition(p, moves);

        // add check
        if (getMoveCount() == 0 && !isChecked()){

            // kingside castling
            Position positionK = new Position(getPosition().getCol() + 3, getPosition().getRow());
            if (getBoard().getPiece(positionK) instanceof Rook
                    && getBoard().getPiece(positionK).isWhite() == isWhite()
                    && getBoard().getPiece(positionK).getMoveCount() == 0){
                Position p1 = new Position(getPosition().getCol() + 1, getPosition().getRow());
                Position p2 = new Position(getPosition().getCol() + 2, getPosition().getRow());
                if (getBoard().isFreePosition(p1) && getBoard().isFreePosition(p2)) {
                    moves.add(new Position(getPosition().getCol() + 2, getPosition().getRow()));
                }
            }

            // queenside castling
            Position positionQ = new Position(getPosition().getCol() - 4, getPosition().getRow());
            if (getBoard().getPiece(positionQ) instanceof Rook
                    && getBoard().getPiece(positionQ).isWhite() == isWhite()
                    && getBoard().getPiece(positionQ).getMoveCount() == 0){
                Position p1 = new Position(getPosition().getCol() - 1, getPosition().getRow());
                Position p2 = new Position(getPosition().getCol() - 2, getPosition().getRow());
                Position p3 = new Position(getPosition().getCol() - 3, getPosition().getRow());
                if (getBoard().isFreePosition(p1) && getBoard().isFreePosition(p2) && getBoard().isFreePosition(p3)) {
                    moves.add(new Position(getPosition().getCol() - 2, getPosition().getRow()));
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return isWhite() ? "K" : "k";
    }
}
