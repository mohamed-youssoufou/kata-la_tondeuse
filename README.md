1 - PREREQUIS

Pour utiliser ce programme vous devez avoir java installer sur votre ordinateur notamment la 17.

2 - LANCEMENT DU PROGRAMME

- Rendez-vous dans le dossier contenant le programme AutoMower-1.0-SNAPSHOT.jar
- Ensuite exécuter la commande suivante : `java -jar AutoMower-1.0-SNAPSHOT.jar`

3 - EXECUTION DES INSTRUCTIONS

- Après exécution du programme une fenêtre du terminal vous demandera un lien de fichier.

  ```
  Veuillez entrer le chemin complet du fichier à injecter (fichier.txt)
  ```

Vous devez entrer le lien d'un fichier au format .txt contenant une séquence d'instructions compréhensible par le
programme.

- Ceci est un exemple fichier doit contenir les informations suivantes :
  ```
  5 5
  1 2 N 
  GAGAGAGAA
- En sortie nous aurons
  ```
  La position finale de la tondeuse (1) 1 3 E
  ```

Après l'affichage , le terminal vous posera la question de savoir si vous voulez continuer à donner des instructions ou
non au programme.

- Si vous voulez continuer alors vous appuyer "O" sinon vous appuyer "N" pour quitter.
  ```
  Voulez-vous continuer et traiter un autre fichier ? (O/N)
  ```