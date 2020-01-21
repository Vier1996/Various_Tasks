package MPP;

public class mpp {

    static class MP{
        double f(double x){
            return x*x - 4*x * Math.sin(x) + Math.cos(x);
        }

        double findMin(double x0, double h, double e) {
            double x1 = x0, y1 = f(x1), y0 = 0;
            while(Math.abs(h) > e/4) {
                x0 = x1;
                y0 = y1;

                x1 = x0 + h;
                y1 = f(x1);
                if(y1 > y0) {
                    h = -1 * h / 4;
                }
            }
            return x0;
        }
    }

    public static void main(String[] args) {
        MP GS = new MP();
        for(int i = 2; i < 9; i += 3) {
            System.out.println(GS.findMin(-4, i, 0.01));
        }
    }
}
