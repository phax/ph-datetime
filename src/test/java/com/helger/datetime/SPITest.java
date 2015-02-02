package com.helger.datetime;

import org.junit.Test;

import com.helger.commons.mock.PHTestUtils;

public class SPITest
{
  @Test
  public void testBasic () throws Exception
  {
    PHTestUtils.testIfAllSPIImplementationsAreValid ();
  }
}
