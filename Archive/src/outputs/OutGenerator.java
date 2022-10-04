package outputs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import consumers.OutConsumers;
import distributors.OutDistributors;
import producers.OutProducers;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class OutGenerator {

  public OutGenerator(
          final String fileName,
          final ArrayList<OutConsumers> outConsumers,
          final ArrayList<OutDistributors> outDistributors,
          final ArrayList<OutProducers> outProducers) {

    final ObjectMapper mapper = new ObjectMapper();

    // cu ajutorul maparii dau titlul in json "consumers" pt lista de consumatori,
    // analog pt distribuitori
    final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
    map.put("consumers", outConsumers);
    map.put("distributors", outDistributors);
    map.put("energyProducers", outProducers);

    // creez stringul de output
    String json = null;
    try {
      json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    // scriu fisierul de output stringul JSON
    try (FileWriter file = new FileWriter(fileName)) {
      file.write(json);
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
