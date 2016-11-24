package si.poponline.play.module.Statistics;

/**
 * Created by samek on 24/11/2016.
 */
public interface StatisticsInterface {
    /**
     * Add a value in the Statistics object.
     *
     * @param value value that you want to accumulate.
     */
    void accumulate(long value);

    /**
     * Clear the Statistics instance (equivalent to recreate a new one).
     */
    void clear();

    /**
     * Return the variance of the inserted elements.
     */
    double variance();

    /**
     * Return the standard deviation of the inserted elements.
     */
    double standardDeviation();

    /**
     * Return the mean of the inserted elements.
     */
    double mean();

    /**
     * Return the min of the inserted elements.
     */
    long min();

    /**
     * Return the max of the inserted elements.
     */
    long max();

    /**
     * Return the range of the inserted elements.
     */
    long range();

    /**
     * Return the sum of the inserted elements.
     */
    long sum();

    /**
     * Return the number of the inserted elements.
     */
    long populationSize();
}
