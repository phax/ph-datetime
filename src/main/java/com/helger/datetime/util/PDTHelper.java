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
package com.helger.datetime.util;//NOPMD

import java.util.Comparator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.ReadableDateTime;
import org.joda.time.base.AbstractInstant;
import org.joda.time.base.AbstractPartial;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.datetime.CPDT;
import com.helger.datetime.PDTFactory;

/**
 * Some date/time utility methods.
 *
 * @author Philip Helger
 */
@Immutable
public final class PDTHelper
{
  @PresentForCodeCoverage
  private static final PDTHelper s_aInstance = new PDTHelper ();

  private PDTHelper ()
  {}

  public static boolean isNullValue (@Nullable final LocalDate aDate)
  {
    return aDate == null || aDate == CPDT.NULL_LOCAL_DATE;
  }

  public static boolean isNullValue (@Nullable final LocalTime aTime)
  {
    return aTime == null || aTime == CPDT.NULL_LOCAL_TIME;
  }

  public static boolean isNullValue (@Nullable final LocalDateTime aDateTime)
  {
    return aDateTime == null || aDateTime == CPDT.NULL_LOCAL_DATETIME;
  }

  public static boolean isNullValue (@Nullable final DateTime aDateTime)
  {
    return aDateTime == null || aDateTime == CPDT.NULL_DATETIME;
  }

  public static boolean isNullValue (@Nullable final ReadableDateTime aDateTime)
  {
    return aDateTime == null || aDateTime.getMillis () == 0;
  }

  public static boolean isWeekendDay (final int nDayOfWeek)
  {
    return nDayOfWeek == DateTimeConstants.SATURDAY || nDayOfWeek == DateTimeConstants.SUNDAY;
  }

  public static boolean isWeekend (@Nonnull final ReadableDateTime aDT)
  {
    return isWeekendDay (aDT.getDayOfWeek ());
  }

  public static boolean isWeekend (@Nonnull final LocalDate aDT)
  {
    return isWeekendDay (aDT.getDayOfWeek ());
  }

  public static boolean isFirstDayOfWeek (final int nDayOfWeek)
  {
    return nDayOfWeek == CPDT.START_OF_WEEK_DAY;
  }

  public static boolean isFirstDayOfWeek (@Nonnull final ReadableDateTime aDT)
  {
    return isFirstDayOfWeek (aDT.getDayOfWeek ());
  }

  public static boolean isFirstDayOfWeek (@Nonnull final LocalDate aDT)
  {
    return isFirstDayOfWeek (aDT.getDayOfWeek ());
  }

  public static boolean isLastDayOfWeek (final int nDayOfWeek)
  {
    return nDayOfWeek == CPDT.END_OF_WEEK_DAY;
  }

  public static boolean isLastDayOfWeek (@Nonnull final ReadableDateTime aDT)
  {
    return isLastDayOfWeek (aDT.getDayOfWeek ());
  }

  public static boolean isLastDayOfWeek (@Nonnull final LocalDate aDT)
  {
    return isLastDayOfWeek (aDT.getDayOfWeek ());
  }

  public static boolean isWorkDay (@Nonnull final LocalDate aDate)
  {
    return !isWeekend (aDate);
  }

  /**
   * Count all non-weekend days in the range. Does not consider holidays!
   *
   * @param aStartDate
   *        start date
   * @param aEndDate
   *        end date
   * @return days not counting Saturdays and Sundays. If start date is after end
   *         date, the value will be negative! If start date equals end date the
   *         return will be 1 if it is a week day.
   */
  public static int getWeekDays (@Nonnull final LocalDate aStartDate, @Nonnull final LocalDate aEndDate)
  {
    ValueEnforcer.notNull (aStartDate, "StartDate");
    ValueEnforcer.notNull (aEndDate, "EndDate");

    final boolean bFlip = aStartDate.isAfter (aEndDate);
    LocalDate aCurDate = bFlip ? aEndDate : aStartDate;
    final LocalDate aRealEndDate = bFlip ? aStartDate : aEndDate;

    int ret = 0;
    while (!aRealEndDate.isBefore (aCurDate))
    {
      if (!isWeekend (aCurDate))
        ret++;
      aCurDate = aCurDate.plusDays (1);
    }
    return bFlip ? -1 * ret : ret;
  }

