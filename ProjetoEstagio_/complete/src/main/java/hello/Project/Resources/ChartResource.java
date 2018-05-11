package hello.Project.Resources;

import java.util.List;
import java.util.Map;

public class ChartResource {

    private int percentageRevenues;
    private int percentageCosts;

    private float total;
    private float totalCosts;
    private float totalRevenues;

    private List<String> costsTypesNames;
    private List<Float> costsTypesValues;

    private List<String> revenuesTypesNames;
    private List<Float> revenuesTypesValues;


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

    public List<String> getCostsTypesNames() {
        return costsTypesNames;
    }

    public void setCostsTypesNames(List<String> costsTypesNames) {
        this.costsTypesNames = costsTypesNames;
    }

    public List<Float> getCostsTypesValues() {
        return costsTypesValues;
    }

    public void setCostsTypesValues(List<Float> costsTypesValues) {
        this.costsTypesValues = costsTypesValues;
    }

    public List<String> getRevenuesTypesNames() {
        return revenuesTypesNames;
    }

    public void setRevenuesTypesNames(List<String> revenuesTypesNames) {
        this.revenuesTypesNames = revenuesTypesNames;
    }

    public List<Float> getRevenuesTypesValues() {
        return revenuesTypesValues;
    }

    public void setRevenuesTypesValues(List<Float> revenuesTypesValues) {
        this.revenuesTypesValues = revenuesTypesValues;
    }
}
