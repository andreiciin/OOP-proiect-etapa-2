package distributors;

import consumers.Contracts;

import java.util.ArrayList;

public final class OutDistributors {

  private long id;
  private long energyNeededKW;
  private long contractCost;
  private long budget;
  private String producerStrategy;
  public boolean isBankrupt;
  private ArrayList<Contracts> contracts;

  // constructor
  public OutDistributors(final long id, final long energyNeededKW,
                         final long contractCost, final long budget,
                         final String producerStrategy, final boolean isBankrupt,
                         final ArrayList<Contracts> contracts) {
    this.id = id;
    this.energyNeededKW = energyNeededKW;
    this.contractCost = contractCost;
    this.budget = budget;
    this.producerStrategy = producerStrategy;
    this.isBankrupt = isBankrupt;
    this.contracts = contracts;
  }

  // getters and setters
  public ArrayList<Contracts> getContracts() {
    return contracts;
  }

  public void setContracts(final ArrayList<Contracts> contracts) {
    this.contracts = contracts;
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public long getBudget() {
    return budget;
  }

  public void setBudget(final long budget) {
    this.budget = budget;
  }

  public long getEnergyNeededKW() {
    return energyNeededKW;
  }

  public void setEnergyNeededKW(final long energyNeededKW) {
    this.energyNeededKW = energyNeededKW;
  }

  public long getContractCost() {
    return contractCost;
  }

  public void setContractCost(final long contractCost) {
    this.contractCost = contractCost;
  }

  public String getProducerStrategy() {
    return producerStrategy;
  }

  public void setProducerStrategy(final String producerStrategy) {
    this.producerStrategy = producerStrategy;
  }
}
