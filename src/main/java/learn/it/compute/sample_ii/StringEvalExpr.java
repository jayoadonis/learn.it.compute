package learn.it.compute.sample_ii;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StringEvalExpr implements IEvalExpr {

  protected String expr;

  @Override
  public void setEvalExpr(String expr) {
    if (expr != null && !expr.isBlank())
      this.expr = expr;
  }

  @Override
  public List<String> getTokens() {
    List<String> tokens = new ArrayList<>(9);
    if (this.expr != null) {
      StringBuilder operand = new StringBuilder();
      boolean expectOperand = true;

      final int EXPR_LEN = this.expr.length();

      for (int i = 0; i < EXPR_LEN; ++i) {
        char ch = this.expr.charAt(i);
        if (IEvalExpr.isOperand(ch)) {
          operand.append(ch);
          expectOperand = false;
        } else if ((ch == '-' || ch == '+') && expectOperand) {
          operand.append(ch);
        } else if (IEvalExpr.isOperator(ch) || (ch == '(' || ch == ')')) {
          try {
            if (!operand.isEmpty())
              tokens.add(operand.toString());
            tokens.add(String.valueOf(ch));
          } finally {
            operand.setLength(0);
            expectOperand = (ch == '(');
          }
        } else if (!Character.isWhitespace(ch)) {
          throw new IllegalArgumentException(String.format("Invalid character in expression: '%c'", ch));
        }
      }

      if (!operand.isEmpty())
        tokens.add(operand.toString());
    }

    return tokens;
  }
}
