package visualizer.visualizer;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


class MyCanvas extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawString("Hello, World!", 50, 50);
        g.drawLine(20, 400, getSize().width-20, 400);
        g.drawLine(400, 20, 400, getSize().height-20);
    }
}

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Robot Sim");

        MyCanvas canvas = new MyCanvas();
        canvas.setSize(800, 800);
        frame.add(canvas);

        frame.setVisible(true);
    }
}
