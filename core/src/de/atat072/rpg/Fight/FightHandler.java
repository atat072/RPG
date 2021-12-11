package de.atat072.rpg.Fight;

import com.badlogic.gdx.Gdx;
import de.atat072.rpg.gameObjects.Char;
import de.atat072.rpg.gameObjects.HealingPotion;
import de.atat072.rpg.gameObjects.NPC;
import de.atat072.rpg.gameObjects.Weapon;
import de.atat072.rpg.screens.GameScreen;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static de.atat072.rpg.RPG.SAVE;

public class FightHandler {
    int lootCoin =0;
    String introText;
    String fightName;
    CharQueue chars = new CharQueue();
    ArrayList<Char> charList = new ArrayList<>();
    ArrayList<HealingPotion> lootPotions = new ArrayList<>();
    public FightHandler(String fightName) {
        this.fightName = fightName;
        charList = readStoryFights(fightName);
        System.out.println(introText);
        refreshButtons();
        sort();
    }

    public void refreshButtons() {
        //Remove the Buttons and add them again to delete the old listeners
        GameScreen.refreshButtons();

        ArrayList<Char> npcList = readStoryFights(fightName);

        //Edit Texts on GameScreen
        GameScreen.addStoryText(introText);
        GameScreen.scrollDown();
    }

    public ArrayList<Char> readStoryFights(String fightName) {
        ArrayList<Char> charList = new ArrayList<>();
        charList.add(SAVE.getCharsWithIndex(0));

        File file = new File(String.valueOf(Gdx.files.internal("Data/StoryFights.xml")));

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
            Node fightNode = root.getElementsByTagName(fightName).item(0);


            if (fightNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList fightContent = fightNode.getChildNodes();
                for(int i=0; i<fightContent.getLength(); i++){
                    Node n = fightContent.item(i);
                    if(n.getNodeType()==Node.ELEMENT_NODE){
                        Element fightElement =(Element) n;
                        switch (fightElement.getNodeName()){
                            case "Intro":
                                introText = fightElement.getTextContent();
                                break;
                            case "Enemies":
                                NodeList enemies = n.getChildNodes();
                                for(int e=0; e<enemies.getLength(); e++){
                                    Node enemyNode = enemies.item(e);
                                    if(enemyNode.getNodeType() == Node.ELEMENT_NODE) {
                                        NodeList enemyValues = enemyNode.getChildNodes();
                                        Element enemyElement = (Element) enemyValues;
                                        Weapon weapon = new Weapon(
                                                enemyElement.getElementsByTagName("wname").item(0).getTextContent(),
                                                Integer.parseInt(enemyElement.getElementsByTagName("wdice").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("wdiceCount").item(0).getTextContent())
                                        );
                                        NPC enemy = new NPC(
                                                enemyElement.getElementsByTagName("name").item(0).getTextContent(),
                                                Integer.parseInt(enemyElement.getElementsByTagName("str").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("dex").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("con").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("ent").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("wis").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("chr").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("armor").item(0).getTextContent()),
                                                Integer.parseInt(enemyElement.getElementsByTagName("relation").item(0).getTextContent()),
                                                weapon
                                        );
                                    }
                                }
                                break;
                            case "Loot":
                                NodeList lootType = n.getChildNodes();
                                for (int e=0; e<lootType.getLength();e++){
                                    Node lootNode = lootType.item(e);
                                    if(lootNode.getNodeType()==Node.ELEMENT_NODE){
                                        switch (lootNode.getNodeName()) {
                                            case "Potions":
                                                NodeList potions = lootNode.getChildNodes();
                                                for(int x=0; x< potions.getLength();x++){
                                                    Element potion = (Element) potions.item(x);
                                                    lootPotions.add(new HealingPotion(Boolean.parseBoolean(potion.getTextContent())));
                                                }
                                                break;
                                            case "Coin":
                                                Element Coin =(Element) lootNode;
                                                lootCoin = Integer.parseInt(Coin.getTextContent());
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
                //Element fightElement = (Element) fightNode;
                //introText = fightElement.getElementsByTagName("Intro").item(0).getTextContent();
                if (fightNode.getNodeName().equals("Intro")) {
                    //introText = fightNode.getTextContent();
                    //System.out.println(introText);
                }

            }


//           for (int e = 0; e < storyRoots.getLength(); e++) {
//               fightName = storyRoots.item(e).getNodeName();
//
//               ArrayList<StoryDecisionValues> storyDecisionValues = new ArrayList<>();
//
//               NodeList decisionList = storyRoots.item(e).getChildNodes();
//               for (int i = 0; i < decisionList.getLength(); i++) {
//                   Node decisionNode = decisionList.item(i);
//                   if (decisionNode.getNodeType() == Node.ELEMENT_NODE) {
//                       NodeList decisionValues = decisionNode.getChildNodes();
//                       Element decisionValuesElement = (Element) decisionValues;
//
//                       storyDecisionValues.add(new StoryDecisionValues(decisionNode.getNodeName(),
//                               decisionValuesElement.getElementsByTagName("DecisionText").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision1").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision3").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision4").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision1Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision2Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision3Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("Decision4Decision1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("Decision1Decision2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("DecisionCheckTyp1").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("DecisionCheckTyp2").item(0).getTextContent(),
//                               decisionValuesElement.getElementsByTagName("DecisionCheckTyp3").item(0).getTextContent(), decisionValuesElement.getElementsByTagName("DecisionCheckTyp4").item(0).getTextContent()
//                       ));
//                   }
//               }
//               storyMap.put(fightName, storyDecisionValues);
//           }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        introText = "";

        return charList;
    }

    private void sort(){
        Collections.sort(charList, new Comparator<Char>() {
            @Override
            public int compare(Char o1, Char o2) {
                return o2.getDex()- o1.getDex();
            }

            Char extractChar(Char c) {
                Char char_ = c;
                // return 0 if no digits found
                return char_;
            }
        });
        for(Char c: charList){
            chars.add(c);
        }
    }
}
