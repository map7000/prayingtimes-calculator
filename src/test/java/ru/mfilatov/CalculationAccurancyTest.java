package ru.mfilatov;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import ru.mfilatov.original.PrayTime;

public class CalculationAccurancyTest {
    @Test
    void checkMoscowTimeTest() {
        double latitude = -37.823689;
        double longitude = 145.121597;
        double timezone = 3;
        // Test Prayer times here
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Jafari);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                latitude, longitude, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            System.out.println(prayerNames.get(i) + " - " + prayerTimes.get(i));
        }

    }
}