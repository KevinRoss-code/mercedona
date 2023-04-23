package com.bezkoder.spring.thymeleaf.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bezkoder.spring.thymeleaf.entity.Article;
import com.bezkoder.spring.thymeleaf.entity.ImageInfo;
import com.bezkoder.spring.thymeleaf.image.upload.controller.ImageController;
import com.bezkoder.spring.thymeleaf.repository.ArticleRepository;
import com.bezkoder.spring.thymeleaf.service.FilesStorageService;



@Controller
public class ArticleController {

  @Autowired
  private ArticleRepository articleRepository;

  
  @Autowired
  private FilesStorageService storageService;
  

  @GetMapping("/articles")
  public String listArticles(Model model, @Param("keyword") String keyword, RedirectAttributes redirectAttributes) {
	    
	  try {
		  List<Article> articles = new ArrayList<Article>();
      if (keyword == null) {
    	  articleRepository.findAll().forEach(articles::add);
        } else {
       	articleRepository.findByTitleContainingIgnoreCase(keyword).forEach(articles::add);
          model.addAttribute("keyword", keyword);
        }
      model.addAttribute("articles", articles);
	  } catch (Exception e) {
	      redirectAttributes.addAttribute("message", e.getMessage());
	    }
      List<ImageInfo> imageInfos = storageService.loadAll().map(path -> {
        String filename = path.getFileName().toString();
         String url = MvcUriComponentsBuilder
                  .fromMethodName(ImageController.class, "getImage", path.getFileName().toString()).build().toString();
         return new ImageInfo(filename, url);
      }).collect(Collectors.toList());
      
    
     model.addAttribute("images", imageInfos);
      
      return "articles";
  }

  @GetMapping("/articles/new")
  public String newArticle(Model model) {
    model.addAttribute("article", new Article());
    return "article_form";
  }

  @PostMapping("/articles/save")
  public String saveArticle(Article article, RedirectAttributes redirectAttributes,
		    @RequestParam("file") MultipartFile file) {
		  try {
		    // Enregistrer l'image dans le stockage et récupérer son URL
		    String imageUrl = storageService.save(file);

		    // Ajouter l'URL de l'image à l'article
		    article.setUrl(imageUrl.toString());
		    
		    // Enregistrer l'article dans la base de données
		    articleRepository.save(article);
		    
		    redirectAttributes.addFlashAttribute("message", "The article has been saved successfully!");
		  } catch (Exception e) {
		    redirectAttributes.addAttribute("message", e.getMessage());
		  }

		  return "redirect:/articles";
		}
 
  @PostMapping("/articles/edit/save")
  public String saveArticle(Integer id, Article article, String title, String description, Long promotionPercentage, LocalDate promotionStartDate, LocalDate promotionEndDate, RedirectAttributes redirectAttributes) {
		  try {
			  article = articleRepository.findById(id).get();
		        article.setTitle(title);
		        article.setDescription(description);
		        article.setromotionPercentage(promotionPercentage);
		        article.setPromotionStartDate(promotionStartDate);
		        article.setPromotionEndDate(promotionEndDate);
		        articleRepository.save(article);
		        redirectAttributes.addFlashAttribute("message", "The article has been updated successfully!");
		  } catch (Exception e) {
		    redirectAttributes.addAttribute("message", e.getMessage());
		  }

		  return "redirect:/articles";
		}
  
  


  @GetMapping("/articles/{id}")
  public String editArticle(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Article article = articleRepository.findById(id).get();

      model.addAttribute("article", article);
      model.addAttribute("pageTitle", "Edit Article (ID: " + id + ")");

      return "edit";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/articles";
    }
  }

  @GetMapping("/articles/delete/{id}")
  public String deleteArticle(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      articleRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Article with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/articles";
  }
}
