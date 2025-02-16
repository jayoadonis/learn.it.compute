package learn.it.compute.sample_ii;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostfixEvalExpr extends EvalExprComponent {

  public PostfixEvalExpr() {
    this( new StringEvalExpr() );  //REM: Default
  }

  public PostfixEvalExpr(IEvalExpr evalExpr ) {
    super( evalExpr );
  }

  @Override
  public List<String> getFinalizedExpr() {
    List<String> tokens = super.getTokens();
    List<String> postfix = new ArrayList<>();
    Stack<Character> operators = new Stack<>();

    for( int i = 0; i < tokens.size(); ++i ) {
      char ch = tokens.get(i).charAt(0);
      if(
        IEvalExpr.isOperand( ch )
        || ( IEvalExpr.isOperator(ch)
          && tokens.get(i).length() > 1 && IEvalExpr.isOperand( tokens.get(i).charAt(1) )
        )
      ) {
        postfix.add( tokens.get(i) );
      }
      else if( IEvalExpr.isOperator( ch ) ) {
        while(
          !operators.isEmpty()
          && IEvalExpr.hasHighestOrEqualPrecedence(operators.peek(), ch)
        ) {
          postfix.add( Character.toString(operators.pop()) );
        }
        operators.push(ch);
      }
      else if( ch == '(' ) {
        operators.push(ch);
      }
      else if( ch == ')' ) {
        while(
          !operators.isEmpty()
          && operators.peek() != '('
        ) {
          postfix.add( Character.toString( operators.pop() ) );
        }
        if( operators.isEmpty() || operators.pop() != '(')
          throw new IllegalArgumentException("Mismatched parentheses in expression");
      }
    }

    while( !operators.isEmpty() ) {
      char op = operators.pop();
      if( op == '(' || op == ')' )
        throw new IllegalArgumentException("Mismatched parenthesis in expression");
      postfix.add( Character.toString(op) );
    }

    return postfix;
  }

  @Override
  public double calc() {
    List<String> postfixTokens = this.getFinalizedExpr();
    return 0.0d;
  }
}