  public static boolean isSameYearAndDay (@Nonnull final LocalDate x, @Nonnull final LocalDate y)
  {
    return x.getYear () == y.getYear () && x.getDayOfYear () == y.getDayOfYear ();
  }

  public static boolean isSameYearAndWeek (@Nonnull final LocalDate x, @Nonnull final LocalDate y)
  {
    return x.getYear () == y.getYear () && x.getWeekOfWeekyear () == y.getWeekOfWeekyear ();
  }

  public static boolean isSameMonthAndDay (@Nonnull final LocalDate x, @Nonnull final LocalDate y)
  {
    return x.getMonthOfYear () == y.getMonthOfYear () && x.getDayOfMonth () == y.getDayOfMonth ();
  }

  public static boolean isBetweenIncl (@Nullable final LocalDate aDate,
                                       @Nullable final LocalDate aLowerBound,
                                       @Nullable final LocalDate aUpperBound)
  {
    if (aDate == null || aLowerBound == null || aUpperBound == null)
      return false;
    return !aLowerBound.isAfter (aDate) && !aDate.isAfter (aUpperBound);
  }

  /**
   * Get the start--week number for the passed year and month.
   *
   * @param aDT
   *        The object to use year and month from.
   * @return the start week number.
   */
  @Nonnull
  public static int getStartWeekOfMonth (@Nonnull final DateTime aDT)
  {
    return aDT.withDayOfMonth (1).getWeekOfWeekyear ();
  }

  /**
   * Get the end-week number for the passed year and month.
   *
   * @param aDT
   *        The object to use year and month from.
   * @return The end week number.
   */
  @Nonnull
  public static int getEndWeekOfMonth (@Nonnull final DateTime aDT)
  {
    return aDT.withDayOfMonth (aDT.dayOfMonth ().getMaximumValue ()).getWeekOfWeekyear ();
  }

  @Nonnull
  public static LocalDate getCurrentOrNextWeekday ()
  {
    LocalDate aDT = PDTFactory.getCurrentLocalDate ();
    while (isWeekend (aDT))
      aDT = aDT.plusDays (1);
    return aDT;
  }

  /**
   * Get the next working day based on the current day. If the current day is a
   * working day, the current day is returned. A working day is determined by:
   * it's not a weekend day (usually Saturday or Sunday).
   *
   * @return The next matching date.
   */
  @Nonnull
  public static LocalDate getCurrentOrNextWorkDay ()
  {
    LocalDate aDT = PDTFactory.getCurrentLocalDate ();
    while (isWorkDay (aDT))
      aDT = aDT.plusDays (1);
    return aDT;
  }

  /**
   * Compare two dates by birthday. This means, the dates are only compared by
   * day and month, and <b>not</b> by year!
   *
   * @param aDate1
   *        First date. May be <code>null</code>.
   * @param aDate2
   *        Second date. May be <code>null</code>.
   * @return same as {@link Comparator#compare(Object, Object)}
   */
  public static int birthdayCompare (@Nullable final LocalDate aDate1, @Nullable final LocalDate aDate2)
  {
    if (aDate1 == aDate2)
      return 0;
    if (aDate1 == null)
      return -1;
    if (aDate2 == null)
      return 1;

    // first compare month
    int ret = aDate1.getMonthOfYear () - aDate2.getMonthOfYear ();
    if (ret == 0)
    {
      // on equal month, compare day of month
      ret = aDate1.getDayOfMonth () - aDate2.getDayOfMonth ();
    }
    return ret;
  }

  /**
   * Check if the two birthdays are equal. Equal birthdays are identified by
   * equal months and equal days.
   *
   * @param aDate1
   *        First date. May be <code>null</code>.
   * @param aDate2
   *        Second date. May be <code>null</code>.
   * @return <code>true</code> if month and day are equal
   */
  public static boolean birthdayEquals (@Nullable final LocalDate aDate1, @Nullable final LocalDate aDate2)
  {
    return birthdayCompare (aDate1, aDate2) == 0;
  }

