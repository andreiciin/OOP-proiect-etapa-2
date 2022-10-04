package consumers;

public final class OutConsumers {

  private long id;
  public boolean isBankrupt;
  private long budget;

  public OutConsumers(final long id, final boolean isBankrupt, final long budget) {
    this.id = id;
    this.isBankrupt = isBankrupt;
    this.budget = budget;
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
}
