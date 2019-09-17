
import java.sql.Struct;
import java.util.*;
import javax.swing.*;
import java.awt.Point;
import java.awt.geom.*;

import javax.vecmath.*;
import java.lang.Math.*;


//import com.sun.javafx.scene.paint.GradientUtils.Point;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private String state = "empty";
    private String colorState = "empty";
    private int thickState = 2;
    private Point startPoint = new Point(); 
    private Point endPoint = new Point();
    private Point movePoint = new Point();
    private Point selectPoint = new Point(); 
    private Point dragPoint = new Point();
    private boolean painting = false;
    private boolean finishSelect = false;
    private List<drawObject> drawObjects = new ArrayList<drawObject>();
    private List<List<drawObject>> saveFiles = new ArrayList<List<drawObject>>();

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList<Observer>();
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public void setSelectPoint(Point tempStart) {
        this.selectPoint = tempStart;
        int x = tempStart.x;
        int y = tempStart.y;
        if (!this.finishSelect) {
            for (drawObject object: drawObjects) {
                String type = object.type;
                if (type == "line") {
                    // use course material closestPoint
                    Point2d M2 = new Point2d(tempStart.x, tempStart.y);
                    Point2d I1 = closestPoint(M2, 
				    			new Point2d(object.startPoint.x, object.startPoint.y),
                                new Point2d(object.endPoint.x, object.endPoint.y));
                    double d1 = M2.distance(I1);
                    if (d1 < 5) {
                        object.beSelect = true;
                            this.finishSelect = true;
                            this.colorState = object.lineColor;
                            this.thickState = object.thick;
                            break;
                    }

                } else if (type == "rect"){
                    int width = object.endPoint.x - object.startPoint.x;
                    int height = object.endPoint.y - object.startPoint.y;
                    
                        if (x >= object.startPoint.x 
                        && x <= object.startPoint.x + width
                        && y >= object.startPoint.y 
                        && y <= object.startPoint.y + height) {
                            object.beSelect = true;
                            this.finishSelect = true;
                            this.colorState = object.lineColor;
                            this.thickState = object.thick;
                            break;
                        }
                   
                } else if (type == "circle") {
                    int xDis = object.endPoint.x - object.startPoint.x;
                    int yDis = object.endPoint.y - object.startPoint.y;
                    int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));               
                    
                        int centerX = object.startPoint.x + size/2;
                        int centerY = object.startPoint.y + size/2;
                        int selectDisX = x - centerX;
                        int selectDisY = y - centerY;
                        int dis = (int) (Math.sqrt(selectDisX * selectDisX + selectDisY * selectDisY));  
                        if (dis <= size/2) {
                            object.beSelect = true;
                            this.finishSelect = true;
                            this.colorState = object.lineColor;
                            this.thickState = object.thick;
                            break;
                        }
                    
                }
            }
        }       
        this.notifyObservers();
    }

    public Point returnSelectPoint() {
        return this.selectPoint;
    }

    public void setDragPoint(int x, int y) {
        this.dragPoint.x = x;
        this.dragPoint.y = y;
        if (this.finishSelect) {
            for (drawObject object: drawObjects) {
                if (object.beSelect) {
                    if (object.type == "line") {
                        // use course material closestPoint
                        Point2d M2 = new Point2d(x, y);
                        Point2d I1 = closestPoint(M2, 
				    			new Point2d(object.startPoint.x, object.startPoint.y),
                                new Point2d(object.endPoint.x, object.endPoint.y));
                    double d1 = M2.distance(I1);
                    if (d1 < 5) {
                        int width = object.endPoint.x - object.startPoint.x;
                        int height = object.endPoint.y - object.startPoint.y;
                        object.startPoint.x = this.dragPoint.x - width/2;
                        object.startPoint.y = this.dragPoint.y - height/2;
                        object.endPoint.x = object.startPoint.x + width;
                        object.endPoint.y = object.startPoint.y + height;
                        break;
                    }

                    } else if (object.type == "rect") {
                        int width = object.endPoint.x - object.startPoint.x;
                        int height = object.endPoint.y - object.startPoint.y;
                        object.startPoint.x = this.dragPoint.x - width/2;
                        object.startPoint.y = this.dragPoint.y - height/2;
                        object.endPoint.x = object.startPoint.x + width;
                        object.endPoint.y = object.startPoint.y + height;
                        break;
                    } else if (object.type == "circle") {
                        int xDis = object.endPoint.x - object.startPoint.x;
                        int yDis = object.endPoint.y - object.startPoint.y;
                        int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));
                        object.startPoint.x = this.dragPoint.x - size/2;
                        object.startPoint.y = this.dragPoint.y - size/2;
                        object.endPoint.x = object.startPoint.x + xDis;
                        object.endPoint.y = object.startPoint.y + yDis;
                        break;
                    }
                }
            }
        }
        this.notifyObservers();
    }

    public void setStartPoint(Point tempStart) {
        this.startPoint = tempStart;
        this.notifyObservers();
    }

    public Point returnStartPoint() {
        return this.startPoint;
    }

    public void setEndPoint(Point tempEnd) {
        this.endPoint = tempEnd;
        this.notifyObservers();
    }

    public Point returnEndPoint() {
        return this.endPoint;
    }

    public void setMovePoint(Point tempEnd) {
        this.movePoint = tempEnd;
        this.notifyObservers();
    }

    public Point returnMovePoint() {
        return this.movePoint;
    }

    public String returnState() {
        return this.state;
    }

    public String returnColorState() {
        return this.colorState;
    }

    public int returnThickState() {
        return this.thickState;
    }

    public void updateState(String newState) {
        this.selectPoint = new Point();
        this.finishSelect = false;
        this.state = newState;
        if (newState != "selection") {
            this.unSelect();
        }
        this.notifyObservers();
    }

    public void updateColorState(String newState) {
        this.colorState = newState;
        if (this.finishSelect) {
            int x = this.selectPoint.x;
            int y = this.selectPoint.y;
            for (drawObject object: drawObjects) {
                String type = object.type;
                if (type == "line") {
                    // use course material closestPoint
                    Point2d M2 = new Point2d(x, y);
                    Point2d I1 = closestPoint(M2, 
                            new Point2d(object.startPoint.x, object.startPoint.y),
                            new Point2d(object.endPoint.x, object.endPoint.y));
                    double d1 = M2.distance(I1);
                    if (d1 < 5) {
                        object.setLineColor(newState);
                        break;
                    }
    
                } else if (type == "rect"){
                    int width = object.endPoint.x - object.startPoint.x;
                    int height = object.endPoint.y - object.startPoint.y;
                    
                        if (x >= object.startPoint.x 
                        && x <= object.startPoint.x + width
                        && y >= object.startPoint.y 
                        && y <= object.startPoint.y + height) {
                            object.setLineColor(newState);
                            break;
                        }
                   
                } else if (type == "circle") {
                    int xDis = object.endPoint.x - object.startPoint.x;
                    int yDis = object.endPoint.y - object.startPoint.y;
                    int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));               
                    
                        int centerX = object.startPoint.x + size/2;
                        int centerY = object.startPoint.y + size/2;
                        int selectDisX = x - centerX;
                        int selectDisY = y - centerY;
                        int dis = (int) (Math.sqrt(selectDisX * selectDisX + selectDisY * selectDisY));  
                        if (dis <= size/2) {
                            object.setLineColor(newState);
                            break;
                        }
                    
                }
            }
        }
        this.notifyObservers();
    }

    public void updateThickState(int newState) {
        this.thickState = newState;
        if (this.finishSelect) {
            int x = this.selectPoint.x;
            int y = this.selectPoint.y;
            for (drawObject object: drawObjects) {
                String type = object.type;
                if (type == "line") {
                    // use course material closestPoint
                    Point2d M2 = new Point2d(x, y);
                    Point2d I1 = closestPoint(M2, 
                            new Point2d(object.startPoint.x, object.startPoint.y),
                            new Point2d(object.endPoint.x, object.endPoint.y));
                    double d1 = M2.distance(I1);
                    if (d1 < 5) {
                        object.setThick(newState);
                        break;
                    }
                } else if (type == "rect"){
                    int width = object.endPoint.x - object.startPoint.x;
                    int height = object.endPoint.y - object.startPoint.y;
                    
                        if (x >= object.startPoint.x 
                        && x <= object.startPoint.x + width
                        && y >= object.startPoint.y 
                        && y <= object.startPoint.y + height) {
                            object.setThick(newState);
                            break;
                        }
                   
                } else if (type == "circle") {
                    int xDis = object.endPoint.x - object.startPoint.x;
                    int yDis = object.endPoint.y - object.startPoint.y;
                    int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));               
                    
                        int centerX = object.startPoint.x + size/2;
                        int centerY = object.startPoint.y + size/2;
                        int selectDisX = x - centerX;
                        int selectDisY = y - centerY;
                        int dis = (int) (Math.sqrt(selectDisX * selectDisX + selectDisY * selectDisY));  
                        if (dis <= size/2) {
                            object.setThick(newState);
                            break;
                        }
                    
                }
            }
        }
        this.notifyObservers();
    }

    public boolean isPainting() {
        return this.painting;
    }

    public void changePainting() {
        this.painting = !this.painting;
        this.notifyObservers();
    }

    public List<drawObject> drawn() {
        return this.drawObjects;
    }

    public void pushObject() {
        // add to front
        this.drawObjects.add(0, new drawObject(this.startPoint.x, this.startPoint.y, this.endPoint.x, this.endPoint.y, this.state, this.colorState, this.thickState));
        this.notifyObservers();
    }

    public void setThickandColor(int thick, String color) {
        this.thickState = thick;
        this.colorState = color;
    }

    public void removeObject(int x, int y) {
        for (drawObject object: drawObjects) {
            String type = object.type;
            if (type == "line") {
                // use course material closestPoint
                Point2d M2 = new Point2d(x, y);
                Point2d I1 = closestPoint(M2, 
                        new Point2d(object.startPoint.x, object.startPoint.y),
                        new Point2d(object.endPoint.x, object.endPoint.y));
                double d1 = M2.distance(I1);
                if (d1 < 5) {
                    this.drawObjects.remove(object);
                    break;
                }
            } else if (type == "rect"){
                int width = object.endPoint.x - object.startPoint.x;
                int height = object.endPoint.y - object.startPoint.y;
                
                    if (x >= object.startPoint.x 
                    && x <= object.startPoint.x + width
                    && y >= object.startPoint.y 
                    && y <= object.startPoint.y + height) {
                        this.drawObjects.remove(object);
                        break;
                    }
               
            } else if (type == "circle") {
                int xDis = object.endPoint.x - object.startPoint.x;
                int yDis = object.endPoint.y - object.startPoint.y;
                int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));               
                
                    int centerX = object.startPoint.x + size/2;
                    int centerY = object.startPoint.y + size/2;
                    int selectDisX = x - centerX;
                    int selectDisY = y - centerY;
                    int dis = (int) (Math.sqrt(selectDisX * selectDisX + selectDisY * selectDisY));  
                    if (dis <= size/2) {
                        this.drawObjects.remove(object);
                        break;
                    }
                
            }
        }
        
        this.notifyObservers();
    }

    public void fillObject(int x, int y) {
        
        for (drawObject object: drawObjects) {
            String type = object.type;
            if (type == "line") {

            } else if (type == "rect"){
                int width = object.endPoint.x - object.startPoint.x;
                int height = object.endPoint.y - object.startPoint.y;
                
                    if (x >= object.startPoint.x 
                    && x <= object.startPoint.x + width
                    && y >= object.startPoint.y 
                    && y <= object.startPoint.y + height) {
                        object.backColor = this.colorState;
                        
                        break;
                    }
               
            } else if (type == "circle") {
                int xDis = object.endPoint.x - object.startPoint.x;
                int yDis = object.endPoint.y - object.startPoint.y;
                int size =   (int) (Math.sqrt(xDis * xDis + yDis * yDis));               
                
                    int centerX = object.startPoint.x + size/2;
                    int centerY = object.startPoint.y + size/2;
                    int selectDisX = x - centerX;
                    int selectDisY = y - centerY;
                    int dis = (int) (Math.sqrt(selectDisX * selectDisX + selectDisY * selectDisY));  
                    if (dis <= size/2) {
                        object.backColor = this.colorState;
                        
                        break;
                    }
                
            }
        }
        
        this.notifyObservers();
    }

    public boolean finishSelected() {
        return this.finishSelect;
        
    }

    public void unSelect() {
        this.selectPoint = new Point();
        this.finishSelect = false;
        for (drawObject object: drawObjects) {
            object.beSelect = false;
        }
        
        this.notifyObservers();
    }

    // use code from course material ClosestPoint
    private Point2d closestPoint(Point2d M, Point2d P0, Point2d P1) {
    	Vector2d v = new Vector2d();
    	v.sub(P1,P0); // v = P1 - P0
    	
    	// early out if line is less than 1 pixel long
    	if (v.lengthSquared() < 0.5)
    		return P0;
    	
    	Vector2d u = new Vector2d();
    	u.sub(M,P0); // u = M - P1

    	// scalar of vector projection ...
    	double s = u.dot(v) / v.dot(v); 
    	
    	// find point for constrained line segment
    	if (s < 0) 
    		return P0;
    	else if (s > 1)
    		return P1;
    	else {
    		Point2d I = P0;
        	Vector2d w = new Vector2d();
        	w.scale(s, v); // w = s * v
    		I.add(w); // I = P0 + w
    		return I;
    	}
    }

    public void newFile() {
        this.drawObjects = new ArrayList<drawObject>();
        this.notifyObservers();
    }

    public void loadFile(List<drawObject> old) {
        this.drawObjects = old;
        this.notifyObservers();
    }

    public void saveFile() {
        this.saveFiles.add(this.drawObjects);
        this.notifyObservers();
    }

    public List<List<drawObject>> returnSaveFiles() {
        return saveFiles;
    }
    
}

