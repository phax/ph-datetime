package com.helger.datetime;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

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

  // To ZonedDateTime

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final LocalDateTime aLDT)
  {
    return ZonedDateTime.of (aLDT, _getZoneId ());
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final LocalDate aLD)
  {
    return createDateTime (createLocalDateTime (aLD));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final YearMonth aYM)
  {
    return createDateTime (createLocalDateTime (aYM));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final Year aYear)
  {
    return createDateTime (createLocalDateTime (aYear));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final LocalTime aLT)
  {
    return createDateTime (createLocalDateTime (aLT));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (final int nYear, final Month eMonth, final int nDay)
  {
    return createDateTime (createLocalDateTime (nYear, eMonth, nDay));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (final int nYear,
                                              final Month eMonth,
                                              final int nDay,
                                              final int nHour,
                                              final int nMinute,
                                              final int nSecond)
  {
    return createDateTime (createLocalDateTime (nYear, eMonth, nDay, nHour, nMinute, nSecond));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final Instant aInstant)
  {
    return ZonedDateTime.ofInstant (aInstant, _getZoneId ());
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final Date aDate)
  {
    return createDateTime (createLocalDateTime (aDate));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final Timestamp aTimestamp)
  {
    return createDateTime (createLocalDateTime (aTimestamp));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (final long nMillis)
  {
    return createDateTime (createLocalDateTime (nMillis));
  }

  @Nonnull
  public static ZonedDateTime createDateTime (@Nonnull final Number aMillis)
  {
    return createDateTime (createLocalDateTime (aMillis));
  }

  // To LocalDateTime

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final ZonedDateTime aDT)
  {
    return aDT.toLocalDateTime ();
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final LocalDate aLD)
  {
    return aLD.atStartOfDay ();
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final YearMonth aYM)
  {
    return createLocalDateTime (aYM.atDay (1));
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Year aYear)
  {
    return createLocalDateTime (aYear.atDay (1));
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final LocalTime aLT)
  {
    return aLT.atDate (CPDT.NULL_LOCAL_DATE);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final int nYear, final Month eMonth, final int nDay)
  {
    return createLocalDateTime (nYear, eMonth, nDay, 0, 0, 0);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final int nYear,
                                                   final Month eMonth,
                                                   final int nDay,
                                                   final int nHour,
                                                   final int nMinute,
                                                   final int nSecond)
  {
    return LocalDateTime.of (nYear, eMonth, nDay, nHour, nMinute, nSecond);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Instant aInstant)
  {
    return LocalDateTime.ofInstant (aInstant, _getZoneId ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Date aDate)
  {
    return createLocalDateTime (aDate.toInstant ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Timestamp aDate)
  {
    return aDate.toLocalDateTime ();
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final GregorianCalendar aCal)
  {
    return createLocalDateTime (aCal.toZonedDateTime ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final long nMillis)
  {
    return createLocalDateTime (Instant.ofEpochMilli (nMillis));
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Number aMillis)
  {
    return createLocalDateTime (aMillis.longValue ());
  }
}
