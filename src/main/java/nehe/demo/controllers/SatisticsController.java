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
@RequestMapping("/api")
public class SatisticsController
{ 
    private StatisticsService statisticsService;
    
    @Autowired
    public SatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
    
    @GetMapping("/statistics")
    public Statistics getStatistics(@RequestParam(required = true) int adminId)
    {
      Objects.requireNonNull(adminId);

      if(statisticsService.getStatistics(adminId) != null)
      {
        return statisticsService.getStatistics(adminId);
      }

      return null;
     
    }

    
}