  public static boolean isNewYearsEve (@Nonnull final LocalDate aDate)
  {
    if (aDate == null)
      throw new NullPointerException ("date");
    return aDate.getMonthOfYear () == DateTimeConstants.DECEMBER && aDate.getDayOfMonth () == 31;
  }

  @Nullable
  public static <T extends AbstractPartial> T min (@Nullable final T aPartial1, @Nullable final T aPartial2)
  {
    if (aPartial1 == null)
      return aPartial2;
    if (aPartial2 == null)
      return aPartial1;
    return aPartial1.isBefore (aPartial2) ? aPartial1 : aPartial2;
  }

  @Nullable
  public static <T extends AbstractPartial> T max (@Nullable final T aPartial1, @Nullable final T aPartial2)
  {
    if (aPartial1 == null)
      return aPartial2;
    if (aPartial2 == null)
      return aPartial1;
    return aPartial1.isAfter (aPartial2) ? aPartial1 : aPartial2;
  }

  @Nullable
  public static <T extends AbstractInstant> T min (@Nullable final T aInstant1, @Nullable final T aInstant2)
  {
    if (aInstant1 == null)
      return aInstant2;
    if (aInstant2 == null)
      return aInstant1;
    return aInstant1.isBefore (aInstant2) ? aInstant1 : aInstant2;
  }

  @Nullable
  public static <T extends AbstractInstant> T max (@Nullable final T aInstant1, @Nullable final T aInstant2)
  {
    if (aInstant1 == null)
      return aInstant2;
    if (aInstant2 == null)
      return aInstant1;
    return aInstant1.isAfter (aInstant2) ? aInstant1 : aInstant2;
  }

  /**
   * Compare two periods. The problem is, that
   * <code>period1.equals (period2)</code> is not equal even though they are
   * semantically equal.
   *
   * @param aPeriod1
   *        First period. May not be <code>null</code>.
   * @param aPeriod2
   *        Second period. May not be <code>null</code>.
   * @return negative value if aPeriod1 is less aPeriod2, 0 if equal, or
   *         positive value if aPeriod1 greater aPeriod2
   */
  public static int compare (@Nonnull final Period aPeriod1, @Nonnull final Period aPeriod2)
  {
    return aPeriod1.toStandardDuration ().compareTo (aPeriod2.toStandardDuration ());
  }

  /**
   * <code>null</code> safe compare.<br>
   * Note: it has the same semantics as
   * {@link com.helger.commons.compare.CompareUtils#nullSafeCompare(Comparable, Comparable)}
   * except that the parameter class does not implement
   * {@link java.lang.Comparable} in a Generics-way!
   *
   * @param aPeriod1
   *        First object. May be <code>null</code>.
   * @param aPeriod2
   *        Second object. May be <code>null</code>.
   * @return -1, 0 or +1
   */
  public static int nullSafeCompare (@Nullable final Period aPeriod1, @Nullable final Period aPeriod2)
  {
    return aPeriod1 == aPeriod2 ? 0 : aPeriod1 == null ? -1 : aPeriod2 == null ? +1 : compare (aPeriod1, aPeriod2);
  }

  /**
   * Check if two periods are equal.
   *
   * @param aPeriod1
   *        First period. May not be <code>null</code>.
   * @param aPeriod2
   *        Second period. May not be <code>null</code>.
   * @return <code>true</code> if the periods are equal
   */
  public static boolean equals (@Nonnull final Period aPeriod1, @Nonnull final Period aPeriod2)
  {
    return aPeriod1 == aPeriod2 || compare (aPeriod1, aPeriod2) == 0;
  }

  public static boolean isGreater (@Nonnull final Period aPeriod1, @Nonnull final Period aPeriod2)
  {
    return compare (aPeriod1, aPeriod2) > 0;
  }

  public static boolean isGreaterOrEqual (@Nonnull final Period aPeriod1, @Nonnull final Period aPeriod2)
  {
    return compare (aPeriod1, aPeriod2) >= 0;
  }

