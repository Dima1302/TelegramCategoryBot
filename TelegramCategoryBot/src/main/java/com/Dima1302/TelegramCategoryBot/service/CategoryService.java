package com.Dima1302.TelegramCategoryBot.service;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import com.Dima1302.TelegramCategoryBot.entity.Category;
import com.Dima1302.TelegramCategoryBot.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

/**
 * Сервис для работы с категориями.
 */

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MessageSender messageSender;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    /**
     * Конструктор класса.
     *
     * @param categoryRepository Репозиторий категорий.
     * @param messageSender      Отправитель сообщений.
     */
    public CategoryService(CategoryRepository categoryRepository, MessageSender messageSender) {
        this.categoryRepository = categoryRepository;
        this.messageSender = messageSender;
    }

    /**
     * Генерирует древовидное представление категорий.
     *
     * @return Древовидное представление категорий в виде строки.
     */
    public String generateCategoryTree() {
        Map<Long, List<Category>> categoryMap = buildCategoryMap(categoryRepository.findAll());
        return stringifyCategoryTree(categoryMap, 0, 0);
    }

    private Map<Long, List<Category>> buildCategoryMap(List<Category> categories) {
        Map<Long, List<Category>> categoryMap = new HashMap<>();

        for (Category category : categories) {
            long parentId = category.getParent() != null ? category.getParent().getId() : 0;
            categoryMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(category);
        }

        return categoryMap;
    }

    private String stringifyCategoryTree(Map<Long, List<Category>> categoryMap, long parentId, int depth) {
        StringBuilder treeBuilder = new StringBuilder();

        List<Category> children = categoryMap.getOrDefault(parentId, Collections.emptyList());

        for (Category child : children) {
            for (int i = 0; i < depth; i++) {
                treeBuilder.append("  ");
            }

            treeBuilder.append("- ").append(child.getName()).append("\n");

            String childTree = stringifyCategoryTree(categoryMap, child.getId(), depth + 1);
            treeBuilder.append(childTree);
        }

        return treeBuilder.toString();
    }

    /**
     * Удаляет категорию и все её дочерние категории.
     *
     * @param categoryName Имя категории для удаления.
     */
    public void removeElement(String categoryName) {
        Category categoryToRemove = categoryRepository.findByName(categoryName);

        if (categoryToRemove == null) {
            logger.error("Категория не найдена: " + categoryName);
            return;
        }

        categoryRepository.delete(categoryToRemove);
        logger.info("Категория '" + categoryName + "' и все её дочерние категории удалены");
    }

    /**
     * Добавляет дочернюю категорию к родительской категории.
     *
     * @param parentCategoryName Имя родительской категории.
     * @param childCategoryName  Имя дочерней категории.
     * @param chatId             Идентификатор чата для отправки уведомлений.
     */
    public void addElement(String parentCategoryName, String childCategoryName, long chatId) {
        Optional<Category> optionalParentCategory = Optional.ofNullable(categoryRepository.findByName(parentCategoryName));

        if (optionalParentCategory.isEmpty()) {
            messageSender.sendMessage(String.valueOf(chatId), "Родительская категория не найдена");
            return;
        }

        Category childCategory = new Category();
        childCategory.setName(childCategoryName);
        childCategory.setParent(optionalParentCategory.get());

        categoryRepository.save(childCategory);

        messageSender.sendMessage(String.valueOf(chatId), "Категория '" + childCategoryName + "' добавлена к категории '" + parentCategoryName + "'");
    }
}
