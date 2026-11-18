# AppForge Studio — Standard transversal V3.0

Applicabilité : toutes les applications actuelles et futures de RMLala. Ce document définit des exigences, pas des fonctionnalités déjà implémentées.

## UX et identité
- Interface Premium/Pro responsive, FR/MG, thèmes, navigation lisible, formulaires/recherche/tri/filtres fonctionnels.
- Page À propos & Support : signature AppForge Studio, logo officiel, Made in Madagascar, support lalainaadclick@gmail.com, nom/version actuelle/édition/date, état réel de licence/cloud/sauvegarde, guide/FAQ, signalement diagnostic avec consentement, confidentialité/CGU/mentions légales, vérification des mises à jour.
- Ne PAS afficher l'historique des versions dans À propos (notamment WorshipFlow).
- Corriger les logos pixelisés au démarrage, générer icônes adaptatives et assets multi-densités.

## Données et sécurité
- Offline-first, Cloudflare Workers + D1 pour licences et synchronisation multi-appareils avec gestion des conflits, quotas, révocation, reprise réseau.
- Sauvegarde/restauration Google Drive SANS API OAuth : partage natif Android vers Drive, restauration via sélecteur de fichier, import/export local sur HTML/Windows. Chiffrement facultatif des exports, intégrité et confirmation avant restauration. Drive n'est PAS la synchronisation automatique.
- PIN obligatoire et biométrie prioritaire lorsqu'elle est disponible, PIN en secours. Jamais de PIN ou secret en clair.
- Licences : essai 3 jours non renouvelable abusivement, mensuelle, annuelle, à vie, propriétaire ; activation appareils, MVola manuel et vérification en ligne/hors ligne adaptée.

## Activation Cloud automatique par licence — sans compte client
- Après validation du paiement MVola et activation de la licence, le Cloudflare Worker vérifie côté serveur la licence, les droits Cloud et l'appareil autorisé. Le client n'a **aucun compte Cloud à créer, aucune organisation à choisir et aucun mot de passe Cloud à saisir**.
- Le Worker émet un jeton Cloud à durée de vie limitée, lié à la licence et à l'appareil autorisé, avec périmètre minimal. L'application active automatiquement la synchronisation Cloudflare D1 dès que les conditions de licence et de connectivité sont remplies.
- Le renouvellement des jetons exige une nouvelle vérification côté serveur : licence valide, appareils autorisés, quotas, expiration et révocation. Les jetons expirés ou révoqués ne doivent plus permettre l'accès aux données Cloud.
- Les jetons et identifiants sensibles ne doivent jamais être intégrés au HTML, aux APK, aux URL, aux journaux ou aux sauvegardes. Sur Android, utiliser un stockage sécurisé lié à l'appareil ; sur HTML/desktop, appliquer une stratégie adaptée aux limites de sécurité de la plateforme. Les clés de signature restent uniquement côté serveur.
- La synchronisation reste **Offline-First** : les opérations locales autorisées sont conservées hors connexion, puis synchronisées après retour du réseau et réauthentification si nécessaire. Prévoir le traitement des conflits, les quotas et la séparation des données entre licences.
- Les écrans clients ne doivent pas demander d'adresse e-mail, de mot de passe ou d'identifiant d'organisation **pour accéder à AppForge Cloud**. Une authentification métier distincte n'est autorisée que si l'application en a réellement besoin et si elle est clairement séparée de l'accès Cloud.
- Dans AppForge Studio, le propriétaire administre les licences, appareils, quotas, autorisations Cloud et révocations. Les statuts présentés au client doivent refléter les droits et la connexion réels, sans simuler une activation.
- Tests obligatoires avant validation fonctionnelle : activation après paiement validé, absence de compte Cloud, délivrance/expiration/renouvellement du jeton, appareil non autorisé, révocation, isolation des données, usage hors ligne, reprise réseau et synchronisation. Un audit statique V3 ne certifie pas ces parcours.

**Parcours cible : licence activée → jeton Cloud lié à la licence et à l'appareil → Cloud disponible automatiquement, sans compte Cloud client.**

## CI et livraison
- Une branche par travail, jamais directement main, PR et tests obligatoires ; fusion des PR techniques conformes préautorisée, demander autorisation pour changements fonctionnels majeurs.
- Avant CHAQUE Push de livraison et chaque livraison, redemander par cases à cocher : HTML autonome, EXE Windows portable, EXE installable optionnel, APK Debug, APK Release signé API23, APK Release signé API29, Cloud/sauvegarde/licences. Ne pas générer automatiquement tous les formats.
- Ne pas produire de PWA. Signature via secrets GitHub et clé commune. versionName identique Debug/Release, versionCode adapté.
- Finalisé seulement après tests, preuves, artefacts et validation utilisateur. Visibilité public/privé uniquement après accord.

## Audit
- Inventorier tous les dépôts RMLala et les nouveaux dépôts, distinguer prévu/développé/testé/validé ; signaler les écarts sans inventer de preuves.
- Audit sur chaque évolution, rapport hebdomadaire, mise à jour du suivi global et trois prochaines actions par application.
- Une CI de conformité ne doit pas prétendre valider biométrie, partage Drive, licence ou synchronisation sans tests d'intégration pertinents.

## Gouvernance des Pull Requests
Toute modification passe par une Pull Request (PR) avec contrôles CI vérifiés avant fusion. Une PR technique conforme peut être fusionnée selon l'autorisation générale ; une modification fonctionnelle majeure exige une nouvelle validation.
