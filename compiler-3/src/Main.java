import java.util.Scanner;

public class Main {
    public static int index;
    public static String input;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            index = 0;
            try {
                E();
//                System.out.println("success");
                System.out.println(input + " 为合法的输入串");
            } catch (GrammarException e) {
//                System.out.println("error");
                System.out.println(input + " 非法的输入串");
                System.out.println("在\"" + input.substring(0, index + 1) + "\"处出错");
            }
        }
    }

    public static void E() throws GrammarException{
        T();
        G();
    }

    public static void T() throws GrammarException{
        F();
        S();
    }

    public static void G() throws GrammarException{
        char sym = input.charAt(index);
        if (sym == '+' || sym == '-') {
            index++;
            T();
            G();
        }
    }

    public static void S() throws GrammarException{
        char sym = input.charAt(index);
        if (sym == '*' || sym == '/') {
            index++;
            F();
            S();
        }
    }

    public static void F() throws GrammarException{
        char sym =input.charAt(index);
        if (sym == '(') {
            index++;
            E();
            if (sym == ')') index++;
            else throw new GrammarException();
        } else if (sym == 'i') {

        } else throw new GrammarException();
    }
}
