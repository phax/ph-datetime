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
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.Fixed;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.Month;
import com.helger.holiday.jaxb.MovingCondition;
import com.helger.holiday.jaxb.Weekday;
import com.helger.holiday.jaxb.With;

/**
 * @author Sven
 */
public final class FixedParserTest
{
  private static final FixedParser s_aParser = FixedParser.getInstance ();

  @Test
  public void testFixedWithValidity ()
  {
    final Holidays h = createHolidays (createFixed (1, Month.JANUARY),
                                       createFixed (3, Month.MARCH),
                                       createFixed (5, Month.MAY, Integer.valueOf (2011), null));
    final HolidayMap set = new HolidayMap ();
    s_aParser.parse (2010, set, h);
    _containsAll (set, LocalDate.of (2010, 1, 1), LocalDate.of (2010, 3, 3));
  }

  @Test
  public void testFixedWithMoving ()
  {
    final Holidays h = createHolidays (createFixed (8, Month.JANUARY, createMoving (Weekday.SATURDAY, With.PREVIOUS, Weekday.FRIDAY)),
                                       createFixed (23, Month.JANUARY, createMoving (Weekday.SUNDAY, With.NEXT, Weekday.MONDAY)));
    final HolidayMap set = new HolidayMap ();
    s_aParser.parse (2011, set, h);
    _containsAll (set, LocalDate.of (2011, 1, 7), LocalDate.of (2011, 1, 24));
  }

  @Test
  public void testCyle2YearsInvalid ()
  {
    final Fixed fixed = createFixed (4, Month.JANUARY);
    fixed.setValidFrom (Integer.valueOf (2010));
    fixed.setEvery ("2_YEARS");
    final Holidays holidays = createHolidays (fixed);
    final HolidayMap set = new HolidayMap ();
    s_aParser.parse (2011, set, holidays);
    Assert.assertTrue ("Expected to be empty.", set.isEmpty ());
  }

  @Test
  public void testCyle3Years ()
  {
    final Fixed fixed = createFixed (4, Month.JANUARY);
    fixed.setValidFrom (Integer.valueOf (2010));
    fixed.setEvery ("3_YEARS");
    final Holidays holidays = createHolidays (fixed);
    final HolidayMap set = new HolidayMap ();
    s_aParser.parse (2013, set, holidays);
    Assert.assertEquals ("Wrong number of holidays.", 1, set.size ());
  }

  private void _containsAll (final HolidayMap list, final LocalDate... dates)
  {
    Assert.assertEquals ("Number of holidays.", dates.length, list.size ());
    final List <LocalDate> aSortedList = CollectionHelper.getSorted (list.getAllDates ());
    final List <LocalDate> expected = CollectionHelper.getSorted (dates);
    for (int i = 0; i < expected.size (); i++)
    {
      Assert.assertEquals ("Missing date.", expected.get (i), aSortedList.get (i));
    }
  }

  public static Holidays createHolidays (final Fixed... fs)
  {
    final Holidays h = new Holidays ();
    h.getFixed ().addAll (Arrays.asList (fs));
    return h;
  }

  public static Fixed createFixed (final int day, final Month m, final MovingCondition... mc)
  {
    final Fixed f = new Fixed ();
    f.setDay (Integer.valueOf (day));
    f.setMonth (m);
    f.getMovingCondition ().addAll (Arrays.asList (mc));
    return f;
  }

  public static Fixed createFixed (final int day,
                                   final Month m,
                                   final Integer validFrom,
                                   final Integer validUntil,
                                   final MovingCondition... mc)
  {
    final Fixed f = createFixed (day, m, mc);
    f.setValidFrom (validFrom);
    f.setValidTo (validUntil);
    return f;
  }

  public static MovingCondition createMoving (final Weekday substitute, final With with, final Weekday weekday)
  {
    final MovingCondition mc = new MovingCondition ();
    mc.setSubstitute (substitute);
    mc.setWith (with);
    mc.setWeekday (weekday);
    return mc;
  }
}
