package de.atat072.rpg.Story;

import com.badlogic.gdx.Gdx;
import de.atat072.rpg.Fight.FightDecision;
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
import java.util.HashMap;

public abstract class StoryHandler {
    public static ArrayList<Story> createStories() {
        ArrayList<Story> stories = new ArrayList<>();

        HashMap<String, ArrayList<StoryDecisionValues>> storyMap = new HashMap<>();

        storyMap = readXmlFile();

        for (String storyName : storyMap.keySet()) {
            HashMap<String, Decision> storyDecisionMap = new HashMap<>();

            for (StoryDecisionValues v : storyMap.get(storyName)) {

                //region Create Null Variables for Decision.PassedValue class
                FightDecision fight1 = null;
                FightDecision fight2 = null;
                FightDecision fight3 = null;
                FightDecision fight4 = null;

                String storyName1 = null;
                String storyName2 = null;
                String storyName3 = null;
                String storyName4 = null;

                Decision decision1 = null;
                Decision decision2 = null;
                Decision decision3 = null;
                Decision decision4 = null;
                //endregion

                //region Set valid decision options
                //Set first decision options
                if (v.decision1Decision1.contains("Fight") && !v.decision1Decision1.contains("Story")) {
                    fight1 = new FightDecision(storyName, v.decision1Decision1);
                } else if (!v.decision1Decision1.contains("Fight") && v.decision1Decision1.contains("Story")) {
                    storyName1 = v.decision1Decision1;
                } else if (!v.decision1Decision1.contains("Fight") && !v.decision1Decision1.contains("Story")) {
                    decision1 = Decision.checkDecision(storyDecisionMap.get(v.decision1Decision1), storyDecisionMap.get(v.decision1Decision2), v.checkTyp1);
                }

                //Set second decision options
                if (v.decision2Decision1.contains("Fight") && !v.decision2Decision1.contains("Story")) {
                    fight2 = new FightDecision(storyName, v.decision2Decision1);
                } else if (!v.decision2Decision1.contains("Fight") && v.decision2Decision1.contains("Story")) {
                    storyName2 = v.decision2Decision1;
                } else if (!v.decision2Decision1.contains("Fight") && !v.decision2Decision1.contains("Story")) {
                    decision2 = Decision.checkDecision(storyDecisionMap.get(v.decision2Decision1), storyDecisionMap.get(v.decision2Decision2), v.checkTyp2);
                }

                //Set third decision options
                if (v.decision3Decision1.contains("Fight") && !v.decision3Decision1.contains("Story")) {
                    fight3 = new FightDecision(storyName, v.decision3Decision1);
                } else if (!v.decision3Decision1.contains("Fight") && v.decision3Decision1.contains("Story")) {
                    storyName3 = v.decision3Decision1;
                } else if (!v.decision3Decision1.contains("Fight") && !v.decision3Decision1.contains("Story")) {
                    decision3 = Decision.checkDecision(storyDecisionMap.get(v.decision3Decision1), storyDecisionMap.get(v.decision3Decision2), v.checkTyp3);
                }

                //Set fourth decision options
                if (v.decision4Decision1.contains("Fight") && !v.decision4Decision1.contains("Story")) {
                    fight4 = new FightDecision(storyName, v.decision4Decision1);
                } else if (!v.decision4Decision1.contains("Fight") && v.decision4Decision1.contains("Story")) {
                    storyName4 = v.decision4Decision1;
                } else if (!v.decision4Decision1.contains("Fight") && !v.decision4Decision1.contains("Story")) {
                    decision4 = Decision.checkDecision(storyDecisionMap.get(v.decision4Decision1), storyDecisionMap.get(v.decision4Decision2), v.checkTyp4);
                }
                //endregion

                Decision storyDecision =
                        new Decision(
                                v.decisionName,
                                storyName,
                                v.decisionText,
                                v.decision1, new Decision.PassedValue(fight1, storyName1, decision1),
                                v.decision2, new Decision.PassedValue(fight2, storyName2, decision2),
                                v.decision3, new Decision.PassedValue(fight3, storyName3, decision3),
                                v.decision4, new Decision.PassedValue(fight4, storyName4, decision4)
                        );
                storyDecisionMap.put(v.decisionName, storyDecision);
            }
            Story story = new Story(storyName, storyDecisionMap);
            stories.add(story);
        }
        return stories;
    }

    public void writeXmlFile(HashMap<String, ArrayList<StoryDecisionValues>> story) {
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

                for (StoryDecisionValues sv : story.get(s)) {
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

    public static HashMap<String, ArrayList<StoryDecisionValues>> readXmlFile() {
        File file = new File(String.valueOf(Gdx.files.internal("Data/Stories.xml")));

        String storyName = "";
        HashMap<String, ArrayList<StoryDecisionValues>> storyMap = new HashMap<>();

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
            NodeList storyRoots = root.getChildNodes();

            for (int e = 0; e < storyRoots.getLength(); e++) {
                storyName = storyRoots.item(e).getNodeName();

                ArrayList<StoryDecisionValues> storyDecisionValues = new ArrayList<>();

                NodeList decisionList = storyRoots.item(e).getChildNodes();
                for (int i = 0; i < decisionList.getLength(); i++) {
                    Node decisionNode = decisionList.item(i);
                    if (decisionNode.getNodeType() == Node.ELEMENT_NODE) {
                        NodeList decisionValues = decisionNode.getChildNodes();
                        Element decisionValuesElement = (Element) decisionValues;

                        storyDecisionValues.add(new StoryDecisionValues(decisionNode.getNodeName(),
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
                storyMap.put(storyName, storyDecisionValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return storyMap;
    }

    public void writeSerializedStory(String name, Object c) {
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

    public ArrayList<StoryDecisionValues> readSerializedStory(String name) {
        File storyFile = new File(String.valueOf(Gdx.files.internal("Data/" + name)));

        XMLDecoder decoder=null;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(storyFile)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File dvd.xml not found");
        }
        return (ArrayList<StoryDecisionValues>) decoder.readObject();
    }
}


