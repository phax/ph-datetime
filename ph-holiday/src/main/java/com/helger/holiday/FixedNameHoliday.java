/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
package com.helger.holiday;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Default implementation of the {@link ISingleHoliday} interface.
 *
 * @author Philip Helger
 */
@Immutable
public class FixedNameHoliday implements ISingleHoliday
{
  private final boolean m_bIsOfficial;
  private final String m_sHolidayName;

  public FixedNameHoliday (@Nonnull final String sHolidayName)
  {
    this (EHolidayType.OFFICIAL_HOLIDAY, sHolidayName);
  }

  public FixedNameHoliday (@Nonnull final IHolidayType aType, @Nonnull final String sHolidayName)
  {
    ValueEnforcer.notNull (aType, "Type");
    ValueEnforcer.notEmpty (sHolidayName, "HolidayName");
    m_bIsOfficial = aType.isOfficialHoliday ();
    m_sHolidayName = sHolidayName;
  }

  public boolean isOfficialHoliday ()
  {
    return m_bIsOfficial;
  }

  public String getHolidayName (final Locale aContentLocale)
  {
    return m_sHolidayName;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final FixedNameHoliday rhs = (FixedNameHoliday) o;
    return m_bIsOfficial == rhs.m_bIsOfficial && m_sHolidayName.equals (rhs.m_sHolidayName);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_bIsOfficial).append (m_sHolidayName).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("official", m_bIsOfficial)
                                       .append ("holidayName", m_sHolidayName)
                                       .getToString ();
  }
}
