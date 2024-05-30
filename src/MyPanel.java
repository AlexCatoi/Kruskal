import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;
//import javafx.event.setOnAction;
//import javafx.event.ActionEvent;

public class MyPanel extends JPanel {
    Vector<CreareLegatura> vec=new Vector<CreareLegatura>();
    public Vector<CreareLegatura> getVec(){
        return vec;
    }
    int[][] mat;
    Point CercStart,CercEnd;
    Vector<Arc> Aprim=new Vector<>();
    public int nodeNr = 1;
    private int node_diam = 30;
    public Vector<Node> listaNoduri;
    public Vector<Arc> listaArce;
    public Vector<arrow> listaSageti;
    Point pointStart = null;
    Arc saved;
    Scanner s = new Scanner(System.in);
    //ArrayList<Integer> costuri=new ArrayList<>();
    Point pointEnd = null;
    int StartNodeValue=0;
    int EndNodeValue=0;
    boolean isDragging = false;
    boolean orientat=false;
    boolean ok2=false;
    boolean ok1=true;
    boolean ok3=false;
    boolean ok4=false;
    public MyPanel()
    {

        listaNoduri = new Vector<Node>();
        listaArce = new Vector<Arc>();
        listaSageti=new Vector<arrow>();
        // borderul panel-ului
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter() {
            //evenimentul care se produce la apasarea mousse-ului
            public void mousePressed(MouseEvent e) {
                pointStart = e.getPoint();
            }
            public boolean goodPoint() {
                ok1=true;
                for(Node i:listaNoduri) {
                    if(pointStart.x>i.getCoordX()-1.5*node_diam && pointStart.x<i.getCoordX()+1.5*node_diam)
                        if(pointStart.y>i.getCoordY()-1.5*node_diam && pointStart.y<i.getCoordY()+1.5*node_diam)
                        {
                            ok1=false;
                            break;
                        }
                }
                return ok1;
            }
            public boolean goodStart(boolean ok2) {
                ok2=false;
                for(Node i:listaNoduri) {
                    if(pointStart.x>i.getCoordX()-node_diam/2 && pointStart.x<i.getCoordX()+node_diam/2)
                        if(pointStart.y>i.getCoordY()-node_diam/2 && pointStart.y<i.getCoordY()+node_diam/2)
                        {
                            ok2=true;
                            StartNodeValue=i.getNumber();
                            CercStart=new Point(i.getCoordX(),i.getCoordY());
                            break;
                        }
                }
                return ok2;
            }

            public boolean goodEnd(boolean ok3) {
                ok3=false;
                if(isDragging)
                    for(Node i:listaNoduri) {
                        if(pointEnd.x>i.getCoordX()-node_diam/2 && pointEnd.x<i.getCoordX()+node_diam/2)
                            if(pointEnd.y>i.getCoordY()-node_diam/2 && pointEnd.y<i.getCoordY()+node_diam/2)
                            {
                                ok3=true;
                                EndNodeValue=i.getNumber();
                                CercEnd= new Point(i.getCoordX(),i.getCoordY());
                                break;
                            }
                    }
                return ok3;
            }


            //evenimentul care se produce la eliberarea mousse-ului
            public void mouseReleased(MouseEvent e) {
                boolean ok2=false;
                boolean ok1=true;
                boolean ok3=false;
                repaint();
                if ((!isDragging && goodPoint())||listaNoduri.isEmpty()) {
                    addNode(e.getX(), e.getY());
                    if(orientat)
                    {
                        Fisier file=new Fisier();
                        mat=new int[listaNoduri.size()][listaNoduri.size()];
                        mat=file.CreareMatriceOrientata(vec,listaNoduri.size());
                        file.Write(mat);
                    }
                    else{
                        Fisier file=new Fisier();
                        mat=new int[listaNoduri.size()][listaNoduri.size()];
                        mat=file.CreareMatriceNeorientata(vec,listaNoduri.size());
                        file.Write(mat);
                    }
                    ok1=false;
                }
                else if(isDragging && goodStart(ok2) && goodEnd(ok3)){
                    if(orientat==false)
                    {	//Arc arc = new Arc(pointStart, pointEnd);
                        Arc arc=new Arc(CercStart,CercEnd);
                        CreareLegatura Legatura=new CreareLegatura(StartNodeValue,EndNodeValue);
                        if(Legatura.findNeorientat(vec, Legatura)==false) {
                            vec.add(Legatura);
                            int nr=GetNumber();
                            arc.setValue(nr);
                            Fisier file=new Fisier();
                            mat=new int[listaNoduri.size()][listaNoduri.size()];
                            mat=file.CreareMatriceNeorientata(vec,listaNoduri.size());
                            file.Write(mat);
                            listaArce.add(arc);
                            repaint();
                        }
                        StartNodeValue=0;
                        EndNodeValue=0;
                    }
                    else {
                        //Arc a = new Arc(pointStart, pointEnd);
                        Arc a=new Arc(CercStart,CercEnd);
                        arrow Sageata=new arrow(a,pointEnd);
                        CreareLegatura Legatura=new CreareLegatura(StartNodeValue,EndNodeValue);
                        if(Legatura.findOrientat(vec, Legatura)==false) {
                            vec.add(Legatura);
                            Fisier file=new Fisier();
                            mat=new int[listaNoduri.size()][listaNoduri.size()];
                            mat=file.CreareMatriceOrientata(vec,listaNoduri.size());
                            file.Write(mat);
                            listaSageti.add(Sageata);
                        }
                        StartNodeValue=0;
                        EndNodeValue=0;
                    }
                }
                pointStart = null;
                isDragging = false;
                ok3=false;
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            //evenimentul care se produce la drag&drop pe mousse
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                isDragging = true;
                repaint();
            }
        });
    }

    //metoda care se apeleaza la eliberarea mouse-ului
    private void addNode(int x, int y) {
        Node node = new Node(x, y, nodeNr);
        listaNoduri.add(node);
        nodeNr++;
        repaint();
    }
    void ShowMessage(){
        Aprim.clear();
        Kruskal();
    }

    //se executa atunci cand apelam repaint()
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
        g.drawString("This is my Graph!", 10, 20);

        //deseneaza arcele existente in lista
		/*for(int i=0;i<listaArce.size();i++)
		{
			listaArce.elementAt(i).drawArc(g);
		}*/
        if(orientat==false)
        {
            if(Aprim.isEmpty()) {
                int indexArc = 0;
                for (Arc a : listaArce) {
                    a.drawArc(g);
                    Point mijloc = new Point();
                    mijloc.x = Math.abs(a.getArcEX() + a.getArcSX()) / 2;
                    mijloc.y = Math.abs(a.getArcEY() + a.getArcSY()) / 2;
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Courier", Font.BOLD, 10));
                    g.drawString(String.valueOf(a.getValue()), mijloc.x + 5, mijloc.y + 5);
                    indexArc++;
                }
            }
            else{
                int indexArc = 0;
                for (Arc a : Aprim) {
                    a.drawArc(g);
                    Point mijloc = new Point();
                    mijloc.x = Math.abs(a.getArcEX() + a.getArcSX()) / 2;
                    mijloc.y = Math.abs(a.getArcEY() + a.getArcSY()) / 2;
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Courier", Font.BOLD, 10));
                    g.drawString(String.valueOf(a.getValue()), mijloc.x + 5, mijloc.y + 5);
                    indexArc++;
                }
            }
        }
        else
        {
            for (arrow a : listaSageti)
            {
                a.drawArrow(g);
            }
        }
        //deseneaza arcul curent; cel care e in curs de desenare
        if (pointStart != null)
        {
            g.setColor(Color.RED);
            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
        }
        //deseneaza lista de noduri
        for(int i=0; i<listaNoduri.size(); i++) {


            listaNoduri.elementAt(i).drawNode(g, node_diam, Color.RED);


        }
		/*for (Node nod : listaNoduri)
		{
			nod.drawNode(g, node_diam, node_Diam);
		}*/
    }


    int GetNumber(){

        JPanel main=new JPanel();
        main.setLayout(new FlowLayout());
        JTextField introducere=new JTextField(10);
        main.add(introducere);


        int result = JOptionPane.showConfirmDialog(null, main, "Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return Integer.parseInt(introducere.getText());
        } else {
            return -1; // or any default value
        }
    }

    void Kruskal() {
        listaArce.sort(Comparator.comparingInt(a -> a.getValue()));


        Node end;
        Node start;

        Vector<Integer> parinti = new Vector<>();
        for (int i = 0; i <listaNoduri.size(); i++)
            parinti.add(i+1);

        for(Arc aux:listaArce)
        {
            start=getNodeByCoords(aux.getArcSX(),aux.getArcSY());
            end=getNodeByCoords(aux.getArcEX(),aux.getArcEY());
            int rootStart = findRoot(parinti, start.getNumber());
            int rootEnd = findRoot(parinti, end.getNumber());
            if(rootStart != rootEnd){
                parinti.set(rootEnd-1, rootStart);
                Aprim.add(aux);
            }
        }
        repaint();
    }
    int findRoot(Vector<Integer> parinti, int node) {
        while (parinti.get(node-1) != node) {
            node = parinti.get(node-1);
        }
        return node;
    }

     Node getNodeByCoords(int x,int y){
        for(Node node: listaNoduri)
        {
            if(node.getCoordX()==x && node.getCoordY()==y)
                return node;
        }
        return null;
    }
}


