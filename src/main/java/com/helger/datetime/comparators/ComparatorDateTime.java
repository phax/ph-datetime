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
package com.helger.datetime.comparators;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.helger.commons.compare.AbstractComparator;
import com.helger.commons.compare.CompareHelper;

/**
 * Comparator for {@link DateTime} objects.
 *
 * @author Philip Helger
 */
public class ComparatorDateTime extends AbstractComparator <DateTime>
{
  public ComparatorDateTime ()
  {}

  @Override
  protected int mainCompare (@Nullable final DateTime aDateTime1, @Nullable final DateTime aDateTime2)
  {
    return CompareHelper.compare (aDateTime1, aDateTime2, isNullValuesComeFirst ());
  }
}
