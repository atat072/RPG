package de.atat072.rpg.Story;

import com.badlogic.gdx.Gdx;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StoryHandler {
    public HashMap<Integer, Story> stories = new HashMap<>();
    private HashMap<String, Decision> story1DecisionsMap = new HashMap<>();
    public StoryHandler() {

        Story story1 = new Story(null, createStory("Story0.1"));

        stories.put(1, story1);
    }

    public void writeStory(String name, Object c) {
        File storyFile = new File(String.valueOf(Gdx.files.internal("Data/" + name)));

        XMLEncoder encoder=null;
        try{
            encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(storyFile)));
        }catch(FileNotFoundException fileNotFound){
            System.out.println("ERROR: While Creating or Opening the File dvd.xml");
        }
        encoder.writeObject(c);
        encoder.close();
    }

    public ArrayList<StoryValues> readStory(String name) {
        File storyFile = new File(String.valueOf(Gdx.files.internal("Data/" + name)));

        XMLDecoder decoder=null;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(storyFile)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File dvd.xml not found");
        }
        return (ArrayList<StoryValues>) decoder.readObject();
    }

    public HashMap<String, Decision> createStory(String name) {
        HashMap<String, Decision> storyMap = new HashMap<>();
        ArrayList<StoryValues> storyValues = new ArrayList<>();

        storyValues = readXmlFile(name);

        for (StoryValues v : storyValues) {
            Decision storyDecision =
                    new Decision(
                            v.decisionName,
                            v.decisionText,
                            v.decision1, Decision.checkDecision(storyMap.get(v.decision1Decision1), storyMap.get(v.decision1Decision2), v.checkTyp1),
                            v.decision2, Decision.checkDecision(storyMap.get(v.decision2Decision1), storyMap.get(v.decision2Decision2), v.checkTyp2),
                            v.decision3, Decision.checkDecision(storyMap.get(v.decision3Decision1), storyMap.get(v.decision3Decision2), v.checkTyp3),
                            v.decision4, Decision.checkDecision(storyMap.get(v.decision4Decision1), storyMap.get(v.decision4Decision2), v.checkTyp4)
                    );
            storyMap.put(v.decisionName, storyDecision);
        }

        return storyMap;
    }

    public void writeXmlFile(HashMap<String, ArrayList<StoryValues>> story) {
        File file = new File(String.valueOf(Gdx.files.internal("Data/Stories.xml")));

        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement("Stories");
            doc.appendChild(root);

            for (String s : story.keySet()) {
                Element storyRoot = doc.createElement(s);
                root.appendChild(storyRoot);

                for (StoryValues sv : story.get(s)) {
                    Element decisionRoot = doc.createElement(sv.getDecisionName());
                    storyRoot.appendChild(decisionRoot);

                    Element text = doc.createElement("DecisionText");
                    text.appendChild(doc.createTextNode(String.valueOf(sv.getDecisionText())));
                    decisionRoot.appendChild(text);

                    Element decision1 = doc.createElement("Decision1");
                    decision1.appendChild(doc.createTextNode(String.valueOf(sv.getDecision1())));
                    decisionRoot.appendChild(decision1);

                    Element decision2 = doc.createElement("Decision2");
                    decision2.appendChild(doc.createTextNode(String.valueOf(sv.getDecision2())));
                    decisionRoot.appendChild(decision2);

                    Element decision3 = doc.createElement("Decision3");
                    decision3.appendChild(doc.createTextNode(String.valueOf(sv.getDecision3())));
                    decisionRoot.appendChild(decision3);

                    Element decision4 = doc.createElement("Decision4");
                    decision4.appendChild(doc.createTextNode(String.valueOf(sv.getDecision4())));
                    decisionRoot.appendChild(decision4);

                    Element decision1Decision1 = doc.createElement("Decision1Decision1");
                    decision1Decision1.appendChild(doc.createTextNode(String.valueOf(sv.getDecision1Decision1())));
                    decisionRoot.appendChild(decision1Decision1);

                    Element decision2Decision1 = doc.createElement("Decision2Decision1");
                    decision2Decision1.appendChild(doc.createTextNode(String.valueOf(sv.getDecision2Decision1())));
                    decisionRoot.appendChild(decision2Decision1);

                    Element decision3Decision1 = doc.createElement("Decision3Decision1");
                    decision3Decision1.appendChild(doc.createTextNode(String.valueOf(sv.getDecision3Decision1())));
                    decisionRoot.appendChild(decision3Decision1);

                    Element decision4Decision1 = doc.createElement("Decision4Decision1");
                    decision4Decision1.appendChild(doc.createTextNode(String.valueOf(sv.getDecision4Decision1())));
                    decisionRoot.appendChild(decision4Decision1);

                    Element decision1Decision2 = doc.createElement("Decision1Decision2");
                    decision1Decision2.appendChild(doc.createTextNode(String.valueOf(sv.getDecision1Decision1())));
                    decisionRoot.appendChild(decision1Decision2);

                    Element decision2Decision2 = doc.createElement("Decision2Decision2");
                    decision2Decision2.appendChild(doc.createTextNode(String.valueOf(sv.getDecision2Decision2())));
                    decisionRoot.appendChild(decision2Decision2);

                    Element decision3Decision2 = doc.createElement("Decision3Decision2");
                    decision3Decision2.appendChild(doc.createTextNode(String.valueOf(sv.getDecision3Decision2())));
                    decisionRoot.appendChild(decision3Decision2);

                    Element decision4Decision2 = doc.createElement("Decision4Decision2");
                    decision4Decision2.appendChild(doc.createTextNode(String.valueOf(sv.getDecision4Decision2())));
                    decisionRoot.appendChild(decision4Decision2);
                }
            }

            // Save the document to the disk file
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            aTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            try {
                // location and name of XML file you can change as per need
                FileWriter fos = new FileWriter(file);
                StreamResult result = new StreamResult(fos);
                aTransformer.transform(source, result);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (TransformerException ex) {
            System.out.println("Error outputting document");

        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }
    }

    public ArrayList<StoryValues> readXmlFile(String storyName) {
        File file = new File(String.valueOf(Gdx.files.internal("Data/Stories.xml")));

        ArrayList<StoryValues> storyValues = new ArrayList<>();

        try {
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(file);

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            Element root = document.getDocumentElement();

            //Get all employees
            Node storyRoot = document.getElementsByTagName(storyName).item(0);

            NodeList decisionList = storyRoot.getChildNodes();

            for (int i = 0; i < decisionList.getLength(); i++) {
                Node decisionNode = decisionList.item(i);
                if (decisionNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList decisionValues = decisionNode.getChildNodes();
                    Element decisionValuesElement = (Element) decisionValues;

                    storyValues.add(new StoryValues(decisionNode.getNodeName(),
                            decisionValuesElement.getElementsByTagName("DecisionText").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision1").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision2").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision3").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision4").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision1Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision2Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision3Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("Decision4Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("DecisionCheckTyp1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("DecisionCheckTyp2").item(0).getTextContent(),
                            decisionValuesElement.getElementsByTagName("DecisionCheckTyp3").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("DecisionCheckTyp4").item(0).getTextContent()
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return storyValues;
    }
}


