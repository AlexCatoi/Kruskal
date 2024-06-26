import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Node extends MyPanel
{
    public int coordX;
    public int coordY;
    public int number;

    public Node(int coordX, int coordY, int number)
    {
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }

    public int getCoordX() {
        return coordX;
    }
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }
    public int getCoordY() {
        return coordY;
    }
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void drawNode(Graphics g, int node_diam,Color c)
    {
        //g.setColor(Color.RED);
        g.setColor(c);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.fillOval(coordX-node_diam/2, coordY-node_diam/2, node_diam, node_diam);
        g.setColor(Color.WHITE);
        g.drawOval(coordX-node_diam/2, coordY-node_diam/2, node_diam, node_diam);
        if(number < 10)
            g.drawString(((Integer)number).toString(), coordX-4, coordY+4);
        else
            g.drawString(((Integer)number).toString(), coordX-8, coordY+4);
    }
}
