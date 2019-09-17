
import java.io.*;
import java.util.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ActionMap;
import java.awt.event.*;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Point;
import java.awt.Graphics;
import java.util.*;

public class CanvasView extends JComponent implements Observer {

    private Model model;
    private Point startPoint = new Point(); 
    private Point movePoint = new Point();
    private Point endPoint = new Point();
    private Point selectPoint = new Point();
    
    /**
     * Create a new View.
     */
    public CanvasView(Model model) {
        super();
        this.model = model;
        this.layoutView();
        this.registerControllers();
        this.KeyBinding();
        model.addObserver(this);

    }

    private void layoutView() {
        this.setPreferredSize(new Dimension(200, 200));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // JScrollPane jp = new JScrollPane(mp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // jp.getVerticalScrollBar().setUnitIncrement(15); // speedup scrolling
        // main.add(jp);
        // main.pack();
    }

    	/** Register event Controllers for mouse clicks and motion. */
	private void registerControllers() {
		MouseInputListener mil = new MController();
		this.addMouseListener(mil);
        this.addMouseMotionListener(mil);      
	}

    private class MController extends MouseInputAdapter {

		public void mouseClicked(MouseEvent e){
            
                if (model.returnState() == "line" || model.returnState() == "rect" || model.returnState() == "circle") {
                    if (!model.isPainting()) {
                        startPoint.x = e.getX();
                        startPoint.y = e.getY();
                        model.setStartPoint(startPoint);
                        model.changePainting();
                    } else {
                        endPoint.x = e.getX();
                        endPoint.y = e.getY();
                        model.setEndPoint(endPoint);
                        model.changePainting();
                        // System.out.println("Start:" + startPoint.x + ',' + startPoint.y);
                        // System.out.println("End:" + endPoint.x + ',' + endPoint.y);           
                        model.pushObject();
                    }
                } else if (model.returnState() == "selection") {
                    selectPoint.x = e.getX();
                    selectPoint.y = e.getY();
                    model.setSelectPoint(selectPoint);
                } else if (model.returnState() == "erase") {
                    model.removeObject(e.getX(), e.getY());
                } else if (model.returnState() == "fill") {
                    model.fillObject(e.getX(), e.getY());
                }
        }

		// public void mouseReleased(MouseEvent e) {
		// 	painting = onTopCorner(e.getX(), e.getY());
        // }
        
        public void mouseDragged(MouseEvent e) {
            // saySomething("Mouse dragged", e);
            model.setDragPoint(e.getX(),e.getY());
         }     

		/** The user is dragging the mouse. Resize appropriately. */
		public void mouseMoved(MouseEvent e){
            movePoint.x = e.getX();
            movePoint.y = e.getY();
            model.setMovePoint(movePoint);
        }
    } // MController
    
