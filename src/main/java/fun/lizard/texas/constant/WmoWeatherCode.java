package fun.lizard.texas.constant;

import lombok.Getter;

@Getter
public enum WmoWeatherCode {
    CLEAR_SKY(0, "Clear sky"),
    MAINLY_CLEAR(1, "Mainly clear"),
    PARTLY_CLOUDY(2, "Partly cloudy"),
    OVERCAST(3, "Overcast"),
    FOG(45, "Fog"),
    DEPOSITING_RIME_FOG(48, "Fog: Depositing rime"),
    DRIZZLE_LIGHT(51, "Drizzle: Light intensity"),
    DRIZZLE_MODERATE(53, "Drizzle: Moderate intensity"),
    DRIZZLE_DENSE(55, "Drizzle: Dense intensity"),
    FREEZING_DRIZZLE_LIGHT(56, "Freezing Drizzle: Light intensity"),
    FREEZING_DRIZZLE_DENSE(57, "Freezing Drizzle: Dense intensity"),
    RAIN_SLIGHT(61, "Rain: Slight intensity"),
    RAIN_MODERATE(63, "Rain: Moderate intensity"),
    RAIN_HEAVY(65, "Rain: Heavy intensity"),
    FREEZING_RAIN_LIGHT(66, "Freezing Rain: Light intensity"),
    FREEZING_RAIN_HEAVY(67, "Freezing Rain: Heavy intensity"),
    SNOW_SLIGHT(71, "Snow fall: Slight intensity"),
    SNOW_MODERATE(73, "Snow fall: Moderate intensity"),
    SNOW_HEAVY(75, "Snow fall: Heavy intensity"),
    SNOW_GRAINS(77, "Snow grains"),
    RAIN_SHOWERS_SLIGHT(80, "Rain showers: Slight"),
    RAIN_SHOWERS_MODERATE(81, "Rain showers: Moderate"),
    RAIN_SHOWERS_VIOLENT(82, "Rain showers: Violent"),
    SNOW_SHOWERS_SLIGHT(85, "Snow showers: Slight"),
    SNOW_SHOWERS_HEAVY(86, "Snow showers: Heavy"),
    THUNDERSTORM_SLIGHT_MODERATE(95, "Thunderstorm: Slight or moderate"),
    THUNDERSTORM_WITH_HAIL_SLIGHT(96, "Thunderstorm with slight hail"),
    THUNDERSTORM_WITH_HAIL_HEAVY(99, "Thunderstorm with heavy hail"),
    UNKNOWN(-1, "Unknown weather");

    private final int code;
    private final String description;

    WmoWeatherCode(int code, String description) {
        this.code = code;
        this.description = description;
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