package learn.it.compute.sample_i;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EvalExpr {

  private String infixExpr;

  public EvalExpr(String infixExpr) {
    if (infixExpr == null || infixExpr.isBlank())
      throw new IllegalArgumentException("Expression cannot be null or blank");
    this.infixExpr = infixExpr;
  }

  public void setInfixExpr(String infixExpr) {
    if (infixExpr != null && !infixExpr.isBlank()) {
      this.infixExpr = infixExpr;
    }
  }

  public String toPostfix() {
    Stack<Character> operators = new Stack<>();
    List<String> postFix = new ArrayList<>();

    //REM: Tokenize the input expression
    List<String> tokens = this.tokenizeIII(this.infixExpr);

    for (String token : tokens) {
      char firstChar = token.charAt(0);

      if ( EvalExpr.isOperand(firstChar) || ( EvalExpr.isOperator(firstChar) && token.length()>1 && EvalExpr.isOperand(token.charAt(1))) ) {
        //REM: Add operands directly to postfix
        postFix.add(token);
      } else if (EvalExpr.isOperator(firstChar)) {
        //REM: Handle operator precedence and associativity
        while (!operators.isEmpty() &&
          EvalExpr.hasHighestOrEqualPrecedence(operators.peek(), firstChar)
        ) {
          postFix.add(String.valueOf(operators.pop()));
        }
        operators.push(firstChar);
      } else if (firstChar == '(') {
        //REM: Push '(' onto the stack
        operators.push(firstChar);
      } else if (firstChar == ')') {
        //REM: Pop operators until '(' is encountered
        while (!operators.isEmpty() && operators.peek() != '(') {
          postFix.add(String.valueOf(operators.pop()));
        }
        if (operators.isEmpty() || operators.pop() != '(') {
          throw new IllegalArgumentException("Mismatched parentheses in expression");
        }
      }
    }

    //REM: Pop remaining operators
    while (!operators.isEmpty()) {
      char op = operators.pop();
      if (op == '(' || op == ')')
        throw new IllegalArgumentException("Mismatched parentheses in expression");
      postFix.add(String.valueOf(op));
    }

    return String.join(" ", postFix);
  }

  private static boolean isOperand(char op) {
    return Character.isDigit(op) || op == '.';
  }

  private static boolean hasHighestOrEqualPrecedence(char opI, char opII) {
    int p1 = EvalExpr.getPrecedenceOf(opI);
    int p2 = EvalExpr.getPrecedenceOf(opII);
    if (p1 == p2)
      return opI != '^';
    return p1 >= p2;
  }

  private static int getPrecedenceOf(char op) {
    return switch (op) {
      case '+', '-' -> 1;
      case '/', '%' -> 2;
      case '*' -> 3;
      case '^' -> 4;
      default -> -1;
    };
  }

  public static boolean isOperator(char op) {
    return switch (op) {
      case '+', '-', '/', '%', '*', '^' -> true;
      default -> false;
    };
  }

  private List<String> tokenizeI(String expr) {
    List<String> tokens = new ArrayList<>();
    StringBuilder operand = new StringBuilder();

    for (int i = 0; i < expr.length(); i++) {
      char c = expr.charAt(i);

      if (Character.isDigit(c) || c == '.' /*|| c == '^'*/) {
        //REM: Build multi-digit numbers or decimal numbers
//        if( c == '^' ) {
//          if (!operand.isEmpty()) {
//            tokens.add(operand.toString());
//            operand.setLength(0);
//          }
//        }
//        else {
          operand.append( c );
//        }

      } else if (EvalExpr.isOperator(c) || c == '(' || c == ')') {
        //REM: Add the operand number token (if any)

        if (!operand.isEmpty()) {
          tokens.add(operand.toString());
          operand.setLength(0);
        }
        //REM: Add the operator or parentheses
        tokens.add(String.valueOf(c));
      } else if (!Character.isWhitespace(c)) {
        throw new IllegalArgumentException("Invalid character in expression: " + c);
      }
    }

    //REM: Add the last number token (if any)
    if (!operand.isEmpty()) {
      tokens.add(operand.toString());
    }

//    System.out.println("<><> " + (tokens) );
    return tokens;
  }


  private List<String> tokenizeII(String expr) {
    List<String> tokens = new ArrayList<>();
    StringBuilder current = new StringBuilder();

    for (int i = 0; i < expr.length(); i++) {
      char c = expr.charAt(i);

      if (Character.isDigit(c) || c == '.') {
        //REM: Build multi-digit numbers or decimal numbers
        current.append(c);
      } else if (isOperator(c) || c == '^') {
        //REM: Handle signed numbers
        if ((c == '-' || c == '+') && (i == 0 || isOperator(expr.charAt(i - 1)) || expr.charAt(i - 1) == '(')) {
          //REM: Part of a signed number
          current.append(c);
        } else {
          //REM: Add the current number token (if any)
          if (!current.isEmpty()) {
            tokens.add(current.toString());
            current.setLength(0);
          }
          //REM: Add the operator
          tokens.add(String.valueOf(c));
        }
      } else if (c == '(' || c == ')') {
        //REM: Add the current number token (if any)
        if (!current.isEmpty()) {
          tokens.add(current.toString());
          current.setLength(0);
        }
        //REM: Add the parenthesis
        tokens.add(String.valueOf(c));
      } else if (!Character.isWhitespace(c)) {
        throw new IllegalArgumentException("Invalid character in expression: " + c);
      }
    }

    //REM: Add the last number token (if any)
    if (!current.isEmpty()) {
      tokens.add(current.toString());
    }

    return tokens;
  }

  private List<String> tokenizeIII(String expr) {
    List<String> tokens = new ArrayList<>();
    StringBuilder operand = new StringBuilder();

    boolean expectOperand = true; //REM: Tracks if we expect an operand or operator

    for (int i = 0; i < expr.length(); i++) {
      char c = expr.charAt(i);

      if (Character.isDigit(c) || c == '.') {
        //REM: Build multi-digit or decimal numbers
        operand.append(c);
        expectOperand = false; //REM: Next, we expect an operator
      } else if ((c == '-' || c == '+') && (expectOperand /*|| expr.charAt(i - 1) == '('*/)) {
        //REM: Handle negative and explicit positive unary/sign number. i.e: -3 or +3
        operand.append(c);
      } else if (EvalExpr.isOperator(c) || c == '(' || c == ')') {
        //REM: Finalize the current operand (if any)
        if (!operand.isEmpty()) {
          tokens.add(operand.toString());
          operand.setLength(0);
        }
        //REM: Add the operator or parentheses
        tokens.add(String.valueOf(c));
        expectOperand = (c == '('); //REM: After '(' we expect an operand
      } else if (!Character.isWhitespace(c)) {
        throw new IllegalArgumentException("Invalid character in expression: " + c);
      }
    }

    //REM: Add the last operand (if any)
    if (!operand.isEmpty()) {
      tokens.add(operand.toString());
    }
    return tokens;
  }

}
