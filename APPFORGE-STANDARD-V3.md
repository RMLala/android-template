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
