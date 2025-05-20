import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Calendar;

public class Main extends JPanel {
    int frameWidth = 1400, frameHeight = 900;
    int boxX = 150, boxY = 350;
    int boxWidth = 700, boxHeight = 400, boxDepth = 150;

    double sunRotationAngle = 0;

    double bellSwingAngle = 0;
    int bellSwingDirection = 1;
    boolean bellTimerStarted = false;

    double hourAngle, minuteAngle, secondAngle;

    double birdX, birdY;
    boolean birdVisible = false;
    double birdProgress = 0;
    double birdFlightDuration = 40.0;
    boolean birdFlyingOut = true;
    int birdWingAngle = 0;
    int birdWingDirection = 1;
    int birdSize = 60;
    Color birdColor = new Color(70, 130, 180);

    Color backgroundColor = new Color(232, 201, 153);
    Color boxColor = new Color(100, 27, 46);
    Color bellColor = new Color(255, 214, 58);
    Color screenColor = new Color(22, 22, 22);
    Color sunColor = new Color(255, 214, 58);
    Color clockColor = new Color(138, 45, 59);
    Color clockTextColor = new Color(251, 219, 147);

    public void setWindowSettings() {
        JFrame f = new JFrame();
        f.getContentPane().add(this);
        f.setLocation(200, 200);
        f.setSize(frameWidth, frameHeight);
        setBackground(backgroundColor);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void drawBox(Graphics2D p) {
        Polygon front = new Polygon();
        front.addPoint(boxX, boxY);
        front.addPoint(boxX + boxWidth, boxY);
        front.addPoint(boxX + boxWidth, boxY + boxHeight);
        front.addPoint(boxX, boxY + boxHeight);

        Polygon top = new Polygon();
        top.addPoint(boxX, boxY);
        top.addPoint(boxX + boxDepth, boxY - boxDepth);
        top.addPoint(boxX + boxWidth + boxDepth, boxY - boxDepth);
        top.addPoint(boxX + boxWidth, boxY);

        Polygon side = new Polygon();
        side.addPoint(boxX + boxWidth, boxY);
        side.addPoint(boxX + boxWidth + boxDepth, boxY - boxDepth);
        side.addPoint(boxX + boxWidth + boxDepth, boxY + boxHeight - boxDepth);
        side.addPoint(boxX + boxWidth, boxY + boxHeight);

        p.setColor(boxColor);
        p.fillPolygon(front);
        p.setColor(boxColor.brighter());
        p.fillPolygon(top);
        p.setColor(boxColor.darker());
        p.fillPolygon(side);
    }

    void drawSideButton(Graphics2D p) {
        int buttonWidth = 80, buttonHeight = 180;
        int buttonX = boxX + boxWidth + 40, buttonY = boxY + 20;
        Color buttonColor = new Color(138, 45, 59);
        p.setColor(buttonColor.darker());
        p.fillOval(buttonX, buttonY, buttonWidth, buttonHeight);

        p.setColor(buttonColor);
        p.fillOval(buttonX + 20, buttonY, buttonWidth, buttonHeight);

        p.setColor(new Color(100, 27, 46));
        p.fillOval(buttonX + 35, buttonY + 20, 50, 70);
    }

    void drawBell(Graphics2D p, int offsetX, int offsetY, double swingAngle) {
        int handleWidth = 20, handleHeight = 60;
        int bellWidth = 150, bellHeight = 300;
        int baseX = boxX + boxDepth, baseY = boxY - boxDepth;

        int handleX = baseX + 60 + offsetX, handleY = baseY + 30 + offsetY;

        p.setColor(bellColor.darker());
        p.fillRoundRect(handleX + 5, handleY - 5, handleWidth, handleHeight, 10, 10);
        p.setColor(bellColor);
        p.fillRoundRect(handleX, handleY, handleWidth, handleHeight, 10, 10);
        p.setColor(new Color(255, 255, 200, 120));
        p.fillRoundRect(handleX + 4, handleY + 8, 5, handleHeight - 16, 5, 5);

        int bellX = baseX - 5 + offsetX, bellY = baseY - 100 + offsetY;
        int pivotX = handleX + handleWidth / 2;
        int pivotY = handleY + handleHeight;

        Graphics2D g2 = (Graphics2D) p.create();
        g2.rotate(Math.toRadians(swingAngle), pivotX, pivotY);

        g2.setColor(bellColor.darker());
        g2.fillArc(bellX + 5, bellY - 5, bellWidth, bellHeight, 0, 180);
        GradientPaint gp = new GradientPaint(
                bellX, bellY, bellColor.brighter(),
                bellX + bellWidth, bellY + bellHeight / 2, bellColor.darker());
        Paint oldPaint = g2.getPaint();
        g2.setPaint(gp);
        g2.fillArc(bellX, bellY, bellWidth, bellHeight, 0, 180);
        g2.setPaint(oldPaint);
        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillArc(bellX + 20, bellY + 30, bellWidth / 3, bellHeight / 2, 0, 120);

        int ballRadius = 18;
        int ballX = bellX + bellWidth / 2 - ballRadius / 2;
        int ballY = bellY - 15;
        g2.setColor(new Color(90, 70, 40));
        g2.fillOval(ballX, ballY, ballRadius, ballRadius);
        g2.setColor(new Color(255, 255, 255, 120));
        g2.fillOval(ballX + 5, ballY + 5, 6, 6);
    }

    void drawBells(Graphics2D p) {
        drawBell(p, 60, 0, bellSwingAngle);
        drawBell(p, 340, 0, -bellSwingAngle);
    }

    void drawMiddleButton(Graphics2D p) {
        int bell1CenterX = boxX + boxDepth + 60 + 80 + 10;
        int bell2CenterX = boxX + boxDepth + 60 + 320 + 10;
        int centerX = (bell1CenterX + bell2CenterX) / 2;
        int handleWidth = 20, handleHeight = 60;
        int buttonWidth = 60, buttonHeight = 40;
        int handleY = boxY - boxDepth + 30;
        int buttonY = handleY - 20;

        // handle
        p.setColor(bellColor.darker());
        p.fillRoundRect(centerX - handleWidth / 2 + 5, handleY - 5, handleWidth, handleHeight, 10, 10);
        p.setColor(bellColor);
        p.fillRoundRect(centerX - handleWidth / 2, handleY, handleWidth, handleHeight, 10, 10);
        p.setColor(new Color(255, 255, 200, 120));
        p.fillRoundRect(centerX - handleWidth / 2 + 4, handleY + 8, 5, handleHeight - 16, 5, 5);

        // circle
        p.setColor(bellColor.darker());
        p.fillOval(centerX - buttonWidth / 2 + 4, buttonY + 4, buttonWidth, buttonHeight);
        p.setColor(bellColor);
        p.fillOval(centerX - buttonWidth / 2, buttonY, buttonWidth, buttonHeight);
    }

    void drawCloud(Graphics2D p, int centerX, int centerY) {
        p.setColor(new Color(200, 200, 200));

        p.fillOval(centerX + 6, centerY + 6, 110, 70);
        p.setColor(new Color(150, 150, 150));
        p.fillOval(centerX - 45, centerY + 15, 100, 60);
        p.fillOval(centerX + 60, centerY + 15, 100, 60);
        p.fillOval(centerX + 15, centerY - 35, 110, 70);

        p.setColor(new Color(230, 230, 230));
        p.fillOval(centerX, centerY, 110, 70);
        p.fillOval(centerX - 50, centerY + 15, 100, 60);
        p.fillOval(centerX + 55, centerY + 15, 100, 60);
        p.fillOval(centerX + 20, centerY - 35, 110, 70);

        p.setColor(new Color(180, 180, 180));
    }

    void drawSun(Graphics2D p) {
        int sunAreaX = boxX + boxWidth - boxWidth / 2 + 50;
        int sunAreaY = boxY + 10;
        int sunAreaWidth = boxWidth / 2 - 60;
        int sunAreaHeight = boxHeight - 20;
        p.setColor(screenColor);
        p.fillRoundRect(sunAreaX, sunAreaY, sunAreaWidth, sunAreaHeight, 30, 30);

        int sunRadius = 50;
        int sunX = sunAreaX + sunAreaWidth / 2 + 50;
        int sunY = sunAreaY + sunAreaHeight / 2;

        p.setColor(sunColor.darker());
        for (int i = 0; i < 12; i++) {
            double angle = sunRotationAngle + Math.toRadians(i * 30);
            int rayLength = 80;

            int[] xPoints = {
                    sunX,
                    (int) (sunX + rayLength * Math.cos(angle)),
                    (int) (sunX + rayLength * Math.cos(angle + Math.toRadians(10)))
            };
            int[] yPoints = {
                    sunY,
                    (int) (sunY + rayLength * Math.sin(angle)),
                    (int) (sunY + rayLength * Math.sin(angle + Math.toRadians(10)))
            };

            p.fillPolygon(xPoints, yPoints, 3);
        }

        p.setColor(sunColor);
        p.fillOval(sunX - sunRadius, sunY - sunRadius, sunRadius * 2, sunRadius * 2);

        p.setColor(sunColor);
        p.setFont(new Font("Poppins", Font.BOLD, 70));
        FontMetrics fm = p.getFontMetrics();
        String percentage = "45 °";
        int textWidth = fm.stringWidth(percentage);
        p.drawString(percentage, sunX - textWidth / 2 - 100, sunY + sunRadius - 120);

        drawCloud(p, sunX - 120, sunY + 33);
    }

    void drawClock(Graphics2D p) {
        int clockX = boxX + 10, clockY = boxY + 10;
        int clockRadius = 190;

        p.setColor(clockColor);
        p.fillOval(clockX, clockY, clockRadius * 2, clockRadius * 2);

        p.setColor(new Color(50, 50, 50));
        p.fillOval(clockX + clockRadius - 10, clockY + clockRadius - 10, 20, 20);

        p.setColor(clockTextColor);
        p.setFont(new Font("Arial", Font.BOLD, 35));
        FontMetrics fm = p.getFontMetrics();

        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30 - 90);
            int numRadius = clockRadius - 40;

            String number = Integer.toString(i);
            int textWidth = fm.stringWidth(number);
            int textHeight = fm.getHeight();

            int numX = clockX + clockRadius + (int) (numRadius * Math.cos(angle)) - textWidth / 2;
            int numY = clockY + clockRadius + (int) (numRadius * Math.sin(angle)) + textHeight / 4;

            p.drawString(number, numX, numY);
        }

