import javax.swing.JFrame;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class Cup extends JFrame {
    int frameWidth = 800, frameHeight = 800;

    public Cup() {
        setLocation(200, 200);
        setSize(this.frameWidth, this.frameHeight);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D p = (Graphics2D) g ;
        drawBackground(p);
        drawHandle(p);
        drawMug(p);
        drawEyes(p);
        drawMouth(p);
        drawVapor(p);
    }

    public void drawBackground(Graphics2D g){
        g.setColor(Color.PINK.darker());
        g.fillRect(0, 0, frameWidth, frameHeight);
    }

    int mugX = 200, mugY = 200;
    int mugWidth = 400, mugHeight = 500;
    public void drawMug(Graphics2D g){
        g.setColor(Color.ORANGE);
        g.fillRoundRect(mugX, mugY, mugWidth, mugHeight, 50,50);

        g.setStroke(new BasicStroke(20));
        g.setColor(Color.BLACK);
        g.drawRoundRect(mugX, mugY, mugWidth, mugHeight, 50,50);
    }

    int handleWidth = 250, handleHeight = 300;
    public void drawHandle(Graphics2D g){
        g.setColor(Color.ORANGE);
        g.fillRoundRect(mugX + 250 , mugY + 50, handleWidth, handleHeight, 50,50);

        g.setStroke(new BasicStroke(20));
        g.setColor(Color.BLACK);
        g.drawRoundRect(mugX + 250, mugY + 50, handleWidth, handleHeight, 50,50);

        g.setColor(Color.PINK.darker());
        g.fillRoundRect(mugX + 300 , mugY + 100, handleWidth - 100, handleHeight - 100, 50,50);

        g.setStroke(new BasicStroke(20));
        g.setColor(Color.BLACK);
        g.drawRoundRect(mugX + 300 , mugY + 100, handleWidth - 100, handleHeight - 100, 50,50);
    }


    int eyesX = mugX + 80, eyesY = mugY + 100;
    int eyesWidth = 100, eyesHeight = 150;
    public void drawEyes(Graphics2D g){
        g.setColor(Color.BLACK);
        g.fillOval(eyesX, eyesY, eyesWidth, eyesHeight);

        g.setColor(Color.WHITE);
        g.fillOval(eyesX + 20, eyesY + 20, eyesWidth - 40, eyesHeight - 90);
        g.setColor(Color.WHITE);
        g.fillOval(eyesX + 50, eyesY + 90, eyesWidth - 70, eyesHeight - 120);


        g.setColor(Color.BLACK);
        g.fillOval(eyesX + 150, eyesY, eyesWidth, eyesHeight);

        g.setColor(Color.WHITE);
        g.fillOval(eyesX + 20 + 150, eyesY + 20, eyesWidth - 40, eyesHeight - 90);
        g.setColor(Color.WHITE);
        g.fillOval(eyesX + 50 + 150, eyesY + 90, eyesWidth - 70, eyesHeight - 120);
    }

    int mouthX = mugX + 150, mouthY = mugY + 250, mouthWidth = 100, mouthHeight = 100;
    public void drawMouth(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillArc(mouthX, mouthY, mouthWidth, mouthHeight, 180, 180);
        g.setStroke(new BasicStroke(5));

        g.setColor(Color.BLACK);
        g.drawArc(mouthX, mouthY, mouthWidth, mouthHeight, 180, 180);
        g.drawLine(mouthX, mouthY + mouthHeight/2, mouthX + mouthWidth, mouthY + mouthHeight/2);
    }

    public void drawVapor(Graphics2D g){
        g.setColor(Color.BLACK);
        CubicCurve2D curve1 = new CubicCurve2D.Double(300,50,250,90,350,130,300,150);
        g.draw(curve1);

        CubicCurve2D curve2 = new CubicCurve2D.Double(400,50,350,90,450,130,400,150);
        g.draw(curve2);
    }


    public static void main(String[] args) {
        Cup d = new Cup();
    }
}