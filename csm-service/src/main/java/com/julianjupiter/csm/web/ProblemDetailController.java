package com.julianjupiter.csm.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Julian Jupiter
 */
@Controller
@RequestMapping("/problem-detail")
public class ProblemDetailController {
    @GetMapping("/types")
    public String type(@RequestParam(value = "http-status", required = false) Integer httpStatus,
                       @RequestParam(value = "error-code", required = false) Integer errorCode,
                       Model model) {
        model.addAttribute("page", "Problem Detail Types");

        if (httpStatus != null) {
            model.addAttribute("httpStatus", httpStatus);
            return "problem-detail/type";
        }

        return "problem-detail/index";
    }
}
