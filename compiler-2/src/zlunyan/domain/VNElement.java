package zlunyan.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VNElement {
    private String father;
    private Set<String> FirstSet;
    private Set<String> FollowSet;

    public VNElement() {
        FirstSet = new HashSet<>();
        FollowSet = new HashSet<>();
    }

    public VNElement(String father) {
        this.father = father;
        FirstSet = new HashSet<>();
        FollowSet = new HashSet<>();
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public Set<String> getFirstSet() {
        return FirstSet;
    }

    public void setFirstSet(Set<String> firstSet) {
        FirstSet = firstSet;
    }

    public Set<String> getFollowSet() {
        return FollowSet;
    }

    public void setFollowSet(Set<String> followSet) {
        FollowSet = followSet;
    }
}
