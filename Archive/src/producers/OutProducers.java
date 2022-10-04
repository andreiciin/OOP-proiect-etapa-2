package producers;

import java.util.ArrayList;

public class OutProducers {

    private long id;
    private long maxDistributors;
    private double priceKW;
    private String energyType;
    private long energyPerDistributor;
    private ArrayList<MonthlyStats> monthlyStats;

    public OutProducers(final long id, final long maxDistributors,
                        final double priceKW, String energyType,
                        final long energyPerDistributor,
                        final ArrayList<MonthlyStats> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public long getId() {
        return id;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public void setId(final long id) {
        this.id = id;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public long getMaxDistributors() {
        return maxDistributors;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public void setMaxDistributors(final long maxDistributors) {
        this.maxDistributors = maxDistributors;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public double getPriceKW() {
        return priceKW;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public void setPriceKW(final double priceKW) {
        this.priceKW = priceKW;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public String getEnergyType() {
        return energyType;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public void setEnergyType(final String energyType) {
        this.energyType = energyType;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public void setEnergyPerDistributor(final long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public ArrayList<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }
    /** checkstyle - ul ma pune sa pun javadoc */
    public void setMonthlyStats(final ArrayList<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
}
