package simulation;

public final class MyConsumers {

  private long id;
  private long initialBudget;
  private long monthlyIncome;
  private boolean isBankrupt = false;
  private long budget;
  private long contractCost;
  private long monthsLeft = 0;
  private long distributorsId;
  private long debt = 0;
  private boolean isPaying = true;
  private boolean isOk = true;
  private boolean wasChosen = false;

  // constructor
  public MyConsumers(final long id, final long initialBudget, final long monthlyIncome) {
    this.id = id;
    this.initialBudget = initialBudget;
    this.monthlyIncome = monthlyIncome;
  }

  // getters and setters
  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public long getInitialBudget() {
    return initialBudget;
  }

  public void setInitialBudget(final long initialBudget) {
    this.initialBudget = initialBudget;
  }

  public long getMonthlyIncome() {
    return monthlyIncome;
  }

  public void setMonthlyIncome(final long monthlyIncome) {
    this.monthlyIncome = monthlyIncome;
  }

  public boolean isBankrupt() {
    return isBankrupt;
  }

  public void setBankrupt(final boolean bankrupt) {
    isBankrupt = bankrupt;
  }

  public long getBudget() {
    return budget;
  }

  public void setBudget(final long budget) {
    this.budget = budget;
  }

  public long getContractCost() {
    return contractCost;
  }

  public void setContractCost(final long contractCost) {
    this.contractCost = contractCost;
  }

  public long getMonthsLeft() {
    return monthsLeft;
  }

  public void setMonthsLeft(final long monthsLeft) {
    this.monthsLeft = monthsLeft;
  }
  /**
   * decrementeaza numarul luni ramase
   */
  public void decMonthsLeft() {
    this.monthsLeft -= 1;
  }

  public long getDistributorsId() {
    return distributorsId;
  }

  public void setDistributorsId(final long distributorsId) {
    this.distributorsId = distributorsId;
  }

  public long getDebt() {
    return debt;
  }

  public void setDebt(final long debt) {
    this.debt = debt;
  }

  public boolean isPaying() {
    return isPaying;
  }

  public void setPaying(final boolean paying) {
    isPaying = paying;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(final boolean ok) {
    isOk = ok;
  }

  public boolean isWasChosen() {
    return wasChosen;
  }

  public void setWasChosen(final boolean wasChosen) {
    this.wasChosen = wasChosen;
  }
}
