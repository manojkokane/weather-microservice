package com.terena.interview.solution.weather.controllers.util;

import com.terena.interview.solution.weather.common.model.CurrentWeatherDataDTO;
import com.terena.interview.solution.weather.models.WeatherResponse;
import com.terena.interview.solution.weather.services.util.ServiceUtil;

public class MapperUtil {

    public static WeatherResponse mapToWeatherResponse(final CurrentWeatherDataDTO currentWeatherDataDTO) {

        return new WeatherResponse()
                .temp(currentWeatherDataDTO.getWeatherDataDTO().getTemp())
                .pressure(currentWeatherDataDTO.getWeatherDataDTO().getAirPressure())
                .umbrella(ServiceUtil.canTakeUmbrella(currentWeatherDataDTO.getWeatherConditionDTOS()));
    }
}
