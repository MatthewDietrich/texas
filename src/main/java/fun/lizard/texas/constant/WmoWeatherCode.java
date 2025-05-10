package fun.lizard.texas.constant;

import lombok.Getter;

@Getter
public enum WmoWeatherCode {
    CLEAR_SKY(0, "Clear sky", "wi-day-sunny"),
    MAINLY_CLEAR(1, "Mainly clear", "wi-day-sunny"),
    PARTLY_CLOUDY(2, "Partly cloudy", "wi-day-cloudy"),
    OVERCAST(3, "Overcast", "wi-cloudy"),
    FOG(45, "Fog", "wi-fog"),
    DEPOSITING_RIME_FOG(48, "Depositing rime fog", "wi-fog"),
    DRIZZLE_LIGHT(51, "Light drizzle", "wi-sprinkle"),
    DRIZZLE_MODERATE(53, "Moderate drizzle", "wi-sprinkle"),
    DRIZZLE_DENSE(55, "Dense drizzle", "wi-showers"),
    FREEZING_DRIZZLE_LIGHT(56, "Light freezing drizzle", "wi-sprinkle"),
    FREEZING_DRIZZLE_DENSE(57, "Dense freezing drizzle", "wi-showers"),
    RAIN_SLIGHT(61, "Slight rain", "wi-showers"),
    RAIN_MODERATE(63, "Moderate rain", "wi-rain"),
    RAIN_HEAVY(65, "Heavy rain", "wi-rain"),
    FREEZING_RAIN_LIGHT(66, "Light freezing rain", "wi-showers"),
    FREEZING_RAIN_HEAVY(67, "Heavy freezing rain", "wi-rain"),
    SNOW_SLIGHT(71, "Slight snow", "wi-snow"),
    SNOW_MODERATE(73, "Moderate snow", "wi-snow"),
    SNOW_HEAVY(75, "Heavy snow", "wi-snow"),
    SNOW_GRAINS(77, "Snow grains", "wi-snow"),
    RAIN_SHOWERS_SLIGHT(80, "Slight rain showers", "wi-showers"),
    RAIN_SHOWERS_MODERATE(81, "Moderate rain showers", "wi-showers"),
    RAIN_SHOWERS_VIOLENT(82, "Violent rain showers", "wi-showers"),
    SNOW_SHOWERS_SLIGHT(85, "Slight snow showers", "wi-snow"),
    SNOW_SHOWERS_HEAVY(86, "Heavy snow showers", "wi-snow"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "Thunderstorm", "wi-thunderstorm"),
    THUNDERSTORM_WITH_HAIL_SLIGHT(96, "Thunderstorm with slight hail", "wi-thunderstorm"),
    THUNDERSTORM_WITH_HAIL_HEAVY(99, "Thunderstorm with heavy hail", "wi-thunderstorm"),
    UNKNOWN(-1, "Unknown weather", "wi-alien");

    private final int code;
    private final String description;
    private final String iconClass;

    WmoWeatherCode(int code, String description, String iconClass) {
        this.code = code;
        this.description = description;
        this.iconClass = iconClass;
    }

    public static WmoWeatherCode fromCode(int code) {
        for (WmoWeatherCode weatherCode : values()) {
            if (weatherCode.code == code) {
                return weatherCode;
            }
        }
        return UNKNOWN;
    }
}