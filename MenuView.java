
import java.io.*;
import java.util.*;

import javax.swing.*;
import java.awt.event.*;

import java.sql.Struct;



public class MenuView extends JPanel implements Observer {

    private Model model;
    private JButton fileNew = new JButton("New");
    private JMenu fileLoad = new JMenu("Load");
    private JButton fileSave = new JButton("Save");
    private int buttonNumber = 0;

    /**
     * Create a new View.
     */
    public MenuView(Model model) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        this.layoutView();
        this.registerControllers();
        model.addObserver(this);

    }

    private void layoutView() {
		// Box is an easy-to-create JPanel with a BoxLayout
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(fileNew);
        b.add(Box.createHorizontalStrut(20));
    
        
		JMenuBar menubar = new JMenuBar();
		menubar.add(fileLoad);
        
        b.add(menubar);
        b.add(Box.createHorizontalStrut(20));
        b.add(fileSave);
        b.add(Box.createHorizontalGlue());
        
        this.add(b);
    }

    private void registerControllers() {
    
		this.fileNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.newFile();
			}
        });
        
        
        this.fileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.saveFile();
			}
        });
		
    }
    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        // create some menu choices
        // use course material
        List<List<drawObject>> files = model.returnSaveFiles();
        int save = 1;
        fileLoad.removeAll();
		for (List<drawObject> object: files) {
            // add this menu item to the menu
            String name = "Save" + save;
			JMenuItem mi = new JMenuItem(name);
			// set the listener when events occur
			mi.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    model.loadFile(object);
                }
            });
			// add this menu item to the menu
            fileLoad.add(mi);
            save++;
		}
    }
}
