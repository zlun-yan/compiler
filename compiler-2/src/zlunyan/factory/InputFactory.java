package zlunyan.factory;

import java.util.ArrayList;
import java.util.List;

public class InputFactory {
    private static List<String> InputSet;

    private InputFactory() {

    }

    public static List<String> getInstance() {
        if (InputSet == null) {
            InputSet = new ArrayList<>();
        }

        return InputSet;
    }

    public static void addInput(String ch) {
        if (InputSet == null) {
            InputSet = new ArrayList<>();
        }

        InputSet.add(ch);
    }
}
