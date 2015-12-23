package com.helger.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.datetime.config.PDTConfig;

@Immutable
public final class PDTFactory
{
  private PDTFactory ()
  {}

  private static ZoneId _getZoneId ()
  {
    return PDTConfig.getDefaultZoneId ();
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final LocalDateTime aLDT)
  {
    return ZonedDateTime.of (aLDT, _getZoneId ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final int nYear, final Month eMonth, final int nDay)
  {
    return LocalDateTime.of (nYear, eMonth, nDay, 0, 0, 0);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final long nMillis)
  {
    final Instant aInstant = Instant.ofEpochMilli (nMillis);
    return LocalDateTime.ofInstant (aInstant, _getZoneId ());
  }
}
