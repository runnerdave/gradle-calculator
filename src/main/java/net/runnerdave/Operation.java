package net.runnerdave;

import java.util.function.BinaryOperator;

/**
 * Created by David A. Jiménez (e67997) on 23/06/2017.
 */
public enum Operation {
    ADD, SUBTRACT, DIVIDE, MULTIPLY;

    private BinaryOperator<Integer> lambda;

    static {
        ADD.lambda = (u, t) -> u + t;
        SUBTRACT.lambda = (u, t) -> u - t;
        DIVIDE.lambda = (u, t) -> u / t;
        MULTIPLY.lambda = (u, t) -> u * t;
    }

    /**
     * Parses a string into a Operation.
     * @param operation the string representing the operation .
     * @return the Operation parsed, null if nothing matches.
     */
    public static Operation parseOperation(String operation) {
        Operation o = null;
        switch (operation.toUpperCase()) {
            case "ADD":
                o = ADD;
                break;
            case "SUB":
                o = SUBTRACT;
                break;
            case "DIV":
                o = DIVIDE;
                break;
            case "MUL":
                o = MULTIPLY;
                break;
        }
        return o;
    }

    public BinaryOperator<Integer> calculate() {
        return lambda;
    }
}
