package com.adrian.mobsters.actions.tabaction;

import com.adrian.mobsters.actions.JsTabAction;
import com.adrian.mobsters.actions.extract.BankCash;
import org.springframework.stereotype.Service;

@Service
public class Bank extends JsTabAction {

    @Override
    public void response() {
        BankCash bankCash = new BankCash();
        bankCash.extract(getPage(), getMobsterUsername());
    }
}
