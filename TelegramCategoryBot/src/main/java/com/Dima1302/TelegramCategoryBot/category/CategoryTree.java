package com.Dima1302.TelegramCategoryBot.category;

import com.Dima1302.TelegramCategoryBot.entity.Category;
import com.Dima1302.TelegramCategoryBot.repository.CategoryRepository;

import java.util.*;

public class CategoryTree {
    //логика по работе с деревом категорий
    private final CategoryRepository categoryRepository;

    public CategoryTree(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String generateCategoryTree() {
        Map<Long, List<Category>> categoryMap = buildCategoryMap(categoryRepository.findAll());
        return stringifyCategoryTree(categoryMap, 0, 0);
    }

    private Map<Long, List<Category>> buildCategoryMap(List<Category> categories) {
        Map<Long, List<Category>> categoryMap = new HashMap<>();

        // Группируем категории по родительским ID
        for (Category category : categories) {
            long parentId = category.getParent() != null ? category.getParent().getId() : 0; // Если нет родителя, то parentId = 0
            categoryMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(category);
        }

        return categoryMap;
    }


    private String stringifyCategoryTree(Map<Long, List<Category>> categoryMap, long parentId, int depth) {
        StringBuilder treeBuilder = new StringBuilder();

        // Получаем список дочерних категорий для данного родителя
        List<Category> children = categoryMap.getOrDefault(parentId, Collections.emptyList());

        // Перебираем дочерние категории
        for (Category child : children) {
            // Добавляем отступы в зависимости от уровня вложенности
            for (int i = 0; i < depth; i++) {
                treeBuilder.append("  "); // Просто пробелы для отступа
            }

            // Добавляем название текущей категории
            treeBuilder.append("- ").append(child.getName()).append("\n");

            // Рекурсивно обходим дочерние категории
            String childTree = stringifyCategoryTree(categoryMap, child.getId(), depth + 1);
            treeBuilder.append(childTree);
        }

        return treeBuilder.toString();
    }


    public void removeElement(String categoryName) {
        // Находим категорию, которую нужно удалить
        Category categoryToRemove = categoryRepository.findByName(categoryName);

        if (categoryToRemove == null) {
            System.out.println("Категория не найдена");
            return;
        }

        // Удаляем категорию и все её дочерние категории
        categoryRepository.delete(categoryToRemove);

        System.out.println("Категория '" + categoryName + "' и все её дочерние категории удалены");
    }

    public void addElement(String parentCategoryName, String childCategoryName) {
        // Находим родительскую категорию
        Category parentCategory = categoryRepository.findByName(parentCategoryName);

        if (parentCategory == null) {
            System.out.println("Родительская категория не найдена");
            return;
        }

        // Создаем новую дочернюю категорию
        Category childCategory = new Category();
        childCategory.setName(childCategoryName);
        childCategory.setParent(parentCategory);

        // Сохраняем дочернюю категорию в базу данных
        categoryRepository.save(childCategory);

        System.out.println("Категория '" + childCategoryName + "' добавлена к категории '" + parentCategoryName + "'");
    }
}




