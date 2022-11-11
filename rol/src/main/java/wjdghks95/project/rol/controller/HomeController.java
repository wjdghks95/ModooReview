package wjdghks95.project.rol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search/contents")
    public String searchContent(@RequestParam String keyword, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("keyword", keyword);
        return "redirect:/contents";
    }
}