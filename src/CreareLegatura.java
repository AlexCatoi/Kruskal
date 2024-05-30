import java.util.Vector;


public class CreareLegatura{
    int x,y;
    public CreareLegatura(int x,int y) {
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean findOrientat(Vector<CreareLegatura> vect,CreareLegatura j) {
        boolean ok=false;
        for(CreareLegatura i:vect)
            if(i.getX()==j.getX() && i.getY()==j.getY())
                ok=true;
        return ok;
    }
    public boolean findNeorientat(Vector<CreareLegatura> vect,CreareLegatura j) {
        boolean ok=false;
        for(CreareLegatura i:vect)
            if((i.getX()==j.getX() && i.getY()==j.getY())||(i.getX()==j.getY() && i.getY()==j.getX()) )
                ok=true;
        return ok;
    }
}