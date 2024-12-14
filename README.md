# Projet-hop

---

## Features

#### The game is more fast paced and more difficult than demo

The game is set at 60fps to create a smoother experience. There are 10 levels and player can play until infinity. **Double** (even triple, quadruple!!) jumps are allowed. Also, there are fireballs from below which Axel needs to dodge and the blocks are falling faster as the game progresses.

#### Rich UI experience

Main menu, settings menu, pause panel during the game (user can press Esc to pause/unpause the game at anytime) and game over panel are made possible by highly modular code organization.

#### Fully configurable game rules

User can either change the game configurations from the game settings menu or directly modify `config.json` file in the same directory as `Hop.jar`. Fallback system is also implemented if `config.json` doesn't exist or got corrupted.

---

## Acknowledged limitations

#### No textures and sounds system

During the project developpement, we are unable to find suitable textures for our game. We did attempted to try a few of them (inside `assets` folder), but they have turned out to be rather ugly and distracting for our high-paced game.

#### Swing limitations

As Swing is intended to be a GUI library, our game which requires a lot of frame re-rendering unlike a typical GUI application causes a few performance issue. This is very noticable if we increase the size of game's window. 

More particularly, Swing library does not support any hardware acceleration and all rendering operations are done through EDT (Event dispatching thread). To expand our project further, we might have to migrate to more performant and game-oriented java library  

---

## Build

Cloning this repo and inside Projet-hop dir, to compile :

```bash
javac -d build -cp .:libs/gson-2.11.0.jar src/*.java
```

Additionally, if you want to pack the project into jar file (if you just want to compile and run the project normally, skip these) :

     1. Copy the assets and config folder into build folder 

```bash
cp -r assets build/
cp -r config build/
```

    2.  Unpack gson-2.11.0.jar into classes inside build folder

```bash
cd build
jar xf ../libs/gson-2.11.0.jar
cd ..
```

To run:

```bash
java -cp build:libs/gson-2.11.0.jar Hop
```

To pack the project into jar file (Hop.jar)

```bash
jar cfm Hop.jar Manifest.txt -C build/ .
```

## Run

To run the game using Hop.jar file

```bash
java -jar Hop.jar
```