    private void KeyBinding() {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), "exit");
    
        getActionMap().put("exit", new AbstractAction() 
        {
            public void actionPerformed(ActionEvent event)
            {
                model.unSelect();
            }
        });
    }
    
    // custom graphics drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // cast to get 2D drawing methods
        Graphics2D g2 = (Graphics2D) g; 
        Graphics2D fillColor = (Graphics2D) g.create();
        fillColor.setStroke(new BasicStroke(2));
        

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        final List<drawObject> drawObjects = this.model.drawn();
        // boolean isSelected = this.model.isSelected();
        // boolean isRemove = (this.model.returnState() == "erase");
        // boolean removed = false;
        // drawObject removeObject = new drawObject(0, 0, 0, 0, "empty");
        Point currSelect = this.model.returnSelectPoint();

        //ListIterator li = drawObjects.listIterator(drawObjects.size());
        List<drawObject> testObjects = new ArrayList<>(drawObjects);
        Collections.reverse(testObjects);
        for (drawObject object: testObjects) {
            boolean ignore = false;
            g2.setStroke(new BasicStroke(object.thick));
            if (object.lineColor == "empty") {
                g2.setColor(Color.BLACK);
            } else if (object.lineColor == "blue") {
                g2.setColor(Color.BLUE);
            } else if (object.lineColor == "red") {
                g2.setColor(Color.RED);
            } else if (object.lineColor == "orange") {
                g2.setColor(Color.ORANGE);
            } else if (object.lineColor == "yellow") {
                g2.setColor(Color.YELLOW);
            } else if (object.lineColor == "green") {
                g2.setColor(Color.GREEN);
            } else if (object.lineColor == "pink") {
                g2.setColor(Color.PINK);
            }
            if (object.backColor == "empty") {
                fillColor.setColor(Color.WHITE);
                ignore = true;
            } else if (object.backColor == "blue") {
                fillColor.setColor(Color.BLUE);
            } else if (object.backColor == "red") {
                fillColor.setColor(Color.RED);
            } else if (object.backColor == "orange") {
                fillColor.setColor(Color.ORANGE);
            } else if (object.backColor == "yellow") {
                fillColor.setColor(Color.YELLOW);
            } else if (object.backColor == "green") {
                fillColor.setColor(Color.GREEN);
            } else if (object.backColor == "pink") {
                fillColor.setColor(Color.PINK);
            }
            String type = object.type;
            if (type == "line") {
                if (object.beSelect) {
                    //model.setThickandColor(object.thick, object.backColor);
                    Graphics2D g2d = (Graphics2D) g.create();
                    if (object.lineColor == "empty") {
                        g2d.setColor(Color.BLACK);
                    } else if (object.lineColor == "blue") {
                        g2d.setColor(Color.BLUE);
                    } else if (object.lineColor == "red") {
                        g2d.setColor(Color.RED);
                    } else if (object.lineColor == "orange") {
                        g2d.setColor(Color.ORANGE);
                    } else if (object.lineColor == "yellow") {
                        g2d.setColor(Color.YELLOW);
                    } else if (object.lineColor == "green") {
                        g2d.setColor(Color.GREEN);
                    } else if (object.lineColor == "pink") {
                        g2d.setColor(Color.PINK);
                    }
                    Stroke dashed = new BasicStroke(object.thick, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                    g2d.setStroke(dashed);
                    
                    g2d.drawLine(object.startPoint.x, object.startPoint.y, object.endPoint.x, object.endPoint.y);
                    g2d.dispose();
                    continue;
                    
                }
                g2.drawLine(object.startPoint.x, object.startPoint.y, object.endPoint.x, object.endPoint.y);
            } else if(type == "rect") {
                int width = object.endPoint.x - object.startPoint.x;
                int height = object.endPoint.y - object.startPoint.y;
                if (object.beSelect) {
                    
                        //model.setThickandColor(object.thick, object.backColor);
                        Graphics2D g2d = (Graphics2D) g.create();
                        if (object.lineColor == "empty") {
                            g2d.setColor(Color.BLACK);
                        } else if (object.lineColor == "blue") {
                            g2d.setColor(Color.BLUE);
                        } else if (object.lineColor == "red") {
                            g2d.setColor(Color.RED);
                        } else if (object.lineColor == "orange") {
                            g2d.setColor(Color.ORANGE);
                        } else if (object.lineColor == "yellow") {
                            g2d.setColor(Color.YELLOW);
                        } else if (object.lineColor == "green") {
                            g2d.setColor(Color.GREEN);
                        } else if (object.lineColor == "pink") {
                            g2d.setColor(Color.PINK);
                        }
                        Stroke dashed = new BasicStroke(object.thick, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                        g2d.setStroke(dashed);
                        g2d.drawRect(object.startPoint.x, object.startPoint.y, width, height);
                        
                        if (!ignore) {
                            
                            fillColor.fillRect(object.startPoint.x, object.startPoint.y, width, height);
                        }
                        
                        //gets rid of the copy
                        g2d.dispose();
                        continue;
                    
                } 
                g2.drawRect(object.startPoint.x, object.startPoint.y, width, height);
                
                if (!ignore) {
                   
                    fillColor.fillRect(object.startPoint.x, object.startPoint.y, width, height);
                }
            } else if(type == "circle") {
                int xDis = object.endPoint.x - object.startPoint.x;
                int yDis = object.endPoint.y - object.startPoint.y;
                int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));               
                if (object.beSelect) {
                    
                    int centerX = object.startPoint.x + size/2;
                    int centerY = object.startPoint.y + size/2;
                    int selectDisX = currSelect.x - centerX;
                    int selectDisY = currSelect.y - centerY;
                    int dis = (int) (Math.sqrt(selectDisX * selectDisX + selectDisY * selectDisY));  
                    
                        //object.setLineColor(this.model.returnColorState());
                        //model.setThickandColor(object.thick, object.backColor);
                        Graphics2D g2d = (Graphics2D) g.create();
                        if (object.lineColor == "empty") {
                            g2d.setColor(Color.BLACK);
                        } else if (object.lineColor == "blue") {
                            g2d.setColor(Color.BLUE);
                        } else if (object.lineColor == "red") {
                            g2d.setColor(Color.RED);
                        } else if (object.lineColor == "orange") {
                            g2d.setColor(Color.ORANGE);
                        } else if (object.lineColor == "yellow") {
                            g2d.setColor(Color.YELLOW);
                        } else if (object.lineColor == "green") {
                            g2d.setColor(Color.GREEN);
                        } else if (object.lineColor == "pink") {
                            g2d.setColor(Color.PINK);
                        }
                        Stroke dashed = new BasicStroke(object.thick, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                        g2d.setStroke(dashed);
                        g2d.drawOval(object.startPoint.x, object.startPoint.y, size, size);
                        
                        if (!ignore) {
                            
                            fillColor.fillOval(object.startPoint.x, object.startPoint.y, size, size);
                        }
                        
                        //gets rid of the copy
                        g2d.dispose();
                        continue;
                    
                }
                g2.drawOval(object.startPoint.x, object.startPoint.y, size, size);
                
                if (!ignore) {
                    
                    fillColor.fillOval(object.startPoint.x, object.startPoint.y, size, size);
                }
            }          
        }
       
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(this.model.returnThickState()));
        if (this.model.returnColorState() == "empty") {
            g2.setColor(Color.BLACK);
        } else if (this.model.returnColorState() == "blue") {
            g2.setColor(Color.BLUE);
        } else if (this.model.returnColorState() == "red") {
            g2.setColor(Color.RED);
        } else if (this.model.returnColorState() == "orange") {
            g2.setColor(Color.ORANGE);
        } else if (this.model.returnColorState() == "yellow") {
            g2.setColor(Color.YELLOW);
        } else if (this.model.returnColorState() == "green") {
            g2.setColor(Color.GREEN);
        } else if (this.model.returnColorState() == "pink") {
            g2.setColor(Color.PINK);
        }

        if (this.model.isPainting()) {
            startPoint = this.model.returnStartPoint();
            endPoint = this.model.returnEndPoint();
            movePoint = this.model.returnMovePoint();
            String state = this.model.returnState();
            if (state == "line") {
                g2.drawLine(startPoint.x, startPoint.y, movePoint.x, movePoint.y);
                
            } else if(state == "rect") {
                int width = movePoint.x - startPoint.x;
                int height = movePoint.y - startPoint.y;
                
                g2.drawRect(startPoint.x, startPoint.y, width, height);
            } else if(state == "circle") {
                int xDis = movePoint.x - startPoint.x;
                int yDis = movePoint.y - startPoint.y;
                int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));
                g2.drawOval(startPoint.x, startPoint.y, size, size);
            }
            
        } 

        g2.setColor(Color.BLACK);
         

        //g2.drawString(String.format("%d,%d", startPoint.x, startPoint.y), startPoint.x + 10, startPoint.y + 10);       

        
        //g2.drawString(String.format("%d,%d", movePoint.x, movePoint.y), movePoint.x + 10, movePoint.y + 10);
    }
    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        repaint();
    }
}