  public static boolean isLess (@Nonnull final Period aPeriod1, @Nonnull final Period aPeriod2)
  {
    return compare (aPeriod1, aPeriod2) < 0;
  }

  public static boolean isLessOrEqual (@Nonnull final Period aPeriod1, @Nonnull final Period aPeriod2)
  {
    return compare (aPeriod1, aPeriod2) <= 0;
  }

  /**
   * <code>null</code> safe compare.<br>
   * Note: it has the same semantics as
   * {@link com.helger.commons.compare.CompareUtils#nullSafeCompare(Comparable, Comparable)}
   * except that the parameter class does not implement
   * {@link java.lang.Comparable} in a Generics-way!
   *
   * @param aDuration1
   *        First object. May be <code>null</code>.
   * @param aDuration2
   *        Second object. May be <code>null</code>.
   * @return -1, 0 or +1
   */
  public static int nullSafeCompare (@Nullable final Duration aDuration1, @Nullable final Duration aDuration2)
  {
    return aDuration1 == aDuration2 ? 0 : aDuration1 == null ? -1
                                                            : aDuration2 == null ? +1
                                                                                : aDuration1.compareTo (aDuration2);
  }

  public static boolean isGreater (@Nonnull final Duration aDuration1, @Nonnull final Duration aDuration2)
  {
    return aDuration1.compareTo (aDuration2) > 0;
  }

  public static boolean isGreaterOrEqual (@Nonnull final Duration aDuration1, @Nonnull final Duration aDuration2)
  {
    return aDuration1.compareTo (aDuration2) >= 0;
  }

  public static boolean isLess (@Nonnull final Duration aDuration1, @Nonnull final Duration aDuration2)
  {
    return aDuration1.compareTo (aDuration2) < 0;
  }

  public static boolean isLessOrEqual (@Nonnull final Duration aDuration1, @Nonnull final Duration aDuration2)
  {
    return aDuration1.compareTo (aDuration2) <= 0;
  }

  /**
   * <code>null</code> safe compare.<br>
   * Note: it has the same semantics as
   * {@link com.helger.commons.compare.CompareUtils#nullSafeCompare(Comparable, Comparable)}
   * except that the parameter class does not implement
   * {@link java.lang.Comparable} in a Generics-way!
   *
   * @param aDT1
   *        First object. May be <code>null</code>.
   * @param aDT2
   *        Second object. May be <code>null</code>.
   * @return -1, 0 or +1
   */
  public static int nullSafeCompare (@Nullable final DateTime aDT1, @Nullable final DateTime aDT2)
  {
    return aDT1 == aDT2 ? 0 : aDT1 == null ? -1 : aDT2 == null ? +1 : aDT1.compareTo (aDT2);
  }

  public static boolean isGreater (@Nonnull final DateTime aDT1, @Nonnull final DateTime aDT2)
  {
    return aDT1.compareTo (aDT2) > 0;
  }

  public static boolean isGreaterOrEqual (@Nonnull final DateTime aDT1, @Nonnull final DateTime aDT2)
  {
    return aDT1.compareTo (aDT2) >= 0;
  }

  public static boolean isLess (@Nonnull final DateTime aDT1, @Nonnull final DateTime aDT2)
  {
    return aDT1.compareTo (aDT2) < 0;
  }

  public static boolean isLessOrEqual (@Nonnull final DateTime aDT1, @Nonnull final DateTime aDT2)
  {
    return aDT1.compareTo (aDT2) <= 0;
  }

  /**
   * <code>null</code> safe compare.<br>
   * Note: it has the same semantics as
   * {@link com.helger.commons.compare.CompareUtils#nullSafeCompare(Comparable, Comparable)}
   * except that the parameter class does not implement
   * {@link java.lang.Comparable} in a Generics-way!
   *
   * @param aDate1
   *        First object. May be <code>null</code>.
   * @param aDate2
   *        Second object. May be <code>null</code>.
   * @return -1, 0 or +1
   */
  public static int nullSafeCompare (@Nullable final LocalDate aDate1, @Nullable final LocalDate aDate2)
  {
    return aDate1 == aDate2 ? 0 : aDate1 == null ? -1 : aDate2 == null ? +1 : aDate1.compareTo (aDate2);
  }

