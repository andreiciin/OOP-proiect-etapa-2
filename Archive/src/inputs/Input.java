package inputs;

import consumers.Consumers;
import distributors.Distributors;
import monthlyupdate.Update;
import producers.Producers;

import java.util.List;

/**
 * The class contains information about input
 *
 * <p>DO NOT MODIFY
 */
public final class Input {

  /** number of updates */
  private final long numberOfTurns;
  /** List of consumers */
  private final List<Consumers> consumers;
  /** List of distributors */
  private final List<Distributors> distributors;
  /** List of produrcers */
  private final List<Producers> producers;
  /** List of updates */
  private final List<Update> updates;

  public Input() {
    this.numberOfTurns = 0;
    this.consumers = null;
    this.distributors = null;
    this.producers = null;
    this.updates = null;
  }

  public Input(long numberOfTurns, List<Consumers> consumers,
               List<Distributors> distributors, List<Producers> producers,
               List<Update> updates) {
    this.numberOfTurns = numberOfTurns;
    this.consumers = consumers;
    this.distributors = distributors;
    this.producers = producers;
    this.updates = updates;
  }

  public long getNumberOfTurns() {
    return numberOfTurns;
  }

  public List<Consumers> getConsumers() {
    return consumers;
  }

  public List<Distributors> getDistributors() {
    return distributors;
  }

  public List<Producers> getProducers() {
    return producers;
  }

  public List<Update> getUpdates() {
    return updates;
  }
}
