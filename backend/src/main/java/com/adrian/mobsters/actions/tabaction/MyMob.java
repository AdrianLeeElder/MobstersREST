package com.adrian.mobsters.actions.tabaction;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.service.ActionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class MyMob extends AbstractAction {

    private ActionService actionService;


//    @Override
//    public void response() {
//        HtmlDivision div = (HtmlDivision) getPage().getFirstByXPath("//div[@id='tmob']/div/div");
//        Pattern pattern = Pattern
//                .compile("(Hired Guns: )([0-9]+)( \\+ Active Top Mob Slots: )([0-9]+)");
//        Matcher m = pattern.matcher(div.asText());
//
//        if (m.find()) {
//            int myMobCount = Integer.parseInt(m.group(4));
//
//            ((AbstractJsAction) actionService.getAction("Send All"))
//                    .setScript("sendalle(" + myMobCount + ");");
//            ((AbstractJsAction) actionService.getAction("Collect All"))
//                    .setScript("collectallc(" + myMobCount + ");");
//        }
//    }
}
