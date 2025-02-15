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
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.holiday.HolidayMap;
import com.helger.holiday.jaxb.ChristianHoliday;
import com.helger.holiday.jaxb.ChristianHolidayType;
import com.helger.holiday.jaxb.ChronologyType;
import com.helger.holiday.jaxb.Holidays;
import com.helger.holiday.jaxb.RelativeToEasterSunday;

/**
 * @author svdi1de
 */
public final class ChristianHolidayParserTest
{
  private static final AbstractHolidayParser s_aParser = ChristianHolidayParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = new Holidays ();
    s_aParser.parse (2010, aHolidays, aConfig);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testEaster ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (ChristianHolidayType.EASTER);
    s_aParser.parse (2011, aHolidays, aConfig);
    Assert.assertEquals ("Wrong number of aHolidays.", 1, aHolidays.size ());
    final LocalDate aEasterDate = CollectionHelper.getFirstElement (aHolidays.getAllDates ());
    final LocalDate aEndDate = LocalDate.of (2011, 4, 24);
    Assert.assertEquals ("Wrong easter date.", aEndDate, aEasterDate);
  }

  @Test
  public void testChristianInvalidDate ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (ChristianHolidayType.EASTER);
    aConfig.getChristianHoliday ().get (0).setValidTo (Integer.valueOf (2010));
    s_aParser.parse (2011, aHolidays, aConfig);
    Assert.assertEquals ("Wrong number of aHolidays.", 0, aHolidays.size ());
  }

  @Test
  public void testRelativeToEasterSunday ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (1);
    final RelativeToEasterSundayParser aParser = new RelativeToEasterSundayParser ();
    aParser.parse (2011, aHolidays, aConfig);
    final ICommonsList <LocalDate> aExpected = new CommonsArrayList <> ();
    aExpected.add (LocalDate.of (2011, 4, 24));
    Assert.assertEquals ("Wrong number of aHolidays.", aExpected.size (), aHolidays.size ());
    Assert.assertEquals ("Wrong holiday.",
                         aExpected.get (0),
                         CollectionHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testChristianDates ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (ChristianHolidayType.EASTER,
                                            ChristianHolidayType.CLEAN_MONDAY,
                                            ChristianHolidayType.EASTER_SATURDAY,
                                            ChristianHolidayType.EASTER_TUESDAY,
                                            ChristianHolidayType.GENERAL_PRAYER_DAY,
                                            ChristianHolidayType.PENTECOST,
                                            ChristianHolidayType.SACRED_HEART);
    s_aParser.parse (2011, aHolidays, aConfig);
    final ICommonsList <LocalDate> expected = new CommonsArrayList <> ();
    expected.add (LocalDate.of (2011, 3, 7));
    expected.add (LocalDate.of (2011, 4, 23));
    expected.add (LocalDate.of (2011, 4, 24));
    expected.add (LocalDate.of (2011, 4, 26));
    expected.add (LocalDate.of (2011, 5, 20));
    expected.add (LocalDate.of (2011, 6, 12));
    expected.add (LocalDate.of (2011, 7, 1));

    Assert.assertEquals ("Wrong number of aHolidays.", expected.size (), aHolidays.size ());

    Collections.sort (expected);

    final List <LocalDate> found = CollectionHelper.getSorted (aHolidays.getAllDates ());

    for (int i = 0; i < expected.size (); i++)
    {
      Assert.assertEquals ("Wrong date.", expected.get (i), found.get (i));
    }
  }

  private static Holidays _createConfig (final int... days)
  {
    final Holidays aConfig = new Holidays ();
    for (final int day : days)
    {
      final RelativeToEasterSunday d = new RelativeToEasterSunday ();
      d.setDays (day);
      d.setChronology (ChronologyType.GREGORIAN);
      aConfig.getRelativeToEasterSunday ().add (d);
    }
    return aConfig;
  }

  private static Holidays _createConfig (final ChristianHolidayType... types)
  {
    final Holidays aConfig = new Holidays ();
    for (final ChristianHolidayType type : types)
    {
      final ChristianHoliday h = new ChristianHoliday ();
      h.setType (type);
      aConfig.getChristianHoliday ().add (h);
    }
    return aConfig;
  }
}
