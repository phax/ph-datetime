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
package com.helger.holiday;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.lang.ClassHelper;
import com.helger.commons.lang.GenericReflection;
import com.helger.commons.locale.country.ECountry;
import com.helger.commons.string.StringHelper;
import com.helger.holiday.mgr.XMLHolidayManager;
import com.helger.holiday.mgr.XMLHolidayManagerJapan;

/**
 * The main class for creating holiday managers.
 *
 * @author Philip Helger
 */
public final class HolidayManagerFactory
{
  private static final ReadWriteLock s_aRWLock = new ReentrantReadWriteLock ();
  private static final Map <String, Class <? extends IHolidayManager>> s_aClassMap = new HashMap <String, Class <? extends IHolidayManager>> ();
  private static final Map <String, IHolidayManager> s_aInstMap = new HashMap <String, IHolidayManager> ();

  /** All supported default countries */
  private static final Set <String> s_aSupportedCountries = new HashSet <String> ();

  static
  {
    // All supported countries
    s_aSupportedCountries.addAll (StringHelper.getExploded (',',
                                                            "al,ar,at,au,ba,be,bg,bo,br,by,cr,ca,ch,cl,co,cz,de,dk,ec,ee,es,et,fi,fr,gr,hr,hu,ie,is,it,jp,kz,li,lt,lu,lv,md,me,mk,mt,mx,ng,nl,ni,no,pa,pe,pl,pt,py,ro,ru,rs,se,si,sk,ua,uk,us,uy,ve,za"));

    // register predefined special managers
    registerHolidayManagerClass (XMLHolidayManagerJapan.COUNTRY_ID, XMLHolidayManagerJapan.class);
  }

  private HolidayManagerFactory ()
  {}

  public static void registerHolidayManagerClass (@Nonnull @Nonempty final String sCountryID,
                                                  @Nonnull final Class <? extends IHolidayManager> aClass)
  {
    ValueEnforcer.notEmpty (sCountryID, "CountryID");
    ValueEnforcer.notNull (aClass, "Class");
    ValueEnforcer.isTrue (ClassHelper.isInstancableClass (aClass),
                          "The passed class must be public, not abstract and needs a no-argument ctor!");

    s_aRWLock.writeLock ().lock ();
    try
    {
      if (s_aClassMap.containsKey (sCountryID))
        throw new IllegalArgumentException ("A class for country " + sCountryID + " is already registered!");
      s_aClassMap.put (sCountryID, aClass);
    }
    finally
    {
      s_aRWLock.writeLock ().unlock ();
    }
  }

  @Nonnull
  public static IHolidayManager getDefaultHolidayManager ()
  {
    return getHolidayManager (Locale.getDefault ().getCountry ());
  }

  @Nonnull
  public static IHolidayManager getHolidayManager (@Nonnull final ECountry eCountry)
  {
    ValueEnforcer.notNull (eCountry, "Country");

    return getHolidayManager (eCountry.getID ());
  }

  @Nonnull
  public static IHolidayManager getHolidayManager (@Nonnull @Nonempty final String sCountryID)
  {
    ValueEnforcer.notEmpty (sCountryID, "CountryID");

    // is the instance already cached?
    IHolidayManager aMgr;
    s_aRWLock.readLock ().lock ();
    try
    {
      aMgr = s_aInstMap.get (sCountryID);
    }
    finally
    {
      s_aRWLock.readLock ().unlock ();
    }

    if (aMgr == null)
    {
      s_aRWLock.writeLock ().lock ();
      try
      {
        // Check in writeLock again to be 100% sure
        aMgr = s_aInstMap.get (sCountryID);
        if (aMgr == null)
        {
          // Is a special holiday manager registered?
          final Class <? extends IHolidayManager> aClass = s_aClassMap.get (sCountryID);
          aMgr = aClass != null ? GenericReflection.newInstance (aClass) : new XMLHolidayManager (sCountryID);
          if (aMgr == null)
            throw new IllegalArgumentException ("Failed to create holiday manager for country '" + sCountryID + "'");
          s_aInstMap.put (sCountryID, aMgr);
        }
      }
      finally
      {
        s_aRWLock.writeLock ().unlock ();
      }
    }
    return aMgr;
  }

  /**
   * Returns a set of all currently supported country codes.
   *
   * @return Set of supported country codes.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static Set <String> getSupportedCountryCodes ()
  {
    return CollectionHelper.newSet (s_aSupportedCountries);
  }
}
