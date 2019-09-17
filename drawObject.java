import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class drawObject {
    public String type;
    public String backColor = "empty";
    public String lineColor;
    public int thick;
    public Point startPoint = new Point();  
    public Point endPoint = new Point();
    public boolean beSelect = false;

    public drawObject(int startX, int startY, int endX, int endY, String state, String colorState, int thick) {
        this.startPoint.x = startX;
        this.startPoint.y = startY;
        this.endPoint.x = endX;
        this.endPoint.y = endY;
        this.type = state;
        this.lineColor = colorState;
        this.thick = thick;
    }

    public void setFillColor(String tempColor) {
        this.backColor = tempColor;
    }

    public void setLineColor(String tempColor) {
        this.lineColor = tempColor;
    }

    public void setThick(int value) {
        this.thick = value;
    }

    public void info() {
        System.out.println("Start:" + startPoint.x + ',' + startPoint.y);
        System.out.println("End:" + endPoint.x + ',' + endPoint.y);    
    }
}