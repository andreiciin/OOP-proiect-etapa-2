package producers;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyStats {

    private long month;
    private List<Integer> distributorsIds = new ArrayList<>();

    public MonthlyStats(final long month, final List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(final long month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(final List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
