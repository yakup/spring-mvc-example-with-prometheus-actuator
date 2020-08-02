package com.rudysysu.sample.metrics.controller;

import com.rudysysu.sample.metrics.model.User;
import com.rudysysu.sample.metrics.service.UserService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Timed("user_get_time")
    @Counted("user_get_count")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @ResponseBody
    @Timed("user_post_time")
    @Counted("user_post_count")
    public ResponseEntity<String> saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return ResponseEntity.ok().body("User saved! ID:" + user.getId());
    }
}
