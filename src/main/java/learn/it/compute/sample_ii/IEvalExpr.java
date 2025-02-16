package learn.it.compute.sample_ii;

import java.util.List;

public interface IEvalExpr {

  public abstract void setEvalExpr( String expr );
  public abstract List<String> getTokens();

  public static boolean isOperator( char op ) {
    return IEvalExpr.isOperator( String.valueOf(op) );
  }

  public static boolean isOperator( String op ) {
    return switch( op.toUpperCase() ) {
      case "-", "+", "/", "%", "*", "^", "SQRT" -> true;
      default -> false;
    };
  }

  public static boolean isOperand( char operand ) {
    return Character.isDigit(operand) || operand == '.';
  }

  public static byte getPrecedenceOf( char op ) {
    return IEvalExpr.getPrecedenceOf( String.valueOf(op) );
  }

  public static byte getPrecedenceOf( String op ) {
    return switch( op.toUpperCase() ) {
      case "-", "+" -> 1;
      case "/", "%" -> 2;
      case "*" -> 4;
      case "^", "SQRT" -> 8;
      default -> -1;
    };
  }

  public static boolean hasHighestOrEqualPrecedence( char opI, char opII ) {
    return IEvalExpr.getPrecedenceOf(opI) >= IEvalExpr.getPrecedenceOf(opII);
  }

  public static double calc( char op, double operandI, double operandII ) {
    return IEvalExpr.calc(
      Character.toString(op), operandI, operandII
    );
  }

  public static double calc( String op, double operandI, double operandII ) {
    return switch( op.toUpperCase() ) {
      case "-" -> operandI - operandII;
      case "+" -> operandI + operandII;
      case "/" -> {
        if( operandII == 0 ) throw new ArithmeticException("Division by zero.");
        yield operandI / operandII;
      }
      case "%" -> operandI % operandII;
      case "^" -> Math.pow( operandI, operandII );
      case "SQRT" -> Math.sqrt( operandII ); //REM: [TODO] .|. Hmmm...
      default -> throw new IllegalArgumentException(
        String.format("Invalid Arithmetic operation: '%s'", op)
      );
    };
  }
}
