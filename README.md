# Weather App

A simple Android Weather App is written in MVVM Architecture to showcase Architecture Components, UI and Unit test cases.

## Components
### This sample showcases the following Architecture Components:

* Coroutines
* ViewModels
* LiveData
* Data Binding
* Handling Lifecycles

### Other components:
* Navigation Component
* Dagger 2

### Test:
* Unit Test (Both Local and Robolectric)
* UI Test (using Espresso)

## Build and Run
Go to the project directory and run 
```bash
./gradlew assembleDebug
```

## Run Test
* Right click test class -> select run / run with coverage

-or-

* Go to the project directory and run following for local unit tests

```bash
./gradlew test
```

* Go to the project directory and run following for instrumented unit test

```bash
./gradlew connectedAndroidTest
```
