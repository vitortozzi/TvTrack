# TvTrack


## Motivation
TvTrack is a sample application where you can browse movies. The main goal of this project is to apply and 
demonstrate some of the Android Architecture Components.

## How the app was built
- **Kotlin** as its language
- **MVVM** as its archiitecture
- **Livedata** for UI updates
- **Glide** for remote image processing
- **Shimmer** for loadings
- **Flow** and **Coroutines** for async tasks
- **Retrofit** for HTTP requests
- **Hilt** for dependency injection

## Remote DataSource
The Movie DataBase is a free API where you can browse a lot of information about movies and series.
The only requirement is that you you need to register in order to get an API key. More info [here](https://www.themoviedb.org/documentation/api)

## Architecture

![My First Board](https://user-images.githubusercontent.com/4106155/215655956-ea1ec4ba-767e-4ec6-b966-f6aa08d22bba.jpg)

## Try yourself!
### Installing the app
You can download the debug apk version and install on you own device. Download [here](https://drive.google.com/file/d/1YO0BZIn0BhdLA4Iu4o1DbMWXZQnadXv7/view?usp=share_link)
### Running through the Android Studio
Another option is to get a local build of the app. For that you'll need to clone this repo and just run the project.
**IMPORTANT**: You will need a TMBD api key, After you got registered on their platform, please put a file named apikey.properties on the root folder of this project. Inside this file, you should fill with you key like the examble below:
> TMDB_API_KEY="<your_api_key_here>"

## Screenshots
<img width="355" alt="image" src="https://user-images.githubusercontent.com/4106155/215658485-ce317a59-2a5d-4e19-8842-ca8eaabccdf6.png"> <img width="356" alt="image" src="https://user-images.githubusercontent.com/4106155/215658595-397ad2ef-0bae-4806-a18b-a7ec55ffdf93.png">

## DarkMode

<img width="356" alt="image" src="https://user-images.githubusercontent.com/4106155/215658707-528670ea-e9b3-4c47-a302-615799001bf1.png"> <img width="365" alt="image" src="https://user-images.githubusercontent.com/4106155/215658733-63073ecd-268c-4caa-af18-420f03c2cfe6.png">


