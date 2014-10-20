/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.datetime;

import javax.annotation.concurrent.Immutable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import com.helger.commons.annotations.PresentForCodeCoverage;

/**
 * Some date/time related constants.
 *
 * @author Philip Helger
 */
@Immutable
public final class CPDT
{
  /** Default start of week: Monday */
  public static final int START_OF_WEEK_DAY = DateTimeConstants.MONDAY;
  /** Default end of week: Sunday */
  public static final int END_OF_WEEK_DAY = DateTimeConstants.SUNDAY;

  public static final int MIN_YEAR_INT32 = 1970;

  /** Default null local date: 1.1.1970 */
  public static final LocalDate NULL_LOCAL_DATE = PDTFactory.createLocalDate (MIN_YEAR_INT32,
                                                                              DateTimeConstants.JANUARY,
                                                                              1);
  /** Default null local time: 00:00:00.000 */
  public static final LocalTime NULL_LOCAL_TIME = PDTFactory.createLocalTime (0, 0, 0);
  /** Default null local date time : 1.1.1970 00:00:00.000 */
  public static final LocalDateTime NULL_LOCAL_DATETIME = NULL_LOCAL_DATE.toLocalDateTime (NULL_LOCAL_TIME);
  /** Default null date time : 1.1.1970 00:00:00.000 with the default timezone! */
  public static final DateTime NULL_DATETIME = PDTFactory.createDateTimeFromMillis (0);
  /** Default null date time : 1.1.1970 00:00:00.000 with the UTC timezone! */
  public static final DateTime NULL_DATETIME_UTC = PDTFactory.createDateTimeFromMillisUTC (0);
  /** Default empty period */
  public static final Period NULL_PERIOD = new Period ();
  /** Default empty duration */
  public static final Duration NULL_DURATION = new Duration (0);

  /** The last year to which the Julian choreography can be applied. */
  public static final int LAST_JULIAN_YEAR = 1583;

  @PresentForCodeCoverage
  @SuppressWarnings ("unused")
  private static final CPDT s_aInstance = new CPDT ();

  private CPDT ()
  {}
}
