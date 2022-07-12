package cz.cvut.fel.pjv;


import cz.cvut.fel.pjv.board.Position;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to write game moves into a file
 */
public class GameWriter {

    String whiteMoves = "";
    String blackMoves = "";
    public void writeGame(){
        try {
            FileWriter writer = new FileWriter("lastgame.txt", false);
            writer.write("White moves: " + whiteMoves + "\n");
            writer.write("Black moves: " + blackMoves + "\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add move into string
     * @param from source position
     * @param to target position
     * @param move move number
     * @param player white(true) or black(false) player
     */
    public void writeMove(Position from, Position to, int move, boolean player){
        if (player){
            whiteMoves += move + ". " + colToLetter(from.getCol()) + rowToInt(from.getRow()) + " -> " + colToLetter(to.getCol()) + rowToInt(to.getRow()) + " ";
        }else {
            blackMoves += move + ". " + colToLetter(from.getCol()) + rowToInt(from.getRow()) + " -> " + colToLetter(to.getCol()) + rowToInt(to.getRow()) + " ";
        }
    }


    private String colToLetter(int col){
        if (col == 0) return "a";
        if (col == 1) return "b";
        if (col == 2) return "c";
        if (col == 3) return "d";
        if (col == 4) return "e";
        if (col == 5) return "f";
        if (col == 6) return "g";
        return "h";
    }

    private int rowToInt(int row){
        if (row == 0) return 8;
        if (row == 1) return 7;
        if (row == 2) return 6;
        if (row == 3) return 5;
        if (row == 4) return 4;
        if (row == 5) return 3;
        if (row == 6) return 2;
        return 1;
    }
}
