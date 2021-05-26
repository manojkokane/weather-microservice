package com.terena.interview.solution.weather.repositories;

import com.terena.interview.solution.weather.common.entity.WeatherQueryResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherQueryResponseRepository extends JpaRepository<WeatherQueryResult, Long> {

    List<WeatherQueryResult> findRecentResponsesByQueryCity(String city, Pageable pageable);
}
