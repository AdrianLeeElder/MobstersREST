package com.adrian.mobstersrest.mobsters.actions.tabaction;

import com.adrian.mobstersrest.mobsters.actions.JsTabAction;
import com.adrian.mobstersrest.mobsters.actions.extract.BankCash;

public class Bank extends JsTabAction {

  @Override
  public void response() {
    BankCash bankCash = new BankCash();
    bankCash.extract(getPage(), getMobsterUsername());
  }
}
