import java.awt.event.ActionListener;
import javax.swing.JButton;
//import javafx.event.setOnAction;
//import javafx.event.ActionEvent;

public class button extends MyPanel implements ActionListener{

    public button(){
        super();
        JButton buton=new JButton("Switch");
        buton.setBounds(50, 25, 15, 15);
        buton.addActionListener(this);
        add(buton);
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(orientat==false)
        {
            orientat=true;
        }
        else {
            orientat=false;
        }
        listaNoduri.clear();
        if(!listaArce.isEmpty())
            listaArce.clear();
        if(!listaSageti.isEmpty())
            listaSageti.clear();
        nodeNr=1;
        if(!vec.isEmpty())
            vec.clear();
        repaint();
    }
}