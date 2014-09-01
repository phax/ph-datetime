/**
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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.compare.ESortOrder;
import com.helger.datetime.PDTFactory;

/**
 * Test class for class {@link ComparatorLocalDateTime}.
 * 
 * @author Philip Helger
 */
public final class ComparatorLocalDateTimeTest
{
  @Test
  public void testAll ()
  {
    final LocalDateTime aObj1 = PDTFactory.createLocalDateTime (2000, 5, 5);
    final LocalDateTime aObj2 = PDTFactory.createLocalDateTime (1000, 5, 5);
    final List <LocalDateTime> aList = ContainerHelper.newList (aObj1, aObj2);
    List <LocalDateTime> aSorted = ContainerHelper.getSorted (aList, new ComparatorLocalDateTime ());
    assertEquals (aObj2, aSorted.get (0));
    assertEquals (aObj1, aSorted.get (1));
    aSorted = ContainerHelper.getSorted (aList, new ComparatorLocalDateTime (ESortOrder.DESCENDING));
    assertEquals (aObj1, aSorted.get (0));
    assertEquals (aObj2, aSorted.get (1));
  }
}
