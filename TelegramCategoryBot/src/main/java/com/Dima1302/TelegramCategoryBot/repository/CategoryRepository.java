package com.Dima1302.TelegramCategoryBot.repository;

import com.Dima1302.TelegramCategoryBot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);
}
