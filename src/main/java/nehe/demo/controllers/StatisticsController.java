package nehe.demo.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nehe.demo.Modals.Statistics;
import nehe.demo.Services.StatisticsService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/statistics")
public class StatisticsController
{ 
    private StatisticsService statisticsService;
    
    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
    
    @GetMapping("/")
    public Statistics getStatistics(@RequestParam(required = true) int id)
    { 
      Objects.requireNonNull(id);

      if(statisticsService.getStatistics(id) != null)
      {
        return statisticsService.getStatistics(id);
      }

      return null;
     
    }

    
}