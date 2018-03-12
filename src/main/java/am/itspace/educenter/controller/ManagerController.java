package am.itspace.educenter.controller;


import am.itspace.educenter.mail.EmailServiceImpl;
import am.itspace.educenter.model.Lesson;
import am.itspace.educenter.model.User;
import am.itspace.educenter.model.UserType;
import am.itspace.educenter.repository.LessonRepository;
import am.itspace.educenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ManagerController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private Random random;

    @GetMapping(value = "/manager")
    public String userPage(ModelMap map) {
        map.addAttribute("lesson", new Lesson());
        map.addAttribute("user", new User());
        map.addAttribute("users", userRepository.findAll());
        map.addAttribute("lessons", lessonRepository.findAll());
        return "manager";
    }

    @PostMapping(value = "/manager/addLesson")
    public String addLesson(@ModelAttribute("lesson") Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/manager";
    }

    @PostMapping(value = "/manager/addStudent")
    public String addLesson(@ModelAttribute("student") User user) {
        user.setUserType(UserType.STUDENT);
        int nextInt = random.nextInt();
        user.setPassword(passwordEncoder.encode(String.valueOf(nextInt)));
        userRepository.save(user);
        String message = String.format("Hi %s,You are successfully registered , Your email is %s, and Your password is %s", user.getName(), user.getEmail(), nextInt);
        emailService.sendSimpleMessage(user.getEmail(), "Welcome", message);
        return "redirect:/manager";
    }

    @GetMapping("/manager/deleteLesson")
    public String deleteLesson(@RequestParam("lessonId") int id) {
        Lesson lessonById = lessonRepository.findOne(id);
        lessonRepository.delete(lessonById);
        return "redirect:/manager";
    }

    @GetMapping("/manager/changeLesson")
    public String changeLesson(@RequestParam("lessonId") int id, ModelMap map) {
        map.addAttribute("lesson", lessonRepository.findOne(id));
        return "changeLesson";
    }

    @PostMapping("/manager/saveLesson")
    public String saveLesson(@ModelAttribute("lesson") Lesson lesson, @RequestParam("id") int id) {
        lesson.setId(id);
        lessonRepository.save(lesson);
        return "redirect:/manager";
    }

    @Bean
    public Random getRandom() {
        return new Random();
    }

}
