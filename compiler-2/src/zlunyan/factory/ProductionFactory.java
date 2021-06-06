package zlunyan.factory;

import zlunyan.domain.ProductionSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductionFactory {
    private static List<ProductionSet> productionSetList;

    private ProductionFactory() {

    }

    public static void createProduction(String input) {
        if (productionSetList == null) {
            productionSetList = new ArrayList<>();
        }

        String[] rowStr = input.split("->");
        String[] sons = rowStr[1].split("\\|");

        ProductionSet productionSet = new ProductionSet();
        productionSet.setFather(rowStr[0]);
        productionSet.setSons(Arrays.asList(sons));
        productionSetList.add(productionSet);
    }

    public static List<ProductionSet> getProductionSetList() {
        if (productionSetList == null) {
            productionSetList = new ArrayList<>();
        }
        return productionSetList;
    }

    /*
    将前面的替换
    消除直接左递归
     */
    public static void eliminatingLeftRecursion() {
        List<String> beforeVN = new ArrayList<>();
        int productionListLength = productionSetList.size();
        for (int i = 0; i < productionListLength; i++) {  // 遍历所有的产生式(以father分组)
            ProductionSet productionSet = productionSetList.get(i);
            List<String> sonSet = productionSet.getSons();  // 获取同一个father下的所有son
            int sonSetLength = sonSet.size();
            for (int j = 0; j < sonSetLength; j++) {
                String son = sonSet.get(j);  // 对于其中一个son操作
                for (int k = 0; k < beforeVN.size(); k++) {  // 遍历之前的处理过的非终结符
                    String vn = beforeVN.get(k);
                    if (son.length() >= vn.length() && vn.equals(son.substring(0, vn.length()))) {  // 如果此时的son是以这些非终结符开头
                        sonSet.remove(son);  // 那么就要替换掉
                        j--;
                        sonSetLength--;

                        for (int m = 0; m < i; m++) {
                            ProductionSet replaceProductionSet = productionSetList.get(m);
                            if (replaceProductionSet.getFather().equals(vn)) {  // 找到此时匹配的这个非终结符的产生式
                                List<String> replaceSonSet = replaceProductionSet.getSons();
                                for (int n = 0; n < replaceSonSet.size(); n++) {  // 替换
                                    sonSet.add(replaceSonSet.get(n).concat(son.substring(vn.length())));
                                }

                                break;
                            }
                        }
                    }
                }
            }

            beforeVN.add(productionSet.getFather());
            doEliminating(i);
        }
    }

    /**
     * 消除直接左递归
     * @param productionId
     */
    private static void doEliminating(int productionId) {
        boolean doIt = false;  // 如果没有直接左递归则不需要做
        ProductionSet productionSet = productionSetList.get(productionId);
        String father = productionSet.getFather();
        List<String> sonSet = productionSet.getSons();
        for (int i = 0; i < sonSet.size(); i++) {
            String son = sonSet.get(i);
            if (son.length() >= father.length() && father.equals(son.substring(0, father.length()))) {
                doIt = true;
                VNFactory.addVN(father.toLowerCase());
                break;
            }
        }

        if (doIt) {
            ProductionSet tempProductionSet = new ProductionSet();
            tempProductionSet.setFather(father.toLowerCase());
            List<String> tempSonSet = new ArrayList<>();
            List<String> newSonSet = new ArrayList<>();

            for (int i = 0; i < sonSet.size(); i++) {
                String son = sonSet.get(i);
                if (son.length() >= father.length() && father.equals(son.substring(0, father.length()))) {
                    tempSonSet.add(son.substring(father.length()).concat(father.toLowerCase()));
                } else {
                    newSonSet.add(son.concat(father.toLowerCase()));
                }
            }

            productionSet.setSons(newSonSet);
            tempSonSet.add("$");
            tempProductionSet.setSons(tempSonSet);
            productionSetList.add(tempProductionSet);
        }
    }
}
