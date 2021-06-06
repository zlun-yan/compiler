import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Test {
    public static void showPairSet(Map<String, Set<String>> setMap, String tips) {
        for (String father : setMap.keySet()) {
            System.out.print(father + "`s " + tips + " is 【 ");
            Set<String> son = setMap.get(father);
            Iterator<String> stringIterator = son.iterator();
            for (int i = 0; i < son.size(); i++) {
                System.out.print(stringIterator.next() + " ");
            }
            System.out.println("】");
        }
    }

    public static void showSet(Set<String> set, String tips) {
        System.out.print(tips + ": 【 ");
        Iterator<String> stringIterator = set.iterator();
        for (int i = 0; i < set.size(); i++) {
            System.out.print(stringIterator.next() + " ");
        }
        System.out.println("】");
    }

    public static void showTable(Map<String, Map<String, String>> map, Set<String> VTSet) {
        Iterator<String> iterator1 = VTSet.iterator();
        for (int i = 0; i < VTSet.size(); i++) {
            String father = iterator1.next();
            Iterator<String> iterator2 = VTSet.iterator();
            for (int j = 0; j < VTSet.size(); j++) {
                String son = iterator2.next();
                System.out.println(father + " " + map.get(father).get(son) + " " + son);
            }
        }
    }
}
