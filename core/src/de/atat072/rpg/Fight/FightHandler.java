package de.atat072.rpg.Fight;

import com.badlogic.gdx.Gdx;
import de.atat072.rpg.gameObjects.*;
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
import static de.atat072.rpg.gameObjects.Methods.attack;
import static de.atat072.rpg.gameObjects.Methods.dice;
import static de.atat072.rpg.screens.GameScreen.*;

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
        addStoryText(introText);
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
                                                weapon,
                                                Boolean.parseBoolean(enemyElement.getElementsByTagName("ranged").item(0).getTextContent())
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
            if (c == SAVE.getCharsWithIndex(0)){
                charList.remove(c);
            }
        }
    }

    public void act(){
        Char c = chars.retrieve();
        Player player =(Player) SAVE.getCharsWithIndex(0);
        if(c == player){
            String healOption = "Heilen ["+player.getHp()+"/"+player.getMAXHP()+"]";
            changeOptions("Nahkampf","Fernkampf",healOption,"");
            if(option1Btn.isChecked()){
                melee();
            }else if(option2Btn.isChecked()){
                ranged();
            }else if(option3Btn.isChecked()&&!player.getPotions().isEmpty()){
                heal();
            }
        }else{
            NPC enemy = (NPC) c;
            Player p = (Player) SAVE.getCharsWithIndex(0);
            int ac = p.getAc();
            int dice = enemy.getDice();
            int diceCount = enemy.getDiceCount();
            if(enemy.isRanged()){
                int score = enemy.getDex();
                int dmg = attack(ac, score,diceCount, dice);
                if(dmg==0){
                    missR();
                }else{
                    hitR(dmg);
                    boolean isDead = p.takeDmg(dmg);
                    if(isDead){
                        endFight(false);
                    }
                }
            }else{
                int score = enemy.getStr();
                int dmg = attack(ac, score,diceCount, dice);
                if(dmg==0){
                    missM();
                }else{
                    hitM(dmg);
                    boolean isDead = p.takeDmg(dmg);
                    if(isDead){
                        endFight(false);
                    }
                }
            }
            chars.add(enemy);
        }
        if (chars.size()<2){
            endFight(true);
        }else {
            act();
        }
    }

    private void melee(){
        Player p = (Player) SAVE.getCharsWithIndex(0);
        Char target=null;
        if(charList.size()>1){
            String e1 = charList.get(0).getName();
            String e2 = charList.get(1).getName();
            String e3 = charList.get(2).getName();
            String e4 = charList.get(3).getName();
            changeOptions(e1,e2,e3,e4);
            if(option1Btn.isChecked()){
                target = charList.get(0);
            }else if(option2Btn.isChecked()){
                target = charList.get(1);
            }else if(option3Btn.isChecked()){
                target = charList.get(2);
            }else if(option4Btn.isChecked()){
                target = charList.get(3);
            }

        }else {target = charList.get(0);}
        int score = p.getStr();
        int dice = p.getMeleeDice();
        int diceCount = p.getMeleeDiceCount();
        int dmg = attack(target.getAc(),score,diceCount,dice);
        if(dmg>0){
            Boolean isKilled = target.takeDmg(dmg);
            if(isKilled){
                killM(dmg);
            }else{hitM(dmg);}
        }else{
            missM();
        }
        chars.add(p);
        act();
    }

    private void ranged(){
        Player p = (Player) SAVE.getCharsWithIndex(0);
        Char target=null;
        if(charList.size()>1){
            String e1 = charList.get(0).getName();
            String e2 = charList.get(1).getName();
            String e3 = charList.get(2).getName();
            String e4 = charList.get(3).getName();
            changeOptions(e1,e2,e3,e4);
            if(option1Btn.isChecked()){
                target = charList.get(0);
            }else if(option2Btn.isChecked()){
                target = charList.get(1);
            }else if(option3Btn.isChecked()){
                target = charList.get(2);
            }else if(option4Btn.isChecked()){
                target = charList.get(3);
            }

        }else {target = charList.get(0);}
        int score = p.getDex();
        int dice = p.getMeleeDice();
        int diceCount = p.getMeleeDiceCount();
        int dmg = attack(target.getAc(),score,diceCount,dice);
        if(dmg>0){
            Boolean isKilled = target.takeDmg(dmg);
            if(isKilled){
                killR(dmg);
            }else{hitR(dmg);}
        }else{
            missR();
        }
        chars.add(p);
        act();
    }

    private void heal(){
        Player p =(Player) SAVE.getCharsWithIndex(0);
        boolean great=false;
        boolean less=false;
        if(p.getP1().isGreaterHealing()||p.getP2().isGreaterHealing()||p.getP3().isGreaterHealing()||p.getP4().isGreaterHealing()||p.getP5().isGreaterHealing()){
            great = true;
        }else if(!p.getP1().isGreaterHealing()||!p.getP2().isGreaterHealing()||!p.getP3().isGreaterHealing()||!p.getP4().isGreaterHealing()||!p.getP5().isGreaterHealing()){
            less=true;
        }
        if(great&&less){
            GameScreen.refreshButtons();
            changeOptions("Grosser Heiltrank","kleiner Heiltrank","","");
            if(option1Btn.isChecked()){
                if(p.getP1().isGreaterHealing()){
                    p.usePotion(1);
                }else if(p.getP2().isGreaterHealing()){
                    p.usePotion(2);
                }else if(p.getP3().isGreaterHealing()){
                    p.usePotion(3);
                }else if(p.getP4().isGreaterHealing()){
                    p.usePotion(4);
                }else if(p.getP5().isGreaterHealing()){
                    p.usePotion(5);
                }
            }else if(option2Btn.isChecked()){
                if(!p.getP1().isGreaterHealing()&&p.getP1()!=null){
                    p.usePotion(1);
                }else if(!p.getP2().isGreaterHealing()&&p.getP2()!=null){
                    p.usePotion(2);
                }else if(!p.getP3().isGreaterHealing()&&p.getP3()!=null){
                    p.usePotion(3);
                }else if(!p.getP4().isGreaterHealing()&&p.getP4()!=null){
                    p.usePotion(4);
                }else if(!p.getP5().isGreaterHealing()&&p.getP5()!=null){
                    p.usePotion(5);
                }
            }
        }else {
            if (p.getP1()!=null){
                p.usePotion(1);
            }else if(p.getP2()!=null){
                p.usePotion(2);
            }else if(p.getP3()!=null){
                p.usePotion(3);
            }else if(p.getP4()!=null){
                p.usePotion(4);
            }else if(p.getP5()!=null){
                p.usePotion(5);
            }
        }
        act();

    }

    private void endFight(boolean win){
        if(win){
            addStoryText("Du gehst Siegreich aus dem Kampf hervor.");
            //loadNextDecision("opt9");
        }else{
            addStoryText("Du bist gestorben.");
        }
    }

    private void missR() {
        String displayText="";

        File file = new File(String.valueOf(Gdx.files.internal("Data/FightTexts.xml")));

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

            NodeList nodes = root.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if(n.getNodeType()== Node.ELEMENT_NODE){
                    Element typeElement = (Element) n;
                    if (typeElement.getNodeName().equals("Ranged")){
                        NodeList hmList = typeElement.getChildNodes();
                        for (int j = 0; j < hmList.getLength(); j++) {
                            Node m = hmList.item(j);
                            if(m.getNodeType() == Node.ELEMENT_NODE){
                                Element hmElement = (Element) m;
                                if(hmElement.getNodeName().equals("Miss")){
                                    NodeList texts = hmElement.getChildNodes();
                                    Element textsElement = (Element) texts;
                                    displayText = textsElement.getElementsByTagName("opt"+dice(texts.getLength())).item(0).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        addStoryText(displayText);
    }

    private void missM() {
        String displayText="";

        File file = new File(String.valueOf(Gdx.files.internal("Data/FightTexts.xml")));

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

            NodeList nodes = root.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if(n.getNodeType()== Node.ELEMENT_NODE){
                    Element typeElement = (Element) n;
                    if (typeElement.getNodeName().equals("Meele")){
                        NodeList hmList = typeElement.getChildNodes();
                        for (int j = 0; j < hmList.getLength(); j++) {
                            Node m = hmList.item(j);
                            if(m.getNodeType() == Node.ELEMENT_NODE){
                                Element hmElement = (Element) m;
                                if(hmElement.getNodeName().equals("Miss")){
                                    NodeList texts = hmElement.getChildNodes();
                                    Element textsElement = (Element) texts;
                                    displayText = textsElement.getElementsByTagName("opt"+dice(texts.getLength())).item(0).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        addStoryText(displayText);
    }

    private void hitR(int dmg) {
        String displayText="";

        File file = new File(String.valueOf(Gdx.files.internal("Data/FightTexts.xml")));

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

            NodeList nodes = root.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if(n.getNodeType()== Node.ELEMENT_NODE){
                    Element typeElement = (Element) n;
                    if (typeElement.getNodeName().equals("Ranged")){
                        NodeList hmList = typeElement.getChildNodes();
                        for (int j = 0; j < hmList.getLength(); j++) {
                            Node m = hmList.item(j);
                            if(m.getNodeType() == Node.ELEMENT_NODE){
                                Element hmElement = (Element) m;
                                if(hmElement.getNodeName().equals("Hit")){
                                    NodeList texts = hmElement.getChildNodes();
                                    Element textsElement = (Element) texts;
                                    displayText = textsElement.getElementsByTagName("opt"+dice(texts.getLength())).item(0).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        displayText = displayText +"[Fuer "+dmg+" Schaden]";
        addStoryText(displayText);
    }

    private void hitM(int dmg) {
        String displayText="";

        File file = new File(String.valueOf(Gdx.files.internal("Data/FightTexts.xml")));

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

            NodeList nodes = root.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if(n.getNodeType()== Node.ELEMENT_NODE){
                    Element typeElement = (Element) n;
                    if (typeElement.getNodeName().equals("Meele")){
                        NodeList hmList = typeElement.getChildNodes();
                        for (int j = 0; j < hmList.getLength(); j++) {
                            Node m = hmList.item(j);
                            if(m.getNodeType() == Node.ELEMENT_NODE){
                                Element hmElement = (Element) m;
                                if(hmElement.getNodeName().equals("Hit")){
                                    NodeList texts = hmElement.getChildNodes();
                                    Element textsElement = (Element) texts;
                                    displayText = textsElement.getElementsByTagName("opt"+dice(texts.getLength())).item(0).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        displayText = displayText +"[Fuer "+dmg+" Schaden]";
        addStoryText(displayText);
    }

    private void killR(int dmg) {
        String displayText="";

        File file = new File(String.valueOf(Gdx.files.internal("Data/FightTexts.xml")));

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

            NodeList nodes = root.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if(n.getNodeType()== Node.ELEMENT_NODE){
                    Element typeElement = (Element) n;
                    if (typeElement.getNodeName().equals("Ranged")){
                        NodeList hmList = typeElement.getChildNodes();
                        for (int j = 0; j < hmList.getLength(); j++) {
                            Node m = hmList.item(j);
                            if(m.getNodeType() == Node.ELEMENT_NODE){
                                Element hmElement = (Element) m;
                                if(hmElement.getNodeName().equals("Kill")){
                                    NodeList texts = hmElement.getChildNodes();
                                    Element textsElement = (Element) texts;
                                    displayText = textsElement.getElementsByTagName("opt"+dice(texts.getLength())).item(0).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        displayText = displayText +"[Fuer "+dmg+" Schaden]";
        addStoryText(displayText);
    }

    private void killM(int dmg) {
        String displayText="";

        File file = new File(String.valueOf(Gdx.files.internal("Data/FightTexts.xml")));

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

            NodeList nodes = root.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                if(n.getNodeType()== Node.ELEMENT_NODE){
                    Element typeElement = (Element) n;
                    if (typeElement.getNodeName().equals("Meele")){
                        NodeList hmList = typeElement.getChildNodes();
                        for (int j = 0; j < hmList.getLength(); j++) {
                            Node m = hmList.item(j);
                            if(m.getNodeType() == Node.ELEMENT_NODE){
                                Element hmElement = (Element) m;
                                if(hmElement.getNodeName().equals("Kill")){
                                    NodeList texts = hmElement.getChildNodes();
                                    Element textsElement = (Element) texts;
                                    displayText = textsElement.getElementsByTagName("opt"+dice(texts.getLength())).item(0).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        displayText = displayText +"[Fuer "+dmg+" Schaden]";
        addStoryText(displayText);
    }


}
