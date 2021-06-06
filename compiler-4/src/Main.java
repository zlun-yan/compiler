// 算符优先文法

import java.util.*;

public class Main {
    static Set<String> VNSet = new HashSet<>();
    static Set<String> VTSet = new HashSet<>();
    static Map<String, List<String>> productionSet = new HashMap<>();
    static Map<String, Set<String>> FirstSet = new HashMap<>();
    static Map<String, Set<String>> LastSet = new HashMap<>();
    static List<String> InputSet = new ArrayList<>();

    static Map<String, Map<String, String>> priorityTable = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();

            switch (count) {
                case 0:
                    try {
                        createVNArray(str);
                        count ++;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        createVTArray(str);
                        count ++;
                        System.out.println("");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    if (str.equals("")) {
                        count ++;
                        break;
                    }
                    createProduction(str);
                    break;
                case 3:
                    if (str.equals("")) {
                        count ++;
                        break;
                    }
                    addInput(str);
                    break;
            }

            if (count == 4) break;
        }

        getFIRSTVTSet();
        getLASTVTSet();
        Test.showSet(VNSet, "VNSet");
        Test.showSet(VTSet, "VTSet");
        Test.showPairSet(FirstSet, "FirstSet");
        Test.showPairSet(LastSet, "LastSet");

