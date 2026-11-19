# AppForge V3 — Quotas Cloud R2 et changement de plan

Statut : exigence validée, non implémentée. Applicable aux applications actuelles et futures qui utilisent des fichiers.

## Architecture indépendante du plan
- D1 : données et métadonnées ; R2 privé : fichiers ; Worker central : autorisations, quotas, transferts et restauration.
- Aucune URL, clé R2 ou secret dans les applications. Ne pas lier la logique métier au plan Free ou Paid.
- Passage de Free à Paid : aucun changement de format de données, de contrat client ni d'APK requis si les bindings et le Worker restent identiques. Contrôler les plafonds, la facturation et les limites de chaque produit séparément.

## Interface cliente « Licence & Cloud »
- Afficher l'espace utilisé, le quota attribué par licence, le disponible, le nombre de fichiers, la ventilation images/audio/vidéo/documents, la date de mesure et le statut de synchronisation.
- Avertissements à 80 %, 90 % et 100 %. Ne jamais confondre quota par licence et quota global du compte Cloudflare.
- Source des valeurs : API authentifiée du Worker, jamais des constantes dans le client. Si indisponible, afficher la dernière mesure datée comme non actualisée.
- En cas de dépassement, bloquer uniquement les nouveaux transferts concernés, conserver les fichiers locaux et permettre lecture/restauration selon les droits réels.

## Business Manager propriétaire
- Vue consolidée par application, licence et type de fichier ; volume, objets, opérations, tendances et alertes.
- Modification des quotas par licence uniquement par un administrateur autorisé ; audit des changements.
- Les métriques de facturation R2 globales ne sont affichées que si une intégration Cloudflare autorisée les fournit ; distinguer données mesurées et estimées.

## Contrat API cible (à spécifier après audit)
- Route authentifiée de lecture du quota licence et de sa ventilation ; droits vérifiés côté Worker.
- Mise à jour atomique ou réservations de capacité lors des uploads concurrents ; correction après échec, suppression, restauration et nettoyage.
- Comptage des objets actifs et des versions conservées ; déduplication et conservation documentées ; mesures cohérentes avec D1/R2.
- Quotas R2 et facturation Cloudflare indépendants : les 10 Go-mois gratuits éventuels sont une franchise globale, pas 10 Go par licence ni une limite stricte de stockage.

## Restauration unifiée
- Restaurer D1 + R2 avec fusion sans écrasement automatique, contrôle des empreintes, manifestes validés, reprise et protection des fichiers locaux.
- Les quotas de restauration et d'upload sont traités séparément. Ne jamais supprimer des données locales en raison d'un quota atteint.

## Recette obligatoire
1. Affichage exact par licence et par catégorie ; isolation interlicence.
2. Upload et suppression concurrents ; échec réseau ; reprise ; idempotence.
3. Alertes 80/90/100 % et refus contrôlé d'upload au quota.
4. Restauration complète sans écrasement, y compris quota saturé.
5. Passage Free → Paid simulé : aucune modification client ; validation des droits et de la configuration Worker.
6. Contrôle des métriques réelles, tests de non-régression D1, audit sécurité et validation avec compte de test.

Aucun test de production, déploiement R2 ou activation réelle n'est attesté par ce document.
