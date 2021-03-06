package com.example.portalspolecznosciowy.controllers;

import com.example.portalspolecznosciowy.helpingClasses.HelpingClass;
import com.example.portalspolecznosciowy.models.User;
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

    private String userr;
    private static String user_email;

    @GetMapping("/{nickname}")
    public ModelAndView userrender(@PathVariable("nickname") String nickname) {
        ModelAndView modelAndView = new ModelAndView("/user");
        userr = nickname;
        if (userr == null) {
            modelAndView.setViewName("/error");
            return modelAndView;
        } else {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                user_email = authentication.getName();
                long user_id = userServices.findUserByNickname(userr);
                //Put data on web
                User user = userServices.findUserObjbyNickname(userr);
                //check_if_user_is_followed
                User usercheck = userServices.findUserId(user_email);
                if (userr.equals(usercheck.getNickname())) {
                    modelAndView.addObject("yourprofile",true);
                }
                else {
                    if (followersServices.checkifuserisfollowed(usercheck.getId())) {
                        modelAndView.addObject("follow",true);
                    }
                    else {
                        modelAndView.addObject("follow",false);
                    }
                }
                modelAndView.addObject("email",user.getEmail());
                modelAndView.addObject("user_nickname", user.getNickname());
                modelAndView.addObject("user_name", user.getName());
                modelAndView.addObject("user_surname", user.getSurname());
                //Print Photos
                modelAndView.addObject("photos",photosServices.getAmountofPhotos(user_id));
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
            } catch (NullPointerException e) {
                System.out.println("Nie znaleziono uzytkownika");
                modelAndView.setViewName("/error");
                return modelAndView;
            }
        }
    }
}
