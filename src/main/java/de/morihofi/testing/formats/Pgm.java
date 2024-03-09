package de.morihofi.testing.formats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Portable Graymap (ASCII) Loader
 * <p><b>Grammar:</b></p>
 * <code>
 *     'P2' {Number of columns} {Number of lines} {Max Value for Grayscale} [{Grayscale value of Pixel} for columns x lines times]
 * </code>
 */
public class Pgm {
    private final static Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static BufferedImage loadImage(String filename, double zoomFactor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;

            // Read lines from the file and filter out comments.
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    lines.add(line);
                }
            }

            // Flatten all lines into a single stream of tokens.
            Stream<String> tokens = lines.stream().flatMap(l -> Stream.of(l.trim().split("\\s+")));

            // Create an iterator over the stream to fetch tokens as needed.
            Iterator<String> iterator = tokens.iterator();

            // Check the PGM file type signature
            LOGGER.info("Checking Header");
            if (!iterator.next().equals("P2")) {
                System.err.println("Invalid PGM file.");
                return null;
            }

            // Read image metadata, this contains
            // image dimensions and max value
            LOGGER.info("Reading metadata");
            int width = Integer.parseInt(iterator.next());
            int height = Integer.parseInt(iterator.next());
            int maxVal = Integer.parseInt(iterator.next());
            LOGGER.info("Got image metadata: {}x{}px, max gray value is {}", width, height, maxVal);


            // Create a new image.
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Parse the next value from the iterator.
                    int value = Integer.parseInt(iterator.next());
                    int grayLevel = value * 255 / maxVal;
                    int argb = (255 << 24) | (grayLevel << 16) | (grayLevel << 8) | grayLevel;
                    image.setRGB(x, y, argb);
                }
            }

            // Apply zoom if necessary.
            if (zoomFactor != 1.0) {
                LOGGER.info("Applying zoom with factor {}", zoomFactor);
                int newWidth = (int) (width * zoomFactor);
                int newHeight = (int) (height * zoomFactor);
                BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics2D = scaledImage.createGraphics();
                graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);
                graphics2D.dispose();
                return scaledImage;
            }

            LOGGER.info("Image reading complete");
            return image;
        } catch (IOException e) {
            LOGGER.error("Error reading PGM file.", e);
            throw new IllegalArgumentException(e);
        } catch (NumberFormatException e) {
            LOGGER.error("Format error in PGM file.", e);
            throw new IllegalArgumentException(e);
        }
    }
}
