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

import static ru.mfilatov.prayingtimes.calculator.functions.MathFunctions.asin;
import static ru.mfilatov.prayingtimes.calculator.functions.MathFunctions.atan2;
import static ru.mfilatov.prayingtimes.calculator.functions.MathFunctions.cos;
import static ru.mfilatov.prayingtimes.calculator.functions.MathFunctions.fixAngle;
import static ru.mfilatov.prayingtimes.calculator.functions.MathFunctions.fixHour;
import static ru.mfilatov.prayingtimes.calculator.functions.MathFunctions.sin;

public class SunPositionCalculator {

  /**
   * Compute declination angle of sun and equation of time
   *
   * <p>Ref: http://aa.usno.navy.mil/faq/docs/SunApprox.php
   *
   * @param jd
   * @return
   */
  public SunPosition usnoMethod(double jd) {
    double D = jd - 2451545.0;
    double g = fixAngle(357.529 + 0.98560028 * D);
    double q = fixAngle(280.459 + 0.98564736 * D);
    double L = fixAngle(q + (1.915 * sin(g)) + (0.020 * sin(2 * g)));
    double e = 23.439 - 0.00000036 * D;
    double d = asin(sin(e) * sin(L));
    double ra = (atan2((cos(e) * sin(L)), (cos(L)))) / 15.0;
    double eqt = q / 15.0 - fixHour(ra);

    return new SunPosition(d, eqt);
  }

  public record SunPosition(double declination, double equation) {}
}
