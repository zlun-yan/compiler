package zlunyan.utils;

import zlunyan.domain.VNElement;
import zlunyan.factory.VNFactory;
import zlunyan.factory.VTFactory;

import java.util.*;

public class Util {
    /**
     * 判断是否为 VT 终结符
     * @param temp
     * @return
     */
    public static boolean judgeVT(String temp) {
        List<String> vtList = VTFactory.getInstance();
        for (int i = 0; i < vtList.size(); i++) {
            if (temp.equals(vtList.get(i))) return true;
        }

        return false;
    }

    /**
     * 判断这个集合中是否有空字符串
     * @param strings
     * @return
     */
    public static boolean judgeEmptyString(Set<String> strings) {
        Iterator<String> stringIterator = strings.iterator();
        for (int i = 0; i < strings.size(); i++) {
            if (stringIterator.next().equals("$")) return true;
        }

        return false;
    }

    /**
     * 通过String 获取对应的VNElement
     * @param str
     * @return
     */
    public static VNElement getVNElementByString(String str) {
        List<VNElement> vnElementList = VNFactory.getInstance();
        for (int i = 0 ; i < vnElementList.size(); i++) {
            if (vnElementList.get(i).getFather().equals(str)) return vnElementList.get(i);
        }

        return null;  // 这里应该做出一个异常处理，保证不会返回 null
    }

    public static Set<String> getStringFirstSet(String str) {
        Set<String> temp = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            String currentChar = str.substring(i, i + 1);
            if (judgeVT(currentChar)) {  // 如果是VT 终结符
                temp.add(currentChar);
                break;
            } else {  // 如果是VN 非终结符
                temp.addAll(getVNElementByString(currentChar).getFirstSet());
                if (!judgeEmptyString(getVNElementByString(currentChar).getFirstSet())) break;
            }
        }

        return temp;
    }
}
