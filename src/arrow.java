import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class arrow
{
    Point end;
    Arc a;
    double panta;
    double angle;
    //Line2D line;
    public arrow(Arc a,Point end) {
        this.a=a;
        this.end=end;
    }
    public void drawArrow(Graphics g) {
        a.drawArc(g);


        double angle=Math.atan2(a.getArcEY()-a.getArcSY(),a.getArcEX()-a.getArcSX());
        int pointStartTX=(int)(a.getArcEX()-15*Math.cos(angle));
        int pointStartTY=(int)(a.getArcEY()-15*Math.sin(angle));
        int[] x= {pointStartTX,(int)(pointStartTX-15*Math.cos(angle-Math.PI/6)),(int)(pointStartTX-15*(Math.cos(angle+Math.PI/6)))};
        int[] y={pointStartTY,(int)(pointStartTY-15*Math.sin(angle-Math.PI/6)),(int)(pointStartTY-15*(Math.sin(angle+Math.PI/6)))};
        Polygon p=new Polygon(x,y,3);
        g.fillPolygon(p);;
    }
    public double Angle()
    {
        double x1,y1;
        y1=a.getArcEY()-a.getArcSY();
        x1=a.getArcEX()-a.getArcSX();
        panta=y1/x1;
        angle=Math.atan(panta);
        return angle;
    }
}