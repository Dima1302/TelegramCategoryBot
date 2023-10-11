package com.Dima1302.TelegramCategoryBot.repository;

import com.Dima1302.TelegramCategoryBot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для доступа к сущности "Категория" в базе данных.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Находит категорию по имени.
     *
     * @param categoryName Имя категории.
     * @return Найденная категория или null, если категория не найдена.
     */
    Category findByName(String categoryName);
}
