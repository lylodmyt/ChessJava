package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.board.Position;
import cz.cvut.fel.pjv.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class to represent simple ai that make random moves
 */
public class AI {

    public Random random = new Random();

    /**
     * Make move in chess match
     * @param chessMatch given chess match
     */
    public void makeMove(ChessMatch chessMatch){
        List<Piece> blackPieces = new ArrayList<>();
        chessMatch.getAllPieces().forEach(piece -> {if (!piece.isWhite()) blackPieces.add(piece);});
        int rnd = blackPieces.size();
        Piece currentPiece = blackPieces.get(random.nextInt(rnd));
        while (chessMatch.getValidPosition(currentPiece.getPosition()).isEmpty()){
            rnd--;
            blackPieces.remove(currentPiece);
            currentPiece = blackPieces.get(random.nextInt(rnd));
        }

        List<Position> moves = chessMatch.getValidPosition(currentPiece.getPosition());
        rnd = moves.size();
        Position position = moves.get((random.nextInt(rnd)));

        chessMatch.makeMove(currentPiece.getPosition(), position);
    }
}
