package com.example.article.controller;

import com.example.article.entity.User;
import com.example.article.payload.ApiResponse;
import com.example.article.payload.SignIn;
import com.example.article.payload.SignUp;
import com.example.article.payload.UserDto;
import com.example.article.repository.UserRepository;
import com.example.article.secret.CurrentUser;
import com.example.article.servise.UserService;
import com.example.article.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
@Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public HttpEntity<ApiResponse> userAdd(@RequestBody SignUp signUp) {
        ApiResponse apiResponse = userService.register(signUp);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
                                                                          .equals("Saved") ? 201 : 202 : 409)
                             .body(apiResponse);
    }

    // bu userni to'liq ma'lumotlarini kiritish uchun
    @PostMapping("/buildProfile")
    public HttpEntity<ApiResponse> buildProfile(@CurrentUser User user, @RequestBody SignUp signUp) {
        ApiResponse apiResponse = userService.buildProfile(user, signUp);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
                                                                          .equals("Saved") ? 201 : 202 : 409)
                             .body(apiResponse);
    }


    @PostMapping("/edit/{id}")
    public HttpEntity<ApiResponse> edit(@CurrentUser User user, @RequestBody SignUp userDto) {
        ApiResponse apiResponse = userService.edit(user, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
                                                                          .equals("Edit") ? 201 : 202 : 409)
                             .body(apiResponse);

    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody SignIn signIn) {
        return ResponseEntity.ok(userService.login(signIn));
    }


    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@RequestParam UUID id) {
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
                                                                          .equals("Delete") ? 201 : 202 : 409)
                             .body(apiResponse);
    }

    @GetMapping("/me")
    public HttpEntity<?> me(@CurrentUser User user){
        User user1 = userRepository.findById(user.getId()).get();
        return ResponseEntity.status(user!=null?200:409).body(user1);
    }

    @PostMapping("/eddEmployee")
    public HttpEntity<ApiResponse> eddEmployee(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.saveAndEditUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
                                                                          .equals("Saved") ? 201 : 202 : 409)
                             .body(apiResponse);
    }

    @PostMapping("/changeUserActive/{id}")
    public ApiResponse changeUserActive(@PathVariable UUID id, @CurrentUser User user, @RequestBody boolean status) {
        return new ApiResponse("change", true, userService.changeUserActive(id, status, user));
    }

    @PostMapping("/searchUser")
    public HttpEntity<?> searchUsers(@RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)Integer page,
                                     @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE)Integer size,
                                     @RequestParam  String search,
                                     @RequestParam Integer  roles_id,
                                     @RequestParam(defaultValue = "true")  boolean enabled
    ) throws IllegalAccessException {
        return ResponseEntity.ok(userService.searchUser(search,roles_id,enabled,page,size));
    }


}
