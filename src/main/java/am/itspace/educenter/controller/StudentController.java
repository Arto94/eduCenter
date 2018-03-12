package am.itspace.educenter.controller;

import am.itspace.educenter.model.Lesson;
import am.itspace.educenter.model.User;
import am.itspace.educenter.model.UserType;
import am.itspace.educenter.repository.LessonRepository;
import am.itspace.educenter.repository.UserRepository;

import am.itspace.educenter.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/student")
    public String userPage(ModelMap map, @RequestParam("userId") int id) {
        User user = userRepository.findOne(id);
        map.addAttribute("user", user);
        map.addAttribute("lessons", user.getLessons());
        return "student";
    }

    @GetMapping(value = "/student/printLesson")
    public String printLesson(@RequestParam("lessonId") int id, ModelMap map) {
        map.addAttribute("lesson", lessonRepository.findOne(id));
        return "singleLesson";
    }

    @PostMapping(value = "/student/changeData")
    public String changeUserData(@RequestParam("id") int id, @ModelAttribute("user") User user) {
        user.setId(id);
        user.setLessons(lessonRepository.findAllByUsersIsContaining(user));
        user.setUserType(UserType.STUDENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/student?userId=" + user.getId();
    }

}
