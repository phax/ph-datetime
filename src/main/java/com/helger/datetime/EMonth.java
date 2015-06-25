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
package com.helger.datetime;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.DateTimeConstants;

import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.id.IHasIntID;
import com.helger.commons.lang.EnumHelper;

/**
 * Represents all known Gregorian Calendar month as a type-safe enum
 *
 * @author Philip Helger
 */
public enum EMonth implements IHasIntID
{
  JANUARY (DateTimeConstants.JANUARY, Calendar.JANUARY),
  FEBRUARY (DateTimeConstants.FEBRUARY, Calendar.FEBRUARY),
  MARCH (DateTimeConstants.MARCH, Calendar.MARCH),
  APRIL (DateTimeConstants.APRIL, Calendar.APRIL),
  MAY (DateTimeConstants.MAY, Calendar.MAY),
  JUNE (DateTimeConstants.JUNE, Calendar.JUNE),
  JULY (DateTimeConstants.JULY, Calendar.JULY),
  AUGUST (DateTimeConstants.AUGUST, Calendar.AUGUST),
  SEPTEMBER (DateTimeConstants.SEPTEMBER, Calendar.SEPTEMBER),
  OCTOBER (DateTimeConstants.OCTOBER, Calendar.OCTOBER),
  NOVEMBER (DateTimeConstants.NOVEMBER, Calendar.NOVEMBER),
  DECEMBER (DateTimeConstants.DECEMBER, Calendar.DECEMBER);

  private final int m_nJodaID;
  private final int m_nCalID;

  private EMonth (final int nJodaID, final int nCalID)
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
  public String getMonthName (@Nonnull final Locale aLocale)
  {
    return ArrayHelper.getSafeElement (DateFormatSymbols.getInstance (aLocale).getMonths (), m_nCalID);
  }

  @Nullable
  public String getMonthShortName (@Nonnull final Locale aLocale)
  {
    return ArrayHelper.getSafeElement (DateFormatSymbols.getInstance (aLocale).getShortMonths (), m_nCalID);
  }

  @Nullable
  public static EMonth getFromIDOrNull (final int nID)
  {
    return EnumHelper.getFromIDOrNull (EMonth.class, nID);
  }
}
