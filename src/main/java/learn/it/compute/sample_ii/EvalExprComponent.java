package learn.it.compute.sample_ii;

import java.util.List;

public abstract class EvalExprComponent implements IEvalExpr {

  protected IEvalExpr evalExpr;

  private EvalExprComponent() {
  }

  public EvalExprComponent( IEvalExpr evalExpr ) {
    this.evalExpr = evalExpr;
  }

  public void setEvalExpr( IEvalExpr evalExpr ) {
    if( evalExpr != null )
      this.evalExpr = evalExpr;
  }

  @Override
  public void setEvalExpr( String expr ) {
    if( this.evalExpr != null )
      this.evalExpr.setEvalExpr( expr );
  }

  @Override
  public List<String> getTokens() {
    if( this.evalExpr != null )
      return this.evalExpr.getTokens();
    return null;
  }

  public abstract List<String> getFinalizedExpr();

  public abstract double calc();

}
