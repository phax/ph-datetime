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
package com.helger.holiday;

import java.io.Serializable;
import java.time.LocalDate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonnegative;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsOrderedMap;
import com.helger.collection.commons.ICommonsOrderedSet;

/**
 * Contains a map of holidays, were the key is the date.
 *
 * @author Philip Helger
 */
public class HolidayMap implements Serializable
{
  private final ICommonsOrderedMap <LocalDate, ISingleHoliday> m_aMap = new CommonsLinkedHashMap <> ();

  public HolidayMap ()
  {}

  public boolean containsHolidayForDate (@Nullable final LocalDate aDate)
  {
    return m_aMap.containsKey (aDate);
  }

  @Nullable
  public ISingleHoliday getHolidayForDate (@Nullable final LocalDate aDate)
  {
    return m_aMap.get (aDate);
  }

  public void add (@NonNull final LocalDate aDate, @NonNull final ISingleHoliday aHoliday)
  {
    ValueEnforcer.notNull (aDate, "Date");
    ValueEnforcer.notNull (aHoliday, "Holiday");
    m_aMap.put (aDate, aHoliday);
  }

  public void addAll (@NonNull final HolidayMap aSubHolidays)
  {
    ValueEnforcer.notNull (aSubHolidays, "SubHolidays");
    m_aMap.putAll (aSubHolidays.m_aMap);
  }

  @NonNull
  public EChange remove (@Nullable final LocalDate aDate)
  {
    return m_aMap.removeObject (aDate);
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsOrderedSet <LocalDate> getAllDates ()
  {
    return m_aMap.copyOfKeySet ();
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <ISingleHoliday> getAllHolidays ()
  {
    return m_aMap.copyOfValues ();
  }

  @NonNull
  @ReturnsMutableCopy
  public ICommonsOrderedMap <LocalDate, ISingleHoliday> getMap ()
  {
    return m_aMap.getClone ();
  }

  @Nonnegative
  public int size ()
  {
    return m_aMap.size ();
  }

  public boolean isEmpty ()
  {
    return m_aMap.isEmpty ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final HolidayMap rhs = (HolidayMap) o;
    return m_aMap.equals (rhs.m_aMap);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMap).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("map", m_aMap).getToString ();
  }
}
