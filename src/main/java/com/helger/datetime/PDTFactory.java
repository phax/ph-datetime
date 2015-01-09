/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;

import com.helger.commons.annotations.PresentForCodeCoverage;
import com.helger.datetime.config.PDTConfig;

/**
 * This class contains methods for creating date and time objects.
 *
 * @author Philip Helger
 */
@Immutable
public final class PDTFactory
{
  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final PDTFactory s_aInstance = new PDTFactory ();

  private PDTFactory ()
  {}

  @Nonnull
  public static DateTime getCurrentDateTime ()
  {
    return new DateTime (PDTConfig.getDefaultChronology ());
  }

  @Nonnull
  public static DateTime getCurrentDateTimeUTC ()
  {
    return new DateTime (PDTConfig.getDefaultChronologyUTC ());
  }

  @Nonnull
  public static MutableDateTime getCurrentMutableDateTime ()
  {
    return new MutableDateTime (PDTConfig.getDefaultChronology ());
  }

  @Nonnull
  public static MutableDateTime getCurrentMutableDateTimeUTC ()
  {
    return new MutableDateTime (PDTConfig.getDefaultChronologyUTC ());
  }

  @Nonnull
  public static DateTime createDateTimeFromMillis (final long nMillis)
  {
    return new DateTime (nMillis, PDTConfig.getDefaultChronology ());
  }

  @Nonnull
  public static DateTime createDateTimeFromMillisUTC (final long nMillis)
  {
    return new DateTime (nMillis, PDTConfig.getDefaultChronologyUTC ());
  }

  @Nonnull
  public static DateTime createDateTime (final int nYears, final int nMonths, final int nDays)
  {
    return createDateTime (nYears, nMonths, nDays, 0, 0, 0, 0);
  }

  @Nonnull
  public static DateTime createDateTime (final int nYears,
                                         final int nMonths,
                                         final int nDays,
                                         final int nHours,
                                         final int nMinutes)
  {
    return createDateTime (nYears, nMonths, nDays, nHours, nMinutes, 0, 0);
  }

  @Nonnull
  public static DateTime createDateTime (final int nYears,
                                         final int nMonths,
                                         final int nDays,
                                         final int nHours,
                                         final int nMinutes,
                                         final int nSeconds)
  {
    return createDateTime (nYears, nMonths, nDays, nHours, nMinutes, nSeconds, 0);
  }

  @Nonnull
  public static DateTime createDateTime (final int nYears,
                                         final int nMonths,
                                         final int nDays,
                                         final int nHours,
                                         final int nMinutes,
                                         final int nSeconds,
                                         final int nMilliSeconds)
  {
    return new DateTime (nYears,
                         nMonths,
                         nDays,
                         nHours,
                         nMinutes,
                         nSeconds,
                         nMilliSeconds,
                         PDTConfig.getDefaultChronology ());
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final String sDateTime)
  {
    return new DateTime (sDateTime, PDTConfig.getDefaultChronology ());
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final Date aDate)
  {
    return createDateTime (aDate, PDTConfig.getDefaultDateTimeZone ());
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final Date aDate, @Nonnull final TimeZone aTimeZone)
  {
    return createDateTime (aDate, DateTimeZone.forTimeZone (aTimeZone));
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final Date aDate, @Nonnull final DateTimeZone aDateTimeZone)
  {
    return new DateTime (aDate, PDTConfig.getDefaultChronology ().withZone (aDateTimeZone));
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final Calendar aCalendar)
  {
    return new DateTime (aCalendar, PDTConfig.getDefaultChronology ()
                                             .withZone (DateTimeZone.forTimeZone (aCalendar.getTimeZone ())));
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final LocalDate aLocalDate)
  {
    return aLocalDate.toDateTimeAtStartOfDay (getLocalDateTimeZone ())
                     .withChronology (PDTConfig.getDefaultChronology ());
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final LocalTime aLocalTime)
  {
    return CPDT.NULL_LOCAL_DATE.toDateTime (aLocalTime, getLocalDateTimeZone ())
                               .withChronology (PDTConfig.getDefaultChronology ());
  }

  @Nonnull
  public static DateTime createDateTime (@Nonnull final LocalDateTime aLocalDateTime)
  {
    return aLocalDateTime.toDateTime (getLocalDateTimeZone ()).withChronology (PDTConfig.getDefaultChronology ());
  }