        createPriorityTable();
        Test.showTable(priorityTable, VTSet);
    }

    public static void createPriorityTable() {
        // init
        Iterator<String> VTIterator = VTSet.iterator();
        for (int i = 0; i< VTSet.size(); i++) {
            String VT = VTIterator.next();
            Map<String, String> newMap = new HashMap<>();
            priorityTable.put(VT, newMap);
        }

        int oper = 0;
        /*
        0: 开始
        1: 前面为VT
        2: 前面为VN
        3: 前面为VN，前面的前面为VT
         */
        for (String father : productionSet.keySet()) {
            List<String> sons = productionSet.get(father);
            for (int i = 0; i < sons.size(); i++) {
                String son = sons.get(i);
                oper = 0;
                for (int j = 0; j < son.length(); j++) {
                    String now = son.substring(j, j + 1);
                    if (VTSet.contains(now)) {  // VT
                        switch (oper) {
                            case 1:
                                priorityTable.get(son.substring(j - 1, j)).put(now, "=");
                                break;
                            case 2:
                                Set<String> lastSet = LastSet.get(son.substring(j - 1, j));
                                Iterator<String> iterator = lastSet.iterator();
                                for (int k = 0; k < lastSet.size(); k++) {
                                    priorityTable.get(iterator.next()).put(now, ">");
                                }
                                break;
                            case 3:
                                priorityTable.get(son.substring(j - 2, j - 1)).put(now, "=");
                                Set<String> lastSet1 = LastSet.get(son.substring(j - 1, j));
                                Iterator<String> iterator1 = lastSet1.iterator();
                                for (int k = 0; k < lastSet1.size(); k++) {
                                    priorityTable.get(iterator1.next()).put(now, ">");
                                }
                                break;
                            default: break;
                        }

                        oper = 1;
                    } else {  // VN
                        switch (oper) {
                            case 0:
                                oper = 2;
                                break;
                            case 1:
                                oper = 3;
                                Set<String> firstSet = FirstSet.get(now);
                                Iterator<String> iterator = firstSet.iterator();
                                for (int k = 0; k < firstSet.size(); k++) {
                                    priorityTable.get(son.substring(j - 1, j)).put(now, "<");
                                }
                                break;
                            case 2:
                                // 出错
                                try {
                                    throw new GrammarException("输入错误");
                                } catch (GrammarException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                // 出错
                                try {
                                    throw new GrammarException("输入错误");
                                } catch (GrammarException e) {
                                    e.printStackTrace();
                                }
                                break;
                            default: break;
                        }
                    }
                }
            }
        }
    }

    public static void getFIRSTVTSet() {
        List<Pair> FirstStack = new ArrayList<>();
        // 一开始的初始化
        for (String father : productionSet.keySet()) {
            List<String> sons = productionSet.get(father);
            for (int i = 0; i < sons.size(); i++) {
                String son = sons.get(i);
                int len = son.length();
                if (VTSet.contains(son.substring(0, 1))) {
                    String first = son.substring(0, 1);
                    if (!FirstSet.get(father).contains(first)) {
                        FirstSet.get(father).add(first);
                        FirstStack.add(new Pair(father, first));
                    }
                } else if (len >= 2 && VTSet.contains(son.substring(1, 2))) {
                    String first = son.substring(1, 2);
                    if (!FirstSet.get(father).contains(first)) {
                        FirstSet.get(father).add(first);
                        FirstStack.add(new Pair(father, first));
                    }
                }
            }
        }

        // 后面的循环 利用下一个规则
        while (FirstStack.size() != 0) {
            Pair pair = FirstStack.get(0);
            FirstStack.remove(0);

            for (String father : productionSet.keySet()) {
                List<String> sons = productionSet.get(father);
                for (int i = 0; i < sons.size(); i++) {
                    String son = sons.get(i);
                    if (son.substring(0, 1).equals(pair.getVN())) {
                        String first = pair.getVT();
                        if (!FirstSet.get(father).contains(first)) {
                            FirstSet.get(father).add(first);
                            FirstStack.add(new Pair(father, first));
                        }
                    }
                }
            }
        }
    }

    public static void getLASTVTSet() {
        List<Pair> LastStack = new ArrayList<>();
        // 一开始的初始化
        for (String father : productionSet.keySet()) {
            List<String> sons = productionSet.get(father);
            for (int i = 0; i < sons.size(); i++) {
                String son = sons.get(i);
                int len = son.length();
                if (VTSet.contains(son.substring(len - 1))) {
                    String last = son.substring(len - 1);
                    if (!LastSet.get(father).contains(last)) {
                        LastSet.get(father).add(last);
                        LastStack.add(new Pair(father, son.substring(len - 1)));
                    }
                } else if (len >= 2 && VTSet.contains(son.substring(len - 2, len - 1))) {
                    String last = son.substring(len - 2, len - 1);
                    if (!LastSet.get(father).contains(last)) {
                        LastSet.get(father).add(last);
                        LastStack.add(new Pair(father, son.substring(len - 2, len - 1)));
                    }
                }
            }
        }

        // 后面的循环 利用下一个规则
        while (LastStack.size() != 0) {
            Pair pair = LastStack.get(0);
            LastStack.remove(0);

            for (String father : productionSet.keySet()) {
                List<String> sons = productionSet.get(father);
                for (int i = 0; i < sons.size(); i++) {
                    String son = sons.get(i);
                    int len = son.length();
                    if (son.substring(len - 1).equals(pair.getVN())) {
                        String last = pair.getVT();
                        if (!LastSet.get(father).contains(last)) {
                            LastSet.get(father).add(last);
                            LastStack.add(new Pair(father, pair.getVT()));
                        }
                    }
                }
            }
        }
    }

    public static void createVNArray(String input) throws NullPointerException{
        if (input.equals("") || input == null) {
            throw new NullPointerException("空字符串");
        }

        for (int i = 0; i < input.length(); i++) {
            String father = String.valueOf(input.charAt(i));
            VNSet.add(father);
            Set<String> newFirstSet = new HashSet<>();
            Set<String> newLastSet = new HashSet<>();
            FirstSet.put(father, newFirstSet);
            LastSet.put(father, newLastSet);
        }
    }

    public static void createVTArray(String input) throws NullPointerException{
        if (input.equals("") || input == null) {
            throw new NullPointerException("空字符串");
        }

        for (int i = 0; i < input.length(); i++) {
            VTSet.add(String.valueOf(input.charAt(i)));
        }
    }

    public static void createProduction(String input) {
        String[] rowStr = input.split("->");
        List<String> sons = Arrays.asList(rowStr[1].split("\\|"));

        productionSet.put(rowStr[0], sons);
    }

    public static void addInput(String ch) {
        InputSet.add(ch);
    }
}
