import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class FlagPanel extends JPanel {
    // we need a constructor
    // we need to paint stuff
    private final int STRIPES = 13;

    // SCALE FACTORS (A through L)
    //
    // Note: Constants in Java should always be ALL_CAPS, even
    // if we are using single letters to represent them
    //
    // NOTE 2: Do not delete or change the names of any of the
    // variables given here
    //
    // Set the constants to exactly what is specified in the documentation
    // REMEMBER: These are scale factors. They are not numbers of pixels.
    // You will use these and the width and height of the Applet to figure
    // out how to draw the parts of the flag (stripes, stars, field).
    private final double A = 1.0; // Hoist (width) of flag
    private final double B = 1.9; // Fly (length) of flag
    private final double C = 0.5385; // Hoist of Union
    private final double D = 0.76; // Fly of Union
    private final double E = 0.054; // See flag specification
    private final double F = 0.054; // See flag specification
    private final double G = 0.063; // See flag specification
    private final double H = 0.063; // See flag specification
    private final double K = 0.0616; // Diameter of star
    private final double L = 0.0769; // Width of stripe

    // You will need to set values for these in paint()
    private double flag_width; // width of flag in pixels (up and down)
    private double flag_fly; // height of flag in pixels (left right)
    private double stripe_height; // height of an individual stripe in pixels

    public Color RED = Color.RED;
    public Color BLUE = Color.BLUE;
    public Color WHITE = Color.WHITE;

    // init() will automatically be called when an applet is run
    public FlagPanel() {
        // Choice of width = 1.9 * height to start off
        // 760 : 400 is ratio of FLY : HOIST
        setSize(760, 400);
    }

    // paint() will be called every time a resizing of an applet occurs
    public void paintComponent(Graphics g) {
        if ((double) getHeight() / getWidth() > A / B) {// too tall
            flag_fly = getWidth();
            flag_width = flag_fly / B;
        } else {
            flag_width = getHeight();
            flag_fly = flag_width * B;
        }
        drawBackground(g);
        drawStripes(g);
        drawField(g);
        drawStars(g);
    }

    // this function draws a white background
    private void drawBackground(Graphics g) {
        g.setColor(WHITE);
        g.fillRect(0, 0, (int) flag_fly, (int) flag_width);
    }

    // this function draws red stripes over the white background
    public void drawStripes(Graphics g) {
        g.setColor(RED);
        for (int i = 0; i < 7; i++) {
            g.fillRect(0, (int) (i * 2 * L * flag_width), (int) (B * flag_width), (int) (L * flag_width));
        }
    }

    // this function draws the blue portion of the flag
    public void drawField(Graphics g) {
        g.setColor(BLUE);
        g.fillRect(0, 0, (int) (D * flag_width), (int) (C * flag_width));
    }

    // this function draws the pattern of stars on the blue rectangle
    public void drawStars(Graphics g) {
        g.setColor(WHITE);
        // first iterate over columns - there are 11 of them
        int numColums = 11;
        for (int i = 1; i <= numColums; i++) {
            int x = (int) (H * flag_width * i);
            if (i % 2 == 1) {
                // this means i odd
                for (int k = 1; k <= 6; k++) {
                    int y = (int) (F * flag_width * (2 * k - 1));
                    drawStar(g, x, y, (K / 2 * flag_width));
                }
            } else {
                // this means i is even
                for (int j = 1; j <= 4; j++) {
                    int y = (int) (F * flag_width * ((2 * j)));
                    System.out.println("y: " + y);
                    drawStar(g, x, y, (K / 2 * flag_width));
                }
            }
        }

    }
    
    // this portion of the code was copied from paleyontology.com, the rest is my original work
    public void drawStar(Graphics g, int centerX, int centerY, double radius) { 
        /*
         * To produce a polygon that looks like a star, a little trig...
         * 
         * If you draw a regular 5-pointed star with center (0,0) on a graph,
         * the points will be at angles 18, 90, 162, 234, and 306 degrees.
         * The y-coord of the first point will be the radius of the star times
         * sin18. The star can be thought of having 5 inner points at angles
         * 54, 126, 198, 270, and 342 degrees. The y-coord of the point at 54
         * degrees is the same as the one at 18 degrees and is Rsin54 where R
         * is the radius of inner points. Since Rsin54 = rsin18, and since
         * r, sin18, and sin54 are known, we have R = rsin18/sin54. We can use
         * this to do some nice computations for coordinates of points around
         * the polygon; five on the outside and five on the inside.
         */
        double innerRadius = radius * Math.sin(Math.toRadians(18) / Math.sin(Math.toRadians(54)));

        int[] polygonX = new int[10];
        int[] polygonY = new int[10]; // Note that (i-18)/36 will be 0, 2, 4, 6 8
        for (int i = 18; i < 360; i += 72) {
            polygonX[(i - 18) / 36] = centerX + (int) (radius * Math.cos(Math.toRadians(i)));
            polygonY[(i - 18) / 36] = centerY - (int) (radius * Math.sin(Math.toRadians(i)));
        }

        // Here (i-18)/36 will be 1, 3, 5, 7, 9
        for (int i = 54; i < 360; i += 72) {
            polygonX[(i - 18) / 36] = centerX + (int) (innerRadius * Math.cos(Math.toRadians(i)));
            polygonY[(i - 18) / 36] = centerY - (int) (innerRadius * Math.sin(Math.toRadians(i)));
        }

        Color c = g.getColor();
        g.setColor(WHITE);
        g.fillPolygon(polygonX, polygonY, 10);
        g.setColor(c);

    }

}
