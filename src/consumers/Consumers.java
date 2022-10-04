package consumers;

// in aceasta clasa fac citirea consumatorilor
public final class Consumers {

  private long id;
  private long initialBudget;
  private long monthlyIncome;

  // constructor
  public Consumers(final long id, final long initialBudget, final long monthlyIncome) {
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


}
