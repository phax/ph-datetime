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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.helger.commons.collections.CollectionHelper;
import com.helger.commons.compare.ESortOrder;
import com.helger.datetime.PDTFactory;

/**
 * Test class for class {@link ComparatorLocalDate}.
 *
 * @author Philip Helger
 */
public final class ComparatorLocalDateTest
{
  @Test
  public void testAll ()
  {
    final LocalDate aObj1 = PDTFactory.createLocalDate (2000, 5, 5);
    final LocalDate aObj2 = PDTFactory.createLocalDate (1000, 5, 5);
    final List <LocalDate> aList = CollectionHelper.newList (aObj1, aObj2);
    List <LocalDate> aSorted = CollectionHelper.getSorted (aList, new ComparatorLocalDate ());
    assertEquals (aObj2, aSorted.get (0));
    assertEquals (aObj1, aSorted.get (1));
    aSorted = CollectionHelper.getSorted (aList, new ComparatorLocalDate (ESortOrder.DESCENDING));
    assertEquals (aObj1, aSorted.get (0));
    assertEquals (aObj2, aSorted.get (1));
  }
}
