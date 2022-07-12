package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(boolean white, Board board, String image) {
        super(white, board, image);
    }

    public List<Position> getPossibleMoves() {

        List<Position> moves = new ArrayList<>();
        Position p = new Position(0, 0);
        p.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() + 2);
        addPosition(new Position(p.getCol(), p.getRow()), moves);

        p.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() - 2);
        addPosition(new Position(p.getCol(), p.getRow()), moves);

        p.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() + 2);
        addPosition(new Position(p.getCol(), p.getRow()), moves);

        p.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() - 2);
        addPosition(new Position(p.getCol(), p.getRow()), moves);

        p.setValues(this.getPosition().getCol() + 2, this.getPosition().getRow() + 1);
        addPosition(new Position(p.getCol(), p.getRow()), moves);

        p.setValues(this.getPosition().getCol() + 2, this.getPosition().getRow() - 1);
        addPosition(new Position(p.getCol(), p.getRow()), moves);

        p.setValues(this.getPosition().getCol() - 2, this.getPosition().getRow() + 1);
        addPosition(new Position(p.getCol(), p.getRow()), moves);

        p.setValues(this.getPosition().getCol() - 2, this.getPosition().getRow() - 1);
        addPosition(new Position(p.getCol(), p.getRow()), moves);



        return moves;
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
                    moves.add(p);
                }
            }else {
                moves.add(p);
            }
        }
    }

    @Override
    public String toString() {
        return isWhite() ? "N" : "n";
    }
}
