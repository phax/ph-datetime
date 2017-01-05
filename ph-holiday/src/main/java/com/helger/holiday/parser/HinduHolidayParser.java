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
package com.helger.holiday.parser;

import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.HinduHoliday;
import com.helger.holiday.jaxb.Holidays;

/**
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class HinduHolidayParser extends AbstractHolidayParser
{
  private static final HinduHolidayParser s_aInstance = new HinduHolidayParser ();

  private HinduHolidayParser ()
  {}

  public static HinduHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final HinduHoliday aHinduHoliday : aConfig.getHinduHoliday ())
    {
      if (!isValid (aHinduHoliday, nYear))
        continue;
      switch (aHinduHoliday.getType ())
      {
        case HOLI:
          // 20 February and ending on 21 March (20th march in leap years)
          // TODO: Calculate with hindu calendar.
          break;
        default:
          throw new IllegalArgumentException ("Unknown hindu holiday " + aHinduHoliday.getType ());
      }
    }
  }
}
