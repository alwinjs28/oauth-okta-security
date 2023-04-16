package org.ma.okta.security.controller;

import org.ma.okta.security.config.JwtGeneratorInterface;
import org.ma.okta.security.exception.UserNotFoundException;
import org.ma.okta.security.model.User;
import org.ma.okta.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("oauth/okta/authentication")
public class UserController {
    private UserService userService;
    private JwtGeneratorInterface jwtGenerator;

    @Autowired
    public UserController(UserService userService, JwtGeneratorInterface jwtGenerator){
        this.userService=userService;
        this.jwtGenerator=jwtGenerator;
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> postUser(@RequestBody User user){
        try{
            if(!user.getUserName().contains("@") || !user.getUserName().contains(".com")) {
                throw new UserNotFoundException("Invalid User Name");
            }
            if(user.getUserName() == null || user.getPassword() == null) {
                throw new UserNotFoundException("UserName or Password is Empty");
            }
            if(user.getPassword().length() < 8) {
                throw new UserNotFoundException("Password minimum length should be 8");
            }
            User userData = userService.getUserByName(user.getUserName());
            if(userData == null){
                userService.saveUser(user);
            } else {
                throw new UserNotFoundException("UserName already registered!");
            }

            Map<String, String> response = new HashMap<>();
            response.put("status", "Success");
            response.put("message", "UserName and Password successfully registered in Okta!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/user/get-token")
    public ResponseEntity<?> loginUser(@RequestHeader("userName") String userName,
                                       @RequestHeader String password) {
        try {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            if(!user.getUserName().contains("@") || !user.getUserName().contains(".com")) {
                throw new UserNotFoundException("Invalid User Name");
            }
            if(user.getUserName() == null || user.getPassword() == null) {
                throw new UserNotFoundException("UserName or Password is Empty");
            }
            User userData = userService.findByUserNameAndPassword(user.getUserName(), user.getPassword());
            if(userData == null){
                throw new UserNotFoundException("Unknown UserName or Password");
            }
            return new ResponseEntity<>(jwtGenerator.generateToken(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
