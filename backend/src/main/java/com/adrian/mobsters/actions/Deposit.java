package com.adrian.mobsters.actions;

import com.adrian.mobsters.actions.extract.BankCash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Deposit extends AbstractAction {

    @Override
    public void response() {
        BankCash bankCashExtractAction = new BankCash();

        bankCashExtractAction.extract(getChromeDriver(), getMobsterUsername());
    }
}
