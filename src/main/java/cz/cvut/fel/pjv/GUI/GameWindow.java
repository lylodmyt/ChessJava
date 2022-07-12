package cz.cvut.fel.pjv.GUI;


import cz.cvut.fel.pjv.ChessMatch;
import cz.cvut.fel.pjv.MyTimer;
import cz.cvut.fel.pjv.board.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;

/**
 * Class represents view of game and part of game logic
 */
public class GameWindow {

    private final JFrame frame;
    private final int timeLimit = 3600;
    private int bTime;
    private int wTime;
    private MyTimer timer = null;

    JLabel whiteTime = new JLabel("White time: " + wTime + "sec");
    JLabel blackTime = new JLabel("Black time: " + bTime + "sec");

    public GameWindow(boolean ai) {
        frame = new JFrame();
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        ChessMatch chessMatch = new ChessMatch(ai);
        GamePanel gamePanel = new GamePanel(chessMatch);
        frame.add(gamePanel, BorderLayout.CENTER);

        JPanel timeInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel time = new JLabel("Time limit: " + timeLimit + "sec");
        timeInfo.add(time);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton closeGame = new JButton("close");
        closeGame.addActionListener(e-> {
            chessMatch.getWriter().writeGame();
            SwingUtilities.invokeLater(new MainMenu());
            frame.dispose();
        });


        info.add(whiteTime);
        info.add(closeGame);
        info.add(blackTime);

        frame.add(timeInfo, BorderLayout.PAGE_START);
        frame.add(info, BorderLayout.PAGE_END);
        frame.setVisible(true);
    }

    private class GamePanel extends JPanel implements MouseListener {

        ChessMatch chessMatch;

        private Position from;

        public int xCord = 55;
        public int yCord = 15;
        public int panelSize = 60;


        public GamePanel(ChessMatch chessMatch) {
            this.chessMatch = chessMatch;
            addMouseListener(this);
        }

        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g);
            Graphics2D g2 = (Graphics2D) g;
            drawBoard(g2);
            try {
                drawPieces(g2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Image loadImage(String file) throws Exception {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resUrl = classLoader.getResource(file);
            if (resUrl == null) {

                return null;
            } else {
                File imageFile = new File(resUrl.toURI());
                return ImageIO.read(imageFile);
            }

        }

        private void drawPieces(Graphics2D g2) throws Exception {
            for (int col = 0; col < 8; col++){
                for (int row = 0; row < 8; row++){
                    Position position = new Position(col, row);
                    if (chessMatch.getBoard().getPiece(position) != null){
                        Image image = loadImage(chessMatch.getBoard().getPiece(position).getImage());
                        g2.drawImage(image, xCord + col * panelSize, yCord + row * panelSize, panelSize, panelSize, null);
                    }
                }
            }
        }

        private void drawBoard(Graphics2D g2) {
            for (int i = 0; i < 4; i++) {
                g2.setColor(Color.LIGHT_GRAY);
                for (int j = 0; j < 4; j++) {
                    g2.fillRect(xCord + 2 * j * panelSize, yCord + (2 * i) * panelSize, panelSize, panelSize);
                    g2.fillRect(xCord + (1 + 2 * j) * panelSize, yCord + (1 + 2 * i) * panelSize, panelSize, panelSize);
                }

                g2.setColor(Color.DARK_GRAY);
                for (int j = 0; j < 4; j++) {
                    g2.fillRect(xCord + (1 + 2 * j) * panelSize, yCord + (2 * i) * panelSize, panelSize, panelSize);
                    g2.fillRect(xCord + 2 * j * panelSize, yCord + (1 + 2 * i) * panelSize, panelSize, panelSize);
                }
            }

        }



        @Override
        public void mousePressed(MouseEvent e) {
            if (timer == null) {
                timer = new MyTimer();
                timer.start();
            }
            int col = (e.getPoint().x - xCord) / panelSize;
            int row = (e.getPoint().y - yCord) / panelSize;
            from = new Position(col, row);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int col = (e.getPoint().x - xCord) / panelSize;
            int row = (e.getPoint().y - yCord) / panelSize;
            Position to = new Position(col, row);
            if (chessMatch.validatePositionTo(from, to) && chessMatch.validatePositionFrom(from)){
                chessMatch.makeMove(from, to);
                repaint();
                if (chessMatch.getPromotedPiece() != null){
                    String[] options = {"queen", "rook", "bishop", "knight"};
                    int n = JOptionPane.showOptionDialog(frame, "Pawn promotion", "Pawn promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if (n == 0){
                        chessMatch.replacePiece("queen");
                    }
                    if (n == 1){
                        chessMatch.replacePiece("rook");
                    }
                    if (n == 2){
                        chessMatch.replacePiece("bishop");
                    }
                    if (n == 3){
                        chessMatch.replacePiece("knight");
                    }
                    chessMatch.setPromotedPiece(null);
                    repaint();
                }
                int timeSpend = timer.getTime();
                timer = new MyTimer();
                timer.start();

                if (!chessMatch.isWhitePlayer()){
                    wTime += timeSpend;
                    whiteTime.setText("White time: " + wTime);
                }else {
                    bTime += timeSpend;
                    blackTime.setText("Black time: " + bTime);
                }
            }
            repaint();
            if (bTime > timeLimit || wTime > timeLimit){
                chessMatch.getWriter().writeGame();
                int n = JOptionPane.showConfirmDialog(frame, "OUT OF TIME\nYes - to main menu\nNo - close app", "CLOSE GAME", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION){
                    chessMatch.getWriter().writeGame();
                    SwingUtilities.invokeLater(new MainMenu());
                    frame.dispose();
                }
            }
            EndGame();
            if (chessMatch.isAiGame() && !chessMatch.isWhitePlayer()){
                chessMatch.getAiPlayer().makeMove(chessMatch);
                EndGame();
                repaint();
            }
        }

        private void EndGame() {
            if (chessMatch.isCheckmate()){
                chessMatch.getWriter().writeGame();
                int n = JOptionPane.showConfirmDialog(frame, "CHECKMATE\nYes - to main menu\nNo - close app", "CLOSE GAME", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION){
                    chessMatch.getWriter().writeGame();
                    SwingUtilities.invokeLater(new MainMenu());
                    frame.dispose();
                }
            }

            if (chessMatch.isStalemate()){
                chessMatch.getWriter().writeGame();
                int n = JOptionPane.showConfirmDialog(frame, "STALEMATE\nYes - to main menu\nNo - close app", "CLOSE GAME", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION){
                    SwingUtilities.invokeLater(new MainMenu());
                    frame.dispose();
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}

    }
}
