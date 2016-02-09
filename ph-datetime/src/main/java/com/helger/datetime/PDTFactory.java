/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import javax.annotation.Nonnegative;
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

  @Nonnegative
  public static ZonedDateTime getCurrentZonedDateTime ()
  {
    return ZonedDateTime.now (_getZoneId ());
  }

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

  @Nonnegative
  public static OffsetDateTime getCurrentOffsetDateTime ()
  {
    return OffsetDateTime.now (_getZoneId ());
  }

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

  @Nonnegative
  public static LocalDateTime getCurrentLocalDateTime ()
  {
    return LocalDateTime.now (_getZoneId ());
  }

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
    return createLocalDateTime (createLocalDate (aYM));
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Year aYear)
  {
    return createLocalDateTime (createLocalDate (aYear));
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

  // To LocalDate

  @Nonnegative
  public static LocalDate getCurrentLocalDate ()
  {
    return LocalDate.now (_getZoneId ());
  }

  @Nonnull
  public static LocalDate createLocalDate (final int nYear, final Month eMonth, final int nDay)
  {
    return LocalDate.of (nYear, eMonth, nDay);
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final GregorianCalendar aCalendar)
  {
    return aCalendar.toZonedDateTime ().toLocalDate ();
  }

  @Nonnull
  public static LocalDate createLocalDate (final long nMillis)
  {
    return createLocalDateTime (nMillis).toLocalDate ();
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Instant aInstant)
  {
    return createLocalDateTime (aInstant).toLocalDate ();
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Date aDate)
  {
    return createLocalDate (aDate.toInstant ());
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final YearMonth aYM)
  {
    return aYM.atDay (1);
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Year aYear)
  {
    return aYear.atDay (1);
  }

  // To LocalTime

  @Nonnegative
  public static LocalTime getCurrentLocalTime ()
  {
    return LocalTime.now (_getZoneId ());
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final GregorianCalendar aCalendar)
  {
    return aCalendar.toZonedDateTime ().toLocalTime ();
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final Instant aInstant)
  {
    return createLocalDateTime (aInstant).toLocalTime ();
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final Date aDate)
  {
    return createLocalTime (aDate.toInstant ());
  }

  // To Date

  @Nonnull
  public static Date createDate (@Nonnull final ZonedDateTime aZDT)
  {
    return Date.from (aZDT.toInstant ());
  }

  @Nonnull
  public static Date createDate (@Nonnull final OffsetDateTime aODT)
  {
    return Date.from (aODT.toInstant ());
  }

  @Nonnull
  public static Date createDate (@Nonnull final LocalDateTime aLDT)
  {
    return createDate (createZonedDateTime (aLDT));
  }

  @Nonnull
  public static Date createDate (@Nonnull final LocalDate aLD)
  {
    return createDate (createZonedDateTime (aLD));
  }

  @Nonnull
  public static Date createDate (@Nonnull final LocalTime aLT)
  {
    return createDate (createZonedDateTime (aLT));
  }

  // Misc

  @Nonnegative
  public static int getCurrentYear ()
  {
    return LocalDate.now ().getYear ();
  }
}
