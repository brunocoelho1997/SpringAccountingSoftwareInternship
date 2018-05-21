package hello.Project.Resources;

import java.util.ArrayList;
import java.util.List;

public class TypeSubtypeResource {

    String typeName;
    Float typeValueTotal;
    List<String> subTypeNames;
    List<Float> subTypeValues;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Float getTypeValueTotal() {
        return typeValueTotal;
    }

    public void setTypeValueTotal(Float typeValueTotal) {
        this.typeValueTotal = typeValueTotal;
    }

    public List<String> getSubTypeNames() {
        return subTypeNames;
    }

    public void setSubTypeNames(List<String> subTypeNames) {
        this.subTypeNames = subTypeNames;
    }

    public List<Float> getSubTypeValues() {
        return subTypeValues;
    }

    public void setSubTypeValues(List<Float> subTypeValues) {
        this.subTypeValues = subTypeValues;
    }
}
