package zlunyan.domain;

import java.util.List;

public class ProductionSet {
    private String father;
    private List<String> sons;

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public List<String> getSons() {
        return sons;
    }

    public void setSons(List<String> sons) {
        this.sons = sons;
    }
}
