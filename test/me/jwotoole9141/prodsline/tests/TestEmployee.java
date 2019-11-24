/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 23rd, 2019
 * PROJ: ProductionLineTracker
 * FILE: TestEmployee.java
 *
 * Defines the TestEmployee test driver class.
 */

package me.jwotoole9141.prodsline.tests;

import me.jwotoole9141.prodsline.user.Employee;

/**
 * Tests the employee class.
 *
 * @author Jared O'Toole
 */
class TestEmployee {

  /**
   * Runs the test.
   *
   * @param args unused command-line args
   */
  public static void main(String[] args) {

    System.out.println(new Employee(1, "Tim Lee", "aBc!", true));
    System.out.println();
    System.out.println(new Employee(3, "Bart", "1234", true));
    System.out.println();
  }
}
