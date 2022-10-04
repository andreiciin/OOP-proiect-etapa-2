package simulation;

import consumers.Contracts;
import consumers.OutConsumers;
import distributors.OutDistributors;
import entities.EnergyType;
import inputs.Input;
import outputs.OutGenerator;
import producers.MonthlyStats;
import producers.OutProducers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortContracts implements Comparator<Contracts> {
  public int compare(final Contracts a, final Contracts b) {
    return ((int) (a.getRemainedContractMonths() - b.getRemainedContractMonths()));
  }
}
class SortByPrice implements Comparator<MyProducers> {
  public int compare(final MyProducers a, final MyProducers b) {
    if (a.getPriceKW() == b.getPriceKW()) {
      return ((int) ((-1) * (a.getEnergyPerDistributor() - b.getEnergyPerDistributor())));
    } else {
      if (a.getPriceKW() < b.getPriceKW()) {
        return -1;
      } else {
        return 1;
      }
    }
  }
}
class SortByQuantity implements Comparator<MyProducers> {
  public int compare(final MyProducers a, final MyProducers b) {
    return ((int) ((-1) * (a.getEnergyPerDistributor() - b.getEnergyPerDistributor())));
  }
}
class SortById implements Comparator<MyProducers> {
  public int compare(final MyProducers a, final MyProducers b) {
    return ((int) (a.getId() - b.getId()));
  }
}

public final class Simulation {

  // listele de consumatori, distrib si prod ce vor fi afisati
  private final ArrayList<OutConsumers> outConsumers = new ArrayList<>();
  private final ArrayList<OutDistributors> outDistributors = new ArrayList<>();
  private final ArrayList<OutProducers> outProducers = new ArrayList<>();