  /**
   * @return The non-<code>null</code> chronology to be used for
   *         {@link LocalDateTime}, {@link LocalDate} and {@link LocalTime}.
   */
  @Nonnull
  public static Chronology getLocalChronology ()
  {
    return PDTConfig.getDefaultChronology ();
  }

  /**
   * @return The non-<code>null</code> date and time zone to be used for
   *         {@link LocalDateTime}, {@link LocalDate} and {@link LocalTime}.
   */
  @Nonnull
  public static DateTimeZone getLocalDateTimeZone ()
  {
    return PDTConfig.getDefaultDateTimeZone ();
  }

  @Nonnull
  public static LocalDate getCurrentLocalDate ()
  {
    return new LocalDate (getLocalChronology ());
  }

  @Nonnull
  public static LocalDate createLocalDateFromMillis (final long nMillis)
  {
    return new LocalDate (nMillis, getLocalChronology ());
  }

  @Nonnull
  public static LocalDate createLocalDate (final int nYears, final int nMonths, final int nDays)
  {
    return new LocalDate (nYears, nMonths, nDays, getLocalChronology ());
  }

  /**
   * Parse string using ISO format
   *
   * @param sDate
   *        A date in the format yyyy-MM-dd
   * @return the {@link LocalDate}
   */
  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final String sDate)
  {
    return new LocalDate (sDate, getLocalChronology ());
  }

  /**
   * Creates a LocalDate. Does not use the Chronology of the Calendar.
   *
   * @param aCalendar
   *        The calendar to be converted.
   * @return The local date representing the provided date.
   */
  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Calendar aCalendar)
  {
    return new LocalDate (aCalendar,
                          getLocalChronology ().withZone (DateTimeZone.forTimeZone (aCalendar.getTimeZone ())));
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Date aDate)
  {
    return createLocalDate (aDate, PDTConfig.getDefaultDateTimeZone ());
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Date aDate, final TimeZone aTimeZone)
  {
    return createLocalDate (aDate, DateTimeZone.forTimeZone (aTimeZone));
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final Date aDate, final DateTimeZone aDateTimeZone)
  {
    return new LocalDate (aDate, getLocalChronology ().withZone (aDateTimeZone));
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final DateTime aDateTime)
  {
    return aDateTime.toLocalDate ();
  }

  @Nonnull
  public static LocalDate createLocalDate (@Nonnull final LocalDateTime aLocalDateTime)
  {
    return aLocalDateTime.toLocalDate ();
  }

  @Nonnull
  public static LocalTime getCurrentLocalTime ()
  {
    return new LocalTime (getLocalChronology ());
  }

  @Nonnull
  public static LocalTime createLocalTimeFromMillis (final long nMillis)
  {
    return new LocalTime (nMillis, getLocalChronology ());
  }

  @Nonnull
  public static LocalTime createLocalTime (final int nHours, final int nMinutes)
  {
    return createLocalTime (nHours, nMinutes, 0, 0);
  }

  @Nonnull
  public static LocalTime createLocalTime (final int nHours, final int nMinutes, final int nSeconds)
  {
    return createLocalTime (nHours, nMinutes, nSeconds, 0);
  }

  @Nonnull
  public static LocalTime createLocalTime (final int nHours,
                                           final int nMinutes,
                                           final int nSeconds,
                                           final int nMilliSecs)
  {
    // Always using UTC internally!
    return new LocalTime (nHours, nMinutes, nSeconds, nMilliSecs, getLocalChronology ());
  }

  /**
   * Parse string using ISO format
   *
   * @param sTime
   *        A time in the format HH:mm:ss.SSSZZ
   * @return the {@link LocalTime}
   */
  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final String sTime)
  {
    return new LocalTime (sTime, getLocalChronology ());
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final Date aDate)
  {
    return createLocalTime (aDate, PDTConfig.getDefaultDateTimeZone ());
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final Date aDate, @Nonnull final TimeZone aTimeZone)
  {
    return new LocalTime (aDate, DateTimeZone.forTimeZone (aTimeZone));
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final Date aDate, @Nonnull final DateTimeZone aDateTimeZone)
  {
    return new LocalTime (aDate, getLocalChronology ().withZone (aDateTimeZone));
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final Calendar aCalendar)
  {
    return new LocalTime (aCalendar,
                          getLocalChronology ().withZone (DateTimeZone.forTimeZone (aCalendar.getTimeZone ())));
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final DateTime aDateTime)
  {
    return aDateTime.toLocalTime ();
  }

  @Nonnull
  public static LocalTime createLocalTime (@Nonnull final LocalDateTime aLocalDateTime)
  {
    return aLocalDateTime.toLocalTime ();
  }

  @Nonnull
  public static LocalDateTime getCurrentLocalDateTime ()
  {
    return new LocalDateTime (getLocalChronology ());
  }

  /**
   * Parse milli seconds string
   *
   * @param sDateTime
   *        A date and time as a string representation of the milli seconds
   * @return the {@link LocalDateTime}
   */
  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final String sDateTime)
  {
    return new LocalDateTime (sDateTime, getLocalChronology ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Date aDate)
  {
    return createLocalDateTime (aDate, PDTConfig.getDefaultDateTimeZone ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Date aDate, @Nonnull final TimeZone aTimeZone)
  {
    return createLocalDateTime (aDate, DateTimeZone.forTimeZone (aTimeZone));
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Date aDate, @Nonnull final DateTimeZone aDateTimeZone)
  {
    return new LocalDateTime (aDate, getLocalChronology ().withZone (aDateTimeZone));
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final Calendar aCalendar)
  {
    return new LocalDateTime (aCalendar,
                              getLocalChronology ().withZone (DateTimeZone.forTimeZone (aCalendar.getTimeZone ())));
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final DateTime aDateTime)
  {
    return aDateTime.toLocalDateTime ();
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final LocalDate aDate)
  {
    return createLocalDateTime (aDate.getYear (), aDate.getMonthOfYear (), aDate.getDayOfMonth ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final LocalTime aTime)
  {
    return CPDT.NULL_LOCAL_DATE.toLocalDateTime (aTime);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTimeFromMillis (final long nMillis)
  {
    return new LocalDateTime (nMillis, getLocalChronology ());
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final int nYears, final int nMonths, final int nDays)
  {
    return createLocalDateTime (nYears, nMonths, nDays, 0, 0, 0, 0);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final LocalDate aDate, final int nHours, final int nMinutes)
  {
    return createLocalDateTime (aDate.getYear (),
                                aDate.getMonthOfYear (),
                                aDate.getDayOfMonth (),
                                nHours,
                                nMinutes,
                                0,
                                0);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final LocalDate aDate,
                                                   final int nHours,
                                                   final int nMinutes,
                                                   final int nSeconds)
  {
    return createLocalDateTime (aDate.getYear (),
                                aDate.getMonthOfYear (),
                                aDate.getDayOfMonth (),
                                nHours,
                                nMinutes,
                                nSeconds,
                                0);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (@Nonnull final LocalDate aDate,
                                                   final int nHours,
                                                   final int nMinutes,
                                                   final int nSeconds,
                                                   final int nMilliSeconds)
  {
    return createLocalDateTime (aDate.getYear (),
                                aDate.getMonthOfYear (),
                                aDate.getDayOfMonth (),
                                nHours,
                                nMinutes,
                                nSeconds,
                                nMilliSeconds);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final int nYears,
                                                   final int nMonths,
                                                   final int nDays,
                                                   final int nHours,
                                                   final int nMinutes)
  {
    return createLocalDateTime (nYears, nMonths, nDays, nHours, nMinutes, 0, 0);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final int nYears,
                                                   final int nMonths,
                                                   final int nDays,
                                                   final int nHours,
                                                   final int nMinutes,
                                                   final int nSeconds)
  {
    return createLocalDateTime (nYears, nMonths, nDays, nHours, nMinutes, nSeconds, 0);
  }

  @Nonnull
  public static LocalDateTime createLocalDateTime (final int nYears,
                                                   final int nMonths,
                                                   final int nDays,
                                                   final int nHours,
                                                   final int nMinutes,
                                                   final int nSeconds,
                                                   final int nMilliSeconds)
  {
    // Always using UTC internally!
    return new LocalDateTime (nYears, nMonths, nDays, nHours, nMinutes, nSeconds, nMilliSeconds, getLocalChronology ());
  }

  @Nonnegative
  public static long getCurrentMillis ()
  {
    return DateTimeUtils.currentTimeMillis ();
  }

  @Nonnegative
  public static int getCurrentYear ()
  {
    return getCurrentDateTime ().getYear ();
  }
}
