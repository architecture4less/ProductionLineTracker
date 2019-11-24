/*
 * AUTH: Jared O'Toole
 * DATE: Sat, Nov 23rd, 2019
 * PROJ: ProductionLineTracker
 * FILE: Employee.java
 *
 * Defines the Employee class.
 */

package me.jwotoole9141.prodsline.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an employee for the production audit trail.
 *
 * @author Jared O'Toole
 */
public class Employee {

  private static final String EMAIL_SUFFIX = "@oracleacademy.Test";
  private static final Pattern LOWER_PATTERN = Pattern.compile("[a-z]+");
  private static final Pattern UPPER_PATTERN = Pattern.compile("[A-Z]+");
  private static final Pattern OTHER_PATTERN = Pattern.compile("[^a-zA-Z0-9]+");

  private String name;
  private String username;
  private String password;
  private String email;

  /**
   * Creates a representation of an employee with their credentials.
   *
   * @param firstLastName the employee's first and last name, separated by a space
   * @param password      the employee's initial password (one upper, lower, and special character)
   * @param useDefaults   if true, replace invalid arguments with defaults
   */
  public Employee(String firstLastName, String password, boolean useDefaults) {

    // try to set the name, or set a default...
    try {
      setName(firstLastName);
    } catch (IllegalArgumentException ex) {

      if (!useDefaults) {
        throw ex;
      }
      this.name = firstLastName.trim();
      this.username = "default";
      this.email = "user" + EMAIL_SUFFIX;
    }

    // try to set the password, or set a default...
    try {
      setPassword(password);
    } catch (IllegalArgumentException ex) {

      if (!useDefaults) {
        throw ex;
      }
      this.password = "pw";
    }
  }

  /**
   * Gets the employee's name.
   *
   * @return the first and last name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the employee's username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the employee's email.
   *
   * @return the email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the employee's password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the employee's first & last name, username, and email address.
   *
   * <p>
   * A valid name contains a first name and a last name separated by a space.
   * </p>
   *
   * @param firstLastName the name
   * @throws IllegalArgumentException an invalid name was given
   */
  public void setName(String firstLastName) {

    if (!isValidName(firstLastName)) {
      throw new IllegalArgumentException(
          "The given name was invalid. There should be a "
              + "first and last name separated by a space.");
    }
    this.name = firstLastName.trim();

    String[] tokens = name.split(" ", 2);
    setUsername(tokens[0], tokens[1]);
    setEmail(tokens[0], tokens[1]);
  }

  /**
   * Sets the employee's password.
   *
   * <p>
   * A valid password contains at least one lowercase character, one uppercase character, and one
   * special character (that is not a letter or a number).
   * </p>
   *
   * @param password the password
   * @throws IllegalArgumentException an invalid password was given
   */
  public void setPassword(String password) {

    if (!isValidPassword(password)) {
      throw new IllegalArgumentException("The given password was invalid. It should "
          + "contain a capital letter, a lowercase letter, and a special character.");
    }
    this.password = password;
  }

  /**
   * Tests if the given name is valid or not.
   *
   * <p>
   * A valid name contains a first name and a last name separated by a space.
   * </p>
   *
   * @param name the employee's name
   * @return true if the name is valid, else false
   */
  private boolean isValidName(String name) {

    return name.contains(" ");
  }

  /**
   * Tests if the given password is valid or not.
   *
   * <p>
   * A valid password contains at least one lowercase character, one uppercase character, and one
   * special character (that is not a letter or a number).
   * </p>
   *
   * @param password the password to test
   * @return true if the password is valid, else false
   */
  private boolean isValidPassword(String password) {

    Matcher upperMatcher = UPPER_PATTERN.matcher(password);
    Matcher lowerMatcher = LOWER_PATTERN.matcher(password);
    Matcher otherMatcher = OTHER_PATTERN.matcher(password);

    return upperMatcher.find() && lowerMatcher.find() && otherMatcher.find();
  }

  /**
   * Sets the username of this employee based on their name.
   *
   * <p>
   * The username is formed from the first letter of the first name concatenated with the full last
   * name, all in lowercase.
   * </p>
   *
   * @param firstName the employee's first name
   * @param lastName  the employee's surname
   */
  private void setUsername(String firstName, String lastName) {

    this.username = firstName.toLowerCase().substring(0, 1) + lastName.toLowerCase();
  }

  /**
   * Sets the email address of this employee based on their name.
   *
   * <p>
   * The email if formed from the first name joined with the last name by a period, all in
   * lowercase.
   * </p>
   *
   * @param firstName the employee's first name
   * @param lastName  the employee's surname
   */
  private void setEmail(String firstName, String lastName) {

    this.email = firstName.toLowerCase() + "." + lastName.toLowerCase() + EMAIL_SUFFIX;
  }

  /**
   * Gets a string representation of this employee's credentials.
   *
   * @return a multiline string
   */
  @Override
  public String toString() {

    return "Employee Details\n"
        + "Name : " + name + "\n"
        + "Username : " + username + "\n"
        + "Email : " + email + "\n"
        + "Initial Password : " + password;
  }
}
