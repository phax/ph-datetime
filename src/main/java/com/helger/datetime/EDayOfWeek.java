/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.datetime;

import java.util.Calendar;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.DateTimeConstants;

import com.helger.commons.collections.ArrayHelper;
import com.helger.commons.id.IHasSimpleIntID;
import com.helger.commons.lang.DateFormatSymbolsFactory;
import com.helger.commons.lang.EnumHelper;

/**
 * Represents all known Gregorian Calendar days of a week as a type-safe enum
 *
 * @author Philip Helger
 */
public enum EDayOfWeek implements IHasSimpleIntID
{
  MONDAY (DateTimeConstants.MONDAY, Calendar.MONDAY),
  TUESDAY (DateTimeConstants.TUESDAY, Calendar.TUESDAY),
  WEDNESDAY (DateTimeConstants.WEDNESDAY, Calendar.WEDNESDAY),
  THURSDAY (DateTimeConstants.THURSDAY, Calendar.THURSDAY),
  FRIDAY (DateTimeConstants.FRIDAY, Calendar.FRIDAY),
  SATURDAY (DateTimeConstants.SATURDAY, Calendar.SATURDAY),
  SUNDAY (DateTimeConstants.SUNDAY, Calendar.SUNDAY);

  private final int m_nJodaID;
  private final int m_nCalID;

  private EDayOfWeek (final int nJodaID, final int nCalID)
  {
    m_nJodaID = nJodaID;
    m_nCalID = nCalID;
  }

  public int getID ()
  {
    return m_nJodaID;
  }

  /**
   * @return The joda time ID
   */
  public int getDateTimeConstant ()
  {
    return m_nJodaID;
  }

  /**
   * @return The java.util.Calendar ID
   */
  public int getCalendarConstant ()
  {
    return m_nCalID;
  }

  @Nullable
  public String getWeekdayName (@Nonnull final Locale aLocale)
  {
    return ArrayHelper.getSafeElement (DateFormatSymbolsFactory.getInstance (aLocale).getWeekdays (), m_nCalID);
  }

  @Nullable
  public String getWeekdayShortName (@Nonnull final Locale aLocale)
  {
    return ArrayHelper.getSafeElement (DateFormatSymbolsFactory.getInstance (aLocale).getShortWeekdays (), m_nCalID);
  }

  @Nullable
  public static EDayOfWeek getFromIDOrNull (final int nID)
  {
    return EnumHelper.getFromIDOrNull (EDayOfWeek.class, nID);
  }
}
