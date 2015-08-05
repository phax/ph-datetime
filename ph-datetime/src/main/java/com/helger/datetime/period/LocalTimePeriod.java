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
package com.helger.datetime.period;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.joda.time.LocalTime;
import org.joda.time.Period;

import com.helger.commons.ValueEnforcer;
import com.helger.datetime.PDTFactory;

/**
 * Default implementation of the {@link ILocalTimePeriod} interface.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class LocalTimePeriod extends AbstractFlexiblePeriod <LocalTime>implements ILocalTimePeriod
{
  public LocalTimePeriod ()
  {
    this (null, null);
  }

  public LocalTimePeriod (@Nullable final LocalTime aStart)
  {
    this (aStart, null);
  }

  public LocalTimePeriod (@Nullable final LocalTime aStart, @Nullable final LocalTime aEnd)
  {
    super (aStart, aEnd);
  }

  public final boolean isValidFor (@Nonnull final LocalTime aDate)
  {
    ValueEnforcer.notNull (aDate, "Date");

    final LocalTime aStart = getStart ();
    if (aStart != null && aStart.isAfter (aDate))
      return false;
    final LocalTime aEnd = getEnd ();
    if (aEnd != null && aEnd.isBefore (aDate))
      return false;
    return true;
  }

  public final boolean isValidForNow ()
  {
    return isValidFor (PDTFactory.getCurrentLocalTime ());
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
}
