package com.jobtrack.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Controller
public class ResumePageController {

    private static final Set<String> SECTIONS = Set.of(
            "basic", "work", "skills", "education", "cert", "pr"
    );

    @GetMapping("/")
    public String index() {
        return "redirect:/resume/basic";
    }

    @GetMapping("/resume/{section}")
    public String section(@PathVariable String section, Model model) {
        if (!SECTIONS.contains(section)) {
            return "redirect:/resume/basic";
        }
        model.addAttribute("activeSection", section);
        model.addAttribute("years", years());
        model.addAttribute("months", months());
        model.addAttribute("days", days());
        return "resume/" + section;
    }

    private static List<String> years() {
        return java.util.stream.IntStream.rangeClosed(0, 39)
                .mapToObj(i -> String.valueOf(2024 - i))
                .toList();
    }

    private static List<String> months() {
        return java.util.stream.IntStream.rangeClosed(1, 12)
                .mapToObj(i -> String.format("%02d", i))
                .toList();
    }

    private static List<String> days() {
        return java.util.stream.IntStream.rangeClosed(1, 31)
                .mapToObj(i -> String.format("%02d", i))
                .toList();
    }
}
