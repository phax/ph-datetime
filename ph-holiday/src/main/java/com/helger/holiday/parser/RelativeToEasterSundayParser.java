/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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
package com.helger.holiday.parser;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

import org.threeten.extra.chrono.JulianDate;

import com.helger.datetime.CPDT;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.ChronologyType;
import com.helger.holiday.jaxb.HolidayType;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.RelativeToEasterSunday;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * This parser creates holidays relative to easter sunday.
 *
 * @author Sven Diedrichsen
 * @version $Id: $
 */
public class RelativeToEasterSundayParser extends AbstractHolidayParser
{
  /**
   * Parses relative to Easter Sunday holidays.
   */
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final RelativeToEasterSunday aDay : aConfig.getRelativeToEasterSunday ())
    {
      if (!isValid (aDay, nYear))
        continue;
      final ChronoLocalDate aEasterSunday = getEasterSunday (nYear, aDay.getChronology ());
      aEasterSunday.plus (aDay.getDays (), ChronoUnit.DAYS);
      final String sPropertiesKey = "christian." + aDay.getDescriptionPropertiesKey ();
      addChrstianHoliday (aEasterSunday,
                          sPropertiesKey,
                          XMLHolidayHelper.getType (aDay.getLocalizedType ()),
                          aHolidayMap);
    }
  }

  /**
   * Adds the given day to the list of holidays.
   *
   * @param aDate
   *        The day
   * @param sPropertiesKey
   *        a {@link java.lang.String} object.
   * @param aHolidayType
   *        a {@link HolidayType} object.
   * @param holidays
   *        a {@link java.util.Set} object.
   */
  protected final void addChrstianHoliday (final ChronoLocalDate aDate,
                                           final String sPropertiesKey,
                                           final IHolidayType aHolidayType,
                                           final HolidayMap holidays)
  {
    final LocalDate convertedDate = LocalDate.from (aDate);
    holidays.add (convertedDate, new ResourceBundleHoliday (aHolidayType, sPropertiesKey));
  }

  /**
   * Returns the easter Sunday for a given year.
   *
   * @param nYear
   *        The year to retrieve Easter Sunday date
   * @return Easter Sunday.
   */
  public static ChronoLocalDate getEasterSunday (final int nYear)
  {
    return nYear <= CPDT.LAST_JULIAN_YEAR ? getJulianEasterSunday (nYear) : getGregorianEasterSunday (nYear);
  }

  public static ChronoLocalDate getEasterSunday (final int nYear, final ChronologyType eType)
  {
    return eType == ChronologyType.JULIAN ? getJulianEasterSunday (nYear) : getGregorianEasterSunday (nYear);
  }

  /**
   * Returns the easter Sunday within the julian chronology.
   *
   * @param nYear
   *        The year to retrieve Julian Easter Sunday date
   * @return julian easter Sunday
   */
  public static JulianDate getJulianEasterSunday (final int nYear)
  {
    final int a = nYear % 4;
    final int b = nYear % 7;
    final int c = nYear % 19;
    final int d = (19 * c + 15) % 30;
    final int e = (2 * a + 4 * b - d + 34) % 7;
    final int x = d + e + 114;
    final int nMonth = x / 31;
    final int nDay = (x % 31) + 1;
    return JulianDate.of (nYear, (nMonth == 3 ? Month.MARCH : Month.APRIL).getValue (), nDay);
  }

  /**
   * Returns the easter Sunday within the gregorian chronology.
   *
   * @param nYear
   *        The year to retrieve Gregorian Easter Sunday date
   * @return gregorian easter Sunday.
   */
  public static LocalDate getGregorianEasterSunday (final int nYear)
  {
    final int a = nYear % 19;
    final int b = nYear / 100;
    final int c = nYear % 100;
    final int d = b / 4;
    final int e = b % 4;
    final int f = (b + 8) / 25;
    final int g = (b - f + 1) / 3;
    final int h = (19 * a + b - d - g + 15) % 30;
    final int i = c / 4;
    final int j = c % 4;
    final int k = (32 + 2 * e + 2 * i - h - j) % 7;
    final int l = (a + 11 * h + 22 * k) / 451;
    final int x = h + k - 7 * l + 114;
    final int nMonth = x / 31;
    final int nDay = (x % 31) + 1;
    return LocalDate.of (nYear, (nMonth == 3 ? Month.MARCH : Month.APRIL), nDay);
  }
}
