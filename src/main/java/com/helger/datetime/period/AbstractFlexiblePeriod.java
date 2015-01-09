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

import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * Abstract base implementation for {@link IFlexiblePeriod}.
 * 
 * @author Philip Helger
 * @param <DATATYPE>
 *        Date and time type
 */
@NotThreadSafe
public abstract class AbstractFlexiblePeriod <DATATYPE> implements IFlexiblePeriod <DATATYPE>
{
  private DATATYPE m_aStart;
  private DATATYPE m_aEnd;

  public AbstractFlexiblePeriod (@Nullable final DATATYPE aStart, @Nullable final DATATYPE aEnd)
  {
    setStart (aStart);
    setEnd (aEnd);
  }

  @Nullable
  public final DATATYPE getStart ()
  {
    return m_aStart;
  }

  @Nonnull
  public final EChange setStart (@Nullable final DATATYPE aStart)
  {
    if (EqualsUtils.equals (m_aStart, aStart))
      return EChange.UNCHANGED;
    m_aStart = aStart;
    return EChange.CHANGED;
  }

  @Nullable
  public final DATATYPE getEnd ()
  {
    return m_aEnd;
  }

  @Nonnull
  public final EChange setEnd (@Nullable final DATATYPE aEnd)
  {
    if (EqualsUtils.equals (m_aEnd, aEnd))
      return EChange.UNCHANGED;
    m_aEnd = aEnd;
    return EChange.CHANGED;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final AbstractFlexiblePeriod <?> rhs = (AbstractFlexiblePeriod <?>) o;
    return EqualsUtils.equals (m_aStart, rhs.m_aStart) && EqualsUtils.equals (m_aEnd, rhs.m_aEnd);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aStart).append (m_aEnd).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).appendIfNotNull ("start", m_aStart).appendIfNotNull ("end", m_aEnd).toString ();
  }
}
