package learn.it.compute.test.sample_ii;

import learn.it.compute.sample_ii.Calculator;
import learn.it.compute.sample_ii.PostfixEvalExpr;
import learn.it.compute.sample_ii.StringEvalExpr;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class Test {

  @org.junit.jupiter.api.Test
  public void testInit() {
    Calculator calculator = new Calculator();
    double x = calculator.calc("3");

  }

  @org.junit.jupiter.api.Test
  public void testStringEvalExpr() {
    StringEvalExpr evalExpr = new StringEvalExpr();
    evalExpr.setEvalExpr("3+3*(-1)^23");

    Assertions.assertArrayEquals(
      List.<String>of("3", "+", "3", "*", "(", "-1", ")", "^", "23").toArray(),
      evalExpr.getTokens().toArray()
    );
  }

  @org.junit.jupiter.api.Test
  public void testPostfixEvalExpr() {
    PostfixEvalExpr postfixEvalExpr = new PostfixEvalExpr();
    postfixEvalExpr.setEvalExpr("3+3*(-1)^23");

    Assertions.assertArrayEquals(
      List.<String>of("3", "3", "-1", "23", "^", "*", "+").toArray(),
      postfixEvalExpr.getFinalizedExpr().toArray()
    );
  }
}
