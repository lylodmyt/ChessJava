package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Position;
import cz.cvut.fel.pjv.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class represent game logic
 */

public class ChessMatch {


    public static final Logger LOGGER = Logger.getLogger(ChessMatch.class.getName());

    private final Board board;

    private boolean whitePlayer;
    private boolean checkmate;
    private boolean stalemate;
    private boolean check;
    private List<Piece> allPieces = new ArrayList<>();
    private Piece promotedPiece;
    private int moveCount = 1;

    private final boolean aiGame;
    private final AI aiPlayer = new AI();


    private final GameWriter writer = new GameWriter();

    public ChessMatch(boolean aiGame) {
        this.board = new Board();
        this.aiGame = aiGame;
        setUp();
        this.whitePlayer = true;
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public Board getBoard() {
        return board;
    }

    public List<Piece> getAllPieces() {
        return allPieces;
    }

    public Piece getPromotedPiece() {
        return promotedPiece;
    }

    public void setPromotedPiece(Piece promotedPiece) {
        this.promotedPiece = promotedPiece;
    }

    public boolean isAiGame() {
        return aiGame;
    }

    public boolean isWhitePlayer() {
        return whitePlayer;
    }

    public AI getAiPlayer() {
        return aiPlayer;
    }

    public boolean isStalemate() {
        return stalemate;
    }

    public GameWriter getWriter() {
        return writer;
    }

    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Method to move piece from source position to target position
     * @param from source position
     * @param to target position
     */
    public void makeMove(Position from, Position to){
        if (validatePositionFrom(from) && validatePositionTo(from, to)){
            Piece piece = doMove(from, to);
            promotedPiece = null;

            if (testCheck(whitePlayer)){
                undoMove(from, to, piece);
                LOGGER.info("self check");
                return;
            }

            Piece moved = board.getPiece(to);
            // promotion
            if (moved instanceof Pawn) {
                if (to.getRow() == 0 || to.getRow() == 7) {
                    LOGGER.info("PROMOTION");
                    promotedPiece = moved;
                    promotedPiece = replacePiece("queen");
                }
            }

            if (testCheckmate(!whitePlayer)){
                writer.writeMove(from, to, moveCount, whitePlayer);
                checkmate = true;
                LOGGER.info("CHECKMATE");
            }else {
                writer.writeMove(from, to, moveCount, whitePlayer);
                if (!whitePlayer) moveCount++;
                whitePlayer = !whitePlayer;
            }

            if (testStaleMate(whitePlayer)){
                stalemate = true;
                writer.writeMove(from, to, moveCount, whitePlayer);
                LOGGER.info("STALEMATE");
            }

            // en passant
            if (moved instanceof Pawn && (to.getRow() == from.getRow() - 2 || to.getRow() == from.getRow() + 2)) {
                moved.setEnPassantPiece(true);
            }else {
                getAllPieces().forEach(piece1 -> piece1.setEnPassantPiece(false));
            }
        }
    }

    /**
     * Return new piece by name
     * @param name name of piece
     * @return new Piece by name
     */
    public Piece replacePiece(String name){
        Position position = promotedPiece.getPosition();
        Piece piece = board.removePiece(position);
        allPieces.remove(piece);

        Piece newPiece = null;
        if (name.equals("queen")){
            if (promotedPiece.isWhite()) newPiece = new Queen(promotedPiece.isWhite(), board, "WQ.png");
            else newPiece = new Queen(promotedPiece.isWhite(), board, "BQ.png");
        }
        if (name.equals("rook")) {
            if (promotedPiece.isWhite()) newPiece = new Rook(promotedPiece.isWhite(), board, "WR.png");
            else newPiece = new Rook(promotedPiece.isWhite(), board, "BR.png");
        }
        if (name.equals("bishop")){
            if (promotedPiece.isWhite()) newPiece = new Bishop(promotedPiece.isWhite(), board, "WB.png");
            else newPiece = new Rook(promotedPiece.isWhite(), board, "BB.png");
        }
        if (name.equals("knight")){
            if (promotedPiece.isWhite()) newPiece = new Knight(promotedPiece.isWhite(), board, "WN.png");
            else newPiece = new Rook(promotedPiece.isWhite(), board, "BN.png");
        }
        assert newPiece != null;
        board.placePiece(newPiece, position);
        allPieces.add(newPiece);

        return newPiece;
    }

    /**
     * Method to control if source position is valid in board
     * @param from target position
     * @return true if position is valid
     */
    public boolean validatePositionFrom(Position from){
        if (board.isFreePosition(from)){
            LOGGER.info("Position is empty");
            return false;
        }
        else if (board.getPiece(from).isWhite() != whitePlayer){
            LOGGER.info("Opponent's piece");
            return false;
        }
        else if (!board.getPiece(from).thereIsPossibleMoves()){
            LOGGER.info("There aren't possible moves");
            return false;
        }
        else if (getValidPosition(from).isEmpty()){
            LOGGER.info("There aren't possible moves");
            return false;
        }
        return true;
    }

    /**
     * Method to control if target position is valid in board
     * @param from source position
     * @param to target position
     * @return true if position is valid
     */
    public boolean validatePositionTo(Position from, Position to){
        if (!getValidPosition(from).contains(to)){
            LOGGER.info("Can't move to this position");
            return false;
        }
        return true;
    }


    private Piece doMove(Position from, Position to){
        Piece piece = board.removePiece(from);
        piece.increaseMoveCount();
        Piece opponentPiece = board.removePiece(to);
        board.placePiece(piece, to);

        if (opponentPiece != null){
            allPieces.remove(opponentPiece);
        }
        // castling queenside
        if (piece instanceof King && to.getCol() == from.getCol() - 2){
            Position rookFrom = new Position(from.getCol() - 4, from.getRow());
            Position rookTo = new Position(from.getCol() - 1, from.getRow());
            Piece rook = board.removePiece(rookFrom);
            board.placePiece(rook, rookTo);
            rook.increaseMoveCount();
        }
        // castling kingside
        if (piece instanceof King && to.getCol() == from.getCol() + 2){
            Position rookFrom = new Position(from.getCol() + 3, from.getRow());
            Position rookTo = new Position(from.getCol() + 1, from.getRow());
            Piece rook = board.removePiece(rookFrom);
            board.placePiece(rook, rookTo);
            rook.increaseMoveCount();
        }
        // en passant
        if (piece instanceof Pawn){
            if (from.getCol() != to.getCol() && opponentPiece == null){
                Position pawn;
                if (piece.isWhite()){
                    pawn = new Position(to.getCol(), to.getRow() + 1);
                } else {
                    pawn = new Position(to.getCol(), to.getRow() - 1);
                }
                opponentPiece = board.removePiece(pawn);
                allPieces.remove(opponentPiece);
            }
        }
        return opponentPiece;
    }

    private void undoMove(Position from, Position to, Piece opponentPiece){
        Piece piece = board.removePiece(to);
        piece.decreaseMoveCount();
        board.placePiece(piece, from);

        if (opponentPiece != null){
            board.placePiece(opponentPiece, to);
            allPieces.add(opponentPiece);
        }
        // castling queenside
        if (piece instanceof King && to.getCol() == from.getCol() - 2){
            Position rookFrom = new Position(from.getCol() - 4, from.getRow());
            Position rookTo = new Position(from.getCol() - 1, from.getRow());
            Piece rook = board.removePiece(rookTo);
            board.placePiece(rook, rookFrom);
            rook.decreaseMoveCount();
        }
        // castling kingside
        if (piece instanceof King && to.getCol() == from.getCol() + 2){
            Position rookFrom = new Position(from.getCol() + 3, from.getRow());
            Position rookTo = new Position(from.getCol() + 1, from.getRow());
            Piece rook = board.removePiece(rookTo);
            board.placePiece(rook, rookFrom);
            rook.decreaseMoveCount();
        }
        // en passant
        if (piece instanceof Pawn){
            if (from.getCol() != to.getCol() && opponentPiece.isEnPassantPiece()){
                Piece pawn = board.removePiece(to);
                Position pawnPosition;
                if (piece.isWhite()){
                    pawnPosition = new Position(to.getCol(), 3);
                } else {
                    pawnPosition = new Position(to.getCol(), 4);
                }
                board.placePiece(pawn, pawnPosition);
            }
        }

    }

    public List<Position> getValidPosition(Position position){
        List<Position> res = new ArrayList<>();
        List<Position> moves = board.getPiece(position).getPossibleMoves();
        for (Position move : moves){
            Piece piece = doMove(position, move);
            if (!testCheck(whitePlayer)){
                res.add(move);
            }
            undoMove(position, move, piece);
        }
        return res;
    }

    private Position getKingPosition(boolean white){
        Position position = null;
        for (Piece piece : allPieces){
            if (piece instanceof King && piece.isWhite() == white) position = piece.getPosition();
        }
        if (position == null) {
            LOGGER.info("There is not king in the board");
            return null;
        }
        return position;
    }

    private boolean testCheck(boolean white){
        Position kingPosition = getKingPosition(white);
        for (Piece piece : allPieces){
            if (piece.isWhite() != white){
                List<Position> moves = piece.getPossibleMoves();
                if (moves.contains(kingPosition)) {
                    LOGGER.info(white ? "WHITE CHECK" : "BLACK CHECK");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean testCheckmate(boolean white){
        if (!testCheck(white)){
            return false;
        }
        List<Piece> playerPieces = new ArrayList<>();
        for (Piece piece : allPieces) {
            if (piece.isWhite() == white) {
                playerPieces.add(piece);
            }
        }
        for (Piece piece : playerPieces){
            List<Position> positions = piece.getPossibleMoves();
            for (Position position : positions){
                Position from = piece.getPosition();
                Piece movePiece = doMove(from, position);
                boolean check = testCheck(white);
                undoMove(from, position, movePiece);
                if (!check) return false;
            }
        }
        LOGGER.info("CHECKMATE");
        return true;
    }

    private boolean testStaleMate(boolean white){
        List<Piece> playerPieces = new ArrayList<>();
        for (Piece piece : allPieces) {
            if (piece.isWhite() == white) {
                playerPieces.add(piece);
            }
        }
        List<Position> positions = new ArrayList<>();
        for (Piece piece : playerPieces){
            positions.addAll(getValidPosition(piece.getPosition()));
        }
        return positions.isEmpty();
    }


    /**
     * Place chess piece to the board
     * @param piece Chess piece
     * @param col number of column on the board
     * @param row number of row on the board
     */
    private void placePiece(Piece piece, int col, int row){
        Position position = new Position(col, row);
        board.placePiece(piece, position);
        allPieces.add(piece);
    }

    private void setUp() {
        for (int col = 0; col < 8; col++) {
            placePiece(new Pawn(true, board, "WP.png"), col, 6);
        }
        placePiece(new Rook(true, board, "WR.png"), 0, 7);
        placePiece(new Knight(true, board, "WN.png"), 1, 7);
        placePiece(new Bishop(true, board, "WB.png"), 2, 7);
        placePiece(new Queen(true, board, "WQ.png"), 3, 7);
        placePiece(new King(true, board, "WK.png"), 4, 7);
        placePiece(new Bishop(true, board, "WB.png"), 5, 7);
        placePiece(new Knight(true, board, "WN.png"), 6, 7);
        placePiece(new Rook(true, board, "WR.png"), 7, 7);

        for (int col = 0; col < 8; col++) {
            placePiece(new Pawn(false, board, "BP.png"), col, 1);
        }
        placePiece(new Rook(false, board, "BR.png"), 0, 0);
        placePiece(new Knight(false, board, "BN.png"), 1, 0);
        placePiece(new Bishop(false, board, "BB.png"), 2, 0);
        placePiece(new Queen(false, board, "BQ.png"), 3, 0);
        placePiece(new King(false, board, "BK.png"), 4, 0);
        placePiece(new Bishop(false, board, "BB.png"), 5, 0);
        placePiece(new Knight(false, board, "BN.png"), 6, 0);
        placePiece(new Rook(false, board, "BR.png"), 7, 0);

    }
}
