package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(boolean white, Board board, String image) {
        super(white, board, image);
    }

    public List<Position> getPossibleMoves() {
        List<Position> moves = new ArrayList<>();
        Position position = new Position(0, 0);
        if (this.isWhite()){

            position.setValues(this.getPosition().getCol(), this.getPosition().getRow() - 2);
            if (getMoveCount() == 0 && this.getBoard().positionExist(position) && this.getBoard().isFreePosition(position)){
                moves.add(new Position(position.getCol(), position.getRow()));
            }

            position.setValues(this.getPosition().getCol(), this.getPosition().getRow() - 1);
            if (this.getBoard().positionExist(position) && this.getBoard().isFreePosition(position)){
                moves.add(new Position(position.getCol(), position.getRow()));
            }

            position.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() - 1);
            if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                if (!getBoard().getPiece(position).isWhite()){
                    moves.add(new Position(position.getCol(), position.getRow()));
                }
            }

            position.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() - 1);
            if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                if (!getBoard().getPiece(position).isWhite()){
                    moves.add(new Position(position.getCol(), position.getRow()));
                }
            }

            // en passant move
            if (this.getPosition().getRow() == 3) {
               position.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow());
               if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                    if (!getBoard().getPiece(position).isWhite() && getBoard().getPiece(position).isEnPassantPiece()){
                        moves.add(new Position(position.getCol(), position.getRow() - 1));
                    }
                }

               position.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow());
               if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                    if (!getBoard().getPiece(position).isWhite() && getBoard().getPiece(position).isEnPassantPiece()){
                        moves.add(new Position(position.getCol(), position.getRow() - 1));
                    }
                }
            }
        }

        if (!this.isWhite()){

            position.setValues(this.getPosition().getCol(), this.getPosition().getRow() + 2);
            if (getMoveCount() == 0 && this.getBoard().positionExist(position) && this.getBoard().isFreePosition(position)){
                moves.add(new Position(position.getCol(), position.getRow()));
            }

            position.setValues(this.getPosition().getCol(), this.getPosition().getRow() + 1);
            if (this.getBoard().positionExist(position) && this.getBoard().isFreePosition(position)){
                moves.add(new Position(position.getCol(), position.getRow()));
            }

            position.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow() + 1);
            if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                if (getBoard().getPiece(position).isWhite()){
                    moves.add(new Position(position.getCol(), position.getRow()));
                }
            }

            position.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow() + 1);
            if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                if (getBoard().getPiece(position).isWhite()){
                    moves.add(new Position(position.getCol(), position.getRow()));
                }
            }

            // en passant move
            if (this.getPosition().getRow() == 4) {
                position.setValues(this.getPosition().getCol() - 1, this.getPosition().getRow());
                if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                    if (getBoard().getPiece(position).isWhite() && getBoard().getPiece(position).isEnPassantPiece()){
                        moves.add(new Position(position.getCol(), position.getRow() + 1));
                    }
                }

                position.setValues(this.getPosition().getCol() + 1, this.getPosition().getRow());
                if (getBoard().positionExist(position) && !getBoard().isFreePosition(position)){
                    if (getBoard().getPiece(position).isWhite() && getBoard().getPiece(position).isEnPassantPiece()){
                        moves.add(new Position(position.getCol(), position.getRow() + 1));
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return isWhite() ? "P" : "p";
    }


}
