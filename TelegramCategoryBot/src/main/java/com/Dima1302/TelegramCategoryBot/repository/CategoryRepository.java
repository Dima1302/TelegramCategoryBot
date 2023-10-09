package com.Dima1302.TelegramCategoryBot.repository;

import com.Dima1302.TelegramCategoryBot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);
}
