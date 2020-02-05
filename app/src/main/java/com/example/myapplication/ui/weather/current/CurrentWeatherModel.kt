package com.example.myapplication.ui.weather.current

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */
data class CurrentWeatherModel(
    val cityId: Long = 0,
    val cityName: String? = null,
    val minTemp: String? = null,
    val maxTemp: String? = null,
    val windSpeed: String? = null,
    val description: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (other !is CurrentWeatherModel) {
            return false
        }
        return cityId == other.cityId
    }

    override fun hashCode(): Int {
        var result = cityId.hashCode()
        result = 31 * result + (cityName?.hashCode() ?: 0)
        result = 31 * result + (minTemp?.hashCode() ?: 0)
        result = 31 * result + (maxTemp?.hashCode() ?: 0)
        result = 31 * result + (windSpeed?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }

    fun areContentsTheSame(newItem: CurrentWeatherModel): Boolean {
        return cityName == newItem.cityName &&
                minTemp == newItem.minTemp &&
                maxTemp == newItem.maxTemp &&
                windSpeed == newItem.windSpeed &&
                description == newItem.description

    }
}