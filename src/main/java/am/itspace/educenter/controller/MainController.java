package am.itspace.educenter.controller;

import am.itspace.educenter.model.User;
import am.itspace.educenter.model.UserType;
import am.itspace.educenter.security.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(ModelMap map) {
        map.addAttribute("user", new User());
        return "index";
    }

    @GetMapping(value = "/")
    public String redirectHome() {
        return "redirect:/home";
    }


    @GetMapping(value = "/login")
    public String login() {
        return "redirect:/home";
    }


    @GetMapping(value = "/loginsucces")
    public String userOrAdminPage() {
        CurrentUser principal = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole() == UserType.MANAGER) {
            return "redirect:/manager";
        }
        return "redirect:/student?userId=" + principal.getId();
    }

}
