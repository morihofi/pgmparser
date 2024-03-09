# PGM File Parser for Java
## Overview
This project provides a Java-based solution for parsing and displaying Portable Graymap (Pgm) files. It is designed to be simple to integrate into existing Java projects, requiring Java 8 or higher and Maven for dependency management. The parser supports ASCII Pgm files ("P2" format) and offers functionality to zoom and display images using a Swing-based GUI.
Features

- PGM Parsing: Supports ASCII-based PGM files, specifically the "P2" format.
- Image Zooming: Allows the application of a zoom factor to the displayed image.
- Swing GUI: Utilizes the Swing framework for displaying images, with support for the FlatDarkLaf look and feel.

## Prerequisites
 - Java Development Kit (JDK) 8 or higher.
 - Maven for managing dependencies and building the project.


## PGM File Format
The parser expects PGM files to adhere to the following format:

- Header: `P2`
- Width and Height: Specified as integers.
- Maximum Grayscale Value: Specified as an integer.
- Pixel Data: A sequence of integers (one per pixel) defining the grayscale values.

### Example:
```pgm
P2
# This is a comment
5 4
255
100 100 100 100 100
150 150 150 150 150
200 200 200 200 200
250 250 250 250 250
```

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
- The Swing framework for providing the GUI components.
- The FlatLaf look and feel for enhancing the UI aesthetics.