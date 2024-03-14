package ru.mfilatov.functions;

import static ru.mfilatov.functions.MathFunctions.asin;
import static ru.mfilatov.functions.MathFunctions.atan2;
import static ru.mfilatov.functions.MathFunctions.cos;
import static ru.mfilatov.functions.MathFunctions.fixAngle;
import static ru.mfilatov.functions.MathFunctions.fixHour;
import static ru.mfilatov.functions.MathFunctions.sin;

public class SunPositionCalculator {
    

    /**

     * Compute declination angle of sun and equation of time

     * Ref: http://aa.usno.navy.mil/faq/docs/SunApprox.php

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

        double ra = (atan2((cos(e) * sin(L)), (cos(L))))/ 15.0;

        double eqt = q/15.0 - fixHour(ra);


        return new SunPosition(d, eqt);

    }
    public record SunPosition(double declination, double equation){}

}
