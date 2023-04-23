package com.bezkoder.spring.thymeleaf.entity;


import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(length = 128)
  private String title;

  @Column(length = 256)
  private String description;

  @Column
  private Double price;

  @Column
  private Long promotionPercentage;
  
  @Column
  private LocalDate promotionStartDate;
  
  @Column
  private LocalDate promotionEndDate;
  
  @Column
  private String url;
  

  public Article() {

  }

  public Article(String title, String description, Double price,  Long promotionPercentage, LocalDate promotionStartDate, LocalDate promotionEndDate, String url) {
    this.title = title;
    this.description = description;
    this.price = price;
    this.promotionPercentage = promotionPercentage;
    this.promotionStartDate = promotionStartDate;
    this.promotionEndDate = promotionEndDate;
    this.url = url;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
  public Long getPromotionPercentage() {
	    return promotionPercentage;
	  }

  public void setromotionPercentage(Long promotionPercentage) {
    this.promotionPercentage = promotionPercentage;
  }
 
  public LocalDate getPromotionStartDate() {
	    return promotionStartDate;
  }

  public void setPromotionStartDate(LocalDate promotionStartDate) {
    this.promotionStartDate = promotionStartDate;
  }

  public LocalDate getPromotionEndDate() {
    return promotionEndDate;
  }

  public void setPromotionEndDate(LocalDate promotionEndDate) {
    this.promotionEndDate = promotionEndDate;
  }


  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  

  @Override
  public String toString() {
    return "Article [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
        +  ", promotionPercentage=" + promotionPercentage +  ",promotionStartDate=" + promotionStartDate +  ", promotionEndDate=" + promotionEndDate + ", url" + url + "]";
  }
}

