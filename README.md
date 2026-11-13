# RMLala Android Template

Modèle public de démarrage pour les applications Android de **RMLala**.

## Fonctionnement

- Android Gradle Plugin **8.7.3**, Gradle **8.9**, JDK 17.
- Deux variantes : **api23** (Android 6+) et **api29** (Android 10+).
- Quatre artefacts standards : **Debug API23**, **Debug API29**, **Release API23**, **Release API29**.
- Les deux Release utilisent les secrets de signature de l'organisation, jamais le keystore dans Git.
- Les PR compilent les deux Debug (API23 et API29) : **aucun secret de signature n'est exposé dans les PR**.
- Les Release signées sont compilées sur `main` ou sur lancement manuel `workflow_dispatch` ; limiter les autorisations de déclenchement.
- Pas de PWA.

## Secrets attendus

`KEYSTORE_BASE64`, `KEY_ALIAS`, `KEYSTORE_PASSWORD`, `KEY_PASSWORD`.

**Vérifier la correspondance avec les noms réels des secrets de l'organisation** ; ne jamais ajouter leurs valeurs au code source.

## Personnalisation d'une nouvelle application

1. Créer un dépôt public depuis ce modèle après avoir coché **Settings → General → Template repository** dans GitHub.
2. Adapter le `namespace`, l'`applicationId`, le package Java, le nom, les icônes et les sources Android.
3. Conserver le code existant si l'application existe déjà : ce modèle sert de point de départ, pas de remplacement automatique.
4. Développer sur une branche unique, ouvrir une PR, contrôler le build et fusionner uniquement après validation explicite.
5. Conserver `versionName` entre Debug et Release ; incrémenter `releaseVersionCode` uniquement pour une Release réellement distribuée.
6. Ne jamais mettre le fichier keystore, les mots de passe ou les fichiers `local.properties` dans le dépôt.

## Standard de livraison pour tous les dépôts

- Les Pull Requests vers `main` doivent déclencher automatiquement les tests et les deux builds Debug.
- Sur `main` ou déclenchement manuel autorisé, compiler les deux Release signées et vérifier les quatre APK.
- Ne pas exposer les secrets aux Pull Requests non fiables ; éviter `pull_request_target` pour les builds de code proposé.
- Adapter les workflows des projets Capacitor/HTML à leur structure propre : ne pas remplacer les sources métier par ce squelette.
- Vérifier que les noms des secrets correspondent aux secrets de l'organisation ; aucune valeur de secret ne doit être publiée.
- Une fusion nécessite une validation explicite, après tests, artefacts et vérification fonctionnelle.

## Portée de l'automatisation

Le dépôt fournit un squelette Gradle et un workflow CI. Il ne crée pas automatiquement les nouveaux dépôts. Cette étape exige une automatisation distincte avec des droits d'organisation.

## Sécurité

Les dépôts publics utilisant des secrets d'organisation ont besoin de contrôles d'écriture et de PR. Une modification malveillante d'un workflow peut exposer les secrets de signature lors d'un build autorisé. Une clé commune compromettrait potentiellement toutes les applications si elle était divulguée.
