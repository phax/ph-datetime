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
package com.helger.datetime.holiday.parser.impl;

import java.util.Set;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.helger.datetime.holiday.CalendarHelper;
import com.helger.datetime.holiday.HolidayMap;
import com.helger.datetime.holiday.IHolidayType;
import com.helger.datetime.holiday.ResourceBundleHoliday;
import com.helger.datetime.holiday.config.Holidays;
import com.helger.datetime.holiday.config.IslamicHoliday;
import com.helger.datetime.holiday.mgr.XMLHolidayHelper;
import com.helger.datetime.holiday.parser.AbstractHolidayParser;

/**
 * This parser calculates gregorian dates for the different islamic holidays.
 * 
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class IslamicHolidayParser extends AbstractHolidayParser
{
  private static final IslamicHolidayParser s_aInstance = new IslamicHolidayParser ();

  private IslamicHolidayParser ()
  {}

  public static IslamicHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final IslamicHoliday aIslamicHoliday : aConfig.getIslamicHoliday ())
    {
      if (!isValid (aIslamicHoliday, nYear))
        continue;

      Set <LocalDate> aIslamicHolidays;
      switch (aIslamicHoliday.getType ())
      {
        case NEWYEAR:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.JANUARY, 1);
          break;
        case ASCHURA:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.JANUARY, 10);
          break;
        case ID_AL_FITR:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.OCTOBER, 1);
          break;
        case ID_UL_ADHA:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.DECEMBER, 10);
          break;
        case LAILAT_AL_BARAT:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.AUGUST, 15);
          break;
        case LAILAT_AL_MIRAJ:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.JULY, 27);
          break;
        case LAILAT_AL_QADR:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.SEPTEMBER, 27);
          break;
        case MAWLID_AN_NABI:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.MARCH, 12);
          break;
        case RAMADAN:
          aIslamicHolidays = CalendarHelper.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.SEPTEMBER, 1);
          break;
        default:
          throw new IllegalArgumentException ("Unknown islamic holiday " + aIslamicHoliday.getType ());
      }

      final IHolidayType aType = XMLHolidayHelper.getType (aIslamicHoliday.getLocalizedType ());
      final String sPropertiesKey = "islamic." + aIslamicHoliday.getType ().name ();
      for (final LocalDate aDate : aIslamicHolidays)
        aHolidayMap.add (aDate, new ResourceBundleHoliday (aType, sPropertiesKey));
    }
  }
}
