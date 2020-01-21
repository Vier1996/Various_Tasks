package MethodOfSimplefyIterations;

import java.util.ArrayList;

public class MofSimIter {
    static float F(double x) {
        return (float)(x - 5 * Math.pow(Math.sin(x), 2) - 5);
    }

    static float MI(float A, double E) {
        float x0 = A, x1 = 6;
        float de = 5;
        int it = 0;
        while (Math.abs(x0 - x1) > E) {
            x1 = F(x0);
            it++;
            x0 = x1;
        }
        return x1;
    }

    public static void main(String[] args) {
        ArrayList<Float> M = new ArrayList<>();
        for(int i = 3; i < 10; i++) {
            M.add(MI(i, 0.001));
        }
        System.out.println(M);
    }
}
