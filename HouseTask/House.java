import javax.swing.JFrame;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class House extends JFrame {
    int frameWidth = 600, frameHeight = 600;

    public House() {
        setLocation(200, 200);
        setSize(this.frameWidth, this.frameHeight);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        Graphics2D p = (Graphics2D) g ;
        drawGround(p);
        drawSky(p);
        drawHouse(p);
        drawTree(p);
    }

    int groundX = 0, groundY = frameHeight - 100;
    int groundwidth = frameWidth, groundHeight = 100;

    public void drawGround(Graphics2D g) {
        Rectangle2D ground = new Rectangle2D.Double(groundX, groundY, groundwidth, groundHeight);
        g.setColor(Color.green.darker());
        g.draw(ground);
        g.fill(ground);
    }

    public void drawSky(Graphics2D g) {
        Rectangle2D sky = new Rectangle2D.Double(0, 0, frameWidth, groundY);
        g.setColor(Color.cyan.brighter());
        g.draw(sky);
        g.fill(sky);
    }



    public void drawHouse(Graphics2D g) {
        int houseX = 250, houseY = 250;
        int houseWidth = 250, houseHeight = groundY - houseY;
        g.setStroke(new BasicStroke(5));
        Rectangle2D house = new Rectangle2D.Double(houseX, houseY, houseWidth, houseHeight);
        g.setColor(Color.black);
        g.draw(house);
        g.setColor(Color.orange);
        g.fill(house);

        int windowSize = 50, windowOffset = 50;
        // window 1
        Rectangle2D window1 = new Rectangle2D.Double(houseX + windowOffset, houseY + windowOffset, windowSize, windowSize);
        Line2D window1Line = new Line2D.Double(houseX + windowOffset, houseY + windowOffset, houseX + windowSize * 2, houseY + windowSize * 2);
        g.setColor(Color.black);
        g.draw(window1);
        g.setColor(Color.pink.darker());
        g.fill(window1);
        g.setColor(Color.black);
        g.draw(window1Line);

        // window 2
        Rectangle2D window2 = new Rectangle2D.Double(houseX + windowOffset * 3, houseY + windowOffset, windowSize, windowSize);
        Line2D window2Line = new Line2D.Double(houseX + windowOffset * 3, houseY + windowOffset, houseX + windowSize * 4, houseY + windowSize * 2);
        g.setColor(Color.black);
        g.draw(window2);
        g.setColor(Color.pink.darker());
        g.fill(window2);
        g.setColor(Color.black);
        g.draw(window2Line);


        // Trinagle
        int[] xPoints = {houseX - 10, houseX + houseWidth + 10, houseX + houseWidth/2};
        int[] yPoints = {houseY,      houseY,                   houseY - 100};

        Polygon triangle = new Polygon(xPoints, yPoints, 3);
        g.setColor(Color.ORANGE.darker());
        g.fill(triangle);
        g.setColor(Color.black);
        g.draw(triangle);

        // Door
        int doorX = houseX + houseWidth/3, doorY = houseX + (houseHeight * 2)/3;
        int doorHeight = groundY - doorY, doorWidth = 75;
        Rectangle2D door = new Rectangle2D.Double(doorX, doorY, doorWidth, doorHeight);
        g.setColor(Color.ORANGE.darker());
        g.fill(door);
        g.setColor(Color.black);
        g.draw(door);
    }

    public void drawTree(Graphics2D g) {
        int TreeX = 150, TreeY = 150;
        Line2D log = new Line2D.Double(TreeX, TreeY, TreeX, groundY);
        g.setStroke(new BasicStroke(15));
        g.setColor(Color.red.darker());
        g.draw(log);

        g.setStroke(new BasicStroke(3));
        for (int i = 0; i < 100; i++) {
            Random rand = new Random();
            int x = rand.nextInt(100), y = rand.nextInt(100);

            Line2D leaf1 = new Line2D.Double(75 + x, 75 + y, 150 + x,  150 + y);
            g.setColor(Color.green.darker());
            g.draw(leaf1);

            Line2D leaf2 = new Line2D.Double(75 + x, 150 + y,  150 + x,  75 + y);
            g.setColor(Color.green.darker());
            g.draw(leaf2);
        }
    }

    public static void main(String[] args) {
        House d = new House();
    }
}