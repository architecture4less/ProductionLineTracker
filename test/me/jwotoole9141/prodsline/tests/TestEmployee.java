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

public class TestEmployee {

  public static void main(String[] args) {

    System.out.println(new Employee("Tim Lee", "aBc!"));
    System.out.println();
    System.out.println(new Employee("Bart", "1234"));
    System.out.println();
  }
}
