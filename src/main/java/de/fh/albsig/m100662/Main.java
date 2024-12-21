package de.fh.albsig.m100662;

import de.fh.albsig.m100662.ConverterUtils.Lengths;
import de.fh.albsig.m100662.ConverterUtils.NewNumber;
import de.fh.albsig.m100662.ConverterUtils.NumberFactory;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hauptklasse fuer den Converter.
 * Diese Klasse ermoeglichtt die Eingabe von Zahlenwerten und Einheiten,
 * um diese in eine weiter/andere Einheit umrechnen zu lassen.
 */
public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);
  
  /**
  * Startet das Hauptprogramm.
  *
  * @param args Die Befehlszeilenargumente.
  */
  public static void main(final String[] args) {
    System.out.println("Enter a value and a unit.");
    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    System.out.println("Value: ");
    BigDecimal value = null;
    try {
      if (scanner.hasNextBigDecimal()) {
        value = scanner.nextBigDecimal();
        scanner.nextLine();
        logger.info("Value has been given: " + value + "\n");
      } else {
        logger.warn("No value has been given. Restart and enter a value.");
        return;
      }
    } catch (Exception e) {
      logger.error("An error occurred while reading the value:" + e.getMessage());
      return;
    }

    System.out.println("Unit: ");
    String unit = null;
    logger.info("Unit has been given: " + unit + "\n");
    logger.info("Creating new number: " + value + " " + unit + "\n");

    try {
      if (scanner.hasNext()) {
        unit = scanner.nextLine().trim();
        if (Lengths.LENGTH_UNITS.contains(unit)) {
          logger.info("Unit has been given: " + unit);
        } else {
          logger.warn("Unit is invalid. Restart and enter a valid unit.");
          return;
        }
      }
    } catch (Exception e) {
      logger.error("An error occured while reading the unit: " + e.getMessage());
      return;
    }

    NewNumber inputNumber = NumberFactory.createNewNumber(value, unit);

    inputNumber.printNumber();

    if (inputNumber instanceof ConverterUtils.Lengths) {
      logger.info("inputNumber is an instance of ConverterUtils.Lengths.");
      ConverterUtils.Lengths lengthInput = (ConverterUtils.Lengths) inputNumber;
      lengthInput.printLengthUnits();
      lengthInput.convertLength();
      logger.info("Converting ...");
      lengthInput.printNumber();
    }

    scanner.close();
  }
}
