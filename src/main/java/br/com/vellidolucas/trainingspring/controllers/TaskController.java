package br.com.vellidolucas.trainingspring.controllers;

import br.com.vellidolucas.trainingspring.modelos.Task;
import br.com.vellidolucas.trainingspring.modelos.User;
import br.com.vellidolucas.trainingspring.repositories.TaskRepository;
import br.com.vellidolucas.trainingspring.servicos.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("tasks/list");
        String emailUsuario = request.getUserPrincipal().getName();
        mv.addObject("tasks", taskRepository.getTasksByUser(emailUsuario));
        return mv;
    }

    @GetMapping("/insert")
    public ModelAndView insert(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("tasks/insert");
        mv.addObject("task", new Task());
        return mv;
    }

    @PostMapping("/insert")
    public ModelAndView insert(@Valid Task task, BindingResult result, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        if(task.getExpirationDate() == null){
            result.rejectValue("expirationDate", "task.dataExpiracaoInvalida", "A data de expiracao é obrigatória.");
        }else {
            if (task.getExpirationDate().before(new Date())) {
                result.rejectValue("expirationDate", "task.dataExpiracaoInvalida", "A data de expiracao não pode ser anterior a data atual.");
            }
        }
        if(result.hasErrors()){
            mv.setViewName("tasks/insert");
            mv.addObject(task);
        }else {
            String emailUsuario = request.getUserPrincipal().getName();
            User usuarioLogado = userService.encontrarPorEmail(emailUsuario);
            task.setUser(usuarioLogado);
            taskRepository.save(task);
            mv.setViewName("redirect:/tasks/list");
        }
        return mv;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        Task task = taskRepository.getOne(id);
        mv.setViewName("/tasks/update");
        mv.addObject("task", task);
        return mv;
    }

    @PostMapping("/update")
    public ModelAndView alterar(@Valid Task task, BindingResult result){
        ModelAndView mv = new ModelAndView();
        if(task.getExpirationDate() == null){
            result.rejectValue("expirationDate", "task.dataExpiracaoInvalida", "A data de expiracao é obrigatória.");
        }else {
            if (task.getExpirationDate().before(new Date())) {
                result.rejectValue("expirationDate", "task.dataExpiracaoInvalida", "A data de expiracao não pode ser anterior a data atual.");
            }
        }
        if(result.hasErrors()){
            mv.setViewName("tasks/update");
            mv.addObject(task);
        }else {
            mv.setViewName("redirect:/tasks/list");
            taskRepository.save(task);
        }
        return mv;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        taskRepository.deleteById(id);
        return "redirect:/tasks/list";
    }

    @GetMapping("/complete/{id}")
    public String complete(@PathVariable("id") Long id){
        Task task = taskRepository.getOne(id);
        task.setCompleted(true);
        taskRepository.save(task);
        return "redirect:/tasks/list";
    }

}
