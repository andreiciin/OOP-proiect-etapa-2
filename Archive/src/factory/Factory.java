package factory;

public final class Factory {


    /**
     * genereaza array-uri de obiecte de tip consumer, contract sau distributer
     */
    public Object getObject(final String object) {
        if (object == null) {
            return null;
        }
        if (object.equals("contracts")) {
            return new MyContract();
        }
        if (object.equals("consumers")) {
            return new MyConsumer();
        }
        if (object.equals("distributors")) {
            return new MyDistributor();
        }
        return null;
    }
}
