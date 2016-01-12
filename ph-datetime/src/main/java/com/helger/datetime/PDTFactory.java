package com.helger.datetime;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
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

  @Nonnull
  private static ZoneId _getZoneId ()
  {
    return PDTConfig.getDefaultZoneId ();
  }

  // To ZonedDateTime

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final OffsetDateTime aODT)
  {
    return aODT.toZonedDateTime ();
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final LocalDateTime aLDT)
  {
    return ZonedDateTime.of (aLDT, _getZoneId ());
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final LocalDate aLD)
  {
    return createZonedDateTime (createLocalDateTime (aLD));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final YearMonth aYM)
  {
    return createZonedDateTime (createLocalDateTime (aYM));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final Year aYear)
  {
    return createZonedDateTime (createLocalDateTime (aYear));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final LocalTime aLT)
  {
    return createZonedDateTime (createLocalDateTime (aLT));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (final int nYear, final Month eMonth, final int nDay)
  {
    return createZonedDateTime (createLocalDateTime (nYear, eMonth, nDay));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (final int nYear,
                                                   final Month eMonth,
                                                   final int nDay,
                                                   final int nHour,
                                                   final int nMinute,
                                                   final int nSecond)
  {
    return createZonedDateTime (createLocalDateTime (nYear, eMonth, nDay, nHour, nMinute, nSecond));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final Instant aInstant)
  {
    return ZonedDateTime.ofInstant (aInstant, _getZoneId ());
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final GregorianCalendar aCal)
  {
    return aCal.toZonedDateTime ();
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final Date aDate)
  {
    return createZonedDateTime (createLocalDateTime (aDate));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final Timestamp aTimestamp)
  {
    return createZonedDateTime (createLocalDateTime (aTimestamp));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (final long nMillis)
  {
    return createZonedDateTime (createLocalDateTime (nMillis));
  }

  @Nonnull
  public static ZonedDateTime createZonedDateTime (@Nonnull final Number aMillis)
  {
    return createZonedDateTime (createLocalDateTime (aMillis));
  }

  // To OffsetDateTime

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final ZonedDateTime aZDT)
  {
    return aZDT.toOffsetDateTime ();
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final LocalDateTime aLDT)
  {
    return createOffsetDateTime (createZonedDateTime (aLDT));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final LocalDate aLD)
  {
    return createOffsetDateTime (createZonedDateTime (aLD));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final YearMonth aYM)
  {
    return createOffsetDateTime (createZonedDateTime (aYM));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final Year aYear)
  {
    return createOffsetDateTime (createZonedDateTime (aYear));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final LocalTime aLT)
  {
    return createOffsetDateTime (createZonedDateTime (aLT));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (final int nYear, final Month eMonth, final int nDay)
  {
    return createOffsetDateTime (createZonedDateTime (nYear, eMonth, nDay));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (final int nYear,
                                                     final Month eMonth,
                                                     final int nDay,
                                                     final int nHour,
                                                     final int nMinute,
                                                     final int nSecond)
  {
    return createOffsetDateTime (createZonedDateTime (nYear, eMonth, nDay, nHour, nMinute, nSecond));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final Instant aInstant)
  {
    return OffsetDateTime.ofInstant (aInstant, _getZoneId ());
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final Date aDate)
  {
    return createOffsetDateTime (createLocalDateTime (aDate));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final Timestamp aTimestamp)
  {
    return createOffsetDateTime (createLocalDateTime (aTimestamp));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final GregorianCalendar aCal)
  {
    return createOffsetDateTime (aCal.toZonedDateTime ());
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (final long nMillis)
  {
    return createOffsetDateTime (createLocalDateTime (nMillis));
  }

  @Nonnull
  public static OffsetDateTime createOffsetDateTime (@Nonnull final Number aMillis)
  {
    return createOffsetDateTime (createLocalDateTime (aMillis));
  }

  // To LocalDateTime

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final ZonedDateTime aDT)
  {
    return aDT.toLocalDateTime ();
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final OffsetDateTime aDT)
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

  // LocalDate

  @Nonnull
  public static LocalDate createLocalDate (final int nYear, final Month eMonth, final int nDay)
  {
    return LocalDate.of (nYear, eMonth, nDay);
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Date aDate)
  {
    return createLocalDateTime (aDate.toInstant ()).toLocalDate ();
  }

  @Nonnull
  public static LocalDate createLocalDate (final long nMillis)
  {
    return createLocalDateTime (nMillis).toLocalDate ();
  }
}