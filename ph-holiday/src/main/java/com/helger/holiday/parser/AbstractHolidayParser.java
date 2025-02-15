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

import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.holiday.jaxb.Holiday;
import com.helger.holiday.jaxb.MoveableHoliday;
import com.helger.holiday.jaxb.MovingCondition;
import com.helger.holiday.jaxb.With;
import com.helger.holiday.mgr.XMLHolidayHelper;

/**
 * The abstract base class for all {@link IHolidayParser} implementations.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
@Immutable
public abstract class AbstractHolidayParser implements IHolidayParser
{
  protected AbstractHolidayParser ()
  {}

  /**
   * Evaluates if the provided <code>Holiday</code> instance is valid for the
   * provided year.
   *
   * @param aHoliday
   *        The holiday configuration entry to validate
   * @param nYear
   *        The year to validate against.
   * @return is valid for the year.
   */
  protected static final boolean isValid (final Holiday aHoliday, final int nYear)
  {
    return _isValidInYear (aHoliday, nYear) && _isValidForCycle (aHoliday, nYear);
  }

  /**
   * Checks cyclic holidays and checks if the requested year is hit within the
   * cycles.
   *
   * @param aHoliday
   *        Holiday
   * @param nYear
   *        the year to check against
   * @return is valid
   */
  private static boolean _isValidForCycle (@Nonnull final Holiday aHoliday, final int nYear)
  {
    final String sEvery = aHoliday.getEvery ();
    if (sEvery != null && !"EVERY_YEAR".equals (sEvery))
    {
      if ("ODD_YEARS".equals (sEvery))
        return nYear % 2 != 0;
      if ("EVEN_YEARS".equals (sEvery))
        return nYear % 2 == 0;

      if (aHoliday.getValidFrom () != null)
      {
        int nCycleYears = 0;
        if ("2_YEARS".equalsIgnoreCase (sEvery))
          nCycleYears = 2;
        else
          if ("3_YEARS".equalsIgnoreCase (sEvery))
            nCycleYears = 3;
          else
            if ("4_YEARS".equalsIgnoreCase (sEvery))
              nCycleYears = 4;
            else
              if ("5_YEARS".equalsIgnoreCase (sEvery))
                nCycleYears = 5;
              else
                if ("6_YEARS".equalsIgnoreCase (sEvery))
                  nCycleYears = 6;
                else
                  throw new IllegalArgumentException ("Cannot handle unknown cycle type '" + sEvery + "'.");

        return (nYear - aHoliday.getValidFrom ().intValue ()) % nCycleYears == 0;
      }
    }
    return true;
  }

  /**
   * Checks whether the holiday is within the valid date range.
   *
   * @param aHoliday
   * @param nYear
   * @return is valid.
   */
  private static boolean _isValidInYear (@Nonnull final Holiday aHoliday, final int nYear)
  {
    return (aHoliday.getValidFrom () == null || aHoliday.getValidFrom ().intValue () <= nYear) &&
           (aHoliday.getValidTo () == null || aHoliday.getValidTo ().intValue () >= nYear);
  }

  /**
   * Determines if the provided date shall be substituted.
   *
   * @param aFixed
   *        The date to be checked. May not be <code>null</code>.
   * @param aMoveCond
   *        The move condition. May not be <code>null</code>.
   * @return <code>true</code> if it should be substituted
   */
  protected static final boolean shallBeMoved (@Nonnull final LocalDate aFixed, @Nonnull final MovingCondition aMoveCond)
  {
    return aFixed.getDayOfWeek () == XMLHolidayHelper.getWeekday (aMoveCond.getSubstitute ());
  }

  /**
   * Moves the date using the FixedMoving information
   *
   * @param aMoveCond
   * @param aDate
   * @return The moved date
   */
  private static LocalDate _moveDate (final MovingCondition aMoveCond, final LocalDate aDate)
  {
    final DayOfWeek nWeekday = XMLHolidayHelper.getWeekday (aMoveCond.getWeekday ());
    final int nDirection = aMoveCond.getWith () == With.NEXT ? 1 : -1;

    LocalDate aMovedDate = aDate;
    while (aMovedDate.getDayOfWeek () != nWeekday)
    {
      aMovedDate = aMovedDate.plusDays (nDirection);
    }
    return aMovedDate;
  }

  /**
   * Moves a date if there are any moving conditions for this holiday and any of
   * them fit.
   *
   * @param aMoveableHoliday
   *        Date
   * @param aFixed
   *        Optional fixed date
   * @return the moved date
   */
  protected static final LocalDate moveDate (final MoveableHoliday aMoveableHoliday, final LocalDate aFixed)
  {
    for (final MovingCondition aMoveCond : aMoveableHoliday.getMovingCondition ())
      if (shallBeMoved (aFixed, aMoveCond))
        return _moveDate (aMoveCond, aFixed);
    return aFixed;
  }
}
