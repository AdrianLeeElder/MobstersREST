package com.adrian.mobsters.actions;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class WithdrawPopup extends AbstractAction {

    @Override
    public void run() {
        List<?> buttons = getChromeDriver().findElementsByName("button");
        HtmlElement button = null;

        if (buttons != null) {
            for (Object b : buttons) {
                if (((HtmlButton) b).getAttribute("onclick").contains("withdraw2")) {
                    button = (HtmlButton) b;
                }
            }
        } else {
            throw new IllegalStateException("Withdraw Popup bank buttons are null");
        }

        if (button != null) {
            String bankAccountCash = button.getAttribute("onclick").replace(");$.fancybox.close();", "")
                    .replace("withdraw2(", "");
            BigInteger bankCash = new BigInteger(bankAccountCash);
//      BigInteger bankDiffMin = new BigInteger(
            //bankCash.subtract(getMobster().buyPropertyMinProperty().getValue()).toString());

//      if (!bankDiffMin.toString().contains("-")) {
//        BigInteger minWithdraw = new BigInteger("15000000000000");
//        BigInteger withdrawCash = null;
//
//        if (bankDiffMin.compareTo(minWithdraw) < 0) {
//          withdrawCash = bankDiffMin;
//        } else {
//          withdrawCash = minWithdraw;
//        }
//
//        if (!withdrawCash.toString().equals("0")) {
//          setScript("withdraw2(" + withdrawCash.toString().replaceAll(",|[$]", "") + ");");
//          log.debug("Withdrawing " + withdrawCash + " from the bank.");
//        } else {
//          //setFinishedBuyingProperty();
//        }
//      }
//    } else if (
//        getMobster().bankCashProperty().getValue().compareTo(getMobster().getBuyPropertyMin())
//            <= 0) {
//      setFinishedBuyingProperty();
//    } else {
//      throw new IllegalStateException("Error getting Withdraw Popup button");
//    }
        }

//  private void setFinishedBuyingProperty() {
//    getMobster().clearActions();
//    getMobster().addQueuedAction("Logout");
//    addMessage(
//        "Bank minimum reached. (" + getMobster().getBuyPropertyMin() + "). Account finished.");
//    getMobster().setBuyPropertyMobsterComplete(true);
    }
}
