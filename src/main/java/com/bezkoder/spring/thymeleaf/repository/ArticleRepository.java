package com.bezkoder.spring.thymeleaf.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.thymeleaf.entity.Article;

@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<Article, Integer> {
  List<Article> findByTitleContainingIgnoreCase(String keyword);
  List<Article> findByTitle(String title);

  @Query("UPDATE Article t SET t.price = :price WHERE t.id = :id")
  @Modifying
  public void updatePrice(Integer id, double price);
  
  
}
