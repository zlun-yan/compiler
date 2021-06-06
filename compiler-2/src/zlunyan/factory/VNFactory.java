package zlunyan.factory;

import zlunyan.domain.VNElement;

import java.util.ArrayList;
import java.util.List;

/**
 * 非终结符集合
 */
public class VNFactory {
    private static List<VNElement> VNSet;

    private VNFactory() {

    }

    public static List<VNElement> getInstance() {
        if (VNSet == null) {
            VNSet = new ArrayList<>();
        }

        return VNSet;
    }

    public static void createVNArray(String input) throws NullPointerException{
        if (input.equals("") || input == null) {
            throw new NullPointerException("空字符串");
        }
        VNSet = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            VNElement vnElement = new VNElement();
            vnElement.setFather(String.valueOf(input.charAt(i)));
            VNSet.add(vnElement);
        }
        VNSet.get(0).getFollowSet().add("#");
    }

    public static void addVN(String vn) {
        VNElement vnElement = new VNElement();
        vnElement.setFather(vn);
        VNSet.add(vnElement);
    }
}
