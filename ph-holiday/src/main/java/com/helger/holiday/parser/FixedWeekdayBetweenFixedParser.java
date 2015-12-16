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
package com.helger.holiday.parser;

import org.joda.time.LocalDate;

import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.FixedWeekdayBetweenFixed;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * Parses the configuration for fixed weekdays between two fixed dates.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class FixedWeekdayBetweenFixedParser extends AbstractHolidayParser
{
  private static final FixedWeekdayBetweenFixedParser s_aInstance = new FixedWeekdayBetweenFixedParser ();

  private FixedWeekdayBetweenFixedParser ()
  {}

  public static FixedWeekdayBetweenFixedParser getInstance ()
  {
    return s_aInstance;
  }

  /**
   * Parses the provided configuration and creates holidays for the provided
   * year.
   */
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final FixedWeekdayBetweenFixed aFixedWeekdayBetweenFixed : aConfig.getFixedWeekdayBetweenFixed ())
    {
      if (!isValid (aFixedWeekdayBetweenFixed, nYear))
        continue;

      final int nExpectedWeekday = XMLHolidayHelper.getWeekday (aFixedWeekdayBetweenFixed.getWeekday ());
      LocalDate aFrom = XMLHolidayHelper.create (nYear, aFixedWeekdayBetweenFixed.getFrom ());
      final LocalDate aTo = XMLHolidayHelper.create (nYear, aFixedWeekdayBetweenFixed.getTo ());
      LocalDate aResult = null;
      while (!aFrom.isAfter (aTo))
      {
        if (aFrom.getDayOfWeek () == nExpectedWeekday)
        {
          aResult = aFrom;
          break;
        }
        aFrom = aFrom.plusDays (1);
      }

      if (aResult != null)
      {
        final IHolidayType aType = XMLHolidayHelper.getType (aFixedWeekdayBetweenFixed.getLocalizedType ());
        final String sPropertyKey = aFixedWeekdayBetweenFixed.getDescriptionPropertiesKey ();
        aHolidayMap.add (aResult, new ResourceBundleHoliday (aType, sPropertyKey));
      }
    }
  }
}
