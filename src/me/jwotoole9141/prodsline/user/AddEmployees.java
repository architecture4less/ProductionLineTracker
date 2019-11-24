/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 23rd, 2019
 * PROJ: ProductionLineTracker
 * FILE: AddEmployees.java
 *
 * Defines the AddEmployees driver class.
 */

package me.jwotoole9141.prodsline.user;

import java.sql.SQLException;
import java.util.Scanner;
import me.jwotoole9141.prodsline.Model;

/**
 * A Mini application that registers new employees to the database.
 *
 * @author Jared O'Toole
 */
class AddEmployees {

  /**
   * The input scanner.
   */
  private static final Scanner IN = new Scanner(System.in, "utf-8");

  /**
   * Starts the mini app.
   *
   * @param args unused console args
   */
  public static void main(String[] args) {

    Model.open();

    System.out.println("\nAdd employees to the database.");

    while (true) {

      // get the employee's name...
      System.out.println("\n  Enter the employee's first and last name.");
      System.out.print("  >>> ");
      String name = IN.nextLine();

      // get the employee's password...
      System.out.println("\n  Enter the employee's password.");
      System.out.print("  >>> ");
      String pass = IN.nextLine();

      // try to add new employee...
      try {
        Employee user = Model.registerUser(name, pass);
        System.out.println("\n  Successfully registered.");
        System.out.println("\n" + user.toString());

      } catch (SQLException | IllegalStateException ex) {
        ex.printStackTrace();

      } catch (IllegalArgumentException ex) {
        System.out.println("\n  " + ex.getMessage());
      }

      // check if user wants to continue...
      System.out.println("\nAdd another employee?");
      System.out.print("  >>> ");
      String response = IN.nextLine();

      if (!response.trim().toLowerCase().startsWith("y")) {
        break;
      }
    }
    Model.close();
  }
}
