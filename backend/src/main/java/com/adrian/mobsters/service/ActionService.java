package com.adrian.mobsters.service;

import com.adrian.mobsters.actions.AbstractAction;
import com.adrian.mobsters.actions.ActionBuilder;
import com.adrian.mobsters.exception.ActionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author aelder
 */
@Slf4j
@Service
public class ActionService {

    private final ActionFactoryService actionFactoryService;

    private final XPathFactory xPathFactory = XPathFactory.newInstance();
    private Document document;

    public ActionService(ActionFactoryService actionFactoryService) {
        this.actionFactoryService = actionFactoryService;
        loadActionXml();
    }

    private void loadActionXml() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder
                    .parse(ActionService.class.getResource("/AbstractActions.xml").getFile());
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Error importing action configuration", e);
        }
    }

    /**
     * Create a new AbstractAction based on the configuration file AbstractActions.xml located at
     * /resources/.
     */
    public AbstractAction getAction(String actionName) {
        log.debug("Attempting to retrieve new action: " + actionName);
        AbstractAction abstractAction = actionFactoryService.getAbstractAction(actionName);
        XPathExpression xPathExpression = null;

        try {
            xPathExpression = xPathFactory.newXPath()
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
        String name = "", xPath = "";
        boolean fancyBox = false;
        ArrayList<String> finishText = new ArrayList<>();

        Node node = nodeList.item(0);
        do {
            switch (node.getNodeName()) {
                case "name":
                    name = node.getTextContent();
                    break;
                case "finishText":
                    finishText.add(node.getTextContent());
                    break;
                case "xPath":
                    xPath = node.getTextContent();
                    break;
                case "fancyBox":
                    fancyBox = Boolean.valueOf(node.getTextContent());
                    break;
            }
        } while ((node = node.getNextSibling()) != null);

        return actionBuilder
                .name(name)
                .finishText(finishText)
                .xPath(xPath)
                .fancyBox(fancyBox)
                .build(abstractAction);
    }
}
