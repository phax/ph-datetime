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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.Fixed;
import com.helger.holiday.jaxb.FixedWeekdayRelativeToFixed;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.Month;
import com.helger.holiday.jaxb.Weekday;
import com.helger.holiday.jaxb.When;
import com.helger.holiday.jaxb.Which;

/**
 * @author Sven
 */
public final class FixedWeekdayRelativeToFixedParserTest
{
  private static final FixedWeekdayRelativeToFixedParser s_aParser = FixedWeekdayRelativeToFixedParser.getInstance ();

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
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.FIRST);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.BEFORE);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    rule.setValidTo (Integer.valueOf (2010));
    s_aParser.parse (2011, aHolidays, config);
    assertTrue ("Result is not empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testParserFirstBefore ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.FIRST);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.BEFORE);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 1, 24),
                  CollectionHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testParserSecondBefore ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.SECOND);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.BEFORE);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 1, 17),
                  CollectionHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testParserThirdAfter ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.THIRD);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.AFTER);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 2, 14),
                  CollectionHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testParserFourthAfter ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.FOURTH);
    rule.setWeekday (Weekday.TUESDAY);
    rule.setWhen (When.AFTER);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (15));
    fixed.setMonth (Month.MARCH);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 4, 12),
                  CollectionHelper.getFirstElement (aHolidays.getAllDates ()));
  }
}
