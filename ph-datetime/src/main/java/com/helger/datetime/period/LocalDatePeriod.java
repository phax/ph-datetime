package com.helger.datetime.period;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

public class LocalDatePeriod implements ILocalDatePeriod
{
  private final LocalDate m_aStart;
  private final LocalDate m_aEnd;

  public LocalDatePeriod (@Nullable final LocalDate aStart, @Nullable final LocalDate aEnd)
  {
    m_aStart = aStart;
    m_aEnd = aEnd;
  }

  @Nullable
  public LocalDate getStart ()
  {
    return m_aStart;
  }

  @Nullable
  public LocalDate getEnd ()
  {
    return m_aEnd;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final LocalDatePeriod rhs = (LocalDatePeriod) o;
    return EqualsHelper.equals (m_aStart, rhs.m_aStart) && EqualsHelper.equals (m_aEnd, rhs.m_aEnd);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aStart).append (m_aEnd).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Start", m_aStart).append ("End", m_aEnd).toString ();
  }
}
