/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.temporal.ChronoField;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.datetime.PDTFactory;
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
  @Nonnull
  public static ICommonsSet <LocalDate> getIslamicHolidaysInGregorianYear (final int nGregorianYear,
                                                                           final int nIslamicMonth,
                                                                           final int nIslamicDay)
  {
    return getDatesFromChronologyWithinGregorianYear (nIslamicMonth,
                                                      nIslamicDay,
                                                      nGregorianYear,
                                                      HijrahChronology.INSTANCE);
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
   * @param aTargetChrono
   *        Target chronology
   * @return the list of gregorian dates.
   */
  @Nonnull
  public static ICommonsSet <LocalDate> getDatesFromChronologyWithinGregorianYear (final int nTargetMonth,
                                                                                   final int nTargetDay,
                                                                                   final int nGregorianYear,
                                                                                   final Chronology aTargetChrono)
  {
    final Year aIsoYear = Year.of (nGregorianYear);
    final ChronoLocalDate aFirstTargetDate = aTargetChrono.date (aIsoYear.atDay (1));
    final ChronoLocalDate aLastTargetDate = aTargetChrono.date (aIsoYear.atDay (365 + (aIsoYear.isLeap () ? 1 : 0)));

    final ICommonsSet <LocalDate> aHolidays = new CommonsHashSet <> ();
    final int nStartYear = aFirstTargetDate.get (ChronoField.YEAR);
    final int nEndYear = aLastTargetDate.get (ChronoField.YEAR);
    for (int nTargetYear = nStartYear; nTargetYear <= nEndYear; ++nTargetYear)
    {
      final ChronoLocalDate aLocalDate = aTargetChrono.date (nTargetYear, nTargetMonth, nTargetDay);
      if (!aLocalDate.isBefore (aFirstTargetDate) && !aLocalDate.isAfter (aLastTargetDate))
      {
        // Convert to ISO chronology
        aHolidays.add (LocalDate.from (aLocalDate));
      }
    }
    return aHolidays;
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
    LocalDate aDT = PDTFactory.getCurrentLocalDate ();
    while (PDTHelper.isWeekend (aDT))
      aDT = aDT.plusDays (1);
    return aDT;
  }
}
