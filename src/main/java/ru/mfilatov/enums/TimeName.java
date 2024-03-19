package ru.mfilatov.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeName {
  IMSAK("Imsak"),
  FAJR("Fajr"),
  SUNRISE("Sunrise"),
  DHUHR("Dhuhr"),
  ASR("Asr"),
  SUNSET("Sunset"),
  MAGHRIB("Maghrib"),
  ISHA("Isha"),
  MIDNIGHT("Midnight");

  private final String name;
}
