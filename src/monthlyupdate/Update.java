package monthlyupdate;

import consumers.Consumers;

import java.util.ArrayList;
import java.util.List;

public final class Update {

  private List<Consumers> newConsumers = new ArrayList<>();
  private List<DistributorChanges> distributorChanges = new ArrayList<>();
  private List<ProducerChanges> producerChanges = new ArrayList<>();

  public Update(final List<Consumers> newConsumers,
                final List<DistributorChanges> distributorChanges,
                final List<ProducerChanges> producerChanges) {
    this.newConsumers = newConsumers;
    this.distributorChanges = distributorChanges;
    this.producerChanges = producerChanges;
  }

  // getters and setters
  public List<Consumers> getNewConsumers() {
    return newConsumers;
  }

  public void setNewConsumers(final List<Consumers> newConsumers) {
    this.newConsumers = newConsumers;
  }

  public List<DistributorChanges> getDistributorChanges() {
    return distributorChanges;
  }

  public void setDistributorChanges(final List<DistributorChanges> distributorChanges) {
    this.distributorChanges = distributorChanges;
  }

  public List<ProducerChanges> getProducerChanges() {
    return producerChanges;
  }

  public void setProducerChanges(final List<ProducerChanges> producerChanges) {
    this.producerChanges = producerChanges;
  }
}
