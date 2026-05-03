# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src

#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive lib/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)
#
#
# Execution:
# - pour un test : exeNomTest
# - pour lancer la simulation : exeStart









#CARTE = spiralOfMadness-50x50.map
#CARTE = desertOfDeath-20x20.map
CARTE = mushroomOfHell-20x20.map
#CARTE = carteSujet.map


MAKEALL = start


all: $(MAKEALL)

ifeq (1,0)

MAKEALL = testDijkstra testEvent testAffichageSituationInitiale testInvader testLecture testDonneeSim  testAffichageRobots

# Suite à l'évolution du projet, certains tests ne sont plus réalisables

testInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestInvader.java

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java
	
testDonneeSim:
	javac -d bin -sourcepath src src/TestDonneeSim.java

testAffichageRobots:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestAffichageRobots.java
	
testAffichageSituationInitiale:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestAffichageSituationInitiale.java

testEvent:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestEvent.java
	


exeInvader: 
	java -classpath bin:lib/gui.jar TestInvader

exeLecture: 
	java -classpath bin TestLecteurDonnees cartes/$(CARTE)

exeDonnees:
	java -classpath bin TestDonneeSim cartes/$(CARTE)

exeAffichageRobots:
	java -classpath bin:lib/gui.jar TestAffichageRobots

exeSituationInitiale:
	java -classpath bin:lib/gui.jar TestAffichageSituationInitiale cartes/$(CARTE)

exeEvent:
	java -classpath bin:lib/gui.jar TestEvent cartes/$(CARTE)

exeDijkstra:
	java -classpath bin:lib/gui.jar TestDijkstra cartes/$(CARTE)
endif







testDijkstra:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestDijkstra.java

start:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/Start.java


exeStart:
	java -classpath bin:lib/gui.jar Start cartes/$(CARTE)



# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:lib/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader


clean:
	rm -rf bin/*
