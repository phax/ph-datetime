/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
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
package com.helger.datetime.period;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import com.helger.datetime.PDTFactory;

/**
 * Default implementation of the {@link IDateTimePeriod} interface.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class DateTimePeriod extends AbstractFlexiblePeriod <DateTime> implements IDateTimePeriod
{
  public DateTimePeriod ()
  {
    this (null, null);
  }

  public DateTimePeriod (@Nullable final DateTime aStart)
  {
    this (aStart, null);
  }

  public DateTimePeriod (@Nullable final DateTime aStart, @Nullable final DateTime aEnd)
  {
    super (aStart, aEnd);
  }

  public final boolean isValidFor (@Nonnull final DateTime aDate)
  {
    if (aDate == null)
      throw new NullPointerException ("date");

    final DateTime aStart = getStart ();
    if (aStart != null && aStart.isAfter (aDate))
      return false;
    final DateTime aEnd = getEnd ();
    if (aEnd != null && aEnd.isBefore (aDate))
      return false;
    return true;
  }

  public final boolean isValidForNow ()
  {
    return isValidFor (PDTFactory.getCurrentDateTime ());
  }

  public boolean canConvertToPeriod ()
  {
    return getStart () != null && getEnd () != null;
  }

  @Nonnull
  public final Period getAsPeriod ()
  {
    if (!canConvertToPeriod ())
      throw new IllegalStateException ("Cannot convert to a Period!");
    return new Period (getStart (), getEnd ());
  }

  public boolean canConvertToInterval ()
  {
    return getStart () != null && getEnd () != null;
  }

  @Nonnull
  public Interval getAsInterval ()
  {
    if (!canConvertToInterval ())
      throw new IllegalStateException ("Cannot convert to an Interval!");
    return new Interval (getStart (), getEnd ());
  }
}
