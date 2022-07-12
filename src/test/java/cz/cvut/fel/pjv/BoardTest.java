package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Board board = new Board();
    @Test
    public void positionExist(){
        Position position = new Position(7 ,7);
        assertTrue(board.positionExist(position));
    }

    @Test
    public void positionExist_wrongPosition_returnFalse(){
        Position position = new Position(-1 ,0);
        assertFalse(board.positionExist(position));
    }
}

