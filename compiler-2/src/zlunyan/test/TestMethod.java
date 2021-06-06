package zlunyan.test;

import zlunyan.domain.ProductionSet;
import zlunyan.domain.VNElement;
import zlunyan.factory.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestMethod {
    public static void showVNAndVTAndProductionAndInput() {
        System.out.println("VTSet:");
        List<VNElement> vnSet = VNFactory.getInstance();
        for (VNElement vn : vnSet) {
            System.out.println(vn.getFather());
        }

        System.out.println("VTSet:");
        List<String> vtSet = VTFactory.getInstance();
        for (String vt : vtSet) {
            System.out.println(vt);
        }

        System.out.println("Production:");
        List<ProductionSet> productionSetList = ProductionFactory.getProductionSetList();
        for (ProductionSet productionSet : productionSetList) {
            for (String son : productionSet.getSons()) {
                System.out.println(productionSet.getFather() + " -> " + son);
            }
        }

        System.out.println("input");
        List<String> inputSet = InputFactory.getInstance();
        for (String input : inputSet) {
            System.out.println(input);
        }
    }

    public static void showFirstSet() {
        System.out.println("################");
        System.out.println("Show First Set: ");
        List<VNElement> vnElementList = VNFactory.getInstance();
        for (int i = 0; i < vnElementList.size(); i++) {
            System.out.println("----------------");
            VNElement element = vnElementList.get(i);
            Set<String> stringList = element.getFirstSet();
            System.out.println("father: " + element.getFather() + " FirstSet length: " + stringList.size());
            Iterator<String> stringIterator = stringList.iterator();
            for (int j = 0; j < stringList.size(); j++) {
                System.out.println(stringIterator.next());
            }
        }
    }

    public static void showFollowSet() {
        System.out.println("################");
        System.out.println("Show Follow Set: ");
        List<VNElement> vnElementList = VNFactory.getInstance();
        for (int i = 0; i < vnElementList.size(); i++) {
            System.out.println("----------------");
            VNElement element = vnElementList.get(i);
            Set<String> stringList = element.getFollowSet();
            System.out.println("father: " + element.getFather() + " FollowSet length: " + stringList.size());
            Iterator<String> stringIterator = stringList.iterator();
            for (int j = 0; j < stringList.size(); j++) {
                System.out.println(stringIterator.next());
            }
        }
    }

    public static void showLL1Table() {
        System.out.println(LL1TableFactory.showTable());
    }
}
