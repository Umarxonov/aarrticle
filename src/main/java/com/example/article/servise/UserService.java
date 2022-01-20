package com.example.article.servise;


import com.example.article.entity.InformationArticle;
import com.example.article.entity.User;
import com.example.article.entity.enums.RoleName;
import com.example.article.entity.enums.Watdou;
import com.example.article.payload.ApiResponse;
import com.example.article.payload.SignIn;
import com.example.article.payload.SignUp;
import com.example.article.payload.UserDto;
import com.example.article.repository.*;
import com.example.article.secret.JwtProvider;
import com.example.article.utils.CommonUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    PushNotificationService pushNotificationService;


    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    InformationArticleRepository informationArticleRepository;


    public ApiResponse register(SignUp signUp) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(signUp.getPhoneNumber());
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setPhoneNumber(signUp.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(signUp.getPassword()));
            user.setRoles(roleRepository.findAllByRoleNameIn(Collections.singleton(RoleName.ROLE_USER.name())));
            userRepository.save(user);
            return new ApiResponse("Saved", true, jwtProvider.generateJwtToken(user));
        }
        return new ApiResponse("User is already exist", false);
    }


    // bu userni to'liq ma'lumotlarini kiritish uchun
    public ApiResponse buildProfile(User user, SignUp signUp) {
        user.setLastName(signUp.getLastName());
        user.setFirstName(signUp.getFirstName());
        user.setFatherName(signUp.getFatherName());
        user.setEmail(signUp.getEmail());
//                    user.setPhotos(Collections.singletonList(attachmentRepository.getById(signUp.getPhotoId())));

        userRepository.save(user);


        return new ApiResponse("Saved   ", true);
    }


    public ApiResponse saveAndEditUser(UserDto userDto) {
        try {
            Optional<User> userOptional = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
            User user = new User();
            if (userDto.getId() != null) {
                user = userRepository.getById(userDto.getId());
            }
            if (!userOptional.isPresent()) {
                user.setLastName(userDto.getLastName());
                user.setFirstName(userDto.getLastName());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setPhoneNumber(userDto.getPhoneNumber());
                user.setActive(userDto.isActive());
                user.setRoles(roleRepository.findAllByIdIn(userDto.getRole()));
//                user.setCategories(categoryRepository.findAllByIdIn(userDto.getCategoryId()));
//                user.setPhotos(Collections.singletonList(attachmentRepository.getById(userDto.getPhotoId())));
                userRepository.save(user);
                return new ApiResponse("Saved user ", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ApiResponse("Error", false);
    }

    public String login(SignIn signIn) {
        Authentication authenticate = manager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getPassword(), signIn.getPhoneNumber())

        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User principal = (User) authenticate.getPrincipal();
        String token = jwtProvider.generateJwtToken(principal);
        return token;
    }


    public ApiResponse edit(User user, SignUp signUp) {

        user.setPassword(signUp.getPassword());
        user.setEmail(signUp.getEmail());
        user.setLastName(signUp.getLastName());
        user.setPhoneNumber(signUp.getPhoneNumber());
//        user.setPhotos(Collections.singletonList(attachmentRepository.getById(signUp.getPhotoId())));
        userRepository.save(user);
        return new ApiResponse("Edit", true);


    }


    public ApiResponse delete(UUID id) {
        userRepository.deleteById(id);
        return new ApiResponse("delete", true);


    }


    // bu articldi biriktirmoqchi bo'lgan  Reviewer va  Redactor  categoriyasiga qarab chiqarib beradi


    // bu userlarnin activini ozgartiradi

    public ApiResponse changeUserActive(UUID id, boolean changeStatus, User user) {
        User byId = userRepository.getById(id);
        byId.setActive(changeStatus);
        userRepository.save(byId);

//        informationArticleRepository.save(new InformationArticle(user, byId, new Date(), changeStatus ? Watdou.ACTIVE : Watdou.UN_ACTIVE));

        return new ApiResponse(byId.isActive() ? "Activated" : "Blocked", true);

    }


    public ApiResponse searchUser(String search, Integer roles_id, boolean enabled, Integer page, Integer size)
            throws IllegalAccessException {
        Page<User> users;
        if (roles_id == null) {
            users = userRepository.findAllByEnabledAndFirstNameContainingIgnoringCaseOrLastNameContainingIgnoringCaseOrFatherNameContainingIgnoringCaseOrEmailContainingIgnoringCaseOrPhoneNumberContainingIgnoringCase
                    (enabled, search, search, search, search, search, CommonUtills.simplePageable(page, size));
        } else {
            users = userRepository.findAllByRolesIdAndEnabledAndFirstNameContainingIgnoringCaseOrLastNameContainingIgnoringCaseOrFatherNameContainingIgnoringCaseOrEmailContainingIgnoringCaseOrPhoneNumberContainingIgnoringCase
                    (roles_id, enabled, search, search, search, search,search, CommonUtills.simplePageable(page, size));
        }
        return new ApiResponse("All user ", true, users);
    }


}
