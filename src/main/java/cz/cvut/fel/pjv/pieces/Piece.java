package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent chess piece
 */
public abstract class Piece {

    private Position position;
    private boolean white;
    private String image;
    private Board board;
    private boolean wasMoved = false;
    private int moveCount;

    private boolean checked = false;
    private boolean enPassantPiece = false;

    public Piece(boolean white, Board board, String image) {
        this.white = white;
        this.image = image;
        this.board = board;
    }

    public boolean isWhite() {
        return white;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isEnPassantPiece() {
        return enPassantPiece;
    }

    public String getImage() {
        return image;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setEnPassantPiece(boolean enPassantPiece) {
        this.enPassantPiece = enPassantPiece;
    }

    public void increaseMoveCount(){
        moveCount++;
    }

    public void decreaseMoveCount(){
        moveCount--;
    }


    /**
     * get all possible horizontal and vertical positions
     * @return List of position
     */
    public List<Position> getLinearMoves(){
        List<Position> moves = new ArrayList<>();
        Position position = new Position(0, 0);

        position.setValues(this.position.getCol() + 1, this.position.getRow());
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setCol(position.getCol() + 1);
        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }

        position.setValues(this.position.getCol() - 1, this.position.getRow());
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setCol(position.getCol() - 1);
        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }

        position.setValues(this.position.getCol(), this.position.getRow() + 1);
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setRow(position.getRow() + 1);
        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }

        position.setValues(this.position.getCol(), this.position.getRow() - 1);
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setRow(position.getRow() - 1);
        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }
        return moves;
    }

    /**
     * get all possible diagonals positions
     * @return List of position
     */
    public List<Position> getDiagonalMoves(){
        List<Position> moves = new ArrayList<>();
        Position position = new Position(0, 0);

        position.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() + 1);
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setCol(position.getCol() + 1);
            position.setRow(position.getRow() + 1);
        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }

        position.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() - 1);
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setCol(position.getCol() - 1);
            position.setRow(position.getRow() - 1);
        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }

        position.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() - 1);
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setCol(position.getCol() + 1);
            position.setRow(position.getRow() - 1);

        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }

        position.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() + 1);
        while(getBoard().positionExist(position) && getBoard().isFreePosition(position)){
            moves.add(new Position(position.getCol(), position.getRow()));
            position.setCol(position.getCol() - 1);
            position.setRow(position.getRow() + 1);
        }
        if (getBoard().positionExist(position) && getBoard().getPiece(position).isWhite() != isWhite()){
            moves.add(new Position(position.getCol(), position.getRow()));
        }
        return moves;
    }



    /**
     * Return list of all possible moves
     */
    public abstract List<Position> getPossibleMoves();

    /**
     *
     * @return true if piece has possible moves
     */
    public boolean thereIsPossibleMoves(){
        return !getPossibleMoves().isEmpty();
    }

    public int getMoveCount() {
        return moveCount;
    }
}

