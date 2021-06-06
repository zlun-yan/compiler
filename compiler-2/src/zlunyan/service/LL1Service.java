package zlunyan.service;

import zlunyan.domain.MyStack;
import zlunyan.domain.ProductionSet;
import zlunyan.domain.VNElement;
import zlunyan.factory.LL1TableFactory;
import zlunyan.factory.ProductionFactory;
import zlunyan.factory.VNFactory;
import zlunyan.factory.VTFactory;
import zlunyan.utils.Util;

import java.util.*;

public class LL1Service {
    public static void doFirstSet() {
        Map<String, Boolean> VNFirstSetMap = new HashMap<>();
        List<ProductionSet> productionSetList = ProductionFactory.getProductionSetList();
        boolean done = true;

        while (done) {
//            TestMethod.showFirstSet();

            done = false;
            for (int i = 0; i < productionSetList.size(); i++) {  //对于一个father的产生式而言
                ProductionSet productionSet = productionSetList.get(i);
                VNElement father = Util.getVNElementByString(productionSet.getFather());
                if (VNFirstSetMap.get(father.getFather()) != null) {  // 如果Map上不为null 说明这个已经被处理过了
                    if (!VNFirstSetMap.get(father.getFather())) {  // 如果为false的话 说明这其中还有未加入的First元素
                        VNFirstSetMap.put(father.getFather(), true);
                        Set<String> currentFirstSet = father.getFirstSet();
                        Set<String> tempFirstSet = new HashSet<>();
                        Iterator<String> currentFirstSetIterator = currentFirstSet.iterator();
                        for (int j = 0; j < currentFirstSet.size(); j++) {
                            String element = currentFirstSetIterator.next();
                            if (!Util.judgeVT(element)) {  // 如果判断这个元素不是VT 终结符 如果不是则进行处理
                                int len = 0;
                                for (; len < element.length(); i++) {
                                    // 这时应该每一个都处理过了 所以不用判null
                                    String currentChar = element.substring(len, len + 1);
                                    if (VNFirstSetMap.get(currentChar)) {  // 说明这个处理完了
                                        VNElement thisChar = Util.getVNElementByString(currentChar);
                                        tempFirstSet.addAll(thisChar.getFirstSet());
                                        if (!Util.judgeEmptyString(thisChar.getFirstSet())) break;
                                        // 如果这个非终结符的First集中没有空字，那么就说明处理完了
                                    } else {  // 说明还要继续循环
                                        done = true;
                                        tempFirstSet.add(element.substring(len));
                                        VNFirstSetMap.put(father.getFather(), false);
                                        break;
                                    }
                                }
                                currentFirstSetIterator.remove();
                            }
                        }

                        currentFirstSet.addAll(tempFirstSet);
                    }
                    continue;
                }

                // 这个还没处理过
                VNFirstSetMap.put(father.getFather(), true);
                List<String> sonSet = productionSet.getSons();
                for (int j = 0; j < sonSet.size(); j++) {
                    String son = sonSet.get(j);
                    for (int k = 0; k < son.length(); k++) {
                        String currentChar = son.substring(k, k + 1);
                        if (Util.judgeVT(currentChar)) {  // 如果是VT 终结符
                            father.getFirstSet().add(currentChar);
                            break;
                        } else { // 如果是VN 非终结符
                            if (VNFirstSetMap.get(currentChar) != null && VNFirstSetMap.get(currentChar)) {
                                VNElement element = Util.getVNElementByString(currentChar);
                                father.getFirstSet().addAll(element.getFirstSet());
                            } else {
                                father.getFirstSet().add(son.substring(k));
                                VNFirstSetMap.put(father.getFather(), false);
                                done = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void doFollowSet() {
        Map<String, Boolean> VNFollowSetMap = new HashMap<>();
        List<ProductionSet> productionSetList = ProductionFactory.getProductionSetList();

        // 先把所有的处理一遍
        for (int productionCount = 0; productionCount < productionSetList.size(); productionCount++) {
            ProductionSet productionSet = productionSetList.get(productionCount);
            String father = productionSet.getFather();
            List<String> sonList = productionSet.getSons();
            for (int i = 0; i < sonList.size(); i++) {
                String son = sonList.get(i) + father;
                for (int j = 0; j < son.length() - 1; j++) {
                    String currentChar = son.substring(j, j + 1);
                    if (!Util.judgeVT(currentChar)) {
                        if (VNFollowSetMap.get(currentChar) == null) VNFollowSetMap.put(currentChar, true);
                        for (int k = j + 1; k < son.length(); k++) {
                            String nextChar = son.substring(k ,k + 1);
                            if (k == son.length() - 1) {  // 如果是最后一个字符(father)
                                if (currentChar.equals(nextChar)) continue;
                                if (VNFollowSetMap.get(father) != null && VNFollowSetMap.get(father)) {
                                    Util.getVNElementByString(currentChar).getFollowSet().addAll(Util.getVNElementByString(nextChar).getFollowSet());
                                } else {
                                    Util.getVNElementByString(currentChar).getFollowSet().add(nextChar);
                                    VNFollowSetMap.put(currentChar, false);
                                }
                            } else if (Util.judgeVT(nextChar)) {  // 如果是VT 终结符
                                Util.getVNElementByString(currentChar).getFollowSet().add(nextChar);
                                break;
                            } else {  // 如果是VN 非终结符
                                Set<String> nextCharFirstSet = Util.getVNElementByString(nextChar).getFirstSet();
                                Iterator<String> nextCharFirstSetIterator = nextCharFirstSet.iterator();
                                for (int m = 0; m < nextCharFirstSet.size(); m++) {
                                    String element = nextCharFirstSetIterator.next();
                                    if (!element.equals("$")) Util.getVNElementByString(currentChar).getFollowSet().add(element);
                                }
                                if (!Util.judgeEmptyString(nextCharFirstSet)) break;
                            }
                        }
                    }
                }
            }
        }

        boolean done = true;
        while (done) {
            done = false;
            List<VNElement> vnElementList = VNFactory.getInstance();
            for (int i = 0; i < vnElementList.size(); i++) {
                VNElement father = vnElementList.get(i);
                if (!VNFollowSetMap.get(father.getFather())) {
                    VNFollowSetMap.put(father.getFather(), true);
                    Set<String> currentFollowSet = father.getFollowSet();
                    Set<String> tempFollowSet = new HashSet<>();
                    Iterator<String> followIterator = currentFollowSet.iterator();
                    for (int j = 0; j < currentFollowSet.size(); j++) {
                        String element = followIterator.next();
                        if (!Util.judgeVT(element)) {  // 如果element是VN 非终结符
                            if (VNFollowSetMap.get(element)) {  // 如果element处理完了
                                tempFollowSet.addAll(Util.getVNElementByString(element).getFollowSet());
                                followIterator.remove();
                            } else {
                                done = true;
                                VNFollowSetMap.put(father.getFather(), false);
                            }
                        }
                    }

                    currentFollowSet.addAll(tempFollowSet);
                }
            }
        }
    }

    public static void doLL1Table() {
        List<ProductionSet> productionSetList = ProductionFactory.getProductionSetList();

        for (int i = 0; i < productionSetList.size(); i++) {
            ProductionSet productionSet = productionSetList.get(i);
            String father = productionSet.getFather();
            List<String> sonList = productionSet.getSons();
            for (int j = 0; j < sonList.size(); j++) {
                String son = sonList.get(j);
                Set<String> stringFirstSet = Util.getStringFirstSet(son);
                Iterator<String> stringIterator = stringFirstSet.iterator();
                for (int m = 0; m < stringFirstSet.size(); m++) {
                    String currentChar = stringIterator.next();
                    LL1TableFactory.changeTable(father, currentChar, son);
                }
                if (Util.judgeEmptyString(stringFirstSet)) {
                    Set<String> followSet = Util.getVNElementByString(father).getFollowSet();
                    Iterator<String> followSetIterator = followSet.iterator();
                    for (int m = 0; m < followSet.size(); m++) {
                        String currentChar = followSetIterator.next();
                        LL1TableFactory.changeTable(father, currentChar, son);
                    }
                }
            }
        }
    }

    public static void analyse(String input) {
        List<VNElement> vnList = VNFactory.getInstance();
        MyStack charStack = new MyStack(vnList.get(0).getFather());
        input = input + "#";

        for (int i = 0; i < input.length();) {
            System.out.println("分析栈 " + charStack + " 剩余字符串 " + input.substring(i));

            String currentChar = input.substring(i, i + 1);
            String pop = charStack.pop();
            if (Util.judgeVT(pop)) {
                if (pop.equals(currentChar)) {
                    if (pop.equals("#")) {
                        System.out.println("SUCCESS!");
                        break;
                    } else i++;
                } else {
                    System.out.println("ERROR!");
                    break;
                }
            } else {
                String son = LL1TableFactory.getProduction(pop, currentChar);
//                System.out.println("stack: " + pop + " current: " + currentChar + " son: " + son);
                if (son != null) {
                    if (son.equals("$")) continue;
                    charStack.push(son);
                }
                else {
                    System.out.println("ERROR!");
                    break;
                }
            }
        }
    }
}
