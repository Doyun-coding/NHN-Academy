package nhn.academy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal UserDetails userDetails,
                             @AuthenticationPrincipal OAuth2User oAuth2User){
        ModelAndView home = new ModelAndView("home");
        if(Objects.nonNull(userDetails)) {
            home.addObject("loginName", userDetails.getUsername());
        }

        if(Objects.nonNull(userDetails)) {
            home.addObject("loginName", oAuth2User.getAttribute("name"));
        }

        return home;
    }

}
