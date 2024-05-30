import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Fisier {
    int[][] matrix;
    PrintWriter fw;
    //Neorientat
    public void Write(int[][] mat)
    {
        try {
            fw=new PrintWriter(new File("matrice.txt"));
            fw.print(mat.length);
            fw.println();
            for(int i=0;i<mat.length;i++) {
                for(int j=0;j<mat[i].length;j++)
                {
                    fw.print(matrix[i][j]);
                    fw.print(" ");
                }
                fw.println();
            }
            fw.close();
        }
        catch(Exception e) {System.out.print("Eroare");}
    }

    public int[][] CreareMatriceNeorientata (Vector<CreareLegatura> vect,int nr_nods){
        matrix=new int[nr_nods][nr_nods];
        for(int j=0;j<nr_nods;j++)
        {
            for(int h=0;h<nr_nods;h++)
            {
                matrix[j][h]=0;
            }
        }
        for(CreareLegatura i: vect)
        {
            for(int j=0;j<nr_nods;j++)
            {
                for(int h=0;h<nr_nods;h++)
                {
                    if(i.getX()==j+1 && i.getY()==h+1)
                    {
                        matrix[j][h]=1;
                        matrix[h][j]=1;
                    }
                }
            }
        }
        return matrix;
    }
    //orientat

    public int[][] CreareMatriceOrientata (Vector<CreareLegatura> vect,int nr_nods){
        matrix=new int[nr_nods][nr_nods];
        for(int j=0;j<nr_nods;j++)
        {
            for(int h=0;h<nr_nods;h++)
            {
                matrix[j][h]=0;
            }
        }
        for(CreareLegatura i: vect)
        {
            for(int j=0;j<nr_nods;j++)
            {
                for(int h=0;h<nr_nods;h++)
                {
                    if(i.getX()==j+1 && i.getY()==h+1)
                    {
                        matrix[j][h]=1;
                    }
                }
            }
        }
        return matrix;
    }
    public int[][]readGraph() {
         int[][] mat=new int[0][0];
         int rows, cols;
        try {

            File file = new File("matrice.txt");
            Scanner scanner = new Scanner(file);

            // Read the first line to determine the dimensions of the matrix
            String[] dimensions = scanner.nextLine().split(" ");
            rows = Integer.parseInt(dimensions[0]);
            cols = Integer.parseInt(dimensions[0]);
            int i = 0;

            mat = new int[rows][cols];
            while (scanner.hasNextLine()) {
                String[] rowValues = scanner.nextLine().split(" ");
                for (int j = 0; j < cols; j++) {
                    mat[i][j] = Integer.parseInt(rowValues[j]);
                }
                i++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            //System.out.print("Exception");
        }
        return mat;
    }
}
