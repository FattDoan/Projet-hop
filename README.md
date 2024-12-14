# Projet-hop

> Si j'avais un euro à chaque fois que je sur-ingénierie des merdes, j'aurais deux euros, ce qui n'est pas grand-chose, mais c'est bizarre que ça soit arrivé deux fois.

The project is finished. No more features to be added. Only new commits to organize codes and fix some bugs. 

### TODO: Bundle the entire project into jar file.

## Lastest update: config manager now has fallback system and user can press Esc to pause and unpause the game.

Inside Projet-hop dir (<!> not src directory anymore <!>), to compile :

```bash
javac -d build -cp .:libs/gson-2.11.0.jar src/*.java
```

To execute:

```bash
java -cp build:libs/gson-2.11.0.jar Hop
```
