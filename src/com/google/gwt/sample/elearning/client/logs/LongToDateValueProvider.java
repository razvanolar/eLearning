package com.google.gwt.sample.elearning.client.logs;

import com.sencha.gxt.core.client.ValueProvider;

import java.util.Date;

/**
 * Created by Cristi on 11/28/2015.
 */
public class LongToDateValueProvider<M> implements ValueProvider<M,Date> {
  private final ValueProvider<M, Long> base;
  public LongToDateValueProvider(ValueProvider<M, Long> base) {
    this.base = base;
  }
  @Override
  public String getPath() {
    return base.getPath();
  }
  @Override
  public Date getValue(M object) {
    return new Date(base.getValue(object));
  }
  @Override
  public void setValue(M object, Date value) {
    base.setValue(object, value.getTime());
  }
}