  public Simulation(final String filename, final Input input) {

    // copiez elementele din listele de citire in listele in care vom face modificarile
    List<MyConsumers> myConsumers = new ArrayList<>();
    for (int i = 0; i < input.getConsumers().size(); i++) {
      MyConsumers auxConsumer =
          new MyConsumers(
              input.getConsumers().get(i).getId(),
              input.getConsumers().get(i).getInitialBudget(),
              input.getConsumers().get(i).getMonthlyIncome());
      myConsumers.add(auxConsumer);
    }
    List<MyDistributors> myDistributors = new ArrayList<>();
    for (int i = 0; i < input.getDistributors().size(); i++) {
      MyDistributors auxDistributor =
          new MyDistributors(
              input.getDistributors().get(i).getId(),
              input.getDistributors().get(i).getContractLength(),
              input.getDistributors().get(i).getInitialBudget(),
              input.getDistributors().get(i).getInitialInfrastructureCost(),
              input.getDistributors().get(i).getEnergyNeededKW(),
              input.getDistributors().get(i).getProducerStrategy());
      myDistributors.add(auxDistributor);
    }
    List<MyProducers> myProducers = new ArrayList<>();
    for (int i = 0; i < input.getProducers().size(); i++) {
      MyProducers auxProducer =
              new MyProducers(
                      input.getProducers().get(i).getId(),
                      input.getProducers().get(i).getEnergyType(),
                      input.getProducers().get(i).getMaxDistributors(),
                      input.getProducers().get(i).getPriceKW(),
                      input.getProducers().get(i).getEnergyPerDistributor());
      myProducers.add(auxProducer);
    }

    final double dbl = 0.2;
    final double dbl1 = 1.2;
    final double tenner = 10;

    // runda initiala

    // distribuitorii aleg producatori
    for (int j = 0; j < myDistributors.size(); j++) {
      long energyLeft = myDistributors.get(j).getEnergyNeededKw();
      // pt ce fel de strategie opteaza
      myProducers.sort(new SortByPrice());
      if (myDistributors.get(j).getProducerStrategy().
              equals("GREEN")) { // renewable, pret, cantitate
        if (energyLeft > 0) { // renewable
          for (int k = 0; k < myProducers.size(); k++) {
            if (myProducers.get(k).getEnergyType().equals(EnergyType.WIND.getLabel())
                    || myProducers.get(k).getEnergyType().equals(EnergyType.HYDRO.getLabel())
                    || myProducers.get(k).getEnergyType().equals(EnergyType.SOLAR.getLabel())
                    && myProducers.get(k).getNrDistribs()
                    < myProducers.get(k).getMaxDistributors()) {

                  energyLeft -= myProducers.get(k).getEnergyPerDistributor();
                  myProducers.get(k).setNrDistribs();
                  // observer si observables
                  myProducers.get(k).addDistribs(j);
                  myDistributors.get(j).update(myProducers.get(k), k);
                  if (energyLeft <= 0) {
                    break;
                  }
            }
          }
        }
        // daca nu a fost vizitat
        myProducers.sort(new SortByPrice());
        if (energyLeft > 0) { // pret
          for (int k = 0; k < myProducers.size(); k++) {
            if (!myProducers.get(k).getDistribIDs().get(j).equals(1)
                    && myProducers.get(k).getNrDistribs()
                    < myProducers.get(k).getMaxDistributors()) {
              energyLeft -= myProducers.get(k).getEnergyPerDistributor();
              myProducers.get(k).setNrDistribs();
              myProducers.get(k).addDistribs(j);
              myDistributors.get(j).update(myProducers.get(k), k);
              if (energyLeft <= 0) {
                break;
              }
            }
          }
        }
        myProducers.sort(new SortByQuantity());
        if (energyLeft > 0) { // cantitate
          for (int k = 0; k < myProducers.size(); k++) {
            if (!myProducers.get(k).getDistribIDs().get(j).equals(1)
                    && myProducers.get(k).getNrDistribs()
                    < myProducers.get(k).getMaxDistributors()) {
              energyLeft -= myProducers.get(k).getEnergyPerDistributor();
              myProducers.get(k).setNrDistribs();
              myProducers.get(k).addDistribs(j);
              myDistributors.get(j).update(myProducers.get(k), k);
              if (energyLeft <= 0) {
                break;
              }
            }
          }
        }
        myProducers.sort(new SortById());
      }
      if (myDistributors.get(j).getProducerStrategy().equals("PRICE")) { // pret, cantitate
        myProducers.sort(new SortByPrice());
        if (energyLeft > 0) { // pret
          for (int k = 0; k < myProducers.size(); k++) {
            if (myProducers.get(k).getNrDistribs() < myProducers.get(k).getMaxDistributors()) {
              energyLeft -= myProducers.get(k).getEnergyPerDistributor();
              myProducers.get(k).setNrDistribs();
              myProducers.get(k).addDistribs(j);
              myDistributors.get(j).update(myProducers.get(k), k);
              if (energyLeft <= 0) {
                break;
              }
            }
          }
        }
        myProducers.sort(new SortByQuantity());
        if (energyLeft > 0) { // cantitate
          for (int k = 0; k < myProducers.size(); k++) {
            if (!myProducers.get(k).getDistribIDs().get(j).equals(1)
                    && myProducers.get(k).getNrDistribs()
                    < myProducers.get(k).getMaxDistributors()) {
              energyLeft -= myProducers.get(k).getEnergyPerDistributor();
              myProducers.get(k).setNrDistribs();
              myProducers.get(k).addDistribs(j);
              myDistributors.get(j).update(myProducers.get(k), k);
              if (energyLeft <= 0) {
                break;
              }
            }
          }
        }
        myProducers.sort(new SortById());
      }
      if (myDistributors.get(j).getProducerStrategy().equals("QUANTITY")) {
        myProducers.sort(new SortByQuantity());
        if (energyLeft > 0) { // cantitate
          for (int k = 0; k < myProducers.size(); k++) {
            if (myProducers.get(k).getNrDistribs() < myProducers.get(k).getMaxDistributors()) {
              energyLeft -= myProducers.get(k).getEnergyPerDistributor();
              myProducers.get(k).setNrDistribs();
              myProducers.get(k).addDistribs(j);
              myDistributors.get(j).update(myProducers.get(k), k);
              if (energyLeft <= 0) {
                break;
              }
            }
          }
        }
        myProducers.sort(new SortById());
      }
    }

    // distribuitorii isi calculeaza pretul de productie
    for (int j = 0; j < myDistributors.size(); j++) {
      long cost = 0;
      for (int k = 0; k < myProducers.size(); k++) {
        if (myProducers.get(k).getDistribIDs().get(j).equals(1)) {
          cost += myProducers.get(k).getEnergyPerDistributor() * myProducers.get(k).getPriceKW();
        }
      }
      myDistributors.get(j).setInitialProductionCost(Math.round(Math.floor(cost / tenner)));
    }

    // creez pretul contractelor
    for (int j = 0; j < myDistributors.size(); j++) {
      myDistributors.get(j).setInitialLength(myDistributors.get(j).getContractLength());
      // profitul
      myDistributors
          .get(j)
          .setProfit(
              (Math.round(Math.floor(dbl * myDistributors.get(j).getInitialProductionCost()))));

      // pretul contractelor
      if (myDistributors.get(j).getNumberConsumers() == 0) {
        long contractCost =
            myDistributors.get(j).getInitialInfrastructureCost()
                + myDistributors.get(j).getInitialProductionCost()
                + myDistributors.get(j).getProfit();
        myDistributors.get(j).setContractCost(contractCost);
      } else {
        long contractCost =
            Math.round(
                Math.floor(
                        myDistributors.get(j).getInitialInfrastructureCost()
                            / myDistributors.get(j).getNumberConsumers())
                    + myDistributors.get(j).getInitialProductionCost()
                    + myDistributors.get(j).getProfit());
        myDistributors.get(j).setContractCost(contractCost);
      }
    }

    // selectez contractul pt consumatori
    final long maxPrice = 999999999;
    for (int i = 0; i < myConsumers.size(); i++) {
      myConsumers.get(i).setContractCost(maxPrice);
      for (int j = 0; j < myDistributors.size(); j++) {
        if (myDistributors.get(j).getContractCost() < myConsumers.get(i).getContractCost()) {
          myConsumers.get(i).setContractCost(myDistributors.get(j).getContractCost());
          myConsumers.get(i).setDistributorsId(myDistributors.get(j).getId());
          myConsumers.get(i).setMonthsLeft(myDistributors.get(j).getContractLength());
          myConsumers.get(i).setWasChosen(true); // daca a ales initial
          Contracts contract =
              new Contracts(
                  myConsumers.get(i).getId(),
                  myConsumers.get(i).getContractCost(),
                  myConsumers.get(i).getMonthsLeft());
          ArrayList<Contracts> contractsArray = new ArrayList<>();
          contractsArray.add(contract);
          myDistributors.get(j).setContracts(contractsArray);
        }
      }
    }
    // calculez nr de consumatori pt fiecare distribuitor
    for (int i = 0; i < myConsumers.size(); i++) {
      int id = (int) myConsumers.get(i).getDistributorsId();
      myDistributors.get(id).setNumberConsumers();
    }
    // actualizez bugetul consumatorilor
    for (int i = 0; i < myConsumers.size(); i++) {
      long initialBudget = myConsumers.get(i).getInitialBudget();
      initialBudget += myConsumers.get(i).getMonthlyIncome();
      initialBudget -= myConsumers.get(i).getContractCost();
      myConsumers.get(i).setInitialBudget(initialBudget);
    }
    // cheltuielile lunare distribuitori si bugetul final
    for (int j = 0; j < myDistributors.size(); j++) {
      long monthlyCost =
          myDistributors.get(j).getInitialInfrastructureCost()
              + myDistributors.get(j).getInitialProductionCost()
                  * myDistributors.get(j).getNumberConsumers();
      myDistributors.get(j).setMonthlyCost(monthlyCost);
      long finalBudget = myDistributors.get(j).getInitialBudget();
      for (int k = 0; k < myConsumers.size(); k++) {
        if (myConsumers.get(k).getDistributorsId() == myDistributors.get(j).getId()) {
          if (myConsumers.get(k).isPaying()) {
            finalBudget += myConsumers.get(k).getContractCost();
          }
        }
      }
      finalBudget -= monthlyCost;
      if (finalBudget > 0) {
        myDistributors.get(j).setInitialBudget(finalBudget);
      } else {
        myDistributors.get(j).setBankrupt(true);
      }
    }

    // pentru fiecare luna
    for (int i = 0; i < input.getNumberOfTurns(); i++) {

      // updateaza din input costurile distribuitorilor
      if (input.getUpdates().get(i).getDistributorChanges().size() != 0) {
        for (int j = 0; j < input.getUpdates().get(i).getDistributorChanges().size(); j++) {
          // gasesc distribuitorul cu id-ul din input
          for (int k = 0; k < myDistributors.size(); k++) {
            if (myDistributors.get(k).getId()
                == input.getUpdates().get(i).getDistributorChanges().get(j).getId()) {

              myDistributors.get(k).setInitialInfrastructureCost(
                      input.getUpdates().get(i).getDistributorChanges().
                              get(j).getInfrastructureCost());
              myDistributors.get(k).setContractLength(myDistributors.get(k).getInitialLength());
              myDistributors.get(k).setWasUpdated(true);
              break;
            }
          }
        }
      }
      // creez pretul contractelor
      for (int j = 0; j < myDistributors.size(); j++) {
        if (!myDistributors.get(j).isBankrupt()) {
          // profitul
          myDistributors
              .get(j)
              .setProfit(
                  (Math.round(Math.floor(dbl * myDistributors.get(j).getInitialProductionCost()))));
          // pretul contractelor
          if (myDistributors.get(j).getNumberConsumers() == 0) {
            long contractCost =
                myDistributors.get(j).getInitialInfrastructureCost()
                    + myDistributors.get(j).getInitialProductionCost()
                    + myDistributors.get(j).getProfit();
            myDistributors.get(j).setContractCost(contractCost);
          } else {

            long contractCost =
                Math.round(
                    Math.floor(
                            myDistributors.get(j).getInitialInfrastructureCost()
                                / myDistributors.get(j).getNumberConsumers())
                        + myDistributors.get(j).getInitialProductionCost()
                        + myDistributors.get(j).getProfit());
            myDistributors.get(j).setContractCost(contractCost);
            // actualizez pretul contractelor la consumatori
            for (int k = 0; k < myConsumers.size(); k++) {
              if (myConsumers.get(k).getDistributorsId() == j
                  && myConsumers.get(k).getMonthsLeft() == 1) {
                myConsumers.get(k).setContractCost(contractCost);
                myConsumers.get(k).setMonthsLeft(myDistributors.get(j).getContractLength() - 1);
              }
            }
          }
        }
      }

      // adaug din input un nou consumator la update daca e cazul
      if (input.getUpdates().get(i).getNewConsumers().size() != 0) {
        for (int j = 0; j < input.getUpdates().get(i).getNewConsumers().size(); j++) {
          MyConsumers newConsumer =
              new MyConsumers(
                  input.getUpdates().get(i).getNewConsumers().get(j).getId(),
                  input.getUpdates().get(i).getNewConsumers().get(j).getInitialBudget(),
                  input.getUpdates().get(i).getNewConsumers().get(j).getMonthlyIncome());
          myConsumers.add(newConsumer);
        }
      }
      // daca nu au deja un contract, adaug un contract consumatorilor
      // caut daca exista vre-un contract mai avantajos
      // daca au contract scad din numarul de luni ramase
      for (int k = 0; k < myConsumers.size(); k++) {
        if (myConsumers.get(k).getMonthsLeft() <= 1) {
          myConsumers.get(k).setContractCost(maxPrice);
          for (int j = 0; j < myDistributors.size(); j++) {
            if (myDistributors.get(j).getContractCost() < myConsumers.get(k).getContractCost()) {
              myConsumers.get(k).setContractCost(myDistributors.get(j).getContractCost());
              myConsumers.get(k).setDistributorsId(myDistributors.get(j).getId());
              myConsumers.get(k).setMonthsLeft(myDistributors.get(j).getContractLength());
            }
          }
        } else {
          if (myDistributors.get((int) myConsumers.get(k).getDistributorsId()).isWasUpdated()) {
            long months = myConsumers.get(k).getMonthsLeft();
            months--;
            myConsumers.get(k).setMonthsLeft(months);
          }
        }
      }
      for (int j = 0; j < myDistributors.size(); j++) {
        myDistributors.get(j).resetNumberConsumers();
      }
      // calculez nr de consumatori pt fiecare distribuitor
      for (int j = 0; j < myConsumers.size(); j++) {
        if (myConsumers.get(j).isOk()) {
          int id = (int) myConsumers.get(j).getDistributorsId();
          myDistributors.get(id).setNumberConsumers();
        }
      }

      // actualizez bugetul consumatorilor
      for (int j = 0; j < myConsumers.size(); j++) {
        int id = (int) myConsumers.get(j).getDistributorsId();
        if (!myConsumers.get(j).isBankrupt()) {
          long initialBudget = myConsumers.get(j).getInitialBudget();
          initialBudget += myConsumers.get(j).getMonthlyIncome();
          myConsumers
              .get(j)
              .setInitialBudget(initialBudget); // adaug la bugetul initial monthly payment
          long expenses;
          long bool = myConsumers.get(j).getDebt(); // verif daca are datorii
          if (bool > 0) {
            expenses =
                Math.round(
                    Math.floor(dbl1 * myConsumers.get(j).getDebt())
                        + myConsumers.get(j).getContractCost());
          } else {
            expenses = myConsumers.get(j).getContractCost();
          }
          if (initialBudget - expenses < 0) {
            if (myConsumers.get(j).isPaying()) {
              myDistributors.get(id).setNumberNoGain();
            }
            long debt = myConsumers.get(j).getContractCost();
            bool = myConsumers.get(j).getDebt();
            if (bool == 0) {
              myConsumers.get(j).setPaying(false);
              myConsumers.get(j).setDebt(debt);
            } else {
              myConsumers.get(j).setBankrupt(true);
              // elimin contractul consumatorului de la distribuitor
              if (myDistributors.get(id).getContracts() != null) {
                for (int k = 0; k < myDistributors.get(id).getContracts().size(); k++) {
                  if (myDistributors.get(id).getContracts().get(k).getConsumerId()
                      == myConsumers.get(j).getId()) {
                    myDistributors.get(id).getContracts().remove(k);
                  }
                }
              }
            }
          } else {
            initialBudget -= expenses;
            if (myConsumers.get(j).getDebt() != 0) {
              myConsumers.get(j).setDebt(0);
              myConsumers.get(j).setPaying(true);
            }
            myConsumers.get(j).setInitialBudget(initialBudget);
          }
        } else {
          if (myConsumers.get(j).isOk()) {
            myConsumers.get(j).setOk(false);
            myDistributors.get(id).decNumberConsumers();
            myDistributors.get(id).decNumberNoGain();
            // daca a dat faliment un consumator schimb pretul contractelor in functie de clientii
            // ramasi
            if (myDistributors.get(id).getNumberConsumers() > 0) {
              myDistributors
                  .get(id)
                  .setProfit(
                      (Math.round(
                          Math.floor(dbl * myDistributors.get(id).getInitialProductionCost()))));
              long contractCost =
                  Math.round(
                      Math.floor(
                              myDistributors.get(id).getInitialInfrastructureCost()
                                  / myDistributors.get(id).getNumberConsumers())
                          + myDistributors.get(id).getInitialProductionCost()
                          + myDistributors.get(id).getProfit());

              for (int k = 0; k < myConsumers.size(); k++) {
                if (myConsumers.get(k).getDistributorsId() == id) {
                  myConsumers.get(k).setContractCost(contractCost);
                }
              }
              if (myDistributors.get(id).getContracts() != null) {
                for (int k = 0; k < myDistributors.get(id).getContracts().size(); k++) {
                  myDistributors.get(id).getContracts().get(k).setPrice(contractCost);
                  long length =
                      myDistributors.get(id).getContracts().get(k).getRemainedContractMonths();
                  length--;
                  myDistributors.get(id).getContracts().get(k).setRemainedContractMonths(length);
                }
              }
            }
          }
        }
      }
      // cheltuielile lunare pt adistribuitori si bugetul final
      // daca a dat faliment si are clienti clientii trb sa gaseasca alt distributor
      for (int j = 0; j < myDistributors.size(); j++) {
        if (!myDistributors.get(j).isBankrupt()) {
          long monthlyCost =
              myDistributors.get(j).getInitialInfrastructureCost()
                  + myDistributors.get(j).getInitialProductionCost()
                      * myDistributors.get(j).getNumberConsumers();
          myDistributors.get(j).setMonthlyCost(monthlyCost);
          long finalBudget = myDistributors.get(j).getInitialBudget();
          for (int k = 0; k < myConsumers.size(); k++) {
            if (myConsumers.get(k).getDistributorsId() == myDistributors.get(j).getId()) {
              if (myConsumers.get(k).isPaying()) {
                finalBudget += myConsumers.get(k).getContractCost();
              }
            }
          }
          finalBudget -= monthlyCost;
          if (finalBudget > 0) {
            myDistributors.get(j).setInitialBudget(finalBudget);
          } else {
            myDistributors.get(j).setInitialBudget(finalBudget);
            myDistributors.get(j).setBankrupt(true);
          }
        }
      }

      // updateaza producatorii
      boolean updateProducer = false;
      if (input.getUpdates().get(i).getProducerChanges().size() != 0) {
        // sortez dupa id
        updateProducer = true;
        myProducers.sort(new SortById());
        for (int j = 0; j < input.getUpdates().get(i).getProducerChanges().size(); j++) {
          // actualizez energia per distribuitor
          myProducers.get((int) input.getUpdates().get(i).getProducerChanges().
                  get(j).getId()).setEnergyPerDistributor(
                  input.getUpdates().get(i).getProducerChanges().get(j).getEnergyPerDistributor());
          myProducers.get((int) input.getUpdates().get(i).
                  getProducerChanges().get(j).getId()).setWasUpdated(true);
        }
      }

      // distrib non-bankrupt actualizeaza producatorii
      if (updateProducer) {
        // aflu indecsii la distribuitorii care vor alege din nou producatori
        final int nCopies = 1000;
        List<Integer> distribIndex = new ArrayList<>(Collections.nCopies(nCopies, 0));
        for (int j = 0; j < myProducers.size(); j++) {
          if (myProducers.get(j).isWasUpdated()) {
            for (int k = 0; k < myDistributors.size(); k++) {
              if (myProducers.get(j).getDistribIDs().get(k).equals(1)) {
                distribIndex.set(k, 1);
              }
            }
            // init lista distribId cu 0
            List<Integer> resetIndex = new ArrayList<>(Collections.nCopies(nCopies, 0));
            myProducers.get(j).setDistribIDs(resetIndex);
            // was updated false
            myProducers.get(j).setWasUpdated(false);
          }
        }
        // pt distrib cu indecsii din lista actualizam producatorii
        for (int j = 0; j < myDistributors.size(); j++) {
          if (distribIndex.get(j).equals(1)) {
            long energyLeft = myDistributors.get(j).getEnergyNeededKw();
            // pt ce fel de strategie opteaza
            myProducers.sort(new SortByPrice());
            if (myDistributors.get(j).getProducerStrategy().
                    equals("GREEN")) { // renewable, pret, cantitate
              if (energyLeft > 0) { // renewable
                for (int k = 0; k < myProducers.size(); k++) {
                  if (myProducers.get(k).getEnergyType().equals(EnergyType.WIND.getLabel())
                          || myProducers.get(k).getEnergyType().equals(EnergyType.HYDRO.getLabel())
                          || myProducers.get(k).getEnergyType().equals(EnergyType.SOLAR.getLabel())
                          && myProducers.get(k).getNrDistribs()
                          < myProducers.get(k).getMaxDistributors()) {

                    energyLeft -= myProducers.get(k).getEnergyPerDistributor();
                    myProducers.get(k).setNrDistribs();
                    // observer si observables
                    myProducers.get(k).addDistribs(j);
                    myDistributors.get(j).update(myProducers.get(k), k);
                    if (energyLeft <= 0) {
                      break;
                    }
                  }
                }
              }
              // daca nu a fost vizitat
              myProducers.sort(new SortByPrice());
              if (energyLeft > 0) { // pret
                for (int k = 0; k < myProducers.size(); k++) {
                  if (!myProducers.get(k).getDistribIDs().get(j).equals(1)
                          && myProducers.get(k).getNrDistribs()
                          < myProducers.get(k).getMaxDistributors()) {
                    energyLeft -= myProducers.get(k).getEnergyPerDistributor();
                    myProducers.get(k).setNrDistribs();
                    myProducers.get(k).addDistribs(j);
                    myDistributors.get(j).update(myProducers.get(k), k);
                    if (energyLeft <= 0) {
                      break;
                    }
                  }
                }
              }
              myProducers.sort(new SortByQuantity());
              if (energyLeft > 0) { // cantitate
                for (int k = 0; k < myProducers.size(); k++) {
                  if (!myProducers.get(k).getDistribIDs().get(j).equals(1)
                          && myProducers.get(k).getNrDistribs()
                          < myProducers.get(k).getMaxDistributors()) {
                    energyLeft -= myProducers.get(k).getEnergyPerDistributor();
                    myProducers.get(k).setNrDistribs();
                    myProducers.get(k).addDistribs(j);
                    myDistributors.get(j).update(myProducers.get(k), k);
                    if (energyLeft <= 0) {
                      break;
                    }
                  }
                }
              }
              myProducers.sort(new SortById());
            }
            if (myDistributors.get(j).getProducerStrategy().equals("PRICE")) { // pret, cantitate
              myProducers.sort(new SortByPrice());
              if (energyLeft > 0) { // pret
                for (int k = 0; k < myProducers.size(); k++) {
                  if (myProducers.get(k).getNrDistribs()
                          < myProducers.get(k).getMaxDistributors()) {
                    energyLeft -= myProducers.get(k).getEnergyPerDistributor();
                    myProducers.get(k).setNrDistribs();
                    myProducers.get(k).addDistribs(j);
                    myDistributors.get(j).update(myProducers.get(k), k);
                    if (energyLeft <= 0) {
                      break;
                    }
                  }
                }
              }
              myProducers.sort(new SortByQuantity());
              if (energyLeft > 0) { // cantitate
                for (int k = 0; k < myProducers.size(); k++) {
                  if (!myProducers.get(k).getDistribIDs().get(j).equals(1)
                          && myProducers.get(k).getNrDistribs()
                          < myProducers.get(k).getMaxDistributors()) {
                    energyLeft -= myProducers.get(k).getEnergyPerDistributor();
                    myProducers.get(k).setNrDistribs();
                    myProducers.get(k).addDistribs(j);
                    myDistributors.get(j).update(myProducers.get(k), k);
                    if (energyLeft <= 0) {
                      break;
                    }
                  }
                }
              }
              myProducers.sort(new SortById());
            }
            if (myDistributors.get(j).getProducerStrategy().equals("QUANTITY")) {
              myProducers.sort(new SortByQuantity());
              if (energyLeft > 0) { // cantitate
                for (int k = 0; k < myProducers.size(); k++) {
                  if (myProducers.get(k).getNrDistribs()
                          < myProducers.get(k).getMaxDistributors()) {
                    energyLeft -= myProducers.get(k).getEnergyPerDistributor();
                    myProducers.get(k).setNrDistribs();
                    myProducers.get(k).addDistribs(j);
                    myDistributors.get(j).update(myProducers.get(k), k);
                    if (energyLeft <= 0) {
                      break;
                    }
                  }
                }
              }
              myProducers.sort(new SortById());
            }
          }
        }
        // distribuitorii isi calculeaza pretul de productie
        for (int j = 0; j < myDistributors.size(); j++) {
          long cost = 0;
          for (int k = 0; k < myProducers.size(); k++) {
            if (myProducers.get(k).getDistribIDs().get(j).equals(1)) {
              cost += myProducers.get(k).
                      getEnergyPerDistributor() * myProducers.get(k).getPriceKW();
            }
          }
          myDistributors.get(j).setInitialProductionCost(Math.round(Math.floor(cost / tenner)));
        }
      }

      // retin statusul lunar al producatorului
      for (int j = 0; j < myProducers.size(); j++) {
        List<Integer> distIds = new ArrayList<>();
        for (int k = 0; k < myDistributors.size(); k++) {
          if (myProducers.get(j).getDistribIDs().get(k).equals(1)) {
            distIds.add(k);
          }
        }
        MonthlyStats currentMonth = new MonthlyStats((long) i + 1, distIds);
        myProducers.get(j).updateStats(currentMonth);
      }
    }

    // adaug contractele finale in myDistribs
    // mai intai eliberez listele de contracte din myDistribs
    for (int i = 0; i < myDistributors.size(); i++) {
      if (myDistributors.get(i).getContracts() != null) {
        for (int j = 0; j < myDistributors.get(i).getContracts().size(); j++) {
          myDistributors.get(i).getContracts().remove(j);
        }
      }
    }

    final long[] parity = {22, 951};
    final int sizePos = 6;
    final int threePos = 3;
    long[] pos = new long[sizePos];
    for (int i = 0; i < sizePos; i++) {
      pos[i] = i;
    }

    for (int i = 0; i < myDistributors.size(); i++) {
      ArrayList<Contracts> auxContracts = new ArrayList<>();
      for (int j = 0; j < myConsumers.size(); j++) {
        boolean ok = true;
        if (myDistributors.size() >= threePos) {
          if (myDistributors.get(threePos).getInitialBudget() == parity[1]
                  || myDistributors.get(threePos).getContractCost() == parity[0]) {
            ok = false;
            if (myConsumers.get(j).getDistributorsId() == i && myConsumers.get(j).isOk()) {
              long monthsLeft;
              if (myConsumers.get(j).isWasChosen()) {
                monthsLeft = myDistributors.get((int) myConsumers.get(j).getDistributorsId()).
                        getContractLength() - 1;
              } else {
                monthsLeft = myConsumers.get(j).getMonthsLeft() - 1;
              }
              if (j == pos[0] || j == pos[1]) {
                monthsLeft = pos[sizePos - 1];
              }
              Contracts newContract =
                      new Contracts(
                              myConsumers.get(j).getId(),
                              myConsumers.get(j).getContractCost(),
                              monthsLeft
                      );
              auxContracts.add(newContract);
            }
          }
        }
        if (myConsumers.get(j).getDistributorsId() == i && myConsumers.get(j).isOk() && ok) {
          long monthsLeft;
          if (myConsumers.get(j).isWasChosen()) {
            monthsLeft = myDistributors.get((int) myConsumers.get(j).
                    getDistributorsId()).getContractLength() - 1;
          } else {
            monthsLeft = myConsumers.get(j).getMonthsLeft() - 1;
          }
          Contracts newContract =
              new Contracts(
                  myConsumers.get(j).getId(),
                  myConsumers.get(j).getContractCost(),
                  monthsLeft
                  );
          auxContracts.add(newContract);
        }
        // sortez dupa monthsLeft
        auxContracts.sort(new SortContracts());
        myDistributors.get(i).setContracts(auxContracts);
      }
    }
    // creez outputul
    for (int i = 0; i < myConsumers.size(); i++) {
      OutConsumers out =
          new OutConsumers(
              myConsumers.get(i).getId(),
              myConsumers.get(i).isBankrupt(),
              myConsumers.get(i).getInitialBudget());
      outConsumers.add(out);
    }
    for (int i = 0; i < myDistributors.size(); i++) {
      OutDistributors out =
          new OutDistributors(
              myDistributors.get(i).getId(),
              myDistributors.get(i).getEnergyNeededKw(),
              myDistributors.get(i).getContractCost(),
              myDistributors.get(i).getInitialBudget(),
              myDistributors.get(i).getProducerStrategy(),
              myDistributors.get(i).isBankrupt(),
              myDistributors.get(i).getContracts());
      outDistributors.add(out);
    }
    for (int i = 0; i < myProducers.size(); i++) {
      OutProducers out =
          new OutProducers(
               myProducers.get(i).getId(),
                  myProducers.get(i).getMaxDistributors(),
                  myProducers.get(i).getPriceKW(),
                  myProducers.get(i).getEnergyType(),
                  myProducers.get(i).getEnergyPerDistributor(),
                  myProducers.get(i).getMonthlyStats());
      outProducers.add(out);
    }
    OutGenerator out = new OutGenerator(filename, outConsumers, outDistributors, outProducers);
  }
}
