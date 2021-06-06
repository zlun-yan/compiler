import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final char[] operatorList = {'+', '-', '*', '/', '=', '<', '!'};
    private static final char[] separationList = {';', ',', '{', '}', '(', ')'};
    private static final String[] keywordList = {"int","char","string","bool","float","double","float","true","false","return",
            "if","else","while","for","default","do","public","static","switch"};

    private static Integer position;

    public static void main(String[] args) throws IOException {

        BufferedReader file = new BufferedReader(new FileReader("src/input.txt"));
        String input = file.readLine();
        List<Token> words = new ArrayList<>();
        while (input != null) {
            words.addAll(scanLine(input.trim()));
            input = file.readLine();
        }

        for (Token token :
                words) {
            System.out.println(token);
        }
    }

    private static List<Token> scanLine(String line) {
        List<Token> result = new ArrayList<>();
        position = 0;

        while (position != line.length()) {
            if (judgeCharType(line.charAt(position)) == 5) {
                position++;
                continue;
            }
            switch (judgeCharType(line.charAt(position))) {
                case 0:  // 字母开头 标识符或者关键字
                    result.add(function_1(line));
                    break;
                case 1:  // 数字
                    result.add(function_2(line));
                    break;
                case 2:  // 运算符
                    result.add(function_3(line));
                    break;
                case 3:  // 分隔符
                    result.add(function_4(line));
                    break;
                default:  // 错误输入
                    result.add(function_error(line));
                    break;
            }
        }

        return result;
    }

    /**
     * 处理字母开头的字符串 判断是 关键字 标识符
     * @param line
     * @return
     */
    private static Token function_1(String line) {
        int startPos = position;
        // 先找到第一个非字母或数字的字符
        while (position != line.length()) {
            if (judgeCharType(line.charAt(position)) > 1) break;
            position++;
        }
        String word = line.substring(startPos, position);

        for (int i = 0; i < keywordList.length; i++) if (word.equals(keywordList[i])) return Token.getInstance(WordType.getInstance(1), word);
        return Token.getInstance(WordType.getInstance(2), word);
    }

    /**
     * 处理数字开头的字符串 判断是 常量
     * @param line
     * @return
     */
    private static Token function_2(String line) {
        int startPos = position;
        while (position != line.length()) {
            if (judgeCharType(line.charAt(position)) != 1) break;
            position++;
        }
        String word = line.substring(startPos, position);

        return Token.getInstance(WordType.getInstance(3), word);
    }

    /**
     * 处理运算符开头的字符 区别 <= !=
     * @param line
     * @return
     */
    private static Token function_3(String line) {
        int startPos = position;
        while (position != line.length()) {
            if (judgeCharType(line.charAt(position)) != 2) break;
            position++;
        }
        String word = line.substring(startPos, position);

        if (word.length() == 1) {
            if (word.equals("!")) return Token.getInstance(WordType.getInstance(6), word);
            else return Token.getInstance(WordType.getInstance(4), word);
        }
        else if (word.length() == 2) {
            if (word.equals("<=") || word.equals("!=") || word.equals("==")) return Token.getInstance(WordType.getInstance(4), word);
            else return Token.getInstance(WordType.getInstance(6), word);
        }
        else return Token.getInstance(WordType.getInstance(6), word);
    }

    /**
     * 处理分隔符开头的字符 分隔符都只有一个
     * @param line
     * @return
     */
    private static Token function_4(String line) {
        position++;
        return Token.getInstance(WordType.getInstance(5), line.substring(position - 1, position));
    }

    /**
     * 处理无法识别的字符
     * @param line
     * @return
     */
    private static Token function_error(String line) {
        int startPos = position;
        while (position != line.length()) {
            if (judgeCharType(line.charAt(position)) != 4) break;
            position++;
        }
        String word = line.substring(startPos, position);

        return Token.getInstance(WordType.getInstance(6), word);
    }

    /**
     * 检测一个字符是 字母 数字 运算符 分隔符
     * @param ch 需要检测的字符
     * @return 0字母 1数字 2运算符 3分隔符 4无法识别的字符 5空格
     */
    private static int judgeCharType(char ch) {
        if (ch == ' ') return 5;
        if (ch == '_') return 0;
        if ('a' <= ch && ch <= 'z') return 0;
        else if ('0' <= ch && ch <= '9') return 1;
        else {
            for (int i = 0; i < operatorList.length; i++) if (ch == operatorList[i]) return 2;
            for (int i = 0; i < separationList.length; i++) if (ch == separationList[i]) return 3;
        }

        return 4;
    }
}
