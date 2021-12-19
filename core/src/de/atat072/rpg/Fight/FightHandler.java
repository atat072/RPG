package de.atat072.rpg.Fight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.atat072.rpg.gameObjects.*;
import de.atat072.rpg.screens.GameScreen;
import de.atat072.rpg.screens.MainScreen;
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

import static de.atat072.rpg.RPG.INSTANCE;
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

    private static Char target;

    private enum ActionType{Meele, Ranged};
    private enum ActionResult{Miss, Hit, Kill};

    public FightHandler(String fightName) {
        this.fightName = fightName;
        charList = readStoryFights(fightName);
        startFightScreenSetUp();
        sort();
        act();
    }

    public void startFightScreenSetUp() {
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
                                                enemyElement.getElementsByTagName("anrede").item(0).getTextContent(),
                                                enemyElement.getElementsByTagName("nameArtikel1").item(0).getTextContent(),
                                                enemyElement.getElementsByTagName("nameArtikel2").item(0).getTextContent(),
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
                                        charList.add(enemy);
                                    }
                                }
                                break;
//                            case "Loot":
//                                NodeList lootType = n.getChildNodes();
//                                for (int e=0; e<lootType.getLength();e++){
//                                    Node lootNode = lootType.item(e);
//                                    if(lootNode.getNodeType()==Node.ELEMENT_NODE){
//                                        switch (lootNode.getNodeName()) {
//                                            case "Potions":
//                                                NodeList potions = lootNode.getChildNodes();
//                                                for(int x=0; x< potions.getLength();x++){
//                                                    Element potion = (Element) potions.item(x);
//                                                    lootPotions.add(new HealingPotion(Boolean.parseBoolean(potion.getTextContent())));
//                                                }
//                                                break;
//                                            case "Coin":
//                                                Element Coin =(Element) lootNode;
//                                                lootCoin = Integer.parseInt(Coin.getTextContent());
//                                        }
//                                    }
//                                }
//                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

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
        charList.remove(SAVE.getCharsWithIndex(0));
    }

    public void act(){
        refreshButtons();
        Char c = chars.retrieve();
        Player player =(Player) SAVE.getCharsWithIndex(0);
        if(c == player){
            String healOption = "Heilen ["+player.getHp()+"/"+player.getMAXHP()+"]";
            changeOptions("Nahkampf","Fernkampf",healOption,"");
            System.out.println(option1Btn.getListeners().size);

            //Set actions to Buttons
            option1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    actionPrepare(ActionType.Meele);
                }
            });
            option2Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    actionPrepare(ActionType.Meele);
                }
            });
            option3Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    heal();
                }
            });
        }
        else {
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
                        return;
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
                        return;
                    }
                }
            }
            chars.add(enemy);
            if (chars.size()<2){
                endFight(true);
                return;
            }else {
                act();
            }
        }
    }

    private void actionPrepare(ActionType actionType) {
        Player p = (Player) SAVE.getCharsWithIndex(0);
        if(charList.size()>1){
            String e1 = charList.get(0).getNameArtikel1();
            String e2 = charList.get(1).getNameArtikel1();
            String e3 = charList.get(2).getNameArtikel1();
            String e4 = charList.get(3).getNameArtikel1();
            changeOptions(e1 + " Angreifen",e2 + " Angreifen",e3 + " Angreifen",e4 + " Angreifen");
            option1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(0);
                }
            });
            option2Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target= charList.get(1);
                }
            });
            option3Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(2);
                }
            });
            option4Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(3);
                }
            });

        } else {
            target = charList.get(0);
        }
        int score = p.getDex();
        int dice = p.getMeleeDice();
        int diceCount = p.getMeleeDiceCount();
        int dmg = attack(target.getAc(),score,diceCount,dice);
        if(dmg>0){
            Boolean isKilled = target.takeDmg(dmg);
            if(isKilled){
                action(actionType, ActionResult.Kill, dmg);
                chars.remove(target);
                charList.remove(target);
            } else {
                action(actionType, ActionResult.Hit, dmg);
            }
        }else{
            action(actionType, ActionResult.Miss, -1);
        }
        chars.add(p);
        if (chars.size()<2){
            endFight(true);
            return;
        }else {
            act();
        }
    }

    private void action(ActionType actionType, ActionResult actionResult, int actionValue) {
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
                    if (typeElement.getNodeName().equals(actionType.name())){
                        NodeList hmList = typeElement.getChildNodes();
                        for (int j = 0; j < hmList.getLength(); j++) {
                            Node m = hmList.item(j);
                            if(m.getNodeType() == Node.ELEMENT_NODE){
                                Element hmElement = (Element) m;
                                if(hmElement.getNodeName().equals(actionResult.name())){
                                    NodeList textList = hmElement.getChildNodes();
                                    ArrayList<String> texts = new ArrayList<>();
                                    for (int k = 0; k < textList.getLength(); k++) {
                                        Node f = textList.item(k);
                                        if(f.getNodeType() == Node.ELEMENT_NODE){
                                            Element xElement = (Element) f;
                                            texts.add(xElement.getTextContent());
                                        }
                                    }
                                    int random = dice(texts.size())-1;
                                    displayText = texts.get(random);
                                    displayText = displayText.replaceAll("\\b_FeindNameAnrede_\\b", target.getAnrede());
                                    displayText = displayText.replaceAll("\\b_FeindNameArtikel1_\\b", target.getNameArtikel1());
                                    displayText = displayText.replaceAll("\\b_FeindNameArtikel2_\\b", target.getNameArtikel2());
                                    //TODO Find Name of player
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
        displayText = displayText +"[Fuer "+actionValue+" Schaden]";
        addStoryText(displayText);
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
        chars.add(p);
        if (chars.size()<2){
            endFight(true);
            return;
        }else {
            act();
        }

    }

    //<editor-fold desc="Actual useless because you can use actionPrepare()">

    private void melee(){
        Player p = (Player) SAVE.getCharsWithIndex(0);
        if(charList.size()>1){
            String e1 = charList.get(0).getNameArtikel1();
            String e2 = charList.get(1).getNameArtikel1();
            String e3 = charList.get(2).getNameArtikel1();
            String e4 = charList.get(3).getNameArtikel1();
            changeOptions(e1 + " Angreifen",e2 + " Angreifen",e3 + " Angreifen",e4 + " Angreifen");
            refreshButtons();
            option1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(0);
                }
            });
            option2Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target= charList.get(0);
                }
            });
            option3Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(0);
                }
            });
            option4Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(0);
                }
            });

        }else {
            target = charList.get(0);}
        int score = p.getStr();
        int dice = p.getMeleeDice();
        int diceCount = p.getMeleeDiceCount();
        int dmg = attack(target.getAc(),score,diceCount,dice);
        if(dmg>0){
            Boolean isKilled = target.takeDmg(dmg);
            if(isKilled){
                killM(dmg);
                chars.remove(target);
                charList.remove(target);
            } else {
                hitM(dmg);
            }
        } else {
            missM();
        }
        chars.add(p);
        if (chars.size()<2){
            endFight(true);
            return;
        }else {
            act();
        }
    }

    private void ranged(){
        Player p = (Player) SAVE.getCharsWithIndex(0);
        if(charList.size()>1){
            String e1 = charList.get(0).getNameArtikel1();
            String e2 = charList.get(1).getNameArtikel1();
            String e3 = charList.get(2).getNameArtikel1();
            String e4 = charList.get(3).getNameArtikel1();
            changeOptions(e1 + " Angreifen",e2 + " Angreifen",e3 + " Angreifen",e4 + " Angreifen");
            option1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(0);
                }
            });
            option2Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target= charList.get(0);
                }
            });
            option3Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(0);
                }
            });
            option4Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target = charList.get(0);
                }
            });

        } else {
            target = charList.get(0);
        }
        int score = p.getDex();
        int dice = p.getMeleeDice();
        int diceCount = p.getMeleeDiceCount();
        int dmg = attack(target.getAc(),score,diceCount,dice);
        if(dmg>0){
            Boolean isKilled = target.takeDmg(dmg);
            if(isKilled){
                action(ActionType.Ranged, ActionResult.Kill, dmg);
                chars.remove(target);
                charList.remove(target);
            } else {
                action(ActionType.Ranged, ActionResult.Hit, dmg);
            }
        }else{
            action(ActionType.Ranged, ActionResult.Miss, -1);
        }
        chars.add(p);
        if (chars.size()<2){
            endFight(true);
            return;
        }else {
            act();
        }
    }

    private void endFight(boolean win){
        if(win){
            addStoryText("Du gehst Siegreich aus dem Kampf hervor.");
            storyCollection.loadStoryDecision("Opt14");
        }else{
            addStoryText("Du bist gestorben.");
            refreshButtons();
            //TODO Remove SaveFile
            changeOptions("In den Himmel zu Odin steigen und \n" +
                    "die Reise vielleicht von neuen Beginnen.", "", "", "");
            option1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    INSTANCE.setScreen(new MainScreen());
                    screenInstance.dispose();
                }
            });
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
                                    NodeList textList = hmElement.getChildNodes();
                                    ArrayList<String> texts = new ArrayList<>();
                                    for (int k = 0; k < textList.getLength(); k++) {
                                        Node f = textList.item(k);
                                        if(f.getNodeType() == Node.ELEMENT_NODE){
                                            Element xElement = (Element) f;
                                            texts.add(xElement.getTextContent());
                                        }
                                    }
                                    int random = dice(texts.size())-1;
                                    displayText = texts.get(random);
                                    displayText.replace("(FeindNameAnrede)", target.getAnrede());
                                    displayText.replace("(FeindNameArtikel1)", target.getNameArtikel1());
                                    displayText.replace("(FeindNameArtikel2)", target.getNameArtikel2());
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
                                    NodeList textList = hmElement.getChildNodes();
                                    ArrayList<String> texts = new ArrayList<>();
                                    for (int k = 0; k < textList.getLength(); k++) {
                                        Node f = textList.item(k);
                                        if(f.getNodeType() == Node.ELEMENT_NODE){
                                            Element xElement = (Element) f;
                                            texts.add(xElement.getTextContent());
                                        }
                                    }
                                    int random = dice(texts.size())-1;
                                    displayText = texts.get(random);
                                    displayText.replace("(FeindNameAnrede)", target.getAnrede());
                                    displayText.replace("(FeindNameArtikel1)", target.getNameArtikel1());
                                    displayText.replace("(FeindNameArtikel2)", target.getNameArtikel2());
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
                                    NodeList textList = hmElement.getChildNodes();
                                    ArrayList<String> texts = new ArrayList<>();
                                    for (int k = 0; k < textList.getLength(); k++) {
                                        Node f = textList.item(k);
                                        if(f.getNodeType() == Node.ELEMENT_NODE){
                                            Element xElement = (Element) f;
                                            texts.add(xElement.getTextContent());
                                        }
                                    }
                                    int random = dice(texts.size())-1;
                                    displayText = texts.get(random);
                                    displayText.replace("(FeindNameAnrede)", target.getAnrede());
                                    displayText.replace("(FeindNameArtikel1)", target.getNameArtikel1());
                                    displayText.replace("(FeindNameArtikel2)", target.getNameArtikel2());
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
                                    NodeList textList = hmElement.getChildNodes();
                                    ArrayList<String> texts = new ArrayList<>();
                                    for (int k = 0; k < textList.getLength(); k++) {
                                        Node f = textList.item(k);
                                        if(f.getNodeType() == Node.ELEMENT_NODE){
                                            Element xElement = (Element) f;
                                            texts.add(xElement.getTextContent());
                                        }
                                    }
                                    int random = dice(texts.size())-1;
                                    displayText = texts.get(random);
                                    displayText.replace("(FeindNameAnrede)", target.getAnrede());
                                    displayText.replace("(FeindNameArtikel1)", target.getNameArtikel1());
                                    displayText.replace("(FeindNameArtikel2)", target.getNameArtikel2());
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
                                    NodeList textList = hmElement.getChildNodes();
                                    ArrayList<String> texts = new ArrayList<>();
                                    for (int k = 0; k < textList.getLength(); k++) {
                                        Node f = textList.item(k);
                                        if(f.getNodeType() == Node.ELEMENT_NODE){
                                            Element xElement = (Element) f;
                                            texts.add(xElement.getTextContent());
                                        }
                                    }
                                    int random = dice(texts.size())-1;
                                    displayText = texts.get(random);
                                    displayText.replace("(FeindNameAnrede)", target.getAnrede());
                                    displayText.replace("(FeindNameArtikel1)", target.getNameArtikel1());
                                    displayText.replace("(FeindNameArtikel2)", target.getNameArtikel2());
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
                                    NodeList textList = hmElement.getChildNodes();
                                    ArrayList<String> texts = new ArrayList<>();
                                    for (int k = 0; k < textList.getLength(); k++) {
                                        Node f = textList.item(k);
                                        if(f.getNodeType() == Node.ELEMENT_NODE){
                                            Element xElement = (Element) f;
                                            texts.add(xElement.getTextContent());
                                        }
                                    }
                                    int random = dice(texts.size())-1;
                                    displayText = texts.get(random);
                                    displayText.replace("(FeindNameAnrede)", target.getAnrede());
                                    displayText.replace("(FeindNameArtikel1)", target.getNameArtikel1());
                                    displayText.replace("(FeindNameArtikel2)", target.getNameArtikel2());
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
    //</editor-fold>

}
