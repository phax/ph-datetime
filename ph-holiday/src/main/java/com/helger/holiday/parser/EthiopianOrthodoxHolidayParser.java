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
package com.helger.holiday.parser;

import java.time.LocalDate;
import java.util.Set;

import org.threeten.extra.chrono.CopticChronology;

import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.holiday.CalendarHelper;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.EthiopianOrthodoxHoliday;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * Calculates the ethiopian orthodox holidays.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class EthiopianOrthodoxHolidayParser extends AbstractHolidayParser
{
  private static final EthiopianOrthodoxHolidayParser s_aInstance = new EthiopianOrthodoxHolidayParser ();

  private EthiopianOrthodoxHolidayParser ()
  {}

  public static EthiopianOrthodoxHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  /**
   * Returns a set of gregorian dates within a gregorian year which equal the
   * Ethiopian orthodox month and day. Because the Ethiopian orthodox year
   * different from the gregorian there may be more than one occurrence of an
   * Ethiopian orthodox date in an gregorian year.
   *
   * @param nGregorianYear
   * @param nEOMonth
   *        orthodox month
   * @param nEODay
   *        orthodox day
   * @return List of gregorian dates for the Ethiopian orthodox month/day.
   */
  private static ICommonsSet <LocalDate> _getEthiopianOrthodoxHolidaysInGregorianYear (final int nGregorianYear,
                                                                                       final int nEOMonth,
                                                                                       final int nEODay)
  {
    return CalendarHelper.getDatesFromChronologyWithinGregorianYear (nEOMonth,
                                                                     nEODay,
                                                                     nGregorianYear,
                                                                     CopticChronology.INSTANCE);
  }

  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final EthiopianOrthodoxHoliday aEthiopianOrthodoxHoliday : aConfig.getEthiopianOrthodoxHoliday ())
    {
      if (!isValid (aEthiopianOrthodoxHoliday, nYear))
        continue;

      Set <LocalDate> aEthiopianHolidays;
      switch (aEthiopianOrthodoxHoliday.getType ())
      {
        case TIMKAT:
          aEthiopianHolidays = _getEthiopianOrthodoxHolidaysInGregorianYear (nYear, 5, 10);
          break;
        case ENKUTATASH:
          aEthiopianHolidays = _getEthiopianOrthodoxHolidaysInGregorianYear (nYear, 1, 1);
          break;
        case MESKEL:
          aEthiopianHolidays = _getEthiopianOrthodoxHolidaysInGregorianYear (nYear, 1, 17);
          break;
        default:
          throw new IllegalArgumentException ("Unknown ethiopian orthodox holiday type " +
                                              aEthiopianOrthodoxHoliday.getType ());
      }

      final IHolidayType aType = XMLHolidayHelper.getType (aEthiopianOrthodoxHoliday.getLocalizedType ());
      final String sPropertiesKey = "ethiopian.orthodox." + aEthiopianOrthodoxHoliday.getType ().name ();
      for (final LocalDate aDate : aEthiopianHolidays)
        aHolidayMap.add (aDate, new ResourceBundleHoliday (aType, sPropertiesKey));
    }
  }
}
