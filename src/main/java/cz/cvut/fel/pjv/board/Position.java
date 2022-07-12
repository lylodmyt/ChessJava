package cz.cvut.fel.pjv.board;

import java.util.Objects;


/**
 * Class represent position on board
 */
public class Position {
    private int col;
    private int row;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setValues(int col, int row) {
        setCol(col);
        setRow(row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return col == position.col &&
                row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }
}
