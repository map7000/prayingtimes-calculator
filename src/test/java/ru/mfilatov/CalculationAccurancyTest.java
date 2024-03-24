package ru.mfilatov;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.mfilatov.enums.CalculationMethods;
import ru.mfilatov.enums.TimeName;

import java.time.OffsetDateTime;

@Slf4j
public class CalculationAccurancyTest {
    @Test
    void checkMoscowTimeTest() {
        double latitude = -37.823689;
        double longitude = 145.121597;
        int timezone = 3;

    PrayingTimesCalculator prayers = new PrayingTimesCalculator(OffsetDateTime.now(), timezone, latitude, longitude, CalculationMethods.RUSSIA);
    var times = prayers.calculate();
    log.info("{}: {}", TimeName.IMSAK, prayers.getFormattedTime(times.imsak()));
    log.info("{}: {}", TimeName.FAJR, prayers.getFormattedTime(times.fajr()));
    log.info("{}: {}", TimeName.SUNRISE, prayers.getFormattedTime(times.sunrise()));
    log.info("{}: {}", TimeName.DHUHR, prayers.getFormattedTime(times.dhuhr()));
    log.info("{}: {}", TimeName.ASR, prayers.getFormattedTime(times.asr()));
    log.info("{}: {}", TimeName.SUNSET, prayers.getFormattedTime(times.sunset()));
    log.info("{}: {}", TimeName.MAGHRIB, prayers.getFormattedTime(times.maghrib()));
    log.info("{}: {}", TimeName.ISHA, prayers.getFormattedTime(times.isha()));
    }
}