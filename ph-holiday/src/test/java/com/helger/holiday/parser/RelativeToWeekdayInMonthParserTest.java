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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.FixedWeekdayInMonth;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.Month;
import com.helger.holiday.jaxb.RelativeToWeekdayInMonth;
import com.helger.holiday.jaxb.Weekday;
import com.helger.holiday.jaxb.When;
import com.helger.holiday.jaxb.Which;

/**
 * @author svdi1de
 */
public final class RelativeToWeekdayInMonthParserTest
{
  private static final RelativeToWeekdayInMonthParser s_aParser = RelativeToWeekdayInMonthParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    s_aParser.parse (2011, aHolidays, config);
    assertTrue ("Result is not empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testInvalid ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final RelativeToWeekdayInMonth rule = new RelativeToWeekdayInMonth ();
    rule.setWeekday (Weekday.TUESDAY);
    rule.setWhen (When.AFTER);
    final FixedWeekdayInMonth date = new FixedWeekdayInMonth ();
    date.setWhich (Which.SECOND);
    date.setWeekday (Weekday.MONDAY);
    date.setMonth (Month.JULY);
    rule.setFixedWeekday (date);
    config.getRelativeToWeekdayInMonth ().add (rule);
    rule.setValidFrom (Integer.valueOf (2012));
    s_aParser.parse (2011, aHolidays, config);
    assertTrue ("Result is not empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testTueAfter2ndMondayJuly ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final RelativeToWeekdayInMonth rule = new RelativeToWeekdayInMonth ();
    rule.setWeekday (Weekday.TUESDAY);
    rule.setWhen (When.AFTER);
    final FixedWeekdayInMonth date = new FixedWeekdayInMonth ();
    date.setWhich (Which.SECOND);
    date.setWeekday (Weekday.MONDAY);
    date.setMonth (Month.JULY);
    rule.setFixedWeekday (date);
    config.getRelativeToWeekdayInMonth ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  LocalDate.of (2011, 7, 12),
                  CollectionHelper.getFirstElement (aHolidays.getAllDates ()));
  }
}
