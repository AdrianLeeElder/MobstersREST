package com.adrian.mobstersrest.mobsters.services;

import com.adrian.mobstersrest.mobsters.actions.AbstractAction;
import com.adrian.mobstersrest.mobsters.actions.ActionBuilder;
import com.adrian.mobstersrest.mobsters.exceptions.ActionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author aelder
 */
@Slf4j
@Service
public class ActionService {

    @Autowired
    private ActionFactoryService actionFactoryService;

    private final XPathFactory xPathFactory = XPathFactory.newInstance();
    private Document document;
    private XPath xPath;

    private void handleResource() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder
                    .parse(ActionService.class.getResource("/AbstractActions.xml").getFile());
            document.getDocumentElement().normalize();
            xPath = xPathFactory.newXPath();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Error importing action configuration", e);
        }
    }

    /**
     * Create a new AbstractAction based on the configuration file AbstractActions.xml located at
     * /resources/.
     */
    public AbstractAction getAction(String actionName) {
        AbstractAction abstractAction = actionFactoryService.getAbstractAction(actionName);
        XPathExpression xPathExpression = null;

        try {
            xPathExpression = xPath
                    .compile("/AbstractAction/action[./name/text()='" + actionName + "']/child::node()");
        } catch (XPathExpressionException e) {
            throw new ActionNotFoundException("Unable to find action " + actionName);
        }

        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Error evaluating XPath.", e);
        }

        ActionBuilder actionBuilder = new ActionBuilder();
        String name = "", elementID = "", script = "";
        ArrayList<String> finishText = new ArrayList<>();
        int tabID = -1;

        Node node = nodeList.item(0);
        do {
            switch (node.getNodeName()) {
                case "name":
                    name = node.getTextContent();
                    break;
                case "script":
                    script = node.getTextContent();
                    break;
                case "tab":
                    tabID = Integer.parseInt(node.getTextContent());
                    break;
                case "element":
                    elementID = node.getTextContent();
                    break;
                case "finishText":
                    finishText.add(node.getTextContent());
                    break;
            }
        } while ((node = node.getNextSibling()) != null);

        return actionBuilder.elementID(elementID).finishText(finishText).tab(tabID).name(name)
                .script(script).build(abstractAction);
    }
}
