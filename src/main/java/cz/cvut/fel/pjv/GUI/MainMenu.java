package cz.cvut.fel.pjv.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

/**
 * Class represent menu of game
 */
public class MainMenu implements Runnable {

    public static final Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(300,300);
        frame.setResizable(false);

        Container container = frame.getContentPane();
        container.setLayout(null);

        JLabel title = new JLabel("CHESS");
        title.setBounds(130, 25, 250, 50);

        JButton pvp = new JButton("PVP");
        pvp.setBounds(25, 75, 250, 50);
        pvp.addActionListener(e->{
            LOGGER.info("Start new game pvp game");
            new GameWindow(false);
            frame.dispose();
        });

        JButton pvc = new JButton("PVC");
        pvc.setBounds(25, 125, 250, 50);
        pvc.addActionListener(e->{
            new GameWindow(true);
            frame.dispose();
        });

        container.add(title);
        container.add(pvp);
        container.add(pvc);
        frame.setVisible(true);
    }
}

