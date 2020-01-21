package Gauss;

import java.util.ArrayList;

public class gauss {

    public static void ShowM(double[][] Mtrx) {
        String out;
        for(int i = 0; i < Mtrx.length; i++) {
            out = " ";
            for(int j = 0; j < Mtrx[i].length; j++) {
                out += Double.toString(Mtrx[i][j]) + " ";
            }
            System.out.println(out);
        }
    }

    public static double[][] Directpass(double[][] Mtrx) {
        double koef;
        for(int k = 0; k < Mtrx.length - 1; k++) {
            for(int i = 1; i < Mtrx.length - k; i++) {
                if(Mtrx[i+k][k] != 0){
                    koef = Math.abs(Mtrx[k+i][k] / Mtrx[k][k]);
                    if(Mtrx[k][k] < 0 && Mtrx[k + i][k] < 0 || Mtrx[k][k] > 0 && Mtrx[k + i][k] > 0)
                        koef *= -1;
                    for(int j = 0; j < Mtrx[i].length; j++) {
                        Mtrx[k + i][j] += Mtrx[k][j] * koef;
                    }
                }
            }
        }

        return Mtrx;
    }

    public static ArrayList<Double> Reversepass(double[][] Mtrx) {
        int i = Mtrx.length - 2;
        double z = Mtrx[Mtrx.length - 1][Mtrx.length] / Mtrx[Mtrx.length - 1][Mtrx.length - 1];
        ArrayList<Double> Answer = new ArrayList<>(), Ransw = new ArrayList<>();
        Answer.add(z);
        while (i > -1) {
            for(int k = 0; k < Mtrx[i].length; k++) {
                if(Mtrx[i][Mtrx[i].length - 2 - k] == Mtrx[i][i]) {
                    Answer.add(Mtrx[i][Mtrx[i].length - 1] / Mtrx[i][i]);
                    break;
                }
                else {
                    Mtrx[i][Mtrx[i].length - 1] -= Mtrx[i][Mtrx[i].length - 2 - k] * Answer.get(k);
                }
            }
            i--;
        }

        for(int Y = Answer.size() - 1; Y >= 0; Y--) {
            Ransw.add(Answer.get(Y));
        }
        Answer.clear();
        return Ransw;
    }

    public static void MoG(double[][] Mtrx) {
        ArrayList<Double> TT = new ArrayList<>();
        ShowM(Mtrx);
        System.out.println("-//-");
        Mtrx = Directpass(Mtrx);
        ShowM(Mtrx);
        System.out.println("-//-");
        TT = Reversepass(Mtrx);
        System.out.println("Выходная функция: " + TT.get(0).toString() + " + " + TT.get(1).toString() + "x " + TT.get(2).toString() + "x^2 + " + TT.get(3).toString() + "x^3");
    }


    public static void main(String[] args) {
    }
}
