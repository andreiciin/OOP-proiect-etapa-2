package distributors;

// in aceasta clasa fac citirea distribuitorilor
public final class Distributors {

  private long id;
  private long contractLength;
  private long initialBudget;
  private long initialInfrastructureCost;
  private long energyNeededKW;
  private String producerStrategy;

  // constructor
  public Distributors(
      final long id,
      final long contractLength,
      final long initialBudget,
      final long initialInfrastructureCost,
      final long energyNeededKW,
      final String producerStrategy) {
    this.id = id;
    this.contractLength = contractLength;
    this.initialBudget = initialBudget;
    this.initialInfrastructureCost = initialInfrastructureCost;
    this.energyNeededKW = energyNeededKW;
    this.producerStrategy = producerStrategy;
  }

  // getters and setters
  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public long getContractLength() {
    return contractLength;
  }

  public void setContractLength(final long contractLength) {
    this.contractLength = contractLength;
  }

  public long getInitialBudget() {
    return initialBudget;
  }

  public void setInitialBudget(final long initialBudget) {
    this.initialBudget = initialBudget;
  }

  public long getInitialInfrastructureCost() {
    return initialInfrastructureCost;
  }

  public void setInitialInfrastructureCost(final long initialInfrastructureCost) {
    this.initialInfrastructureCost = initialInfrastructureCost;
  }

  public long getEnergyNeededKW() {
    return energyNeededKW;
  }

  public void setEnergyNeededKW(final long energyNeededKW) {
    this.energyNeededKW = energyNeededKW;
  }

  public String getProducerStrategy() {
    return producerStrategy;
  }

  public void setProducerStrategy(final String producerStrategy) {
    this.producerStrategy = producerStrategy;
  }
}
