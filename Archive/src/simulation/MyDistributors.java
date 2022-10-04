package simulation;

import consumers.Contracts;

import java.util.*;

public final class MyDistributors implements Observer {

  private long id;
  private long contractLength;
  private long initialLength;
  private long initialBudget;
  private long initialInfrastructureCost;
  private long energyNeededKw;
  private String producerStrategy;
  private long initialProductionCost;
  private long budget;
  private boolean isBankrupt = false;
  private ArrayList<Contracts> contracts;
  private long contractCost;
  private long numberNoGain = 0;
  private long numberConsumers = 0;
  private long profit;
  private long monthlyCost;
  private long previousContracts;
  private List<Integer> prodIDs = new ArrayList<>(Collections.nCopies(1000, 0));
  private boolean wasUpdated = false;

  @Override
  public void update(Observable o, Object arg) {
    // actualizez noii producatori ai distribuitorului
    this.prodIDs.set((int) arg, 1);
  }

  // constructor
  public MyDistributors(
      final long id,
      final long contractLength,
      final long initialBudget,
      final long initialInfrastructureCost,
      final long energyNeededKw,
      final String producerStrategy) {
    this.id = id;
    this.contractLength = contractLength;
    this.initialBudget = initialBudget;
    this.initialInfrastructureCost = initialInfrastructureCost;
    this.energyNeededKw = energyNeededKw;
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
  /**
   * decrementeaza numarul de contracte
   */
  public void decContractLength() {
    this.contractLength -= 1;
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

  public long getInitialProductionCost() {
    return initialProductionCost;
  }

  public void setInitialProductionCost(final long initialProductionCost) {
    this.initialProductionCost = initialProductionCost;
  }

  public long getBudget() {
    return budget;
  }

  public void setBudget(final long budget) {
    this.budget = budget;
  }

  public boolean isBankrupt() {
    return isBankrupt;
  }

  public void setBankrupt(final boolean bankrupt) {
    isBankrupt = bankrupt;
  }

  public ArrayList<Contracts> getContracts() {
    return contracts;
  }

  public void setContracts(final ArrayList<Contracts> contracts) {
    this.contracts = contracts;
  }

  public long getContractCost() {
    return contractCost;
  }

  public void setContractCost(final long contractCost) {
    this.contractCost = contractCost;
  }

  public long getNumberConsumers() {
    return numberConsumers;
  }
  /**
   * incrementeaza numarul de consumatori
   */
  public void setNumberConsumers() {
    this.numberConsumers += 1;
  }
  /**
   * reseteaza numarul de consumatori
   */
  public void resetNumberConsumers() {
    this.numberConsumers = 0;
  }
  /**
   * decrementeaza numarul de consumatori
   */
  public void decNumberConsumers() {
    this.numberConsumers -= 1;
  }

  public long getProfit() {
    return profit;
  }

  public void setProfit(final long profit) {
    this.profit = profit;
  }

  public long getMonthlyCost() {
    return monthlyCost;
  }

  public void setMonthlyCost(final long monthlyCost) {
    this.monthlyCost = monthlyCost;
  }

  public long getPreviousContracts() {
    return previousContracts;
  }

  public void setPreviousContracts(final long previousContracts) {
    this.previousContracts = previousContracts;
  }

  public long getNumberNoGain() {
    return numberNoGain;
  }
  /**
   * incrementeaza numarul de consumatori care nu pot plati
   */
  public void setNumberNoGain() {
    this.numberNoGain += 1;
  }
  /**
   * decrementeaza numarul de consumatori care nu pot plati
   */
  public void decNumberNoGain() {
    this.numberNoGain -= 1;
  }

  public long getInitialLength() {
    return initialLength;
  }

  public void setInitialLength(final long initialLength) {
    this.initialLength = initialLength;
  }

  public long getEnergyNeededKw() {
    return energyNeededKw;
  }

  public void setEnergyNeededKw(final long energyNeededKw) {
    this.energyNeededKw = energyNeededKw;
  }

  public String getProducerStrategy() {
    return producerStrategy;
  }

  public void setProducerStrategy(final String producerStrategy) {
    this.producerStrategy = producerStrategy;
  }

  public boolean isWasUpdated() {
    return wasUpdated;
  }

  public void setWasUpdated(final boolean wasUpdated) {
    this.wasUpdated = wasUpdated;
  }
}