        p.setStroke(new BasicStroke(3));
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int markLength = (i % 3 == 0) ? 15 : 8;
            int x1 = clockX + clockRadius + (int) ((clockRadius - 5) * Math.sin(angle));
            int y1 = clockY + clockRadius - (int) ((clockRadius - 5) * Math.cos(angle));
            int x2 = clockX + clockRadius + (int) ((clockRadius - markLength) * Math.sin(angle));
            int y2 = clockY + clockRadius - (int) ((clockRadius - markLength) * Math.cos(angle));
            p.drawLine(x1, y1, x2, y2);
        }

        p.setStroke(new BasicStroke(6));
        int hourHandLength = clockRadius / 2;
        int hourX = clockX + clockRadius + (int) (hourHandLength * Math.sin(hourAngle));
        int hourY = clockY + clockRadius - (int) (hourHandLength * Math.cos(hourAngle));
        p.drawLine(clockX + clockRadius, clockY + clockRadius, hourX, hourY);

        p.setStroke(new BasicStroke(4));
        int minuteHandLength = clockRadius * 3 / 4;
        int minuteX = clockX + clockRadius + (int) (minuteHandLength * Math.sin(minuteAngle));
        int minuteY = clockY + clockRadius - (int) (minuteHandLength * Math.cos(minuteAngle));
        p.drawLine(clockX + clockRadius, clockY + clockRadius, minuteX, minuteY);

        p.setStroke(new BasicStroke(2));
        p.setColor(Color.RED);
        int secondHandLength = clockRadius * 4 / 5;
        int secondX = clockX + clockRadius + (int) (secondHandLength * Math.sin(secondAngle));
        int secondY = clockY + clockRadius - (int) (secondHandLength * Math.cos(secondAngle));
        p.drawLine(clockX + clockRadius, clockY + clockRadius, secondX, secondY);
    }

    void drawBird(Graphics2D g) {
        if (!birdVisible)
            return;

        int stickStartX = boxX + boxWidth + 40 + 20 + 40; 
        int stickStartY = boxY + 20 + 50;
        int stickEndX = (int) birdX;
        int stickEndY = (int) birdY + 10; 
        g.setStroke(new BasicStroke(8));
        g.setColor(new Color(120, 90, 60));
        g.drawLine(stickStartX, stickStartY, stickEndX, stickEndY);

        g.setColor(new Color(220, 220, 220)); 
        Ellipse2D.Double body = new Ellipse2D.Double(birdX - 30, birdY - 20, 60, 40);
        g.fill(body);

        g.setColor(new Color(190, 190, 190)); 
        Ellipse2D.Double head = new Ellipse2D.Double(birdX + 10, birdY - 35, 30, 30);
        g.fill(head);

        Path2D.Double beak = new Path2D.Double();
        beak.moveTo(birdX + 38, birdY - 23);
        beak.lineTo(birdX + 50, birdY - 28);
        beak.lineTo(birdX + 50, birdY - 18);
        beak.closePath();
        g.setColor(new Color(255, 100, 0));
        g.fill(beak);

        g.setColor(Color.BLACK);
        g.fillOval((int) (birdX + 30), (int) (birdY - 28), 5, 5);

        g.setColor(new Color(255, 120, 0));
        g.fillOval((int) (birdX + 25), (int) (birdY - 20), 12, 12);

        g.setColor(new Color(120, 120, 120));
        Path2D.Double wing = new Path2D.Double();
        wing.moveTo(birdX - 10, birdY - 5);
        wing.quadTo(birdX - 35, birdY, birdX - 5, birdY + 15);
        wing.closePath();
        g.fill(wing);

        g.setColor(new Color(140, 70, 50));
        for (int i = 0; i < 3; i++) {
            int spotX = (int) (birdX - 5 + i * 6);
            int spotY = (int) (birdY + 10 + (i % 2) * 4);
            g.fillOval(spotX, spotY, 4, 4);
        }

        int tailX = (int) (birdX - 30);
        int tailY = (int) (birdY + 15);

        for (int i = 0; i < 4; i++) {
            g.setColor(i % 2 == 0 ? Color.BLACK : Color.WHITE);
            g.fillRect(tailX, tailY + i * 4, 6, 4);
        }
    }

    Timer sumTimer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sunRotationAngle += 10;
            repaint();
        }
    });

    Timer bellTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            bellSwingAngle += bellSwingDirection * 2.5;
            if (bellSwingAngle > 5) {
                bellSwingAngle = 5;
                bellSwingDirection = -1;
            } else if (bellSwingAngle < -5) {
                bellSwingAngle = -5;
                bellSwingDirection = 1;
            }
            repaint();
        }
    });

    Timer clockTimer = new Timer(1000, e -> {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY % 12);
        int minute = cal.get(java.util.Calendar.MINUTE);
        int second = cal.get(java.util.Calendar.SECOND);

        hourAngle = Math.toRadians((hour * 30) + (minute * 0.5) - 90);
        minuteAngle = Math.toRadians((minute * 6) - 90);
        secondAngle = Math.toRadians((second * 6) - 90);
        repaint();
    });

    Timer birdTimer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            birdWingAngle += birdWingDirection * 2;
            if (birdWingAngle > 10 || birdWingAngle < -10)
                birdWingDirection *= -1;

            if (birdFlyingOut) {
                birdProgress += 1.0 / birdFlightDuration;
                if (birdProgress >= 1.0) {
                    birdProgress = 1.0;
                    birdFlyingOut = false;
                }
            } else {
                birdProgress -= 1.0 / birdFlightDuration;
                if (birdProgress <= 0.0) {
                    birdProgress = 0.0;
                    birdFlyingOut = true;
                    birdTimer.setInitialDelay(1000 + (int) (Math.random() * 3000));
                    birdTimer.restart();
                    birdVisible = false;
                    return;
                }
            }

            birdVisible = true;
            double t = birdProgress;

            int buttonX = boxX + boxWidth + 40 + 20;
            int buttonY = boxY + 20 + 50;

            int startX = buttonX + 40;
            int startY = buttonY;

            int endX = startX + 200;

            birdX = startX + t * (endX - startX);
            birdY = startY;

            repaint();
        }
    });

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D p = (Graphics2D) g;
        p.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBox(p);
        drawSideButton(p);
        drawBells(p);
        drawMiddleButton(p);
        drawSun(p);
        drawClock(p);
        drawBird(p);
        sumTimer.start();
        bellTimer.start();
        clockTimer.start();
        birdTimer.start();
    }

    public static void main(String[] args) {
        Main mainFrame = new Main();
        mainFrame.setWindowSettings();

    
    }
}