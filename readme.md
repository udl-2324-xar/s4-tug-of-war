# Tug of war (Estirar la corda)

## Objectiu

Treballar amb variables compartides entre threads.

## Descripció
L'objectiu és fer un petit joc on un equip ha d'anar enviant missatges per tal que un servidor incrementi una variable i un segon equip ha d'anar enviant missatges per tal que el servidor decrementi la variable.

El jugador l'únic que ha de fer és connectar-se, esperar a que el servidor li digui que comenci i començar a prémer compulsivament la tecla enter per tal de guanyar.

El servidor anirà comprovant el valor de la variable. Si en algun moment supera 10, l'equip positiu guanya. Si queda per sota de -10, l'equip negatiu guanya.

Si un equip guanya, el servidor ho imprimeix per pantalla i tanca les connexions.

## Server

Així, cal fer un servidor que tindrà una variable de suma compartida i que accepta dues connexions. Una serà assignada com a equip positiu i l'altra com a negatiu.

Un cop s'hagin acceptat les dues connexions, s'iniciaran dos threads per a atendre als dos equips.

El thread principal, llavors, iniciarà un bucle (el podeu fer infinit i sortir amb break quan calgui) on anirà comprovant el valor de la suma compartida i detectarà si un equip ha guanyat (si és així, ho imprimirà i sortirà del bucle, per tal de poder tancar connexions).

Els threads secundaris començaran per enviar el nom de l'equip al client (podeu fer que el nom dels dos threads siguin "Positiu" i "Negatiu", per tal de facilitar-vos aquesta part) i començarà un bucle infinit.

Cada vegada que rebi una línia del client, haurà d'incrementar la suma en +1 o -1 segons sigui l'equip (us pot interessar que el thread tingui el valor de l'increment correcte en una variable i que aquesta es passi en el moment de la creació del thread).

## Clients

Per la seva banda, el client, després de connectar-se, esperarà a rebre un string amb el nom del seu equip.

Quan arribi, imprimirà un missatge per avisar al jugador, i començarà un bucle infinit.

Llavors anirà llegint línies (se suposa que el jugador només ha d'anar prement enter), i enviant-les pel socket.

En la captura d'excepcions del client no feu res, donat que el servidor tancarà la connexió abruptament quan acabi.

## Nota

Aquest repte es planteja com a exercici breu a classe i homework. Per tant no té la completesa ni rigurositat que podria tenir, per exemple, una pràctica de l'assignatura.