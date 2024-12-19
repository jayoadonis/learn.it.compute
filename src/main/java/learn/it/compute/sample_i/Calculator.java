package learn.it.compute.sample_i;

import java.util.Stack;

public class Calculator {

  private EvalExpr evalExpr;

  /**
   *
   * @param evalExpr - An Object that accept infix expression
   */
  public void setEvalExpr(EvalExpr evalExpr) {
    this.evalExpr = evalExpr;
  }

  /**
   *
   * @param evalExpr - Infix expression
   */
  public void setEvalExpr(String evalExpr) {
    this.evalExpr = new EvalExpr(evalExpr);
  }

  public String getPostfix() {
    if (this.evalExpr == null)
      throw new IllegalStateException("EvalExpr is not set");
    return this.evalExpr.toPostfix();
  }

  public double calc() {
    return Calculator.calcPostfix(this.getPostfix());
  }

  private static double calcPostfix(String postfixExpr) {
    Stack<Double> operands = new Stack<>();
    final String[] expr = postfixExpr.split(" ");

    for (String token : expr) {
      if (token.isBlank()) continue;

      final String TRIMMED_TOKEN = token.trim();
//      if (TRIMMED_TOKEN.isEmpty()) continue;

      char currentChar = TRIMMED_TOKEN.charAt(0);
      if (EvalExpr.isOperator(currentChar) && TRIMMED_TOKEN.length() == 1) {
        if (operands.size() < 2)
          throw new IllegalStateException("Invalid postfix expression: insufficient operands");
        double y = operands.pop();
        double x = operands.pop();
        operands.push( Calculator.calc(currentChar, x, y) );
      } else {
//        try {
          operands.push(Double.parseDouble(TRIMMED_TOKEN));
//        }
//        catch( final NumberFormatException nFEx) {
//
//        }
      }
    }

    if (operands.size() != 1)
      throw new IllegalStateException("Invalid postfix expression: no leftover operands/no possible final output.");

    return operands.peek();
  }

  private static double calc(char op, double operandI, double operandII) {
//    System.out.printf("%.2f %c %.2f\n", operandI, op, operandII);
    return switch (op) {
      case '-' -> operandI - operandII;
      case '+' -> operandI + operandII;
      case '%' -> operandI % operandII;
      case '/' -> {
        if (operandII == 0) throw new ArithmeticException("Division by zero");
        yield operandI / operandII;
      }
      case '*' -> operandI * operandII;
      case '^' -> {
        yield Math.pow(operandI, operandII);
      }
      default -> throw new IllegalArgumentException("Unsupported operator: " + op);
    };
  }
}