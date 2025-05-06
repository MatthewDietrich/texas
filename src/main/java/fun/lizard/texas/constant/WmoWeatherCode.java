package fun.lizard.texas.constant;

import lombok.Getter;

@Getter
public enum WmoWeatherCode {
    CLEAR_SKY(0, "Clear sky", "wi-day-sunny"),
    MAINLY_CLEAR(1, "Mainly clear", "wi-day-sunny"),
    PARTLY_CLOUDY(2, "Partly cloudy", "wi-day-cloudy"),
    OVERCAST(3, "Overcast", "wi-cloudy"),
    FOG(45, "Fog", "wi-fog"),
    DEPOSITING_RIME_FOG(48, "Fog: Depositing rime", "wi-fog"),
    DRIZZLE_LIGHT(51, "Drizzle: Light intensity", "wi-sprinkle"),
    DRIZZLE_MODERATE(53, "Drizzle: Moderate intensity", "wi-sprinkle"),
    DRIZZLE_DENSE(55, "Drizzle: Dense intensity", "wi-showers"),
    FREEZING_DRIZZLE_LIGHT(56, "Freezing Drizzle: Light intensity", "wi-sprinkle"),
    FREEZING_DRIZZLE_DENSE(57, "Freezing Drizzle: Dense intensity", "wi-showers"),
    RAIN_SLIGHT(61, "Rain: Slight intensity", "wi-showers"),
    RAIN_MODERATE(63, "Rain: Moderate intensity", "wi-rain"),
    RAIN_HEAVY(65, "Rain: Heavy intensity", "wi-rain"),
    FREEZING_RAIN_LIGHT(66, "Freezing Rain: Light intensity", "wi-showers"),
    FREEZING_RAIN_HEAVY(67, "Freezing Rain: Heavy intensity", "wi-rain"),
    SNOW_SLIGHT(71, "Snow fall: Slight intensity", "wi-snow"),
    SNOW_MODERATE(73, "Snow fall: Moderate intensity", "wi-snow"),
    SNOW_HEAVY(75, "Snow fall: Heavy intensity", "wi-snow"),
    SNOW_GRAINS(77, "Snow grains", "wi-snow"),
    RAIN_SHOWERS_SLIGHT(80, "Rain showers: Slight", "wi-showers"),
    RAIN_SHOWERS_MODERATE(81, "Rain showers: Moderate", "wi-showers"),
    RAIN_SHOWERS_VIOLENT(82, "Rain showers: Violent", "wi-showers"),
    SNOW_SHOWERS_SLIGHT(85, "Snow showers: Slight", "wi-snow"),
    SNOW_SHOWERS_HEAVY(86, "Snow showers: Heavy", "wi-snow"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "Thunderstorm: Slight or moderate", "wi-thunderstorm"),
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