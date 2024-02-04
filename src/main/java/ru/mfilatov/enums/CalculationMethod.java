package ru.mfilatov.enums;

public enum CalculationMethod {
  MWL("Muslim World League",
          new CalculationMethodParams(18, 0,AsrJuristicMethod.STANDARD, 0, 17, 10,
                  MidnightMode.STANDARD, AdjustForHigherLatitudes.NIGHT_MIDDLE)),
  ISNA("Islamic Society of North America (ISNA)",
          new CalculationMethodParams(18, 0,AsrJuristicMethod.STANDARD, 0, 17, 10,
          MidnightMode.STANDARD, AdjustForHigherLatitudes.NIGHT_MIDDLE)),
  Egypt("Egyptian General Authority of Survey",
          new CalculationMethodParams(18, 0,AsrJuristicMethod.STANDARD, 0, 17, 10,
                  MidnightMode.STANDARD, AdjustForHigherLatitudes.NIGHT_MIDDLE)),
  Makkah("Umm Al-Qura University, Makkah",
          new CalculationMethodParams(18, 0,AsrJuristicMethod.STANDARD, 0, 17, 10,
                  MidnightMode.STANDARD, AdjustForHigherLatitudes.NIGHT_MIDDLE)),
  Karachi("University of Islamic Sciences, Karachi",
          new CalculationMethodParams(18, 0,AsrJuristicMethod.STANDARD, 0, 17, 10,
                                      MidnightMode.STANDARD, AdjustForHigherLatitudes.NIGHT_MIDDLE)),
  Tehran("Institute of Geophysics, University of Tehran",
                 new CalculationMethodParams(18, 0,AsrJuristicMethod.STANDARD, 0, 17, 10,
         MidnightMode.STANDARD, AdjustForHigherLatitudes.NIGHT_MIDDLE)),
  Jafari("Shia Ithna-Ashari, Leva Institute, Qum",
                 new CalculationMethodParams(18, 0,AsrJuristicMethod.STANDARD, 0, 17, 10,
         MidnightMode.STANDARD, AdjustForHigherLatitudes.NIGHT_MIDDLE));

  private final String name;
  private final CalculationMethodParams params;

  CalculationMethod(String name, CalculationMethodParams params) {
    this.name = name;
    this.params = params;
  }
}

record CalculationMethodParams(
    int fajr,
    int dhuhr,
    AsrJuristicMethod asr,
    int maghrib,
    int isha,
    int imsak,
    MidnightMode midnight,
    AdjustForHigherLatitudes highLatAdjust) {}
