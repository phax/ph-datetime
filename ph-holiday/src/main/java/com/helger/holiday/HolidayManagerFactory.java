/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
import javax.annotation.concurrent.GuardedBy;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsHashMap;
import com.helger.commons.collection.ext.CommonsHashSet;
import com.helger.commons.collection.ext.ICommonsMap;
import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.commons.concurrent.SimpleReadWriteLock;
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
  private static final SimpleReadWriteLock s_aRWLock = new SimpleReadWriteLock ();
  @GuardedBy ("s_aRWLock")
  private static final ICommonsMap <String, Class <? extends IHolidayManager>> s_aClassMap = new CommonsHashMap<> ();
  @GuardedBy ("s_aRWLock")
  private static final ICommonsMap <String, IHolidayManager> s_aInstMap = new CommonsHashMap<> ();

  /** All supported default countries */
  private static final ICommonsSet <String> s_aSupportedCountries = new CommonsHashSet<> ();

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

    s_aRWLock.writeLocked ( () -> {
      if (s_aClassMap.containsKey (sCountryID))
        throw new IllegalArgumentException ("A class for country " + sCountryID + " is already registered!");
      s_aClassMap.put (sCountryID, aClass);
    });
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
    final IHolidayManager aMgr = s_aRWLock.readLocked ( () -> s_aInstMap.get (sCountryID));
    if (aMgr != null)
      return aMgr;

    return s_aRWLock.writeLocked ( () ->
    // Check in writeLock again to be 100% sure
    s_aInstMap.computeIfAbsent (sCountryID, k -> {
      // Is a special holiday manager registered?
      final Class <? extends IHolidayManager> aClass = s_aClassMap.get (sCountryID);
      final IHolidayManager aMgr2 = aClass != null ? GenericReflection.newInstance (aClass)
                                                   : new XMLHolidayManager (sCountryID);
      if (aMgr2 == null)
        throw new IllegalArgumentException ("Failed to create holiday manager for country '" + sCountryID + "'");
      return aMgr2;
    }));
  }

  /**
   * Returns a set of all currently supported country codes.
   *
   * @return Set of supported country codes.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsSet <String> getSupportedCountryCodes ()
  {
    return s_aSupportedCountries.getClone ();
  }
}
