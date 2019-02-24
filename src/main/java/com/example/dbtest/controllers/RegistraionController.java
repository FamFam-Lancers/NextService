package com.example.dbtest.controllers;

import com.example.dbtest.domain.entity.UserInfo;
import com.example.dbtest.domain.service.SendVerifyMailService;
import com.example.dbtest.domain.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegistraionController {

    private final UserInfoService userInfoService;

    private final SendVerifyMailService sendVerifyMailService;

    @Autowired
    public RegistraionController(UserInfoService userInfoService, SendVerifyMailService sendVerifyMailService) {
        this.userInfoService = userInfoService;
        this.sendVerifyMailService = sendVerifyMailService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public ModelAndView showMyLoginPage(
            @ModelAttribute("RegistrationForm") RegistrationForm registrationForm,
            ModelAndView mav) {
        registrationForm.setNewRegistration(true);
        mav.addObject("registrationForm", registrationForm);
        mav.addObject("title", "会員登録");
        mav.setViewName("registration-form");
        return mav;
    }

    @PostMapping("/processRegistrationForm")
    public ModelAndView processRegistrationForm(
            @Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
            BindingResult result,
            ModelAndView mav) {

        // form validation
        if (!result.hasErrors()) {
            UserInfo userInfo = makeUserInfo(registrationForm);
            userInfoService.save(userInfo);
            sendVerifyMailService.execute(userInfo);

            mav.setViewName("registration-confirmation");
        } else {
            registrationForm.setNewRegistration(true);
            mav.addObject("registrationForm", registrationForm);
            mav.addObject("title", "会員登録");
            mav.setViewName("registration-form");



        }
        return mav;

    }

    @GetMapping("/verify")
    public String verify(String id) {

        UserInfo userInfo = userInfoService.findByTempkey(id);

        if(userInfo == null || userInfo.isEnabled()) {
            return "error"; //エラーページへ遷移
        }

        userInfoService.setEnabled(userInfo);

        return "verify";
    }


    private UserInfo makeUserInfo(RegistrationForm registrationForm) {
        return new UserInfo(registrationForm.getUsername(), registrationForm.getEmail(),
                registrationForm.getPassword(), false, "ROLE_USER", UUID.randomUUID().toString());
    }


}
