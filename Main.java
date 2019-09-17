import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

// this assignment use some function in course materials as base
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        MenuView MenuView = new MenuView(model);
        ButtonView ButtonView = new ButtonView(model);
        CanvasView CanvasView = new CanvasView(model);

        JFrame frame = new JFrame("Vector-Drawing Program");

        // use BorderLayout
		BorderLayout layout = new BorderLayout();
        frame.getContentPane().setLayout(layout);
        frame.getContentPane().add(MenuView, BorderLayout.NORTH);
        frame.getContentPane().add(ButtonView, BorderLayout.WEST);
        
        frame.getContentPane().add(CanvasView, BorderLayout.CENTER);
        
        frame.pack();
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setMaximumSize(new Dimension(1600, 1200));
        frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    
    }
}
