/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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

import org.junit.Assert;
import org.junit.Test;

import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.EthiopianOrthodoxHoliday;
import com.helger.holiday.jaxb.EthiopianOrthodoxHolidayType;
import com.helger.holiday.jaxb.Holidays;

/**
 * @author Sven
 */
public final class EthiopianOrthodoxHolidayParserTest
{
  private static final EthiopianOrthodoxHolidayParser s_aParser = EthiopianOrthodoxHolidayParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    s_aParser.parse (2010, aHolidays, config);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testAllHolidays ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    config.getEthiopianOrthodoxHoliday ().add (createHoliday (EthiopianOrthodoxHolidayType.ENKUTATASH));
    config.getEthiopianOrthodoxHoliday ().add (createHoliday (EthiopianOrthodoxHolidayType.MESKEL));
    config.getEthiopianOrthodoxHoliday ().add (createHoliday (EthiopianOrthodoxHolidayType.TIMKAT));
    s_aParser.parse (2010, aHolidays, config);
    Assert.assertEquals ("Wrong number of aHolidays.", 3, aHolidays.size ());
    _assertContains (LocalDate.of (2010, 1, 18), aHolidays);
    _assertContains (LocalDate.of (2010, 9, 11), aHolidays);
    _assertContains (LocalDate.of (2010, 9, 27), aHolidays);
  }

  private static void _assertContains (final LocalDate date, final HolidayMap aHolidays)
  {
    Assert.assertTrue ("Missing holiday " + date, aHolidays.containsHolidayForDate (date));
  }

  /**
   * @param type
   *        Type
   * @return an EthiopianOrthodoxHoliday instance
   */
  public static EthiopianOrthodoxHoliday createHoliday (final EthiopianOrthodoxHolidayType type)
  {
    final EthiopianOrthodoxHoliday aHoliday = new EthiopianOrthodoxHoliday ();
    aHoliday.setType (type);
    return aHoliday;
  }
}
