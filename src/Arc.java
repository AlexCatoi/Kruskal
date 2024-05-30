import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.lang.Math;
import java.util.Scanner;
public class Arc
{
    public Point start;
    public Point end;
    public int value;
    public Arc(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }
    public int getArcSX() {
        return start.x;
    }
    public int getArcSY() {
        return start.y;
    }
    public int getArcEX() {
        return end.x;
    }
    public int getArcEY() {
        return end.y;
    }
    public int getValue() {return value;}
    public void setValue(int value) {this.value=value;}
    public void drawArc(Graphics g)
    {
        if (start != null)
        {
            g.setColor(Color.RED);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }
}
