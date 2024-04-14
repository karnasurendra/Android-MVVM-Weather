# Weather App


## Overview
The Weather App is a mobile application developed to provide users with current weather information and a 4-day forecast using data from the OpenWeatherMap API.

## Features

   - Display current weather conditions (temperature, humidity, wind speed, etc.).
   - Show a 4-day forecast with details such as temperature, weather condition, and date.
   - MVVM architecture for separation of concerns and maintainability.
   - Retrofit library for handling API requests and responses.

## Installation

   - Clone the repository: git clone <repository_url>
   - Open the project in Android Studio.
   - Build and run the app on an Android device or emulator.

## Configuration

Before running the app, make sure to configure the OpenWeatherMap API key in the AppConstants.java file. Replace YOUR_APP_ID with your actual API key obtained from OpenWeatherMap.

## Dependencies

    Retrofit: A type-safe HTTP client for Android and Java.
    Gson: A Java library for JSON serialization and deserialization.
    Lifecycle components: Android Architecture Components for managing UI-related data in a lifecycle-conscious way.

## Usage

    Launch the app on your device or emulator.
    The app will display the current weather information.
    Swipe or navigate to view the 4-day forecast.

## Architecture

The Weather App follows the MVVM (Model-View-ViewModel) architecture pattern:

    Model: Represents the data and business logic. In this app, it includes data classes for weather information and forecast.
    View: Represents the UI components. Activities and fragments are the views in this app.
    ViewModel: Acts as a bridge between the Model and View. It provides data to the UI and handles user interactions.

## API Calls

The app makes two API calls using Retrofit:

    Current Weather API Call: Retrieves the current weather data for a specific location.
        Endpoint: https://api.openweathermap.org/data/2.5/weather
        Parameters: lat, lon, appid (API key)
    Forecast Weather API Call: Fetches the 4-day weather forecast for a location.
        Endpoint: https://api.openweathermap.org/data/2.5/forecast
        Parameters: lat, lon, appid (API key)

## Libraries Used

    Retrofit: https://square.github.io/retrofit/
    Gson: https://github.com/google/gson
    Android Lifecycle Components: https://developer.android.com/topic/libraries/architecture/lifecycle

![Alt Text](https://github.com/karnasurendra/WeatherInfo/blob/master/WhatsApp%20Image%202024-04-14%20at%2013.42.33.jpeg)

![Alt Text](https://github.com/karnasurendra/WeatherInfo/blob/master/WhatsApp%20Image%202024-04-14%20at%2013.42.34.jpeg)
