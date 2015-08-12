/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzle.UI;

import eightpuzzlev.algo.HelperMethods;
import eightpuzzlev.algo.PuzzleMoves;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Chetan
 */
public class drawing extends JPanel {

    private Graphics g;
    BufferedImage[] img = new BufferedImage[9];//{ image1, image2, image3, image4, image5, image6, image7, image8};
    int rec2_xAxis;
    int rec_length = 309;
    int rec_width = 309;

    @Override
    public void paintComponent(Graphics g) {
        this.setG(g);
        g.setColor(Color.GREEN);
        g.drawRect(20, 20, rec_length, rec_width);
        g.setColor(Color.red);
        rec2_xAxis = rec_length / 3;
        int rec2_yAxis = 20;
        int rec2_length = rec_length / 3;
        int rec2_width = rec_width;
        g.drawRect(rec2_xAxis + 20, rec2_yAxis, rec2_length, rec2_width);
        g.setColor(Color.YELLOW);
        int rec3_xAxis = 20;
        int rec3_yAxis = rec2_width / 3;
        int rec3_length = rec_length;
        int rec3_width = rec_width / 3;
        g.drawRect(rec3_xAxis, rec3_yAxis + 20, rec3_length, rec3_width);
        g.setColor(Color.BLACK);

        PaintImage();
        PaintAllImages();
    }

    /**
     * @return the g
     */
    public Graphics getG() {
        return g;
    }

    /**
     * @param g the g to set
     */
    public void setG(Graphics g) {
        this.g = g;
    }

   

    

    public void PaintImage() {
        try {
            img[0] = ImageIO.read(getClass().getResource("/Images/m1.jpg"));
            img[1] = ImageIO.read(getClass().getResource("/Images/1.jpg"));
            img[2] = ImageIO.read(getClass().getResource("/Images/2.jpg"));
            img[3] = ImageIO.read(getClass().getResource("/Images/3.jpg"));
            img[4] = ImageIO.read(getClass().getResource("/Images/4.jpg"));
            img[5] = ImageIO.read(getClass().getResource("/Images/5.jpg"));
            img[6] = ImageIO.read(getClass().getResource("/Images/6.jpg"));
            img[7] = ImageIO.read(getClass().getResource("/Images/7.jpg"));
            img[8] = ImageIO.read(getClass().getResource("/Images/8.jpg"));
        } catch (Exception e) {
            System.out.println("cannot find the file");
        }
    }

    private void PaintAllImages() {
        PaintImageAtZero(img[0]);
        PaintImageAtOne(img[1]);
        PaintImageAtTwo(img[2]);
        PaintImageAtThree(img[3]);
        PaintImageAtFour(img[4]);
        PaintImageAtFive(img[5]);
        PaintImageAtSix(img[6]);
        PaintImageAtSeven(img[7]);
        PaintImageAtEight(img[8]);
    }

    private void PaintImageAtZero(BufferedImage image) {
        getG().drawImage(image, 22, 22, this);
    }

    private void PaintImageAtOne(BufferedImage image) {
        getG().drawImage(image, (rec_length / 3) + 22, 22, this);
    }

    private void PaintImageAtTwo(BufferedImage image) {
        getG().drawImage(image, ((rec_length / 3) * 2) + 22, 22, this);
    }

    private void PaintImageAtThree(BufferedImage image) {
        getG().drawImage(image, 22, ((rec_width / 3)) + 22, this);
    }

    private void PaintImageAtFour(BufferedImage image) {
        getG().drawImage(image, (rec_length / 3) + 22, ((rec_width / 3)) + 22, this);

    }

    private void PaintImageAtFive(BufferedImage image) {
        getG().drawImage(image, ((rec_length / 3) * 2) + 22, ((rec_width / 3)) + 22, this);
    }

    private void PaintImageAtSix(BufferedImage image) {
        getG().drawImage(image, 22, ((rec_width / 3) * 2) + 22, this);
    }

    private void PaintImageAtSeven(BufferedImage image) {
        getG().drawImage(image, (rec_length / 3) + 22, ((rec_width / 3) * 2) + 22, this);
    }

    private void PaintImageAtEight(BufferedImage image) {
        getG().drawImage(image, ((rec_length / 3) * 2) + 22, ((rec_width / 3) * 2) + 22, this);
    }

    public void PaintPuzzle(int[] puzzle) {
        System.out.println("");
        if (puzzle.length != 9) {
            System.out.println("Improper puzzle length");
        }
        for (int i = 0; i < puzzle.length; i++) {
            int position = new HelperMethods().FindPositionOf(puzzle[i], puzzle);
           // System.out.println("Postion of "+puzzle[i]+" -> "+position);
            paintImageAt(position,img[puzzle[i]] );     
        }
//        System.out.println("Repaint");
        repaint();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(drawing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PlaySolution(int[] puzzle, String solution) {
        PuzzleMoves movePuzzle = new PuzzleMoves();

        PaintPuzzle(puzzle);
        for (int i = 0; i < solution.length(); i++) {
            if (solution.charAt(i) == 'L') {
                movePuzzle.moveLEFT(puzzle);
                System.out.println("Move LEFT");
               PaintPuzzle(puzzle);
            } else if (solution.charAt(i) == 'R') {
                movePuzzle.moveRIGHT(puzzle);
                System.out.println("Move RIGHT");
                PaintPuzzle(puzzle);
            }
            if (solution.charAt(i) == 'U') {
                
                movePuzzle.moveUP(puzzle);
                System.out.println("Move UP");
                PaintPuzzle(puzzle);
            } else if (solution.charAt(i) == 'D') {
                movePuzzle.moveDOWN(puzzle);
                System.out.println("Move DOWN");
                PaintPuzzle(puzzle);
            }
        }
    }

    public void paintImageAt(int position, BufferedImage image) {
        if (position == 0) {
            PaintImageAtZero(image);
        } else if (position == 1) {
            PaintImageAtOne(image);
        } else if (position == 2) {
            PaintImageAtTwo(image);
        } else if (position == 3) {
            PaintImageAtThree(image);
        } else if (position == 4) {
            PaintImageAtFour(image);

        } else if (position == 5) {
            PaintImageAtFive(image);
        } else if (position == 6) {
            PaintImageAtSix(image);
        } else if (position == 7) {
            PaintImageAtSeven(image);
        } else if (position == 8) {
            PaintImageAtEight(image);
        }

    }

}
