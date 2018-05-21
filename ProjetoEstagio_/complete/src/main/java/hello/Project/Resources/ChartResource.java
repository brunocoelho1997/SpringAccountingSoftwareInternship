package hello.Project.Resources;

import java.util.List;
import java.util.Map;

public class ChartResource {

    private int percentageRevenues;
    private int percentageCosts;

    private float total;
    private float totalCosts;
    private float totalRevenues;

    private List<TypeSubtypeResource> typeSubtypeResourcesCosts;
    private List<TypeSubtypeResource> typeSubtypeResourcesRevenues;



    public int getPercentageRevenues() {
        return percentageRevenues;
    }

    public void setPercentageRevenues(int percentageRevenues) {
        this.percentageRevenues = percentageRevenues;
    }

    public int getPercentageCosts() {
        return percentageCosts;
    }

    public void setPercentageCosts(int percentageCosts) {
        this.percentageCosts = percentageCosts;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(float totalCosts) {
        this.totalCosts = totalCosts;
    }

    public float getTotalRevenues() {
        return totalRevenues;
    }

    public void setTotalRevenues(float totalRevenues) {
        this.totalRevenues = totalRevenues;
    }

    public List<TypeSubtypeResource> getTypeSubtypeResourcesCosts() {
        return typeSubtypeResourcesCosts;
    }

    public void setTypeSubtypeResourcesCosts(List<TypeSubtypeResource> typeSubtypeResourcesCosts) {
        this.typeSubtypeResourcesCosts = typeSubtypeResourcesCosts;
    }

    public List<TypeSubtypeResource> getTypeSubtypeResourcesRevenues() {
        return typeSubtypeResourcesRevenues;
    }

    public void setTypeSubtypeResourcesRevenues(List<TypeSubtypeResource> typeSubtypeResourcesRevenues) {
        this.typeSubtypeResourcesRevenues = typeSubtypeResourcesRevenues;
    }
}
