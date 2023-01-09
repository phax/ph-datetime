/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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

import com.helger.holiday.HolidayMap;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.ResourceBundleHoliday;
import com.helger.holiday.jaxb.Fixed;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class FixedParser extends AbstractHolidayParser
{
  private static final FixedParser s_aInstance = new FixedParser ();

  private FixedParser ()
  {}

  public static FixedParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidays, final Holidays aConfig)
  {
    for (final Fixed aFixed : aConfig.getFixed ())
    {
      if (!isValid (aFixed, nYear))
        continue;

      final LocalDate aDate = XMLHolidayHelper.create (nYear, aFixed);
      final LocalDate aMovedDate = moveDate (aFixed, aDate);
      final IHolidayType aType = XMLHolidayHelper.getType (aFixed.getLocalizedType ());
      final String sPropertyKey = aFixed.getDescriptionPropertiesKey ();
      aHolidays.add (aMovedDate, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }
}
