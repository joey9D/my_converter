package de.fh.albsig.m100662;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.math.BigDecimal;


/**
 * Unit test for simple App.
 */
public class ConverterUtilsTest {

  private ByteArrayOutputStream outputStream;
  private PrintStream originalOut;
  private static final Logger logger = LogManager.getLogger(ConverterUtilsTest.class);

  @BeforeAll
  static void setUpClass() {
    //System.out.println("Initialisiere Tests...");
    logger.info("Initialize tests ...");
  }

  @AfterAll
  static void tearDownClass() {
    //System.out.println("Beende Tests...");
    logger.info("Finished tests.");
  }

  @BeforeEach
  void setUp() {
  outputStream = new ByteArrayOutputStream();
  originalOut = System.out;
  System.setOut(new PrintStream(outputStream));
}
  
  @AfterEach
  void tearDown() {
      System.setOut(originalOut);
  }

  @Test
  void testNewNumberConstructor() {
    BigDecimal value = BigDecimal.valueOf(10.5);
    String unit = "m";
    ConverterUtils.NewNumber number = new ConverterUtils.NewNumber(value, unit);
    assertEquals(value, number.getValue());
    assertEquals(unit, number.getUnit());
  }

  @Test
  void testNewNumberSettersAndGetters() {
    ConverterUtils.NewNumber number = new ConverterUtils.NewNumber(BigDecimal.valueOf(5), "cm");
    number.setValue(BigDecimal.valueOf(12.3));
    number.setUnit("m");
    assertEquals(BigDecimal.valueOf(12.3), number.getValue());
    assertEquals("m", number.getUnit());
  }

  @Test
  void testPrintNumber() {
    ConverterUtils.NewNumber number = new ConverterUtils.NewNumber(BigDecimal.valueOf(15), "km");
    number.printNumber();
    assertEquals("Input: 15 km\n", outputStream.toString());
  }

  @Test
  void testNumberFactory() {
    BigDecimal value = BigDecimal.valueOf(500);
    String lengthUnit = "m";
    String invalidUnit = "kg";
    ConverterUtils.NewNumber lengthNumber = ConverterUtils.NumberFactory.createNewNumber(value, lengthUnit);
    ConverterUtils.NewNumber invalidNumber = ConverterUtils.NumberFactory.createNewNumber(value, invalidUnit);
    assertNotNull(lengthNumber);
    assertTrue(lengthNumber instanceof ConverterUtils.Lengths);
    assertEquals(value, lengthNumber.getValue());
    assertEquals(lengthUnit, lengthNumber.getUnit());

    assertNull(invalidNumber);
  }

  @Test
  void testConvertLength() {
    ConverterUtils.Lengths length = new ConverterUtils.Lengths(BigDecimal.valueOf(10), "m");
    String simulatedInput = "km\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
    length.convertLength();
    assertEquals(BigDecimal.valueOf(0.01), length.getValue()); // 10 m = 0.01 km
    assertEquals("km", length.getUnit());
    System.setIn(System.in);
  }

}
