package de.fh.albsig.m100662;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Class for converting various length units.
 * Contains functions to convert input numbers.
 */
public class ConverterUtils {
  /**
   * Class to create a new number with value and unit.
   */
  public static class NewNumber {
    /**
     * numerical value.
     */
    private BigDecimal value;
    /**
     * string representation of the unit of the number.
     */
    private String unit;

    /**
     * Constructor for new number with value and unit.
     *
     * @param value The numerical value of the number
     * @param unit The string representation of the unit of the number
     */
    public NewNumber(final BigDecimal value, final String unit) {
      this.value = value;
      this.unit = unit;
    }

    /**
     * Getter method for the value.
     *
     * @return value
     */
    public BigDecimal getValue() {
      return value;
    }

    /**
     * getter method for the unit.
     *
     * @return unit
     */
    public String getUnit() {
      return unit;
    }

    /**
     * setter method for the value.
     *
     * @param value the new value
     */
    public void setValue(final BigDecimal value) {
      this.value = value;
    }

    /**
     * setter method for the unit.
     *
     * @param unit the new unit
     */
    public void setUnit(final String unit) {
      this.unit = unit;
    }

    /**
     * custom print method to print the number with value and unit.
     */
    public void printNumber() {
      System.out.println("Input: " + this.getValue() + " " + this.getUnit());
    }
  }

  /**
   * Class that takes the number, turning it into a dedicated lengthnumber.
   * Contains the conversion  values and conversion functions.
   */
  public static class Lengths extends NewNumber {
    /**
     * An ArrayList which contains length unit strings.
     */
    static final List<String> LENGTH_UNITS = Arrays.asList(
        "km", "m", "dm", "cm", "mm"
    );
    /**
     * Conversionfactor for meters.
     */
    public static final BigDecimal METER_FACTOR =
        BigDecimal.valueOf(1.0);
    /**
     * Conversionfactor for decimeters.
     */
    public static final BigDecimal DECIMETER_FACTOR =
        BigDecimal.valueOf(0.1);
    /**
     * Conversionfactor for centimetres.
     */
    public static final BigDecimal CENTIMETER_FACTOR =
        BigDecimal.valueOf(0.01);
    /**
     * Conversionfactor for millimetres.
     */
    public static final BigDecimal MILLIMETER_FACTOR =
        BigDecimal.valueOf(0.001);
    /**
     * Conversionfactor for kilometers.
     */
    public static final BigDecimal KILOMETER_FACTOR =
        BigDecimal.valueOf(1000.0);
    /**
     * Conversionfactor for miles.
     */
    public static final BigDecimal MILE_FACTOR =
        BigDecimal.valueOf(1609.34);
    
    /**
     * Map that connects the length units with their
     * corresponding conversionvalues.
     */
    static final Map<String, BigDecimal>
        CONVERSION_FACTORS = new HashMap<>();

    static {
      CONVERSION_FACTORS.put("m", METER_FACTOR);
      CONVERSION_FACTORS.put("dm", DECIMETER_FACTOR);
      CONVERSION_FACTORS.put("cm", CENTIMETER_FACTOR);
      CONVERSION_FACTORS.put("mm", MILLIMETER_FACTOR);
      CONVERSION_FACTORS.put("km", KILOMETER_FACTOR);
    }

    /**
     * Constructor for length number. Uses parent constructor.
     *
     * @param lengthValue dedicated length value
     * @param lengthUnit dedicated length unit
     */
    public Lengths(final BigDecimal lengthValue, final String lengthUnit) {
      super(lengthValue, lengthUnit);
    }

    /**
     * Printfunction. Prints available length units.
     */
    public void printLengthUnits() {
      System.out.println("\nUnits to choose from: ");
      for (int i = 0; i < LENGTH_UNITS.size(); i++) {
        System.out.println(LENGTH_UNITS.get(i));
      }
    }

    /**
     * Convert numerical value according to chosen result unit.
     */
    public void convertLength() {
      System.out.println("\nChoose a result unti:");

      Scanner unitScanner = new Scanner(System.in, StandardCharsets.UTF_8);
      String resultUnit = unitScanner.nextLine();
      // throw exception on pre-requiered condition
      if (!CONVERSION_FACTORS.containsKey(this.getUnit())
          || !CONVERSION_FACTORS.containsKey(resultUnit)) {
        throw new IllegalArgumentException(
          "Invalid unit for conversion: " + resultUnit
        );
      }

      BigDecimal siValue = this.getValue().multiply(
          CONVERSION_FACTORS.get(this.getUnit())
      );
      BigDecimal resultValue = siValue.divide(
          CONVERSION_FACTORS.get(resultUnit)
      );

      this.setValue(resultValue);
      this.setUnit(resultUnit);

      unitScanner.close();
    }

  }

  /**
   * Factory to create a new number with value and unit.
   */
  public static class NumberFactory {
    
    /**
     * Number factory chooses right constructor according to the unit parameter.
     *
     * @param value input value
     * @param unit input unit
     * @return number object fitting the given unit.
     */
    public static NewNumber createNewNumber(
        final BigDecimal value, final String unit
    ) {
      if (Lengths.LENGTH_UNITS.contains(unit)) {
        return new Lengths(value, unit);
      } else {
        return null;
      }
    }
  }
}