  public static boolean isGreater (@Nonnull final LocalDate aDate1, @Nonnull final LocalDate aDate2)
  {
    return aDate1.compareTo (aDate2) > 0;
  }

  public static boolean isGreaterOrEqual (@Nonnull final LocalDate aDate1, @Nonnull final LocalDate aDate2)
  {
    return aDate1.compareTo (aDate2) >= 0;
  }

  public static boolean isLess (@Nonnull final LocalDate aDate1, @Nonnull final LocalDate aDate2)
  {
    return aDate1.compareTo (aDate2) < 0;
  }

  public static boolean isLessOrEqual (@Nonnull final LocalDate aDate1, @Nonnull final LocalDate aDate2)
  {
    return aDate1.compareTo (aDate2) <= 0;
  }

  /**
   * <code>null</code> safe compare.<br>
   * Note: it has the same semantics as
   * {@link com.helger.commons.compare.CompareUtils#nullSafeCompare(Comparable, Comparable)}
   * except that the parameter class does not implement
   * {@link java.lang.Comparable} in a Generics-way!
   *
   * @param aTime1
   *        First object. May be <code>null</code>.
   * @param aTime2
   *        Second object. May be <code>null</code>.
   * @return -1, 0 or +1
   */
  public static int nullSafeCompare (@Nullable final LocalTime aTime1, @Nullable final LocalTime aTime2)
  {
    return aTime1 == aTime2 ? 0 : aTime1 == null ? -1 : aTime2 == null ? +1 : aTime1.compareTo (aTime2);
  }

  public static boolean isGreater (@Nonnull final LocalTime aTime1, @Nonnull final LocalTime aTime2)
  {
    return aTime1.compareTo (aTime2) > 0;
  }

  public static boolean isGreaterOrEqual (@Nonnull final LocalTime aTime1, @Nonnull final LocalTime aTime2)
  {
    return aTime1.compareTo (aTime2) >= 0;
  }

  public static boolean isLess (@Nonnull final LocalTime aTime1, @Nonnull final LocalTime aTime2)
  {
    return aTime1.compareTo (aTime2) < 0;
  }

  public static boolean isLessOrEqual (@Nonnull final LocalTime aTime1, @Nonnull final LocalTime aTime2)
  {
    return aTime1.compareTo (aTime2) <= 0;
  }

  /**
   * <code>null</code> safe compare.<br>
   * Note: it has the same semantics as
   * {@link com.helger.commons.compare.CompareUtils#nullSafeCompare(Comparable, Comparable)}
   * except that the parameter class does not implement
   * {@link java.lang.Comparable} in a Generics-way!
   *
   * @param aDateTime1
   *        First object. May be <code>null</code>.
   * @param aDateTime2
   *        Second object. May be <code>null</code>.
   * @return -1, 0 or +1
   */
  public static int nullSafeCompare (@Nullable final LocalDateTime aDateTime1, @Nullable final LocalDateTime aDateTime2)
  {
    return aDateTime1 == aDateTime2 ? 0 : aDateTime1 == null ? -1
                                                            : aDateTime2 == null ? +1
                                                                                : aDateTime1.compareTo (aDateTime2);
  }

  public static boolean isGreater (@Nonnull final LocalDateTime aDateTime1, @Nonnull final LocalDateTime aDateTime2)
  {
    return aDateTime1.compareTo (aDateTime2) > 0;
  }

  public static boolean isGreaterOrEqual (@Nonnull final LocalDateTime aDateTime1,
                                          @Nonnull final LocalDateTime aDateTime2)
  {
    return aDateTime1.compareTo (aDateTime2) >= 0;
  }

  public static boolean isLess (@Nonnull final LocalDateTime aDateTime1, @Nonnull final LocalDateTime aDateTime2)
  {
    return aDateTime1.compareTo (aDateTime2) < 0;
  }

  public static boolean isLessOrEqual (@Nonnull final LocalDateTime aDateTime1, @Nonnull final LocalDateTime aDateTime2)
  {
    return aDateTime1.compareTo (aDateTime2) <= 0;
  }
}
