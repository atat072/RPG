package de.atat072.rpg.Story;

import de.atat072.rpg.chars.Methods;
import de.atat072.rpg.screens.GameScreen;

import java.util.HashMap;

public class StoryHandler {
    public HashMap<Integer, Story> stories = new HashMap<>();

    public StoryHandler() {
        HashMap<String, Decision> story1DecisionsMap = new HashMap<>();

        //Decision 12
        Decision story1Opt12Decisions =
                new Decision(
                        "Opt12",
                        "Du versuchst es ihr gleichzutun rutscht aber\n" +
                                "am Stamm ab und faellst zu Boden wie eine \n" +
                                "faule Frucht.\n" +
                                "Du hoerst von oben ein paar gelaechter und\n" +
                                " wie Tiana ruft: 'Karik hol mal den\n" +
                                "Neuling hoch.'\n" +
                                "Kaum ist sie fertig den Befehl zu erteilen\n" +
                                " landet ein staemmig gebauter Elf neben dir und \n" +
                                "schmeisst dich ueber seine Schulter. Mit dir\n" +
                                "im Schlepptau klettert er muehelos auf den Baum. \n" +
                                "Sobald er oben landet laesst er dich wie ein Sack\n" +
                                " fallen, so dass du dort genauso liegst wie zuvor \n" +
                                "auf dem Boden. Als du dich beim Aufrappeln umschaust\n" +
                                "faellt dir ein offenes Feuer ins Auge.\n" +
                                "Tiana: 'Keine Sorgen wegen dem Feuer das ist\n" +
                                " magisch, das wird die Plattform nicht in\n" +
                                "brand setzen.'\n",
                        "Blaueflecken reiben und versuchen zu entspannen.", null,
                        "", null,
                        "", null,
                        "", null
                ); //ToDo neue Story Laden
        story1DecisionsMap.put("Opt12", story1Opt12Decisions);


        //Decision 11
        Decision story1Opt11Decisions = new Decision(
                "Opt11",
                "Du schwingst dich mit leichtigkeit in die Baumkrone\n" +
                        "und landest auf einer Holzplattform. Du siehst\n" +
                        "von hier aus noch andere kleinere Plattformen. \n" +
                        "Diese scheint jedoch die Hauptplattform zu sein,\n" +
                        "auf ihr befinden sich kleinere Werkstaetten und\n" +
                        "eine winzige Waffenkammer. An den Werkstaetten\n" +
                        "stellen einige Elfen Pfeile her. Bevor du deine \n" +
                        "Aufmerksamkeit dem Feuer unter einem Topf\n" +
                        "widmen kannst spricht dich Tianan an.\n" +
                        "Tiana: 'Bist sportlicher als du aussiehst.\n" +
                        "Willkommen im Hauptquatier des Widerstandes.\n" +
                        "Ich weiss es macht nicht viel her aber es ist\n" +
                        "das beste was wir haben. Und mach dir keine \n" +
                        "Sorgen wegen dem Feuer es ist magisch ist wird\n" +
                        "nicht auf die Plattform uebergreifend.'\n",
                "(Entspannen und den Aufenthalt geniessen)", null,
                "", null,
                "", null,
                "", null
        ); //ToDo neue Story Laden

        story1DecisionsMap.put("Opt11", story1Opt11Decisions);

        //Decision 10
        Decision story1Opt10Decisions =
                new Decision(
                        "Opt10",
                        "Tiana:'Ich respektiere deine Entscheidung auch\n" +
                                "wenn ich sie nicht gut heisse. Ueberleg es dir \n " +
                                "bitte noch mal.'\n" +
                                "Du kehrst zu deinem Dorf zurueck und siehst\n" +
                                "schon von weitem Rauch aufsteigen. Als du das \n" +
                                "Dorf erreichst siehst du einen brennenden\n" +
                                "Leichenhaufen. Daneben steht ein Schild mit der \n" +
                                "Aufschrift 'Dies ist die Strafe für deinen\n" +
                                "Verrat an Asmodeus.'. \n" +
                                "Du hoerst das klappern der Knochen von einigen\n" +
                                "Skeletten. Das Geraeusch wird Lauter und du'\n" +
                                "rennst zurueck in den Wald. Dir wird klar,\n" +
                                "dass es kein zurueck mehr gibt.\n" +
                                "Im Wald suchst du nach Tiana und findest sie\n" +
                                "einige Zeit spaeter.\n" +
                                "Tiana: 'Oh du hast deine Meinung aber\n" +
                                "schnell geaendert.'\n",
                        "Die Daemonen haben mein Dorf zerstoert\n" +
                                "und alle getoetet, ich schliesse mich\n" +
                                "euch an!", story1DecisionsMap.get("Opt9"),
                        "", null,
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt10", story1Opt10Decisions);

        //Decision 9
        Decision story1Opt9Decisions =
                new Decision(
                        "Opt9",
                        "Tiana: 'Schoen dich dabei zu haben. Komm mit!'\n" +
                                "Sie fuehrt dich durch den dichten Buchenwald,\n" +
                                " wobei sie nicht den direkten weg nimmt. Nach \n" +
                                "einigen schnellen richtungsaenderungen weisst du\n" +
                                " nicht mehr von wo ihr gekommen seid.\n" +
                                "Kurz darauf bleibt Tiana neben einem Baum stehen.\n" +
                                "Tiana: 'Hoffen wir mal, das du gelernt hast\n" +
                                " zu Klettern.'\n" +
                                "Ruft sie dir noch zu waehrend sie ueber dir im\n" +
                                " dichten Eichenlaub verschwindet.\n",
                        "(Auf den Baum klettern) [Strength Ckeck]", new Decision(story1DecisionsMap.get("Opt11"), story1DecisionsMap.get("opt12"), GameScreen.option1Btn, "str"),
                        "", null,
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt9", story1Opt9Decisions);

        //Decision 8
        Decision story1Opt8Decisions =
                new Decision(
                        "Opt8",
                        "Tiana:'Du weisst also nichts relevantes. Naja wenn\n" +
                                "du jetzt eh schon auf ihrer Abschussliste \n " +
                                "stehst, kannst du dich uns auch anschliessen der\n" +
                                " Zorn der Hoellen erwartet dich so \n" +
                                "oder so, der Unterschied besteht lediglich darin,\n" +
                                "ob du den Daemonen vorher noch eins auswischt.'\n",
                        "Ok, ich komme mit euch.", story1DecisionsMap.get("Opt9"),
                        "Nein, ich denke wir sollten\n" +
                                "getrennte wege gehen.", story1DecisionsMap.get("Opt10"),
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt8", story1Opt8Decisions);

        //Decision 7
        Decision story1Opt7Decisions =
                new Decision(
                        "Opt7",
                        "Tiana:'Hmm Ueberrascht das ich deine Luege durchschaut\n" +
                                "habe. Ich bin am Koeniglichen Hof \n" +
                                "aufgewachsen, umgeben von Politikern. Da entwickelt\n" +
                                " man zwangslauufig ein gutes gespuer fuer \n" +
                                "Luegen. Und jetzt raus mit der Sprache! Was weisst du?'\n",
                        "Ja ja ok, ich weiß etwas ueber ein Relikt\n" +
                                "welches Andariel gerne haben wuerde.", story1DecisionsMap.get("Opt5"),
                        "", null,
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt7", story1Opt7Decisions);

        //Decision 6
        Decision story1Opt6Decisions =
                new Decision(
                        "Opt6",
                        "Tiana:'Genauso wenig wie ich, also rueck raus\n" +
                                "mit der Sprache. Wenn wir ueberleben wollen \n" +
                                "brauchen wir alle Informationen, die wir\n" +
                                " kriegen koennen.'\n",
                        "Ja ja ok, ich weiß etwas über ein Relikt\n" +
                                "welches Andariel gerne haben würde.", story1DecisionsMap.get("Opt5"),
                        "", null,
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt6", story1Opt6Decisions);

        //Decision 5
        Decision story1Opt5Decisions =
                new Decision(
                        "Opt5",
                        "Tiana:'Interessant. Ein altes Relikt also, aber\n" +
                                "wofuer braucht sie das? Jetzt nicht, nicht hier!\n " +
                                "Komm mit zu uns, da bist du sicher und dort \n" +
                                "koennen wir alles in ruhe Besprechen.'\n",
                        "Ok, ich komme mit euch.", story1DecisionsMap.get("Opt9"),
                        "Nein, ich denke wir sollten\n" +
                                "getrennte wege gehen.", story1DecisionsMap.get("Opt10"),
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt5", story1Opt5Decisions);

        //Decision 4
        Decision story1Opt4Decisions =
                new Decision(
                        "Opt4",
                        "Tiana:'Du weisst also nichts? Aber warum musstest\n" +
                                "du dann fliehen, die anderen, die nichts \n" +
                                "wussten, liess Andariel doch gehen?'\n",
                        "(Nichts Sagen)", story1DecisionsMap.get("Opt7"),
                        "Andariel haette mit nicht geglaubt, \n" +
                                "wenn ich gesagt haette, das ich nichts weiss.", story1DecisionsMap.get("Opt6"),
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt4", story1Opt4Decisions);

        //Decision 3
        Decision story1Opt3Decisions =
                new Decision(
                        "Opt3",
                        "Tiana:'Nicht ich persoenlich aber meine Verbuendeten,\n" +
                                "wenn du willst kannst du dich uns gern \n" +
                                "anschliessen wenn dir Andariel auch auf den\n" +
                                "Geist geht oder wir sorgen fuer deinen Schutz in \n" +
                                "unserem Stuetzpunkt wenn du etwas weisst, das\n" +
                                "Andariel braucht. Also, zurueck zu meiner \n" +
                                "Frage was weisst du?'\n",
                        "Ich weiss nichts was euch helfen koennte.\n" +
                                "(Luegen) [Charisma Check]", new Decision(story1DecisionsMap.get("Opt8"), story1DecisionsMap.get("opt4"), GameScreen.option1Btn, "wis"),
                        "Ich weiss etwas ueber ein Relikt welches\n" +
                                "Andariel gerne haben wuerde.", story1DecisionsMap.get("Opt5"),
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt3", story1Opt3Decisions);


        //Decision 2
        Decision story1Opt2Decisions =
                new Decision(
                        "Opt2",
                        "Tiana:'Als ob sie jemals nett zu uns gewesen wären.\n" +
                                "Früher oder später hätten sie uns eh hingerichtet\n" +
                                "oder versklavt. Da sterb ich lieber im Kampf um\n" +
                                "mein Volk zu befreien als das zuzulassen.\n" +
                                "Außerdem hast du es mit deiner lüge\n" +
                                "von ebend den anderen auch nicht leichter\n" +
                                "gemacht. Somit musst du dir selbst genauso\n" +
                                "Vorwürfe machen wie mir.\n" +
                                "Nun las das leid das du gebracht hast nicht\n" +
                                "umsonst sein und hilf uns. Was weißt du was\n" +
                                "Andriel wissen will?'",
                        "Ich weiss nichts was euch helfen koennte.\n" +
                                "(Luegen) [Charisma Check]", new Decision(story1DecisionsMap.get("Opt8"), story1DecisionsMap.get("opt4"), GameScreen.option1Btn, "wis"),
                        "Ich weiss etwas ueber ein Relikt welches\n" +
                                " Andariel gerne haben wuerde.", story1DecisionsMap.get("Opt5"),
                        "", null,
                        "", null
                );
        story1DecisionsMap.put("Opt2", story1Opt2Decisions);


        //Decision 1
        Decision story1Opt1Decisions =
                new Decision(
                        "Opt1",
                        "Tiana: 'Ja die bin ich, aber das ist egal solange\n" +
                                "die Daemonen uns unterdruecken habe ich auch\n" +
                                "keine Macht. Also versuche ich mit den Truppen,\n" +
                                "die dem Koenigshaus treu sind die Daemonen zu\n" +
                                "vertreiben. Um das zu schaffen brauchen wir\n" +
                                "einen Vorteil gegenueber Andariel und Wissen\n" +
                                "ist einer der besten, also was weisst du?'",
                        "Ich weiss nichts was euch helfen könnte.\n" +
                                "(Luegen) [Charisma Check]", new Decision(story1DecisionsMap.get("Opt8"), story1DecisionsMap.get("opt4"), GameScreen.option1Btn, "wis"),
                        "Mit deinen aktionen machts du uns das \n" +
                                "Leben nur schwerer!", story1DecisionsMap.get("Opt2"),
                        "Ich weiss etwas ueber ein Relikt welches \n" +
                                "Andariel gerne haben wuerde.", story1DecisionsMap.get("Opt5"),
                        "", null
                );
        story1DecisionsMap.put("Opt1", story1Opt1Decisions);


        //Decision Intro
        Decision story1IntroDecisions =
                new Decision(
                        "Intro",
                        "Du wirst von einer Handvoll Untote verfolgt nachdem\n" +
                                "du dich geweigert hast Andariel zu verraten wo\n" +
                                "das Relikt ist.\n" +
                                "Nach dem du dich in den Wald gefluechtet hast \n" +
                                "werden die Untoten von einer kleinen Gruppe \n" +
                                "Elfen durch einen Angriff abgelenkt. \n" +
                                "Nachdem der Kampf außer Sicht- und \n" +
                                "Hoerreichweite ist bleibst du stehen. \n" +
                                "Kurz danach springt eine Elfe aus den \n" +
                                "Bauumen und landet neben dir. \n" +
                                "Im sprung ruft sie dir zu 'Bist ziemlich mutig\n" +
                                "dich Andariel zu widersetzen'. \n" +
                                "'Leute wie dich kann ich im Widerstand gebrauchen.'\n" +
                                "Vor dir steht eine junge Elfe in hochwertiger \n" +
                                "Lederruestung sie hat einen Langbogen und \n" +
                                "Koecher auf dem Ruecken.\n" +
                                "Bevor du antworten kannst sagt sie 'Ich bin \n" +
                                "Tiana Holimion die Anfuehrerin des Widerstandes. \n" +
                                "Was weisst du, dass du die ehre \n" +
                                "hattest eine Audienz bei Andariel zu bekommen?'",
                        "Es gab keinen Grund. (Luegen) [Charisma Check]", new Decision(story1DecisionsMap.get("Opt8"), story1DecisionsMap.get("opt4"), GameScreen.option1Btn, "wis"),
                        "Hast du mich gerettet?", story1DecisionsMap.get("Opt3"),
                        "Wegen dir behandeln uns die Daemonen immer schlechter!", story1DecisionsMap.get("Opt2"),
                        "Du bist die Prinzessin der Elfen?", story1DecisionsMap.get("Opt1")
                ); //TODO Implement right values for Lügen Check und für ... die antribut einsetzen
        story1DecisionsMap.put("Intro", story1IntroDecisions);

        //Debug
//        System.out.println(story1DecisionsMap.get("Opt12").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt11").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt10").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt9").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt8").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt7").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt6").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt5").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt4").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt3").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt2").decisionText);
//        System.out.println(story1DecisionsMap.get("Opt1").decisionText);
//        System.out.println(story1DecisionsMap.get("Intro").decisionText);
        //Debug
//        for (Decision d : story1DecisionsMap.values()) {
//            System.out.println(d.decisionName + " " + d.decisionText);
//        }

        //TODO load decidion path out of savegame

        Story story1 = new Story(null, story1DecisionsMap);

        stories.put(1, story1);
    }
}
