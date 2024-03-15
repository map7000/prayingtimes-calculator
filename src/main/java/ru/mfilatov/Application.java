package ru.mfilatov;

import static ru.mfilatov.functions.MathFunctions.acos;
import static ru.mfilatov.functions.MathFunctions.acot;
import static ru.mfilatov.functions.MathFunctions.cos;
import static ru.mfilatov.functions.MathFunctions.fixHour;
import static ru.mfilatov.functions.MathFunctions.sin;
import static ru.mfilatov.functions.MathFunctions.tan;

import lombok.extern.slf4j.Slf4j;
import ru.mfilatov.enums.CalculationMethods;
import ru.mfilatov.functions.JulianDayCalculator;
import ru.mfilatov.functions.MathFunctions;
import ru.mfilatov.functions.SunPositionCalculator;
import ru.mfilatov.functions.Times;
import ru.mfilatov.functions.SunPositionCalculator.SunPosition;

@Slf4j
public class Application {
  public static JulianDayCalculator julianDayCalculator = new JulianDayCalculator();
  public static SunPositionCalculator sunPositionCalculator = new SunPositionCalculator();

  public static void main(String[] args) {
    var latitude = 55.75222;
    var longitude = 37.61556;
    var timeZone = 3;
    var julianDate =
        julianDayCalculator.getJulianDayNumberEdwardGrahamRichards(2024, 3, 14)
            - longitude / (15 * 24.0);
    var defaultTimes = new Times(5, 5, 6, 12, 13, 18, 18, 18);
    var portionTimes = MathFunctions.dayPortion(defaultTimes);

    var computedTimes =
        computePrayerTimes(portionTimes, CalculationMethods.RUSSIA, julianDate, latitude);
    var adjustedTimes = adjustTimes(computedTimes, timeZone, longitude);

    log.info("{}", adjustTimes(computedTimes, timeZone, longitude));
    log.info("{}", computedTimes);
    log.info(getFormattedTime(adjustedTimes.fajr()));
    log.info(getFormattedTime(adjustedTimes.sunrise()));
    log.info(getFormattedTime(adjustedTimes.dhuhr()));
    log.info(getFormattedTime(adjustedTimes.asr()));
    log.info(getFormattedTime(adjustedTimes.sunset()));
    log.info(getFormattedTime(adjustedTimes.maghrib()));
    log.info(getFormattedTime(adjustedTimes.isha()));
  }

  public static Times computePrayerTimes(
      Times times, CalculationMethods method, double julianDate, double latitude) {

    var sunPositionFajr = sunPositionCalculator.usnoMethod(julianDate + times.fajr());
    var sunPositionSunrise = sunPositionCalculator.usnoMethod(julianDate + times.sunrise());
    var sunPositionDhuhr = sunPositionCalculator.usnoMethod(julianDate + times.dhuhr());
    var sunPositionAsr = sunPositionCalculator.usnoMethod(julianDate + times.asr());
    var sunPositionSunset = sunPositionCalculator.usnoMethod(julianDate + times.sunset());

    var imsak = sunAngleTime(method.getTimes().imsak(), latitude, sunPositionFajr, true);
    var fajr = sunAngleTime(method.getTimes().fajr(), latitude, sunPositionFajr, true);
    var sunrise = sunAngleTime(riseSetAngle(0), latitude, sunPositionSunrise, true);
    var dhuhr = midDay(sunPositionDhuhr);
    var asr = asrTime(asrFactor(method.getTimes().asr()), latitude, sunPositionAsr);
    var sunset = sunAngleTime(riseSetAngle(0), latitude, sunPositionSunset, false);
    var maghrib = sunAngleTime(method.getTimes().maghrib(), latitude, sunPositionSunset, false);
    var isha = sunAngleTime(method.getTimes().isha(), latitude, sunPositionSunset, false);

    return new Times(imsak, fajr, sunrise, dhuhr, asr, sunset, maghrib, isha);
  }

  // compute mid-day time
  public static double midDay(SunPosition sunPosition) {
    return MathFunctions.fixHour(12 - sunPosition.equation());
  }

  // return sun angle for sunset/sunrise
  public static double riseSetAngle(double time) {
    var elevation = time;

    return 0.833 + 0.0347 * Math.sqrt(elevation);
  }

  // get asr shadow factor
  public static double asrFactor(double time) {
    //    def asrFactor(self, asrParam):
    //        methods = {'Standard': 1, 'Hanafi': 2}
    //        return methods[asrParam] if asrParam in methods else self.eval(asrParam)

    return 1;
  }

  // compute the time at which sun reaches a specific angle below horizon
  public static double sunAngleTime(
      double angle, double latitude, SunPosition sunPosition, boolean directionCCW) {

    var declination = sunPosition.declination();
    var noon = midDay(sunPosition);
    var t = 1 / 15.0 * acos(
                (-sin(angle) - sin(declination) * sin(latitude))
                    / (cos(declination) * cos(latitude)));

    return noon + (directionCCW ? -t : t);
  }

  // compute asr time
  public static double asrTime(double factor, double latitude, SunPosition sunPosition) {
    var declination = sunPosition.declination();
    var angle = -acot(factor + tan(Math.abs(latitude - declination)));

    return sunAngleTime(angle, latitude, sunPosition, false);
  }

  public static String getFormattedTime(double timeFloat) {
    var time = fixHour(timeFloat + 0.5 / 60);
    var hours = Math.floor(time);
    var minutes = Math.floor((time - hours) * 60);

    return String.format("%02d:%02d", Math.round(hours), Math.round(minutes));
  }

  public static Times adjustTimes(Times times, double timeZone, double longitude) {
    var tzAdjust = timeZone - longitude / 15.0;

    return new Times(
        times.imsak() + tzAdjust,
        times.fajr() + tzAdjust,
        times.sunrise() + tzAdjust,
        times.dhuhr() + tzAdjust,
        times.asr() + tzAdjust,
        times.sunset() + tzAdjust,
        times.maghrib() + tzAdjust,
        times.isha() + tzAdjust);
  }
}
