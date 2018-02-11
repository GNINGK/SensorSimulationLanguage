package main.java.dsl.kernel.definition;

/**
 *
 * @author Maxime
 */
public class Interval {

    private double min;
    private double max;
    private String conditionMin;
    private String conditionMax;

    /**
     * *
     *
     * @param min Nombre qui peut aussi etre egale a -infinie ou etre NaN
     * @param conditionMin
     * @param conditionMax
     * @param max Nombre qui peut aussi etre egale a infinie ou etre NaN
     */
    public Interval(double min, double max) {
        if (min <= max) {
            this.min = min;

            this.max = max;
        } else {
            throw new IllegalArgumentException("La borne max doit être >= a la borne min");
        }
    }

    public boolean intersects(Interval interval) {

        if (this.min > interval.min && this.min < interval.getMax()) {
            return true;
        } else if (interval.getMin() > this.min && interval.getMin() < this.getMax()) {
            return true;
        }
        return false;
    }

    /* Version ou min et max peuvent etre nulle
     public boolean intersects(Interval interval) {
     if (getMin() != Double.NaN && getMax() != Double.NaN) {
     if (interval.getMin() != Double.NaN && interval.getMax() != Double.NaN) {
     if (this.getMax() < interval.getMin()) {
     return false;
     }
     if (interval.getMax() < this.getMin()) {
     return false;
     }
     } else {
     if (interval.getMin() == Double.NaN) {
     if (interval.getMax() < this.getMin()) {
     return false;
     }
     } else {
     if (this.getMax() < interval.getMin()) {
     return false;
     }
     }
     }
     } else {
     if (interval.getMin() != Double.NaN && interval.getMax() != Double.NaN) {
     if (getMin() == Double.NaN) {
     if (this.getMax() < interval.getMin()) {
     return false;
     }
     } else {
     if (interval.getMax() < this.getMin()) {
     return false;
     }
     }
     } else {
                
     }
     }

     return true;
     }*/
    public boolean contains(double x) {
        if (getMin() <= x && x <= getMax()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * @return the conditionMin
     */
    public String getConditionMin() {
        return conditionMin;
    }

    /**
     * @return the conditionMax
     */
    public String getConditionMax() {
        return conditionMax;
    }

    public String toString() {
        return "" + getMin() + " " + getConditionMin() + " X " + getConditionMax() + " " + getMax();
    }
}
