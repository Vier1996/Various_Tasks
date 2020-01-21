package MNK;
import java.util.ArrayList;
import Gauss.gauss;

public class mnk {

    static double roundD(double Q){
        int O = (int)Math.round(Q * 1000);
        Q = (double)O / 1000;
        return Q;
    }

    static ArrayList<Double> Rx(double A, double B, double M){
        ArrayList<Double> Out = new ArrayList<>();
        double Q;
        for(int i = 0; i < 11; i++){
            Q = A + (i * (B - A) / (M - 1));
            Out.add(Q);
        }
        return Out;
    }

    static ArrayList<Double> Ry(ArrayList<Double> X){
        ArrayList<Double> Out = new ArrayList<>();
        double Q;
        for(int i = 0; i < X.size(); i++){
            Q = X.get(i) - 5 * (Math.pow(Math.sin(X.get(i)), 2));
            Out.add(roundD(Q));
        }
        return Out;
    }

    static void MethNaimKvad(double A, double B, double M, double N){
        ArrayList<Double> PoolX, PoolY;
        double[][] Mtrx = new double[4][5];
        PoolX = Rx(A, B, M);
        PoolY = Ry(PoolX);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                Mtrx[i][j] = 0;
            }
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 11; j++){
                double a = -1, b =-1 * PoolX.get(j), c = -1 * Math.pow(PoolX.get(j), 2), d = -1 * Math.pow(PoolX.get(j), 3);
                double [] m = {a, b, c, d};
                Mtrx[i][0] += 2 * m[i] * a;
                Mtrx[i][1] += 2 * m[i] * b;
                Mtrx[i][2] += 2 * m[i] * c;
                Mtrx[i][3] += 2 * m[i] * d;
                Mtrx[i][4] += 2 * m[i] * PoolY.get(j);
            }
        }

        System.out.println();
        gauss.MoG(Mtrx);
    }


    public static void main(String[] args) {
        MethNaimKvad(1, 4, 11, 4);
    }
}
