package monthlyupdate;

public final class ProducerChanges {

    private long id;
    private long energyPerDistributor;

    // constructor
    public ProducerChanges(long id, long energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    // getter and setter
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
}
