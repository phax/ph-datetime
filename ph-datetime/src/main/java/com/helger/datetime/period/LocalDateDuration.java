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

import java.time.Duration;
import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;

/**
 * Default implementation of the {@link ILocalDateDuration} interface.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class LocalDateDuration extends AbstractFlexibleDuration <LocalDate> implements ILocalDateDuration
{
  public LocalDateDuration ()
  {
    this (null, null);
  }

  public LocalDateDuration (@Nullable final LocalDate aStart)
  {
    this (aStart, null);
  }

  public LocalDateDuration (@Nullable final LocalDate aStart, @Nullable final LocalDate aEnd)
  {
    super (aStart, aEnd);
  }

  public final boolean isValidFor (@Nonnull final LocalDate aDate)
  {
    ValueEnforcer.notNull (aDate, "Date");

    final LocalDate aStart = getStart ();
    if (aStart != null && aStart.isAfter (aDate))
      return false;
    final LocalDate aEnd = getEnd ();
    if (aEnd != null && aEnd.isBefore (aDate))
      return false;
    return true;
  }

  @Nonnull
  public final Duration getAsDuration ()
  {
    if (!canConvertToDuration ())
      throw new IllegalStateException ("Cannot convert to a Duration!");
    return Duration.between (getStart (), getEnd ());
  }
}
