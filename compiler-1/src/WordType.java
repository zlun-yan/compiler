import java.util.HashMap;
import java.util.Map;

public enum WordType {

    KEYWORD(1),
    IDENTIFIER(2),
    CONSTANT(3),
    OPERATOR(4),
    SEPARATION(5),

    UNKNOWN(6),


    ;

    private static Map<Integer, WordType> codeMap = new HashMap<Integer, WordType>();

    private Integer code;

    private WordType(Integer code) {
        this.code = code;
    }

    public Integer code() {
        return this.code;
    }

    /**
     * @param codeValue
     * @return
     */
    public static WordType getInstance(Integer codeValue) {
        return getCodeMap().get(codeValue);
    }

    /**
     * @return
     */
    private static Map<Integer, WordType> getCodeMap() {
        if (codeMap == null || codeMap.size() == 0) {
            WordType[] codeList = WordType.values();
            for (WordType c : codeList) {
                codeMap.put(c.code(), c);
            }
        }
        return codeMap;
    }
}
