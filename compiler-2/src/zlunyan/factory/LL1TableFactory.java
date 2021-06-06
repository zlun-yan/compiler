package zlunyan.factory;

import zlunyan.domain.VNElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LL1TableFactory {
    /**
     * 第一个String代表行(VN 非终结符 也是 father) 第二个String代表列(VT 终结符) 第三个String代表son
     */
    private static Map<String, Map<String, String>> LL1Table;

    private LL1TableFactory() {

    }

    public static String getProduction(String vn, String vt) {
        if (LL1Table == null) createTable();
        return LL1Table.get(vn).get(vt);
    }

    public static void changeTable(String vn, String vt, String son) {
        if (LL1Table == null) createTable();

        LL1Table.get(vn).put(vt, son);
    }

    private static void createTable() {
        LL1Table = new HashMap<>();

        List<VNElement> vnList = VNFactory.getInstance();
        List<String> vtList = VTFactory.getInstance();
        for (int i = 0; i < vnList.size(); i++) {
            Map<String, String> temp = new HashMap<>();
            for (int j = 0; j < vtList.size(); j++) temp.put(vtList.get(j), null);
            LL1Table.put(vnList.get(i).getFather(), temp);
        }
    }

    public static String showTable() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" \t\t");
        List<VNElement> vnList = VNFactory.getInstance();
        List<String> vtList = VTFactory.getInstance();

        for (int i = 0; i < vtList.size(); i++) stringBuilder.append(vtList.get(i) + "\t\t");
        for (int i = 0; i < vnList.size(); i++) {
            String father = vnList.get(i).getFather();
            stringBuilder.append("\n" + father + "\t\t");
            for (int j = 0; j < vtList.size(); j++) stringBuilder.append(LL1Table.get(father).get(vtList.get(j)) + "\t\t");
        }
        return stringBuilder.toString();
    }
}
