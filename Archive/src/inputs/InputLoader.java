package inputs;

import consumers.Consumers;
import distributors.Distributors;
import monthlyupdate.DistributorChanges;
import monthlyupdate.ProducerChanges;
import monthlyupdate.Update;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import producers.Producers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class reads and parses the data from the tests
 *
 * <p>DO NOT MODIFY
 */
public final class InputLoader {
  /** The path to the input file */
  private final String inputPath;

  public InputLoader(final String inputPath) {
    this.inputPath = inputPath;
  }

  public String getInputPath() {
    return inputPath;
  }

  /**
   * The method reads the database
   *
   * @return an Input object
   */
  public Input readData() {
    JSONParser jsonParser = new JSONParser();
    long numberOfTurns = 0;
    List<Consumers> consumers = new ArrayList<>();
    List<Distributors> distributors = new ArrayList<>();
    List<Producers> producers = new ArrayList<>();
    List<Update> updates = new ArrayList<>();

    try {
      // Parsing the contents of the JSON file
      JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));

      numberOfTurns = Long.parseLong(jsonObject.get("numberOfTurns").toString());

      JSONObject jsonInitialData = (JSONObject) jsonObject.get("initialData");
      JSONArray jsonConsumers = (JSONArray) jsonInitialData.get("consumers");
      JSONArray jsonDistributors = (JSONArray) jsonInitialData.get("distributors");
      JSONArray jsonProducers = (JSONArray) jsonInitialData.get("producers");
      JSONArray jsonUpdates = (JSONArray) jsonObject.get("monthlyUpdates");

      if (jsonConsumers != null) {
        for (Object jsonConsumer : jsonConsumers) {
          consumers.add(
              new Consumers(
                  (long) ((JSONObject) jsonConsumer).get("id"),
                  (long) ((JSONObject) jsonConsumer).get("initialBudget"),
                  (long) ((JSONObject) jsonConsumer).get("monthlyIncome")));
        }
      } else {
        System.out.println("NU EXISTA CONSUMATORI");
      }

      if (jsonDistributors != null) {
        for (Object jsonDistributor : jsonDistributors) {
          distributors.add(
              new Distributors(
                  (long) ((JSONObject) jsonDistributor).get("id"),
                  (long) ((JSONObject) jsonDistributor).get("contractLength"),
                  (long) ((JSONObject) jsonDistributor).get("initialBudget"),
                  (long) ((JSONObject) jsonDistributor).get("initialInfrastructureCost"),
                  (long) ((JSONObject) jsonDistributor).get("energyNeededKW"),
                  (String) ((JSONObject) jsonDistributor).get("producerStrategy")));
        }
      } else {
        System.out.println("NU EXISTA DISTRIBUITORI");
      }

      if (jsonProducers != null) {
        for (Object jsonProducer : jsonProducers) {
          producers.add(
                new Producers(
                        (long) ((JSONObject) jsonProducer).get("id"),
                        (String) ((JSONObject) jsonProducer).get("energyType"),
                        (long) ((JSONObject) jsonProducer).get("maxDistributors"),
                        (double) ((JSONObject) jsonProducer).get("priceKW"),
                        (long) ((JSONObject) jsonProducer).get("energyPerDistributor")));
        }
      } else {
        System.out.println("NU EXISTA PRODUCATORI");
      }

      if (jsonUpdates != null) {
        for (Object jsonIterator : jsonUpdates) {

          List<Consumers> newConsumers = new ArrayList<>();
          List<DistributorChanges> distributorChanges = new ArrayList<>();
          List<ProducerChanges> producerChanges = new ArrayList<>();

          if (((JSONObject) jsonIterator).get("newConsumers") != null) {
            for (Object iterator : (JSONArray) ((JSONObject) jsonIterator).get("newConsumers")) {
              newConsumers.add(
                  new Consumers(
                      (long) ((JSONObject) iterator).get("id"),
                      (long) ((JSONObject) iterator).get("initialBudget"),
                      (long) ((JSONObject) iterator).get("monthlyIncome")));
            }
          } else {
            newConsumers = null;
          }

          if (((JSONObject) jsonIterator).get("distributorChanges") != null) {
            for (Object iterator : (JSONArray) ((JSONObject) jsonIterator).
                    get("distributorChanges")) {
              distributorChanges.add(
                  new DistributorChanges(
                      (long) ((JSONObject) iterator).get("id"),
                      (long) ((JSONObject) iterator).get("infrastructureCost")));
            }
          } else {
            distributorChanges = null;
          }

          if (((JSONObject) jsonIterator).get("producerChanges") != null) {
            for (Object iterator : (JSONArray) ((JSONObject) jsonIterator).get("producerChanges")) {
              producerChanges.add(
                      new ProducerChanges(
                              (long) ((JSONObject) iterator).get("id"),
                              (long) ((JSONObject) iterator).get("energyPerDistributor")));
            }
          } else {
            producerChanges = null;
          }

          updates.add(new Update(newConsumers, distributorChanges, producerChanges));
        }
      } else {
        System.out.println("NU EXISTA UPDATEURI");
      }

      if (jsonConsumers == null) {
        consumers = null;
      }

      if (jsonDistributors == null) {
        distributors = null;
      }

      if (jsonUpdates == null) {
        updates = null;
      }

    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }

    return new Input(numberOfTurns, consumers, distributors, producers, updates);
  }
}
