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

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.FixedWeekdayInMonth;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.Which;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class FixedWeekdayInMonthParser extends AbstractHolidayParser
{
  private static final FixedWeekdayInMonthParser s_aInstance = new FixedWeekdayInMonthParser ();

  protected FixedWeekdayInMonthParser ()
  {}

  public static FixedWeekdayInMonthParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidays, final Holidays aConfig)
  {
    for (final FixedWeekdayInMonth aFWM : aConfig.getFixedWeekday ())
    {
      if (!isValid (aFWM, nYear))
        continue;
      final LocalDate aDate = parse (nYear, aFWM);
      final IHolidayType aType = XMLHolidayHelper.getType (aFWM.getLocalizedType ());
      final String sPropertyKey = aFWM.getDescriptionPropertiesKey ();
      aHolidays.add (aDate, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }

  protected static LocalDate parse (final int nYear, final FixedWeekdayInMonth aFixedWeekdayInMonth)
  {
    LocalDate aDate = LocalDate.of (nYear, XMLHolidayHelper.getMonth (aFixedWeekdayInMonth.getMonth ()), 1);
    int nDirection = 1;
    if (aFixedWeekdayInMonth.getWhich () == Which.LAST)
    {
      aDate = aDate.plusMonths (1).withDayOfMonth (1).minusDays (1);
      nDirection = -1;
    }
    final DayOfWeek nWeekDay = XMLHolidayHelper.getWeekday (aFixedWeekdayInMonth.getWeekday ());
    while (aDate.getDayOfWeek () != nWeekDay)
    {
      aDate = aDate.plusDays (nDirection);
    }
    switch (aFixedWeekdayInMonth.getWhich ())
    {
      case FIRST:
        break;
      case SECOND:
        aDate = aDate.plusDays (7);
        break;
      case THIRD:
        aDate = aDate.plusDays (14);
        break;
      case FOURTH:
        aDate = aDate.plusDays (21);
        break;
      case LAST:
        break;
    }
    return aDate;
  }
}
