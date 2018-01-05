/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.hashcode.IHashCodeGenerator;
import com.helger.commons.lang.ClassLoaderHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.text.resourcebundle.ResourceBundleKey;

/**
 * Represents the holiday and contains the actual date and an localized
 * description.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
@Immutable
public class ResourceBundleHoliday implements ISingleHoliday
{
  private final boolean m_bIsOfficial;

  /** The properties key to retrieve the description with. */
  private final ResourceBundleKey m_aRBKey;

  // Status vars
  private transient int m_nHashCode = IHashCodeGenerator.ILLEGAL_HASHCODE;

  /**
   * Constructs a holiday for a date using the provided properties key to
   * retrieve the description with.
   *
   * @param aType
   *        type
   * @param sPropertiesKey
   *        key
   */
  public ResourceBundleHoliday (@Nonnull final IHolidayType aType, @Nullable final String sPropertiesKey)
  {
    ValueEnforcer.notNull (aType, "Type");
    m_bIsOfficial = aType.isOfficialHoliday ();
    m_aRBKey = StringHelper.hasNoText (sPropertiesKey) ? null
                                                       : new ResourceBundleKey ("descriptions.holiday_descriptions",
                                                                                "holiday.description." +
                                                                                                                     sPropertiesKey);
  }

  public boolean isOfficialHoliday ()
  {
    return m_bIsOfficial;
  }

  /**
   * @param aLocale
   *        The locale to use.
   * @return The description read with the provided locale.
   */
  public String getHolidayName (final Locale aLocale)
  {
    String ret = null;
    if (m_aRBKey != null)
    {
      try
      {
        final ResourceBundle aBundle = ResourceBundle.getBundle (m_aRBKey.getBundleName (),
                                                                 aLocale,
                                                                 ClassLoaderHelper.getDefaultClassLoader ());
        ret = aBundle.getString (m_aRBKey.getKey ());
      }
      catch (final MissingResourceException ex)
      {
        // fall through
      }
    }
    return ret == null ? "undefined" : ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ResourceBundleHoliday rhs = (ResourceBundleHoliday) o;
    return m_bIsOfficial == rhs.m_bIsOfficial && EqualsHelper.equals (m_aRBKey, rhs.m_aRBKey);
  }

  @Override
  public int hashCode ()
  {
    // We need a cached one!
    int ret = m_nHashCode;
    if (ret == IHashCodeGenerator.ILLEGAL_HASHCODE)
      ret = m_nHashCode = new HashCodeGenerator (this).append (m_bIsOfficial).append (m_aRBKey).getHashCode ();
    return ret;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("official", m_bIsOfficial).append ("propsKey", m_aRBKey).getToString ();
  }
}
