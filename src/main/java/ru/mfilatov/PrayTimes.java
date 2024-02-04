// --------------------- Copyright Block ----------------------
/*

PrayTimes.java: Prayer Times Calculator (ver 2.3)
Copyright (C) 2007-2011 PrayTimes.org

Java Code By: Mikhail Filatov
Original JS Code By: Hamid Zarrabi-Zadeh

License: GNU LGPL v3.0

TERMS OF USE:
	Permission is granted to use this code, with or
	without modification, in any website or application
	provided that credit is given to the original work
	with a link back to PrayTimes.org.

This program is distributed in the hope that it will
be useful, but WITHOUT ANY WARRANTY.

PLEASE DO NOT REMOVE THIS COPYRIGHT BLOCK.

*/

// --------------------- Help and Manual ----------------------
/*

User's Manual:
http://praytimes.org/manual

Calculation Formulas:
http://praytimes.org/calculation



//------------------------ User Interface -------------------------


	getTimes (date, coordinates [, timeZone [, dst [, timeFormat]]])

	setMethod (method)       // set calculation method
	adjust (parameters)      // adjust calculation parameters
	tune (offsets)           // tune times by given offsets

	getMethod ()             // get calculation method
	getSetting ()            // get current calculation parameters
	getOffsets ()            // get current time offsets


//------------------------- Sample Usage --------------------------


	var PT = new PrayTimes('ISNA');
	var times = PT.getTimes(new Date(), [43, -80], -5);
	document.write('Sunrise = '+ times.sunrise)


*/

package ru.mfilatov;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.mfilatov.enums.CalculationMethod;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

@AllArgsConstructor
public class PrayTimes {
private final CalculationMethod method;

    static class DMath {
        Function<Double, Double> dtr = d -> (d * Math.PI) / 180.0;
        Function<Double, Double> rtd = r -> (r * 180.0) / Math.PI;
        Function<Double, Double> sin = d -> Math.sin(dtr.apply(d));
        Function<Double, Double> cos = d -> Math.cos(dtr.apply(d));
        Function<Double, Double> tan = d -> Math.tan(dtr.apply(d));
        Function<Double, Double> arcsin = d -> (d * Math.PI) / 180.0;
        Function<Double, Double> arccos = d -> (d * Math.PI) / 180.0;
        Function<Double, Double> arctan = d -> (d * Math.PI) / 180.0;
        Function<Double, Double> arccot = x -> rtd.apply(Math.atan(1/x));
        BiFunction<Double, Double, Double> arctan2 = (y, x) -> rtd.apply(Math.atan2(y, x));
        BiFunction<Double, Double, Double> fix = (a,b) -> {
            a = a- b* (Math.floor(a/ b));
            return (a < 0) ? a+ b : a;
        };
        Function<Double, Double> fixAngle = a -> fix.apply(a, 360d);
        Function<Double, Double> fixHour = a -> fix.apply(a, 24d);
    }
}
