package ru.mfilatov.prayingtimes.calculator.model;

public class TimesDoubleBuilder {
    private double imsak;
    private double fajr;
    private double sunrise;
    private double dhuhr;
    private double asr;
    private double sunset;
    private double maghrib;
    private double isha;
    private double midnight;

    public TimesDoubleBuilder(TimesDouble timesDouble) {
        this.imsak = timesDouble.imsak();
        this.fajr = timesDouble.fajr();
        this.sunrise = timesDouble.sunrise();
        this.dhuhr = timesDouble.dhuhr();
        this.asr = timesDouble.asr();
        this.sunset = timesDouble.sunset();
        this.maghrib = timesDouble.maghrib();
        this.isha = timesDouble.isha();
        this.midnight = timesDouble.midnight();
    }

    public TimesDoubleBuilder imsak(double imsak) {
        this.imsak = imsak;
        return this;
    }

    public TimesDoubleBuilder fajr(double fajr) {
        this.fajr = fajr;
        return this;
    }

    public TimesDoubleBuilder sunrise(double sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public TimesDoubleBuilder dhuhr(double dhuhr) {
        this.dhuhr = dhuhr;
        return this;
    }

    public TimesDoubleBuilder asr(double asr) {
        this.asr = asr;
        return this;
    }

    public TimesDoubleBuilder sunset(double sunset) {
        this.sunset = sunset;
        return this;
    }

    public TimesDoubleBuilder maghrib(double maghrib) {
        this.maghrib = maghrib;
        return this;
    }

    public TimesDoubleBuilder isha(double isha) {
        this.isha = isha;
        return this;
    }

    public TimesDoubleBuilder midnight(double midnight) {
        this.midnight = midnight;
        return this;
    }

    public TimesDouble build() {
        return new TimesDouble(imsak, fajr, sunrise, dhuhr, asr, sunset, maghrib, isha, midnight);
    }
}