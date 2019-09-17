
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ButtonView extends JPanel implements Observer {

    private Model model;
    private JButton selectionButton = new JButton("selection");
    private JButton eraseButton = new JButton("erase");
    private JButton lineButton = new JButton("line");
    private JButton circleButton = new JButton("circle");
    private JButton rectButton = new JButton("rect");
    private JButton fillButton = new JButton("fill");
    private JButton blueButton = new JButton();
    private JButton redButton = new JButton();
    private JButton orangeButton = new JButton();
    private JButton yellowButton = new JButton();
    private JButton greenButton = new JButton();
    private JButton pinkButton = new JButton();
    private JButton chooserButton = new JButton("Chooser");
    private JMenu thickButton = new JMenu("thick");
    private JMenuItem thin = new JMenuItem("thin");
    private JMenuItem medium = new JMenuItem("medium");
    private JMenuItem thick = new JMenuItem("thick");

    /**
     * Create a new View.
     */
    public ButtonView(Model model) {
        this.model = model;
        this.layoutView();
        this.registerControllers();
        model.addObserver(this);
    }

    private void layoutView() {
        this.selectionButton.setOpaque(true);
        this.selectionButton.setBorderPainted(true);
        this.selectionButton.setBackground(Color.WHITE);

        this.eraseButton.setOpaque(true);
        this.eraseButton.setBorderPainted(true);
        this.eraseButton.setBackground(Color.WHITE);

        this.lineButton.setOpaque(true);
        this.lineButton.setBorderPainted(true);
        this.lineButton.setBackground(Color.WHITE);

        this.rectButton.setOpaque(true);
        this.rectButton.setBorderPainted(true);
        this.rectButton.setBackground(Color.WHITE);

        this.circleButton.setOpaque(true);
        this.circleButton.setBorderPainted(true);
        this.circleButton.setBackground(Color.WHITE);

        this.fillButton.setOpaque(true);
        this.fillButton.setBorderPainted(true);
        this.fillButton.setBackground(Color.WHITE);

        this.blueButton.setOpaque(true);
        this.blueButton.setBorderPainted(true);
        this.blueButton.setBackground(Color.BLUE);

        this.redButton.setOpaque(true);
        this.redButton.setBorderPainted(true);
        this.redButton.setBackground(Color.RED);

        this.orangeButton.setOpaque(true);
        this.orangeButton.setBorderPainted(true);
        this.orangeButton.setBackground(Color.ORANGE);

        this.yellowButton.setOpaque(true);
        this.yellowButton.setBorderPainted(true);
        this.yellowButton.setBackground(Color.YELLOW);

        this.greenButton.setOpaque(true);
        this.greenButton.setBorderPainted(true);
        this.greenButton.setBackground(Color.GREEN);

        this.pinkButton.setOpaque(true);
        this.pinkButton.setBorderPainted(true);
        this.pinkButton.setBackground(Color.PINK);
        
        this.setLayout(new GridLayout(7, 2));

        
        this.add(selectionButton);
        this.add(eraseButton);
        this.add(lineButton);
        this.add(circleButton);
        this.add(rectButton);
        this.add(fillButton);
        
        
        this.add(blueButton);
        this.add(redButton);
        this.add(orangeButton);
        this.add(yellowButton);
        this.add(greenButton);
        this.add(pinkButton);
        
        this.add(chooserButton);
        thickButton.add(thin);
        thickButton.add(medium);
        thickButton.add(thick);
        JMenuBar menubar = new JMenuBar();
		menubar.add(thickButton);
        
        this.add(menubar);

		
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        if (model.returnThickState() == 2) {
            thickButton.setText("thin");
        } else if (model.returnThickState() == 5) {
            thickButton.setText("medium");
        } else if (model.returnThickState() == 7) {
            thickButton.setText("thick");
        }

        this.chooseColor(model.returnColorState());
        this.stateButton(model.returnState());
        
    }

    private void stateButton(String state) {
        if (state == "selection") {
            this.selectionButton.setBackground(Color.GRAY);
        } else {
            this.selectionButton.setBackground(Color.WHITE);
        } 

        if (state == "line") {
            this.lineButton.setBackground(Color.GRAY);
        } else {
            this.lineButton.setBackground(Color.WHITE);
        } 

        if (state == "erase") {
            this.eraseButton.setBackground(Color.GRAY);
        }  else {
            this.eraseButton.setBackground(Color.WHITE);
        }

        if (state == "circle") {
            this.circleButton.setBackground(Color.GRAY);
        }  else {
            this.circleButton.setBackground(Color.WHITE);
        }

        if (state == "rect") {
            this.rectButton.setBackground(Color.GRAY);
        } else {
            this.rectButton.setBackground(Color.WHITE);
        } 

        if (state == "fill") {
            this.fillButton.setBackground(Color.GRAY);
        } else {
            this.fillButton.setBackground(Color.WHITE);
        } 
    }

    private void chooseColor(String color) {
        if (color == "blue") {
            blueButton.setBorderPainted(false);
        } else {
            blueButton.setBorderPainted(true);
        }

        if (color == "red") {
            redButton.setBorderPainted(false);
        } else {
            redButton.setBorderPainted(true);
        }

        if (color == "orange") {
            orangeButton.setBorderPainted(false);
        } else {
            orangeButton.setBorderPainted(true);
        }

        if (color == "yellow") {
            yellowButton.setBorderPainted(false);
        } else {
            yellowButton.setBorderPainted(true);
        }

        if (color == "green") {
            greenButton.setBorderPainted(false);
        } else {
            greenButton.setBorderPainted(true);
        }

        if (color == "pink") {
            pinkButton.setBorderPainted(false);
        } else {
            pinkButton.setBorderPainted(true);
        }
    }

    private void registerControllers() {
    
		this.selectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateState("selection");
			}
		});

		this.eraseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateState("erase");
			}
        });
        
        this.lineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateState("line");
			}
        });
        
        this.circleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateState("circle");
			}
        });
        
        this.rectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateState("rect");
			}
        });
        
        this.fillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateState("fill");
			}
        });
        
        this.blueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                model.updateColorState("blue");
                chooseColor("blue");
			}
        });

        this.redButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                model.updateColorState("red");
                chooseColor("red");
			}
        });

        this.orangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                model.updateColorState("orange");
                chooseColor("orange");
			}
        });

        this.yellowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                model.updateColorState("yellow");
                chooseColor("yellow");
			}
        });

        this.greenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                model.updateColorState("green");
                chooseColor("green");
			}
        });

        this.pinkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
                model.updateColorState("pink");
                chooseColor("pink");
			}
        });

        this.chooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateState("chooser");
			}
        });

        this.thin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateThickState(2);
			}
        });

        this.medium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateThickState(5);
			}
        });

        this.thick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.updateThickState(7);
			}
        });
    }
    
}
