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
import java.util.*;
import java.util.stream.Collectors;

import static de.atat072.rpg.RPG.INSTANCE;
import static de.atat072.rpg.RPG.SAVE;
import static de.atat072.rpg.gameObjects.Methods.attack;
import static de.atat072.rpg.gameObjects.Methods.dice;
import static de.atat072.rpg.screens.GameScreen.*;

public class FightHandler {

    int lootCoin =0;
    private String introText;
    private String fightName;
    private CharQueue chars = new CharQueue();
    private ArrayList<Char> charList = new ArrayList<>();
    private ArrayList<HealingPotion> lootPotions = new ArrayList<>();

    private String winDecision;
    private String storyName;

    private static Char target;
    private static Player p;

    private enum ActionType{Meele, Ranged};
    private enum ActionResult{Miss, Hit, Kill};

    public FightHandler(String storyName, String fightName) {
        this.storyName = storyName;
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
                            case "WinnDecision":
                                winDecision = n.getChildNodes().item(0).getTextContent();
                                break;
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
        p =(Player) SAVE.getCharsWithIndex(0);
        if(c == p){
            String healOption = "Heilen ["+p.getHp()+"/"+p.getMAXHP()+"]";
            changeOptions("Nahkampf","Fernkampf",healOption,"");

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
                    actionPrepare(ActionType.Ranged);
                }
            });
            option3Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (p.getHp() < p.getMAXHP()) {
                        if (!p.hasNoPotions()) {
                            heal();
                        }
                        else {
                            addStoryText("Du bist nicht im besitz eines Heiltrankes \nund kannst deshalb auch keinen trinken!");
                        }
                    }
                    else {
                        addStoryText("Du bist bereits vollstaendig geheilt und \nbrauchst deshalb keinen Heiltrank!");
                    }
                }
            });
        }
        else {
            NPC enemy = (NPC) c;
            p = (Player) SAVE.getCharsWithIndex(0);
            int ac = p.getAc();
            int dice = enemy.getDice();
            int diceCount = enemy.getDiceCount();
            if(enemy.isRanged()){
                int score = enemy.getDex();
                int dmg = attack(ac, score,diceCount, dice);
                if(dmg==0){
                    action(ActionType.Ranged, ActionResult.Miss, 0, enemy);;
                }else{
                    action(ActionType.Ranged, ActionResult.Hit, dmg, enemy);
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
                    action(ActionType.Meele, ActionResult.Miss, 0, enemy);
                }else{
                    action(ActionType.Meele, ActionResult.Hit, dmg, enemy);
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

    private void actionPrepare(final ActionType actionType) {
        p = (Player) SAVE.getCharsWithIndex(0);

        String e1 = "";
        String e2 = "";
        String e3 = "";
        String e4 = "";

        if (charList.size() > 0)
            e1 = charList.get(0).getNameArtikel1() + " Angreifen";
        if (charList.size() > 1)
            e2 = charList.get(1).getNameArtikel1() + " Angreifen";
        if (charList.size() > 2)
            e3 = charList.get(2).getNameArtikel1() + " Angreifen" ;
        if (charList.size() > 3)
            e4 = charList.get(3).getNameArtikel1() + " Angreifen";

        changeOptions(e1, e2, e3, e4);

        if (charList.size() > 0)
            option1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target= charList.get(0);
                    doDamage(p, actionType);
                }
            });
        if (charList.size() > 1)
            option2Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target= charList.get(1);
                    doDamage(p, actionType);
                }
            });
        if (charList.size() > 2)
            option3Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target= charList.get(2);
                    doDamage(p, actionType);
                }
            });
        if (charList.size() > 3)
            option4Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    target= charList.get(3);
                    doDamage(p, actionType);
                }
            });
    }

    private void doDamage(Player p, ActionType actionType) {
        int score = p.getDex();
        int dice = p.getMeleeDice();
        int diceCount = p.getMeleeDiceCount();
        int dmg = attack(target.getAc(),score,diceCount,dice);
        if(dmg>0){
            Boolean isKilled = target.takeDmg(dmg);
            if(isKilled){
                chars.remove(target);
                charList.remove(target);
                action(actionType, ActionResult.Kill, dmg, p);
            } else {
                action(actionType, ActionResult.Hit, dmg, p);
            }
        }else{
            action(actionType, ActionResult.Miss, 0, p);
        }
        chars.add(p);
        if (chars.size()<2){
            endFight(true);
            return;
        }else {
            zugBeenden();
        }
    }

    private void action(ActionType actionType, ActionResult actionResult, int actionValue, Char attacker) {
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

            NodeList typeNodes = root.getChildNodes();

            for (int i = 0; i < typeNodes.getLength(); i++) {
                Node typeNode = typeNodes.item(i);
                if(typeNode.getNodeType()== Node.ELEMENT_NODE){
                    Element typeElement = (Element) typeNode;

                    //Check if Player or Enemy is Attacking
                    if (target != null) {
                        //Player Attacks
                        if (typeElement.getNodeName().equals("Player")) {
                            NodeList attackTypeNodeList = typeElement.getChildNodes();

                            for (int s = 0; s < attackTypeNodeList.getLength(); s++) {
                                Node attackTypeNode = attackTypeNodeList.item(s);
                                if (attackTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element attackTypeElement = (Element) attackTypeNode;

                                    if (attackTypeElement.getNodeName().equals(actionType.name())){
                                        NodeList hmList = attackTypeElement.getChildNodes();
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
                                                    displayText = displayText.replaceAll("\\b_NameAnrede_\\b", target.getAnrede());
                                                    displayText = displayText.replaceAll("\\b_NameArtikel1_\\b", target.getNameArtikel1());
                                                    displayText = displayText.replaceAll("\\b_NameArtikel2_\\b", target.getNameArtikel2());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        //Enemy Attacks
                        if (typeElement.getNodeName().equals("Enemy")) {
                            NodeList attackTypeNodeList = typeElement.getChildNodes();

                            for (int s = 0; s < attackTypeNodeList.getLength(); s++) {
                                Node attackTypeNode = attackTypeNodeList.item(s);
                                if (attackTypeNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element attackTypeElement = (Element) attackTypeNode;

                                    if (attackTypeElement.getNodeName().equals(actionType.name())){
                                        NodeList hmList = attackTypeElement.getChildNodes();
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
                                                    displayText = displayText.replaceAll("\\b_NameAnrede_\\b", attacker.getAnrede());
                                                    displayText = displayText.replaceAll("\\b_NameArtikel1_\\b", attacker.getNameArtikel1());
                                                    displayText = displayText.replaceAll("\\b_NameArtikel2_\\b", attacker.getNameArtikel2());
                                                }
                                            }
                                        }
                                    }
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
        target = null;
    }

    private void heal(){
        p =(Player) SAVE.getCharsWithIndex(0);
        boolean great = false;
        boolean less = false;

        for (HealingPotion h : p.getPotions()) {
            if (h != null && h.isGreaterHealing()) {
                great = true;
            } else if (h != null && !h.isGreaterHealing()){
                less = true;
            }
        }

        if(great&&less){
            GameScreen.refreshButtons();
            changeOptions("Grosser Heiltrank","kleiner Heiltrank","","");

            option1Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    p.usePotion(true);

                    chars.add(p);
                    if (chars.size()<2){
                        endFight(true);
                        return;
                    }else {
                        zugBeenden();
                    }
                }
            });
            option2Btn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    p.usePotion(false);

                    chars.add(p);
                    if (chars.size()<2){
                        endFight(true);
                        return;
                    }else {
                        zugBeenden();
                    }
                }
            });
        }else if (great && !less || !great && less){

            HealingPotion[] potions = p.getPotions();
            if (potions[0] != null) {p.usePotion(potions[0].isGreaterHealing());}
            else if (potions[1] != null) {p.usePotion(potions[1].isGreaterHealing());}
            else if (potions[2] != null) {p.usePotion(potions[2].isGreaterHealing());}
            else if (potions[3] != null) {p.usePotion(potions[3].isGreaterHealing());}
            else if (potions[4] != null) {p.usePotion(potions[4].isGreaterHealing());}

            chars.add(p);
            if (chars.size()<2){
                endFight(true);
                return;
            }else {
                zugBeenden();
            }
        }
    }

    private void zugBeenden() {
        refreshButtons();
        changeOptions("Zug beenden", "", "", "");

        option1Btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                act();
            }
        });
    }

    private void endFight(boolean win){
        if(win){
            addStoryText("Du gehst Siegreich aus dem Kampf hervor.");
            storyCollection.loadStoryDecision(winDecision);
            SAVE.addDecisionToDecisionPath(storyName, winDecision);
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
                action(ActionType.Ranged, ActionResult.Kill, dmg, p);
                chars.remove(target);
                charList.remove(target);
            } else {
                action(ActionType.Ranged, ActionResult.Hit, dmg, p);
            }
        }else{
            action(ActionType.Ranged, ActionResult.Miss, -1, p);
        }
        chars.add(p);
        if (chars.size()<2){
            endFight(true);
            return;
        }else {
            act();
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
