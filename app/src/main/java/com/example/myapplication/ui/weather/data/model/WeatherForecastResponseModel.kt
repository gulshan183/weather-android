package com.example.myapplication.ui.weather.data.model

/**
 * Created by Gulshan Ahluwalia on 2020-02-04.
 */


data class WeatherForecastResponseModel (
    val cod: String? = null,
    val message: Double? = null,
    val cnt: Long? = null,
    val list: List<ListElement>? = null,
    val city: City? = null
)

data class City (
    val id: Long? = null,
    val name: String? = null,
    val coord: Coord? = null,
    val country: String? = null
)

data class ListElement (
    val dt: Long? = null,
    val main: MainClass? = null,
    val weather: List<Weather>? = null,
    val clouds: Clouds? = null,
    val wind: Wind? = null,
    val sys: Sys? = null,
    val dtTxt: String? = null,
    val rain: Rain? = null,
    val snow: Rain? = null
)

data class MainClass (
    val temp: Double? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
    val pressure: Double? = null,
    val seaLevel: Double? = null,
    val grndLevel: Double? = null,
    val humidity: Long? = null,
    val tempKf: Double? = null
)

data class Rain (
    val the3H: Double? = null
)

data class Sys (
    val pod: Pod? = null
)

enum class Pod {
    D,
    N
}

enum class Description {
    BrokenClouds,
    ClearSky,
    FewClouds,
    LightRain,
    ModerateRain
}

enum class MainEnum {
    Clear,
    Clouds,
    Rain
}

