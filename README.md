# RPG
Das Konzept dahinter:

https://docs.google.com/document/d/1dJa6UFqVOf5F7RnHTYonQWHSlxDuDmHtewa31LyrFho/edit?usp=sharing

Die Geschichte:

https://docs.google.com/document/d/1JpSB-8xLShk5_p79O70QtSH_HzJyFNmTyDI1lNzUFI8/edit

# Infos für P für das FightSystem:
- Um nach dem kampf wieder eine Decision zu laden:
  - Die Methode loadNextDecision() im fight handler hinzufügen und mit dem gewünschten decision nammen aufrufen:
  
  public void loadNextDecision(String decisionName) {
        GameScreen.storyCollection.decisions.get(decisionName).loadDecision();
  }
 
