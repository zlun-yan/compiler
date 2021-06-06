package zlunyan.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * 终结符集合
 * $ 代表空字符串  #就是占位符
 */
public class VTFactory {
    private static List<String> VTSet;

    private VTFactory() {

    }

    public static List<String> getInstance() {
        if (VTSet == null) {
            VTSet = new ArrayList<>();
            VTSet.add("$");
            VTSet.add("#");
        }

        return VTSet;
    }

    public static void createVTArray(String input) throws NullPointerException{
        if (input.equals("") || input == null) {
            throw new NullPointerException("空字符串");
        }
        VTSet = new ArrayList<>();
        VTSet.add("$");
        VTSet.add("#");

        for (int i = 0; i < input.length(); i++) {
            VTSet.add(String.valueOf(input.charAt(i)));
        }
    }

    public static void addVT(String ch) {
        if (VTSet == null) {
            VTSet = new ArrayList<>();
            VTSet.add("$");
            VTSet.add("#");
        }

        VTSet.add(ch);
    }
}
