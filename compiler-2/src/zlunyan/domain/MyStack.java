package zlunyan.domain;

public class MyStack {
    private String str;

    public MyStack(String begin) {
        str = "#" + begin;
    }

    public void push(String temp) {
        for (int i = temp.length() - 1; i >= 0 ; i--) str = str + temp.charAt(i);
    }

    public String pop() {
        String pop = str.substring(str.length() - 1);
        str = str.substring(0, str.length() - 1);
        return pop;
    }

    @Override
    public String toString() {
        return str;
    }
}
