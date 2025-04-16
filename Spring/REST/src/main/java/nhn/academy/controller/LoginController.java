package nhn.academy.controller;

import jakarta.servlet.http.HttpServletResponse;
import nhn.academy.model.Member;
import nhn.academy.model.MemberLoginRequest;
import nhn.academy.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//@Controller
//@RequestMapping("/login")
public class LoginController {
    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String loginPage() {
        return "login";
    }
    @PostMapping
    public ModelAndView processLogin(@ModelAttribute MemberLoginRequest loginRequest)  {
        Member memberResponse = memberService.login(loginRequest);
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("loginName", memberResponse.getName());
        return mav;
    }
}

