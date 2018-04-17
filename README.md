# Prerequistes

jdk8 installed

# Compile ia-java

```
cd src

Windows :
javac -cp .;../lib/* IA.java
Unix :
javac -cp .:../lib/* IA.java
```

# Create a new game

```
Windows :
java -cp .;../lib/* IA CREATE [GAME_NAME] [CHARACTER] [PLAYER_NAME] [VERSUS_PLAYER]
Unix :
java -cp .:../lib/* IA CREATE [GAME_NAME] [CHARACTER] [PLAYER_NAME] [VERSUS_PLAYER]
```
* GAME_NAME: Name of the new game
* CHARACTER: Type of character (WARRIOR, PALADIN, DRUID, SORCERER, ELF, TROLL)
* PLAYER_NAME: Your name
* VERSUS_PLAYER: If true, an other player must join your game, otherwise it's a server side IA

Example to play against a server side IA :
```
java -cp .:../lib/* IA CREATE youShouldNotPass SORCERER gandalf false
```
Example to play against another player :
```
java -cp .:../lib/* IA CREATE youShouldNotPass SORCERER gandalf true
```

# Join a created game as the second player

```
java -cp .;../lib/* IA JOIN [GAME_TOKEN] [CHARACTER] [PLAYER_NAME]
```
* GAME_TOKEN: Key of the game you want to join
* CHARACTER: Type of your character (WARRIOR, PALADIN, DRUID, SORCERER, ELF, TROLL)
* PLAYER_NAME: Your name

Example :
```
java -cp .:../lib/* IA JOIN vyhu5a SORCERER saroumane
```
