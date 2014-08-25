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
package com.helger.datetime.comparators;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.helger.commons.compare.AbstractComparator;
import com.helger.commons.compare.CompareUtils;
import com.helger.commons.compare.ESortOrder;

/**
 * Comparator for {@link DateTime} objects.
 * 
 * @author Philip Helger
 */
public class ComparatorDateTime extends AbstractComparator <DateTime>
{
  private final boolean m_bNullValueComeFirst;

  public ComparatorDateTime ()
  {
    this (CompareUtils.DEFAULT_NULL_VALUES_COME_FIRST);
  }

  public ComparatorDateTime (@Nonnull final ESortOrder eSortOrder)
  {
    this (eSortOrder, CompareUtils.DEFAULT_NULL_VALUES_COME_FIRST);
  }

  public ComparatorDateTime (final boolean bNullValueComeFirst)
  {
    m_bNullValueComeFirst = bNullValueComeFirst;
  }

  public ComparatorDateTime (@Nonnull final ESortOrder eSortOrder, final boolean bNullValueComeFirst)
  {
    super (eSortOrder);
    m_bNullValueComeFirst = bNullValueComeFirst;
  }

  @Override
  protected int mainCompare (@Nullable final DateTime aDateTime1, @Nullable final DateTime aDateTime2)
  {
    return CompareUtils.nullSafeCompare (aDateTime1, aDateTime2, m_bNullValueComeFirst);
  }
}
