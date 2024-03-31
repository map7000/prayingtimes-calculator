package ru.mfilatov.model;

import java.time.OffsetDateTime;

public record Times(
    OffsetDateTime imsak,
    OffsetDateTime fajr,
    OffsetDateTime sunrise,
    OffsetDateTime dhuhr,
    OffsetDateTime asr,
    OffsetDateTime sunset,
    OffsetDateTime maghrib,
    OffsetDateTime isha,
    OffsetDateTime  midnight) {}
