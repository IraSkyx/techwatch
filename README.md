# TechWatch

TechWatch permet de prendre avantage de l'API https://newsapi.org/ en modelant ses liens pour se créer des fils d'actualités personnalisés.  
L'application cible l'API 16 minimum.

## Conception

![](https://i.imgur.com/Wf1XrJa.png)

Dans ce schéma de conception, on propose de respecter le pattern MVVM sous Android mais en ajoutant une couche intermédiaire Repository qui va faire le lien entre la partie Network et la partie Database. Le Repository ne fait que déléguer les tâches. Pour la base de données, à la DAO et si il ne sait pas entre le Network ou Database alors c'est le Mediator qui décidera s'il doit faire appel au Service ou bien à la Database. 

## Outils d'AndroidX et Jetpack

- ConstraintLayout + SwipeRefreshLayout
- RecyclerView
- Material Design
- Room database 
- ViewModel et LiveData
- Data Binding
- Paging Library 3.0
- Navigation

## Autres outils

- Coroutines de Kotlin
- Swipe to delete
- Preload de font google
- Retrofit pour les requêtes HTTP, la conversion de données et le logging avec okhttp3
- Picasso pour le chargement, l'affichage et la mise en cache d'image 
