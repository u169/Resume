package ru.ilya.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ParserXML {

    private String pathXML;
    private LinkedHashMap<String, Integer> mapOut;

    public String getPathXML() {
        return pathXML;
    }

    public LinkedHashMap<String, Integer> getMapOut() {
        return mapOut;
    }

    public ParserXML(String path){
        this.pathXML = path;
        this.mapOut = xmlMap(this.pathXML);
    }

    private static LinkedHashMap<String, Integer> xmlMap(String path) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.parse(path);
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (!isText(node)) {
                    if (node.getNodeName().equals("page")) {
                        NodeList pageInfo = node.getChildNodes();
                        for (int j = 0; j < pageInfo.getLength(); j++) {
                            Node value = pageInfo.item(j);
                            if (!isText(value)) {
                                int len = Integer.parseInt(value.getTextContent());
                                result.put(value.getNodeName(), len);
                            }
                        }
                    } else if (node.getNodeName().equals("columns")) {
                        NodeList columns = node.getChildNodes();
                        for (int j = 0; j < columns.getLength(); j++) {
                            Node column = columns.item(j);
                            if (!isText(column)) {
                                NodeList params = column.getChildNodes();
                                ArrayList<String> par = new ArrayList<String>();
                                for (int n = 0; n < params.getLength(); n++) {
                                    Node param = params.item(n);
                                    if (!isText(param)) {
                                        par.add(param.getTextContent());
                                    }
                                }
                                int len = Integer.parseInt(par.get(1));
                                result.put(par.get(0), len);
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static boolean isText(Node mNode) {
        return mNode.getNodeType() == Node.TEXT_NODE;
    }
}
