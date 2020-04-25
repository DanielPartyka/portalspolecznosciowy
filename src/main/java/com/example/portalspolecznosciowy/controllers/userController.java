package com.example.portalspolecznosciowy.controllers;

import com.example.portalspolecznosciowy.controllers.helpingClasses.HelpingClass;
import com.example.portalspolecznosciowy.models.Photos;
import com.example.portalspolecznosciowy.models.User;
import com.example.portalspolecznosciowy.repositories.FollowersRepository;
import com.example.portalspolecznosciowy.services.FollowersServices;
import com.example.portalspolecznosciowy.services.PhotosServices;
import com.example.portalspolecznosciowy.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class userController {

    @Autowired
    private PhotosServices photosServices;
    @Autowired
    private UserServices userServices;
    @Autowired
    private FollowersServices followersServices;

    private static String userr;

    @GetMapping("/{nickname}")
    public ModelAndView userrender(@PathVariable("nickname") String nickname) {
        ModelAndView modelAndView = new ModelAndView("/user");
        userr = nickname;
        if (userr == null) {
            modelAndView.setViewName("/404");
            return modelAndView;
        }
        else {
            long user_id = userServices.findUserByNickname(userr);
            //Put data on web
            User user = userServices.findUserObjectbyNickname(userr);
            modelAndView.addObject("user_nickname", user.getNickname());
            modelAndView.addObject("user_name", user.getName());
            modelAndView.addObject("user_surname", user.getSurname());
            //HowManyPhotos Does the user posted
            long howmanyPhotos = photosServices.howManyPhotos(user_id);
            HelpingClass helpingClass = new HelpingClass(howmanyPhotos);
            modelAndView.addObject("hmp", helpingClass);
            //HowManyUsersFollowsthisUser
            howmanyPhotos = followersServices.howManyUsersFollowsThisUser(user_id);
            HelpingClass helpingClass1 = new HelpingClass(howmanyPhotos);
            modelAndView.addObject("hmuftu", helpingClass1);
            //HowManyUserFollows
            howmanyPhotos = followersServices.howManyUserisFollwing(user_id);
            HelpingClass helpingClass2 = new HelpingClass(howmanyPhotos);
            modelAndView.addObject("hmuf", helpingClass2);
            return modelAndView;
            }
        }
}
