package org.example;

import org.example.ui.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                MainFrame mainFrame = new MainFrame();
                ProductionQueryFrame queryframe = new ProductionQueryFrame();
                mainFrame.setVisible(true);
                queryframe.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}