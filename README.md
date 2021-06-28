# TechWatch

TechWatch allows you to take advantage of the https://newsapi.org/ API by shaping your links to create custom news feeds.  
The application targets the 16 API minimum.

## Design

![](https://i.imgur.com/fpmMOET.png)

In this design scheme, we propose to respect the MVVM pattern under Android but by adding an intermediate layer Repository which will make the link between the Network part and the Database part. The Repository only delegates tasks. For the database, to the DAO and if it doesn't know between the Network or Database then it's the Mediator that will decide if it should call the Service or the Database. 

## AndroidX and Jetpack tools

- ConstraintLayout + SwipeRefreshLayout
- RecyclerView
- Material Design
- Room database 
- ViewModel and LiveData
- Data Binding
- Paging Library 3.0
- Navigation

## Other tools

- Kotlin Coroutines
- Swipe to delete
- Google font preload
- Retrofit for HTTP requests, data conversion and logging with okhttp3
- Picasso for image loading, displaying and caching 
