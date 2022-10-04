package simulation;

import producers.MonthlyStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public final class MyProducers extends Observable {

    private long id;
    private String energyType;
    private long maxDistributors;
    private double priceKW;
    private long energyPerDistributor;
    private long currentDistribs;
    private List<Integer> distribIDs = new ArrayList<>(Collections.nCopies(1000, 0));
    private long nrDistribs;
    private ArrayList<MonthlyStats> monthlyStats = new ArrayList<>();
    private boolean wasUpdated = false;

    /**
     * adaug distribuitori in lista
     */
    // la update-ul producerilor
    public void addDistribs(int i) {
        // notifiy observers
        this.distribIDs.set(i, 1);
        setChanged();
        notifyObservers(i);
    }

    /** pentru Observable */
    public void updateStats(MonthlyStats stats) {
        this.monthlyStats.add(stats);
    }

    // constructor
    public MyProducers(final long id, final String energyType, final long maxDistributors,
                     final double priceKW, final long energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final String energyType) {
        this.energyType = energyType;
    }

    public long getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final long maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(final double priceKW) {
        this.priceKW = priceKW;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor; }

    public long getCurrentDistribs() {
        return currentDistribs;
    }

    public void setCurrentDistribs(final long currentDistribs) {
        this.currentDistribs = currentDistribs;
    }

    public long getNrDistribs() {
        return nrDistribs;
    }
    /**
     * incrementeaza numarul de distribuitori ai producatorului
     */
    public void setNrDistribs() {
        this.nrDistribs += 1;
    }

    public List<Integer> getDistribIDs() {
        return distribIDs;
    }

    public void setDistribIDs(List<Integer> distribIDs) {
        this.distribIDs = distribIDs;
    }

    public void setNrDistribs(long nrDistribs) {
        this.nrDistribs = nrDistribs;
    }

    public ArrayList<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(ArrayList<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public boolean isWasUpdated() {
        return wasUpdated;
    }

    public void setWasUpdated(boolean wasUpdated) {
        this.wasUpdated = wasUpdated;
    }
}
