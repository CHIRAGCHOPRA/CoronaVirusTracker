package com.personal.coronavirustracker.controllers;

import com.personal.coronavirustracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    CovidDataService covidDataService = new CovidDataService();

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("locationStats",covidDataService.getAllStats());
        model.addAttribute("total",covidDataService.getAllStats().stream().mapToInt(stat -> stat.getLatestTotalCases()).sum());
        model.addAttribute("totalNewCases",covidDataService.getAllStats().stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum());
        return "home";
    }
}
