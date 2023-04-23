package com.bezkoder.spring.thymeleaf.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bezkoder.spring.thymeleaf.entity.Article;


@Controller
public class AdminController {

  

 @GetMapping("/login")
  public String fromAdmin(Model model) {
     return "login";
  }
  @PostMapping("/articles/log")
  public String loginAdmin(RedirectAttributes redirectAttributes, Model model) {  
	  model.addAttribute("article", new Article());
	   return "article_form";
  }
}
