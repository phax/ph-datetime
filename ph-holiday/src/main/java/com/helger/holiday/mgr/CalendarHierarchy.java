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
package com.helger.holiday.mgr;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.id.IHasID;
import com.helger.commons.locale.country.ECountry;
import com.helger.commons.string.ToStringGenerator;

/**
 * Bean class for describing the configuration hierarchy.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public final class CalendarHierarchy implements IHasID <String>
{
  private final String m_sID;
  private final ECountry m_eCountry;
  private final ICommonsMap <String, CalendarHierarchy> m_aChildren = new CommonsHashMap <> ();

  /**
   * Constructor which takes a eventually existing parent hierarchy node and the
   * ID of this hierarchy.
   *
   * @param aParent
   *        parent entry
   * @param sID
   *        Calendar ID
   * @param eCountry
   *        Country to use
   */
  public CalendarHierarchy (@Nullable final CalendarHierarchy aParent,
                            @Nonnull final String sID,
                            @Nullable final ECountry eCountry)
  {
    ValueEnforcer.notNull (sID, "ID");
    m_sID = aParent == null ? sID : aParent.getID () + "_" + sID;
    m_eCountry = eCountry;
  }

  @Nonnull
  public String getID ()
  {
    return m_sID;
  }

  /**
   * Returns the hierarchies description text from the resource bundle.
   *
   * @param aContentLocale
   *        Locale to return the description text for.
   * @return Description text
   */
  @Nonnull
  public String getDescription (final Locale aContentLocale)
  {
    final String ret = m_eCountry == null ? null : m_eCountry.getDisplayText (aContentLocale);
    return ret != null ? ret : "undefined";
  }

  public void addChild (@Nonnull final CalendarHierarchy aChild)
  {
    m_aChildren.put (aChild.getID (), aChild);
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsMap <String, CalendarHierarchy> getChildren ()
  {
    return m_aChildren.getClone ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CalendarHierarchy rhs = (CalendarHierarchy) o;
    return m_sID.equals (rhs.m_sID);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_sID).append ("country", m_eCountry).getToString ();
  }
}
