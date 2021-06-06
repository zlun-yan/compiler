package zlunyan;

import zlunyan.factory.*;
import zlunyan.service.LL1Service;
import zlunyan.test.TestMethod;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        /*
        0: 输入非终结符
        1:输入终结符
        2:输入产生式
        3:输入读取测试
        4:退出输入
         */

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();

            switch (count) {
                case 0:
                    try {
                        VNFactory.createVNArray(str);
                        count ++;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        VTFactory.createVTArray(str);
                        count ++;
                        System.out.println("");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    if (str.equals("")) {
                        count ++;
                        break;
                    }
                    ProductionFactory.createProduction(str);
                    break;
                case 3:
                    if (str.equals("")) {
                        count ++;
                        break;
                    }
                    InputFactory.addInput(str);
                    break;
            }

            if (count == 4) break;
        }

        ProductionFactory.eliminatingLeftRecursion();  // 消除左递归
        LL1Service.doFirstSet();  // 求First集
        LL1Service.doFollowSet();  // 求Follow集
        LL1Service.doLL1Table();  // 构造LL1分析表
        TestMethod.showVNAndVTAndProductionAndInput();
        TestMethod.showFirstSet();
        TestMethod.showFollowSet();
        TestMethod.showLL1Table();


        List<String> inputSet = InputFactory.getInstance();
        for (String input : inputSet) {
            LL1Service.analyse(input);
        }
    }
}
