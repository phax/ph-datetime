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

import com.helger.commons.collection.CollectionHelper;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.FixedWeekdayBetweenFixed;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.Month;
import com.helger.holiday.jaxb.Weekday;

/**
 * @author svdi1de
 */
public final class FixedWeekdayBetweenFixedParserTest
{
  private static final FixedWeekdayBetweenFixedParser parser = FixedWeekdayBetweenFixedParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    parser.parse (2010, aHolidays, config);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testInvalid ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayBetweenFixed e = new FixedWeekdayBetweenFixed ();
    e.setValidTo (Integer.valueOf (2009));
    config.getFixedWeekdayBetweenFixed ().add (e);
    parser.parse (2010, aHolidays, config);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testWednesday ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayBetweenFixed e = new FixedWeekdayBetweenFixed ();
    e.setFrom (FixedParserTest.createFixed (17, Month.JANUARY));
    e.setTo (FixedParserTest.createFixed (21, Month.JANUARY));
    e.setWeekday (Weekday.WEDNESDAY);
    config.getFixedWeekdayBetweenFixed ().add (e);
    parser.parse (2011, aHolidays, config);
    Assert.assertEquals ("Wrong number of results.", 1, aHolidays.size ());
    Assert.assertEquals ("Wrong date.",
                         LocalDate.of (2011, 1, 19),
                         CollectionHelper.getFirstElement (aHolidays.getAllDates ()));
  }
}
