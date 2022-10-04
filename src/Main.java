import inputs.Input;
import inputs.InputLoader;
import simulation.Simulation;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {

        // citire, in "input" vom avea toate obiectele din jsonuri
        InputLoader inputLoader = new InputLoader(args[0]);
        Input input = inputLoader.readData();

        // simularea jocului
        Simulation simulation = new Simulation(args[1], input);
    }
}
