/*
 * PrayingTimes Calculator
 * Copyright (C) 2024 Mikhail Filatov
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ru.mfilatov.prayingtimes.calculator.functions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MathFunctions {
  private static final double HOURS_IN_DAY = 24.0;
  private static final double DEGREES_IN_CIRCLE = 360.0;

  public static double sin(double degrees) {
    return Math.sin(Math.toRadians(degrees));
  }

  public static double cos(double degrees) {
    return Math.cos(Math.toRadians(degrees));
  }

  public static double tan(double degrees) {
    return Math.tan(Math.toRadians(degrees));
  }

  /**
   * Returns the arc sine of a value in degrees
   *
   * @param x - the value whose arc sine is to be returned.
   * @return the arc sine of the argument.
   */
  public static double asin(double x) {

    return Math.toDegrees(Math.asin(x));
  }

  public static double acos(double x) {
    return Math.toDegrees(Math.acos(x));
  }

  public static double atan(double x) {
    return Math.toDegrees(Math.atan(x));
  }

  public static double acot(double x) {
    return Math.toDegrees(Math.atan(1.0 / x));
  }

  public static double atan2(double x, double y) {
    return Math.toDegrees(Math.atan2(x, y));
  }

  public static double modulo(double x, double y) {
    double mod = x % y;

    // if the sign are negative and modulo not zero, adjust result
    if (x < 0 && mod != 0) {
      mod += y;
    }

    return Math.abs(mod);
  }

  public static double fixAngle(double angle) {
    return modulo(angle, DEGREES_IN_CIRCLE);
  }

  public static double fixHour(double hour) {
    return modulo(hour, HOURS_IN_DAY);
  }

  public static double timeDiff(double time1, double time2) {
    return fixHour(time2 - time1);
  }

}
