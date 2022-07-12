package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.board.Position;
import cz.cvut.fel.pjv.ChessMatch;
import cz.cvut.fel.pjv.pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessMatchTest {

    @Test
    public void replacePieceTest(){
        ChessMatch cm = new ChessMatch(false);
        cm.setPromotedPiece(cm.getBoard().getPiece(new Position(0, 1)));
        assertTrue(cm.replacePiece("rook") instanceof Rook);
    }

    @Test
    public void moveCountIncrease(){
        ChessMatch cm = new ChessMatch(false);
        cm.makeMove(new Position(0,6), new Position(0,5));
        assertEquals(cm.getMoveCount(), 1);
        cm.makeMove(new Position(0,1), new Position(0,2));
        assertEquals(cm.getMoveCount(), 2);
    }

    @Test
    public void validatePositionFromTest(){
        ChessMatch cm = new ChessMatch(false);
        assertTrue(cm.validatePositionFrom(new Position(0,6)));
        assertFalse(cm.validatePositionFrom(new Position(0,5)));
        assertFalse(cm.validatePositionFrom(new Position(0,7)));
        assertFalse(cm.validatePositionFrom(new Position(0,2)));
    }

    @Test
    public void fastCheckMateTest(){
        ChessMatch cm = new ChessMatch(false);
        cm.makeMove(new Position(6,6), new Position(6, 4));
        cm.makeMove(new Position(4, 1), new Position(4, 3));
        cm.makeMove(new Position(5,6), new Position(5, 4));
        cm.makeMove(new Position(3,0), new Position(7, 4));
        assertTrue(cm.isCheckmate());
    }

}
