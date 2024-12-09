# Projet-hop

## TODO in order of importance

- [ ] Organize all the configs to the classes

- [ ] Add main menu and settings menu 

- [ ] Add fallback system for config manager (default_config.json)

- [ ] Add textures (maybe?!)

- [ ] Add multiplayer (should be easy after organizing the code)

## UPDATES 1.0.0beta2

#### <!> The compile commands have been changed to accomodate the config manager (using google Gson library to parse JSON file)

Inside Projet-hop dir (<!> not src directory anymore <!>), to compile :

```bash
javac -d build -cp .:libs/gson-2.11.0.jar src/*.java
```

To execute:

```bash
java -cp build:libs/gson-2.11.0.jar Hop
```

- Add fireballs to make the game harder (maybe it becomes too hard now idk)

- Setup to add assets (textures), main menu, settings menu and pause menu (pressing Esc while playing)

- Added config manager through JSON file. User are now able to change more advanced game settings (which aren't shown in settings menu).

## UPDATES 1.0.0beta1

- checkCollision delta (la différence entre Axel y et le Block y) est Block.HEIGHT/2 -> tout au long des tests, il a été prouvé que c'était le plus fiable.

- Le bloc dont la valeur y < 0 est supprimé (à l'avant de la liste chaînée Blocks) et s'il y a assez de place en haut, un bloc est ajouté à l'arrière de la liste chaînée Blocks -> simule la descente des blocs.

- Ajout d'un système de Score et de difficulté (voir InfoBarPanel.java). À des niveaux de difficulté plus élevés, non seulement les blocs deviennent plus petits, mais le jeu se déplace également plus rapidement.

- Ajout d'un GameOver panel (voir GameOverPanel.java). Le bouton Restart est attaché à la méthode startGame() de la classe Hop (en d'autres termes, lorsque le bouton Restart est cliqué, le programme exécute startGame() -> initialiser récursivement le jeu). Le bouton Exit est rattaché à la méthode System.exit(0).

### Git

1) Changed files -> Commit first then Push

    2. We only use main branch for this project

First time downloading the repo to local system

```bash
git clone https://github.com/FattDoan/Projet-hop.git
```

After that, to update the local repo to match with the changes in the actual repo on Github

```bash
git pull
```

After editing the files, to show the info

```bash
git status
```

  To add all files to be commited (staging)

```bash
git add *
```

Commit with message

```bash
git commit -m "Message"
```

Finally, push to Github repo

```bash
git push
```

It may ask for the account info for the first time. The password is your personal pass key of Github account, not the actual password of Github account.

To execute the project, inside src folder :

```bash
javac *java && java Hop 
```

## Notes

##### DELAY

The DELAY variable inside Hop.java the prof gave us initially is 40. This means each 40ms the game "refreshes" (redraw the frame based on internal game state and user input)

1s = 1000ms / 40ms = 25. This gives us 25fps. Ok for 2d games.

I changed DELAY = 15 so it gives a bit more than 60fps because why not?

##### SPEED in pixels/second

Logically, Axel speed as well as game constants such as GRAVITY and DIVE_SPEED should be calculated in pixels/second and NOT pixels/frame. This helps our game's physics not be affected by how fast the game is rendered (25fps or 60fps should be the same).

However, Axel position needs to be updated at each frame, therefore its new position is calculated as:

```java
x += xSpeed * (double)(DELAY/1000);
y += ySpeed * (double)(DELAY/1000);
// (xSpeed, ySpeed) are double but (x,y) coord must be int
```

**TODO**: this can cause LATERAL ACCELERATION, xSpeed = -LATERAL_SPEED and = LATERAL_SPEED to disable it.

xSpeed -= LATERAL_SPEED when LEFT arrow key is pressed

xSpeed += LATERAL_SPEED  when RIGHT arrow key is pressed

if LEFT or RIGHT arrow key is released, xSpeed = 0

ySpeed += JUMP_SPEED when UP arrow key is pressed

ySpeed -= DIVE_SPEED when DOWN arrow key is pressed

ySpeed -= GRAVITY at each frame while Axel is inAir

ySpeed = max(VERTICAL_SPEED, MAX_FALL_SPEED) to prevent diving too quickly

If Axel is not inAir, in other words, resting on the block, ySpeed = 0

https://prod.liveshare.vsengsaas.visualstudio.com/join?DCBC09D586FD947CA61F6EEF59325430818E
