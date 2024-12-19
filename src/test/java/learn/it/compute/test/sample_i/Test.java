package learn.it.compute.test.sample_i;

import learn.it.compute.sample_i.*;
import org.junit.jupiter.api.Assertions;

public class Test {

  @org.junit.jupiter.api.Test
  public void testInit() {
    Calculator calculator = new Calculator();

    calculator.setEvalExpr("5+1*2.0*1/2+( 3  -1    )+0.5-8");

    Assertions.assertEquals( 0.50, calculator.calc());

    calculator.setEvalExpr("5 / 3 / 2 ");

    //REM: [TODO] .|. Hmmm...
    Assertions.assertTrue(Math.abs(0.83334 - calculator.calc() ) <= 1e-4,
      "Results cannot be considered approximately equals");
  }

  @org.junit.jupiter.api.Test
  public void testModulo() {

    Calculator calculator = new Calculator();
    calculator.setEvalExpr("5 % 3 % 2 ");

    Assertions.assertEquals( 0, calculator.calc() );

    calculator.setEvalExpr("5 % 3 ");

    Assertions.assertEquals( 2, calculator.calc() );

  }

  @org.junit.jupiter.api.Test
  public void testPower() {

    Calculator calculator = new Calculator();

    calculator.setEvalExpr("2^31-1");

    Assertions.assertEquals(2_147_483_647.0, calculator.calc() );

    calculator.setEvalExpr("2^(31-1)");

    Assertions.assertEquals(1_073_741_824.0, calculator.calc() );

    calculator.setEvalExpr("2^(-1)");

    Assertions.assertEquals(0.5, calculator.calc() );

    calculator.setEvalExpr("1+(+9*(-3))^(-3)");

    //REM: [TODO] .|. Hmmm...
    final double APPROXIMATELY_RESULT = Math.abs(0.9999491947 - calculator.calc());

    Assertions.assertTrue(
      Double.compare(APPROXIMATELY_RESULT, 1e-9) <= 0,
      "Results cannot be considered approximately equals"
    );

  }

  @org.junit.jupiter.api.Test
  public void testUnaryOrSignedNumber() {
    Calculator calculator = new Calculator();

    calculator.setEvalExpr("-9");

    Assertions.assertEquals(-9.0, calculator.calc() );

    calculator.setEvalExpr("+911");

    Assertions.assertEquals(911, calculator.calc() );

    calculator.setEvalExpr("-1*(-9+1*(-1))");

    Assertions.assertEquals( 10, calculator.calc() );
  }
}
