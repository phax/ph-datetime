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
package com.helger.datetime.period;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import com.helger.datetime.PDTFactory;

/**
 * Default implementation of the {@link ILocalDateTimePeriod} interface.
 * 
 * @author Philip Helger
 */
@NotThreadSafe
public class LocalDateTimePeriod extends AbstractFlexiblePeriod <LocalDateTime> implements ILocalDateTimePeriod
{
  public LocalDateTimePeriod ()
  {
    this (null, null);
  }

  public LocalDateTimePeriod (@Nullable final LocalDateTime aStart)
  {
    this (aStart, null);
  }

  public LocalDateTimePeriod (@Nullable final LocalDateTime aStart, @Nullable final LocalDateTime aEnd)
  {
    super (aStart, aEnd);
  }

  public final boolean isValidFor (@Nonnull final LocalDateTime aDate)
  {
    if (aDate == null)
      throw new NullPointerException ("date");

    final LocalDateTime aStart = getStart ();
    if (aStart != null && aStart.isAfter (aDate))
      return false;
    final LocalDateTime aEnd = getEnd ();
    if (aEnd != null && aEnd.isBefore (aDate))
      return false;
    return true;
  }

  public final boolean isValidForNow ()
  {
    return isValidFor (PDTFactory.getCurrentLocalDateTime ());
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
