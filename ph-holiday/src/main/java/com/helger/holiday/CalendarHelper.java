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
package com.helger.holiday;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import org.threeten.extra.Interval;

import com.helger.datetime.config.PDTConfig;
import com.helger.datetime.util.PDTHelper;

/**
 * Utility class for date operations.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
@Immutable
public final class CalendarHelper
{
  private CalendarHelper ()
  {}

  /**
   * Returns a set of gregorian dates within a gregorian year which equal the
   * islamic month and day. Because the islamic year is about 11 days shorter
   * than the gregorian there may be more than one occurrence of an islamic date
   * in an gregorian year. i.e.: In the gregorian year 2008 there were two 1/1.
   * They occurred on 1/10 and 12/29.
   *
   * @param nGregorianYear
   *        Year to convert
   * @param nIslamicMonth
   *        Month to convert
   * @param nIslamicDay
   *        Day to convert
   * @return List of gregorian dates for the islamic month/day.
   */
  public static Set <LocalDate> getIslamicHolidaysInGregorianYear (final int nGregorianYear,
                                                                   final Month nIslamicMonth,
                                                                   final int nIslamicDay)
  {
    return getDatesFromChronologyWithinGregorianYear (nIslamicMonth,
                                                      nIslamicDay,
                                                      nGregorianYear,
                                                      IslamicChronology.getInstanceUTC ());
  }

  /**
   * Searches for the occurrences of a month/day in one chronology within one
   * gregorian year.
   *
   * @param nTargetMonth
   *        Target month
   * @param nTargetDay
   *        Target day
   * @param nGregorianYear
   *        Gregorian year
   * @param aTargetChronoUTC
   *        Target chronology
   * @return the list of gregorian dates.
   */
  @Nonnull
  public static Set <LocalDate> getDatesFromChronologyWithinGregorianYear (final int nTargetMonth,
                                                                           final int nTargetDay,
                                                                           final int nGregorianYear,
                                                                           final Chronology aTargetChronoUTC)
  {
    final Set <LocalDate> aHolidays = new HashSet <LocalDate> ();
    final LocalDate aFirstGregorianDate = LocalDate.of (nGregorianYear, Month.JANUARY, 1);
    final LocalDate aLastGregorianDate = LocalDate.of (nGregorianYear, Month.DECEMBER, 31);

    final LocalDate aFirstTargetDate = new LocalDate (aFirstGregorianDate.toDateTimeAtStartOfDay (PDTConfig.getUTCZoneId ())
                                                                         .getMillis (),
                                                      aTargetChronoUTC);
    final LocalDate aLastTargetDate = new LocalDate (aLastGregorianDate.toDateTimeAtStartOfDay (PDTConfig.getDateTimeZoneUTC ())
                                                                       .getMillis (),
                                                     aTargetChronoUTC);

    final Interval aInterv = new Interval (aFirstTargetDate.toDateTimeAtStartOfDay (PDTConfig.getDateTimeZoneUTC ()),
                                           aLastTargetDate.plusDays (1)
                                                          .toDateTimeAtStartOfDay (PDTConfig.getDateTimeZoneUTC ()));

    for (int nTargetYear = aFirstTargetDate.getYear (); nTargetYear <= aLastTargetDate.getYear (); ++nTargetYear)
    {
      final LocalDate aLocalDate = new LocalDate (nTargetYear, nTargetMonth, nTargetDay, aTargetChronoUTC);
      if (aInterv.contains (aLocalDate.toDateTimeAtStartOfDay (PDTConfig.getDateTimeZoneUTC ())))
      {
        aHolidays.add (convertToGregorianDate (aLocalDate));
      }
    }
    return aHolidays;
  }

  /**
   * Takes converts the provided date into a date within the gregorian
   * chronology. If it is already a gregorian date it will return it.
   *
   * @param aDate
   *        Date to convert
   * @return Converted date
   */
  @Nonnull
  public static LocalDate convertToGregorianDate (@Nonnull final ChronoLocalDate aDate)
  {
    if (aDate.getChronology () == IsoChronology.INSTANCE)
      return LocalDate.from (aDate);
    // FIXME
    return LocalDate.ofFromMillis (aDate.toDateTimeAtStartOfDay (PDTConfig.getDateTimeZoneUTC ()).getMillis ());
  }

  /**
   * Get the next working day based on the current day. If the current day is a
   * working day, the current day is returned. A working day is determined by:
   * it's not a weekend day (usually Saturday and Sunday).
   *
   * @return The next matching date.
   * @see PDTHelper#isWorkDay(LocalDate)
   */
  @Nonnull
  public static LocalDate getCurrentOrNextWorkDay ()
  {
    LocalDate aDT = LocalDate.now ();
    while (!PDTHelper.isWorkDay (aDT))
      aDT = aDT.plusDays (1);
    return aDT;
  }
}
