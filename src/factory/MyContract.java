package factory;

import consumers.Contracts;

import java.util.ArrayList;

public class MyContract implements NewObject {

    /**
     * returneaza un array de contracts
     */
    public void getObject() {
        ArrayList<Contracts> contracts = new ArrayList<>();
    }
}
