package cz.cvut.fel.pjv.board;

import cz.cvut.fel.pjv.pieces.Piece;

/**
 * Class chess represent board
 */

public class Board {

    private final Piece[][] pieces;

    public Board() {
        this.pieces = new Piece[8][8];
    }

    /**
     * @param position position on the board
     * @return piece on given position
     */
    public Piece getPiece(Position position) {
        return pieces[position.getCol()][position.getRow()];
    }

    /**
     * Method to place given piece on given position
     * @param piece which piece place on the board
     * @param position position to place piece
     */
    public void placePiece(Piece piece, Position position){
        pieces[position.getCol()][position.getRow()] = piece;
        piece.setPosition(position);
    }

    /**
     * Method to remove piece from given position
     * @param position position on the board
     * @return null if position is empty or piece if position is not empty
     */
    public Piece removePiece(Position position){
        if (getPiece(position) == null){
            return null;
        }
        Piece piece = getPiece(position);
        pieces[position.getCol()][position.getRow()] = null;
        piece.setPosition(null);
        return piece;
    }

    /**
     * Method to control if given position is empty
     * @param position given position
     * @return true if position is empty, false if position is not epty
     */
    public boolean isFreePosition(Position position){
        return pieces[position.getCol()][position.getRow()] == null;
    }

    /**
     * Check if chosen position exist on board
     * @param position - position to check
     * @return true if position exist on board
     */

    public boolean positionExist(Position position){
        return position.getCol() < 8 && position.getCol() >= 0 && position.getRow() < 8 && position.getRow() >= 0;
    }

    /**
     * Represent board in string format
     */
    @Override
    public String toString() {
        StringBuilder board = new StringBuilder("  0 1 2 3 4 5 6 7");
        board.append("\n");

        for (int row = 0; row < 8; row++) {
            board.append(row);
            for (int col = 0; col < 8; col++) {
                if (pieces[col][row] == null) {
                    board.append(" .");
                } else {
                    board.append(" ").append(pieces[col][row].toString());
                }
            }
            board.append("\n");
        }
        return board.toString();
    }

}
