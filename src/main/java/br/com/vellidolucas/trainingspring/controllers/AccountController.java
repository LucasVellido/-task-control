package br.com.vellidolucas.trainingspring.controllers;

import br.com.vellidolucas.trainingspring.modelos.User;
import br.com.vellidolucas.trainingspring.servicos.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("account/register");
        mv.addObject("user", new User());
        return mv;
    }

    @PostMapping("/registration")
    public ModelAndView registration(@Valid User user, BindingResult result){

        ModelAndView mv = new ModelAndView();
        User usr = userService.encontrarPorEmail(user.getEmail());
        if(usr != null){
            result.rejectValue("email", "", "Usuário ja cadastrado");
        }
        if(result.hasErrors()){
            mv.setViewName("account/register");
            mv.addObject("user", user);
        }else {
            userService.salvar(user);
            mv.setViewName("redirect:/login");
        }
        return mv;
    }
}
