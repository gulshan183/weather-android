package com.example.myapplication.ui.weather.current

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */
data class CurrentWeatherModel(
    val cityId: Long = 0,
    val cityName: String? = null,
    val maxMinTemp: String? = null,
    val temp: String? = null,
    val windSpeed: String? = null,
    val description: String? = null,
    val iconCode: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (other !is CurrentWeatherModel) {
            return false
        }
        return cityId == other.cityId
    }

    override fun hashCode(): Int {
        return cityId.hashCode()
    }

    /**
     * Compare the contents of the two [CurrentWeatherModel] objects
     *
     * @param newItem
     * @return true if contents are same, false otherwise.
     */
    fun areContentsTheSame(newItem: CurrentWeatherModel): Boolean {
        return cityName == newItem.cityName &&
                maxMinTemp == newItem.maxMinTemp &&
                temp == newItem.temp &&
                windSpeed == newItem.windSpeed &&
                description == newItem.description

    }
}