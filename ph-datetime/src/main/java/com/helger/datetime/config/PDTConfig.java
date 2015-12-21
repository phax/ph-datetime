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
package com.helger.datetime.config;

import java.util.TimeZone;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.GJChronology;
import org.joda.time.chrono.ISOChronology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.PresentForCodeCoverage;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.state.ESuccess;
import com.helger.datetime.PDTFactory;

/**
 * This class provides the most basic settings for date time operating: the
 * date-time-zone and the chronology to use.
 *
 * @author Philip Helger
 */
@ThreadSafe
public final class PDTConfig
{
  /**
   * The default-default date time zone.
   */
  public static final String DEFAULT_DATETIMEZONEID = "Europe/Vienna";
  public static final boolean DEFAULT_USE_ISO_CHRONOLOGY = true;

  private static final Logger s_aLogger = LoggerFactory.getLogger (PDTConfig.class);
  private static final SimpleReadWriteLock s_aRWLock = new SimpleReadWriteLock ();
  @GuardedBy ("s_aRWLock")
  private static DateTimeZone s_aDefaultDateTimeZone;
  @GuardedBy ("s_aRWLock")
  private static volatile boolean s_bUseISOChronology = DEFAULT_USE_ISO_CHRONOLOGY;

  static
  {
    // Ensure the JDK timezone is aligned to our default
    setDefaultDateTimeZoneID (DEFAULT_DATETIMEZONEID);
  }

  @PresentForCodeCoverage
  private static final PDTConfig s_aInstance = new PDTConfig ();

  private PDTConfig ()
  {}

  /**
   * Set the default date time zone to use. This effects all objects created via
   * {@link PDTFactory} as well as the default JDK TimeZone.
   *
   * @param sDateTimeZoneID
   *        Must be a valid, non-<code>null</code> time zone.
   * @return {@link ESuccess}
   */
  @Nonnull
  public static ESuccess setDefaultDateTimeZoneID (@Nonnull @Nonempty final String sDateTimeZoneID)
  {
    ValueEnforcer.notEmpty (sDateTimeZoneID, "DateTimeZoneID");

    return s_aRWLock.writeLocked ( () -> {
      try
      {
        // Try to resolve ID -> throws IAE if unknown
        s_aDefaultDateTimeZone = DateTimeZone.forID (sDateTimeZoneID);
        // getTimeZone falls back to GMT if unknown
        final TimeZone aDefaultTimeZone = TimeZone.getTimeZone (sDateTimeZoneID);
        TimeZone.setDefault (aDefaultTimeZone);
        return ESuccess.SUCCESS;
      }
      catch (final IllegalArgumentException ex)
      {
        // time zone ID is unknown
        s_aLogger.warn ("Unsupported dateTimeZone ID '" + sDateTimeZoneID + "'");
        return ESuccess.FAILURE;
      }
    });
  }

  /**
   * @return The default date time zone to use. Never <code>null</code>. The
   *         default is {@link #DEFAULT_DATETIMEZONEID}.
   */
  @Nonnull
  public static DateTimeZone getDefaultDateTimeZone ()
  {
    return s_aRWLock.readLocked ( () -> s_aDefaultDateTimeZone);
  }

  /**
   * @return The default time zone to use. Never <code>null</code>. The default
   *         is {@link #DEFAULT_DATETIMEZONEID}.
   */
  @Nonnull
  public static TimeZone getDefaultTimeZone ()
  {
    return getDefaultDateTimeZone ().toTimeZone ();
  }

  /**
   * @return The default UTC date time zone. Never <code>null</code>.
   */
  @Nonnull
  public static DateTimeZone getDateTimeZoneUTC ()
  {
    return DateTimeZone.UTC;
  }

  /**
   * @return <code>true</code> if {@link ISOChronology} should be used by
   *         default. Defaults to {@value #DEFAULT_USE_ISO_CHRONOLOGY}.
   */
  public static boolean isUseISOChronology ()
  {
    return s_aRWLock.readLocked ( () -> s_bUseISOChronology);
  }

  /**
   * Change whether ISO chronology should be used by default or
   * {@link GJChronology}.
   *
   * @param bUse
   *        <code>true</code> to use {@link ISOChronology}, <code>false</code>
   *        to use {@link GJChronology}.
   */
  public static void setUseISOChronology (final boolean bUse)
  {
    s_aRWLock.writeLocked ( () -> {
      s_bUseISOChronology = bUse;
    });
  }

  /**
   * @return The default chronology ({@link ISOChronology} or
   *         {@link GJChronology}) using the result of
   *         {@link #getDefaultDateTimeZone()}
   * @see #isUseISOChronology()
   * @see #getDefaultDateTimeZone()
   */
  @Nonnull
  public static Chronology getDefaultChronology ()
  {
    if (isUseISOChronology ())
      return ISOChronology.getInstance (getDefaultDateTimeZone ());
    return GJChronology.getInstance (getDefaultDateTimeZone ());
  }

  /**
   * @return The default chronology ({@link ISOChronology} or
   *         {@link GJChronology}) with UTC date time zone. Never
   *         <code>null</code>.
   * @see #isUseISOChronology()
   */
  @Nonnull
  public static Chronology getDefaultChronologyUTC ()
  {
    if (isUseISOChronology ())
      return ISOChronology.getInstanceUTC ();
    return GJChronology.getInstanceUTC ();
  }

  /**
   * @return The default chronology ({@link ISOChronology} or
   *         {@link GJChronology}) with the system date time zone. Never
   *         <code>null</code>.
   * @see #isUseISOChronology()
   */
  @Nonnull
  public static Chronology getDefaultChronologyWithDefaultDateTimeZone ()
  {
    if (isUseISOChronology ())
      return ISOChronology.getInstance ();
    return GJChronology.getInstance ();
  }
}
