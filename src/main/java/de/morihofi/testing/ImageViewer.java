package de.morihofi.testing;

import com.formdev.flatlaf.FlatDarkLaf;
import de.morihofi.testing.formats.Pgm;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer {


    public static void main(String[] args) throws Exception {

        //Pgm.loadImage("haus.pgm", 100.0);
        //Pgm.loadImage("C:\\Users\\Moritz\\Pictures\\error.pgm", 1.0);
        //Pgm.loadImage("C:\\Users\\Moritz\\Pictures\\water.pgm", 1.0);
        BufferedImage image = Pgm.loadImage("haus.pgm", 50.0);

        UIManager.setLookAndFeel(new FlatDarkLaf());
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Image Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            if (image != null) {
                ImageIcon imageIcon = new ImageIcon(image);
                JLabel imageLabel = new JLabel(imageIcon);
                frame.add(imageLabel);

                frame.setMinimumSize(new Dimension(image.getWidth(), image.getHeight()));
            }

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


}

