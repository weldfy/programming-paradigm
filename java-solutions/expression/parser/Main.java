package expression.parser;

import expression.operations.ShortOperation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        ExpressionParser<Short> exp = new ExpressionParser<>(new ShortOperation());
        System.out.println(exp.parse(in.nextLine()).evaluate((short) 8, (short) -6, (short) -4));
    }
}
