package MOA;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MethodOfAvarange {
    static double Func(double x){
        return x - 5 * Math.pow(Math.sin(x), 2);
    }
    static double PFunc(double x){
        return (Math.pow(x, 2)/2) - (5*x/2) + (5 * Math.sin(2*x) / 4);
    }

    static void MethodOfAv(double A, double B, double Step){
        DecimalFormat df = new DecimalFormat("#.####");
        double H = (B - A) / Step, h, answ, Summ = 0;
        h = H / 2;
        ArrayList<Double> Pool = new ArrayList<>();
        while (A < B){
            answ = Func(A + h);
            System.out.println("X " + (A + h) + " | F(x) " + answ + " | Step: " + h);
            Pool.add(answ);
            A += H;
        }
        for(int i = 0; i < Pool.size(); i++){
            Summ += Pool.get(i);
        }
        System.out.println("Приближенное значение интегралла: " + df.format(H * Summ));
    }
    public static void main(String[] args) {
        MethodOfAv(1, 4, 50);
    }
}
