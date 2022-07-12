package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.GUI.MainMenu;

import javax.swing.*;

public class Main implements Runnable{

    public void run() {
        SwingUtilities.invokeLater(new MainMenu());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}
