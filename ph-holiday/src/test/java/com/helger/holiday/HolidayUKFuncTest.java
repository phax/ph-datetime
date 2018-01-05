/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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
package com.helger.holiday;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import com.helger.commons.locale.country.ECountry;

public final class HolidayUKFuncTest extends AbstractCountryTestBase
{
  private static final int YEAR = 2010;
  private static final String ISO_CODE = "uk";

  @Test
  public void testManagerUKStructure () throws Exception
  {
    validateCalendarData (ISO_CODE, YEAR);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnSunday ()
  {
    doChristmasContainmentTest (2011, 26, 27);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnSaturday ()
  {
    doChristmasContainmentTest (2010, 27, 28);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnFriday ()
  {
    doChristmasContainmentTest (2009, 25, 28);
  }

  private void doChristmasContainmentTest (final int year, final int dayOfChristmas, final int dayOfBoxingday)
  {
    final LocalDate christmas = LocalDate.of (year, Month.DECEMBER, dayOfChristmas);
    final LocalDate boxingday = LocalDate.of (year, Month.DECEMBER, dayOfBoxingday);
    final IHolidayManager holidayManager = HolidayManagerFactory.getHolidayManager (ECountry.UK);
    final HolidayMap holidays = holidayManager.getHolidays (year);
    assertTrue ("There should be christmas on " + christmas, holidays.containsHolidayForDate (christmas));
    assertTrue ("There should be boxing day on " + boxingday, holidays.containsHolidayForDate (boxingday));
  }
}
