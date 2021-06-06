public class Token {
    private Integer code;
    private String value;

    public WordType getWord() {
        return WordType.getInstance(this.code);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Token getInstance(WordType wordType) {
        return getInstance(wordType, null);
    }

    public static Token getInstance(WordType wordType, String value) {
        if (wordType == null) {
            return null;
        }
        Token token = new Token();
        token.setCode(wordType.code());
        token.setValue(value);
        return token;
    }

    @Override
    public String toString() {
        if (code.equals(6)) return "unknown word: " + this.value;
        return "(" + this.code + ", " + this.value + ")";
    }
}
