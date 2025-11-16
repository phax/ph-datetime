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
package com.helger.holiday.mgr;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonnegative;
import com.helger.holiday.EHolidayType;
import com.helger.holiday.IHolidayType;
import com.helger.holiday.jaxb.Fixed;
import com.helger.holiday.jaxb.HolidayType;
import com.helger.holiday.jaxb.Month;
import com.helger.holiday.jaxb.Weekday;

/**
 * @author svdi1de
 * @author Philip Helger
 */
public final class XMLHolidayHelper
{
  private XMLHolidayHelper ()
  {}

  /**
   * Returns the {@link DayOfWeek} value for the given weekday.
   *
   * @param eWeekday
   *        Day of week to convert
   * @return {@link DayOfWeek} value.
   */
  @Nonnegative
  public static DayOfWeek getWeekday (@NonNull final Weekday eWeekday)
  {
    switch (eWeekday)
    {
      case MONDAY:
        return DayOfWeek.MONDAY;
      case TUESDAY:
        return DayOfWeek.TUESDAY;
      case WEDNESDAY:
        return DayOfWeek.WEDNESDAY;
      case THURSDAY:
        return DayOfWeek.THURSDAY;
      case FRIDAY:
        return DayOfWeek.FRIDAY;
      case SATURDAY:
        return DayOfWeek.SATURDAY;
      case SUNDAY:
        return DayOfWeek.SUNDAY;
      default:
        throw new IllegalArgumentException ("Unknown weekday " + eWeekday);
    }
  }

  /**
   * Returns the {@link java.time.Month} value for the given month.
   *
   * @param eMonth
   *        Month of year to convert
   * @return {@link java.time.Month} value.
   */
  @Nonnegative
  public static java.time.Month getMonth (@NonNull final Month eMonth)
  {
    switch (eMonth)
    {
      case JANUARY:
        return java.time.Month.JANUARY;
      case FEBRUARY:
        return java.time.Month.FEBRUARY;
      case MARCH:
        return java.time.Month.MARCH;
      case APRIL:
        return java.time.Month.APRIL;
      case MAY:
        return java.time.Month.MAY;
      case JUNE:
        return java.time.Month.JUNE;
      case JULY:
        return java.time.Month.JULY;
      case AUGUST:
        return java.time.Month.AUGUST;
      case SEPTEMBER:
        return java.time.Month.SEPTEMBER;
      case OCTOBER:
        return java.time.Month.OCTOBER;
      case NOVEMBER:
        return java.time.Month.NOVEMBER;
      case DECEMBER:
        return java.time.Month.DECEMBER;
      default:
        throw new IllegalArgumentException ("Unknown month " + eMonth);
    }
  }

  /**
   * Gets the type.
   *
   * @param eType
   *        the type of holiday in the config
   * @return the type of holiday
   */
  @NonNull
  public static IHolidayType getType (@NonNull final HolidayType eType)
  {
    switch (eType)
    {
      case OFFICIAL_HOLIDAY:
        return EHolidayType.OFFICIAL_HOLIDAY;
      case UNOFFICIAL_HOLIDAY:
        return EHolidayType.UNOFFICIAL_HOLIDAY;
      default:
        throw new IllegalArgumentException ("Unknown type " + eType);
    }
  }

  /**
   * Creates the date from the month/day within the specified year.
   *
   * @param nYear
   *        Year to created the date for
   * @param aFixed
   *        The fixed information
   * @return A local date instance.
   */
  @NonNull
  public static LocalDate create (@Nonnegative final int nYear, @NonNull final Fixed aFixed)
  {
    return LocalDate.of (nYear, getMonth (aFixed.getMonth ()), aFixed.getDay ().intValue ());
  }
}
