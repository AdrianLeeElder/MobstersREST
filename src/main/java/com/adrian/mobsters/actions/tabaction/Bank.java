package com.adrian.mobsters.actions.tabaction;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.actions.extract.BankCash;
import org.springframework.stereotype.Service;

@Service
public class Bank extends AbstractAction {

    @Override
    public void response() {
        BankCash bankCash = new BankCash();
        bankCash.extract(getChromeDriver(), getMobsterUsername());
    }
}