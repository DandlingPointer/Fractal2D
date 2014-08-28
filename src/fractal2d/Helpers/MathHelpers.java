package fractal2d.Helpers;

/**
 * Created by lenni on 28.08.14.
 */
public class MathHelpers {
    public static double mapValue(double val, double minA, double maxA, double minB, double maxB) throws ArithmeticException {
        //X = val, Y = return, A =
        //Y = (X-A)/(B-A) * (D-C) + C => http://stackoverflow.com/questions/345187/math-mapping-numbers
        return (val - minA) / (maxA - minA) * (maxB - minB) + minB;
    }
}
