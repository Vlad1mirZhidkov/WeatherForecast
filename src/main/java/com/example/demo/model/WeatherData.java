package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    @Getter
    @JsonProperty("days")
    private List<Day> days;

    @Getter
    @JsonProperty("currentConditions")
    private CurrentTime currentTime;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Day{
        @Getter
        @JsonProperty("datetime")
        private String datetimeDay;

        @Getter
        @JsonProperty("hours")
        private List<Hour> hours;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hour{
        @Getter
        @JsonProperty("datetime")
        private String datetime;

        @Getter
        @JsonProperty("temp")
        private int temp;

        @Getter
        @JsonProperty("precitype")
        private String precipType;

        @Getter
        @JsonProperty("windspeed")
        private double windspeed;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentTime{
        @Getter
        @JsonProperty("datetime")
        private String datetime;

        @Getter
        @JsonProperty("temp")
        private int temp;

        @Getter
        @JsonProperty("feelslike")
        private int feelsLike;

        @Getter
        @JsonProperty("humidity")
        private double humidity;

        @Getter
        @JsonProperty("precitype")
        private String precipType;

        @Getter
        @JsonProperty("windspeed")
        private double windspeed;

        @Getter
        @JsonProperty("winddir")
        private double winddir;

        @Getter
        @JsonProperty("solarradiation")
        private double solarradiation;
    }

    public List<Day> getDays() {
        return days;
    }
}