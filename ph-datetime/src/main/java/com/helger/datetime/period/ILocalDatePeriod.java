package com.helger.datetime.period;

import java.time.LocalDate;

import javax.annotation.Nullable;

public interface ILocalDatePeriod
{
  @Nullable
  LocalDate getStart ();

  @Nullable
  LocalDate getEnd ();
}
