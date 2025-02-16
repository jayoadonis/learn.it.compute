package learn.it.compute.sample_ii;

import java.util.List;

public class Calculator {

  private EvalExprComponent evalExprComponent;

  public Calculator() {
    this( new PostfixEvalExpr() ); //REM: Default
  }

  public Calculator( EvalExprComponent evalExprComponent ) {
    this.evalExprComponent = evalExprComponent;
  }

  public void setEvalExprComponent( EvalExprComponent evalExprComponent ) {
    if( evalExprComponent != null )
      this.evalExprComponent = evalExprComponent;
  }

  public void setEvalExprComponent( String expr ) {
    if( this.evalExprComponent != null )
      this.evalExprComponent.setEvalExpr( expr );
  }

  public void setEvalExpr( IEvalExpr expr ) {
    if( this.evalExprComponent != null )
      this.evalExprComponent.setEvalExpr( expr );
  }

  public List<String> getTokens() {
    return this.evalExprComponent.getTokens();
  }

  public double calc() {
    return this.evalExprComponent.calc();
  }

  public double calc( String expr ) {
    this.setEvalExprComponent( expr );
    return this.calc();
  }

}
