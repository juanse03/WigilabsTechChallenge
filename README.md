# MoviesApp - The Movie Database (TMDB) API

Meet MoviesApp, an Android app for browsing the most popular movies right now. It fetches the
current popular titles from The Movie Database (TMDB) API and displays them as a list with poster,
title, and a short description. Tapping a movie opens a detail screen with its featured backdrop
image, title, full description, vote average, release date, and popularity. The movie list is
cached locally, so previously loaded data is still available offline.

## Features

- Display the popular movies list based on the TMDB API.
- View a movie's detail screen (image, title, description, vote average, release date,
  popularity) on tap.
- Saves the movie list in a local database for offline access.

## Permissions

MoviesApp requires the following permissions:

- Internet

## Libraries Used

- Retrofit: Used for making HTTP requests to the TMDB API.
- Hilt: Employed for dependency injection to efficiently manage dependencies within the app.
- Gson: Utilized for parsing JSON responses retrieved from the API.
- Flow: Integrated for seamless handling of asynchronous operations.
- Mockito: Employed for mocking dependencies during unit testing, ensuring robust testing practices.
- Jetpack Compose: Utilized for crafting the user interface, facilitating modern UI development.
- Navigation Compose: Handles in-app navigation between the movies list and the movie detail
  screen.
- Coil: Incorporated for efficient asynchronous image loading and caching, enhancing user
  experience.
- Room: Integrated for local data storage and database management within the app, ensuring data
  persistence.

## Architecture Overview

The architecture of this project is designed to ensure a clear separation of concerns
and maintainability.
It consists of the following layers:

### Data Layer

The data layer plays a crucial role in managing data operations, including fetching data from
external or local sources, and implementing caching mechanisms. It encompasses:

- DataSource: Interfaces designed to interact with either the API or local database, facilitating
  data retrieval and storage.
- DTO (MoviesApiResponse / MovieDto): Objects that serve as representations of data exchanged
  between the application and the TMDB API, ensuring seamless data transfer.
- Repository Implementation: Concrete implementations of repository interfaces responsible for
  orchestrating interactions with data sources, ensuring efficient data management and retrieval.
- Mapper: Responsible for transforming DTOs into domain objects, facilitating seamless data
  presentation and interaction within the application's domain.

### Domain Layer

The domain layer contains the business logic of the application and serves as an intermediary
between the data and UI layers. It includes:

- Entities: Objects that represent business entities in the application. These entities are
  typically mapped from the DTOs received from the data layer.
- Repository Interfaces: Interfaces defining the contract for interacting with data sources. These
  interfaces are implemented by classes in the data layer.
- Use Cases: Classes that encapsulate the application's business logic and represent specific
  actions or operations.

### UI Layer

The UI layer is responsible for presenting data to the user and handling user interactions. It
includes:

- UI Models: Objects that represent the data to be displayed on the user interface. These UI models
  are typically mapped from entities in the domain layer.
- Mappers: Classes responsible for mapping entities from the domain layer to UI models.
- ViewModels: Components that manage UI-related data and state, exposing it to the UI layer.
  ViewModel instances use MutableStateFlow to emit pre-defined states for each screen, ensuring
  seamless updates to UI elements.
- Navigation: A `NavHost` in `MainActivity` routes between the movies list screen and the movie
  detail screen, passing the selected movie's id as a navigation argument.
