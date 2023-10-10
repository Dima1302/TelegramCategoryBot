package com.Dima1302.TelegramCategoryBot.service;

import com.Dima1302.TelegramCategoryBot.entity.Category;
import com.Dima1302.TelegramCategoryBot.listener.TelegramCategoryBot;
import com.Dima1302.TelegramCategoryBot.repository.CategoryRepository;

import java.util.*;

public class CategoryService {
    //логика по работе с деревом категорий
    private final CategoryRepository categoryRepository;
    private final TelegramCategoryBot telegramBot;
    // Конструктор класса, принимающий репозиторий категорий и экземпляр Telegram бота для инициализации сервиса
    public CategoryService(CategoryRepository categoryRepository, TelegramCategoryBot telegramBot) {
        this.categoryRepository = categoryRepository;
        this.telegramBot = telegramBot;
    }
    // Метод для генерации древовидной структуры категорий и возврата её в виде текста
    public String generateCategoryTree() {
        // Строим карту категорий
        Map<Long, List<Category>> categoryMap = buildCategoryMap(categoryRepository.findAll());
        // Преобразуем карту в текстовое представление
        return stringifyCategoryTree(categoryMap, 0, 0);
    }
    // Метод для построения карты категорий, сгруппированных по родительским ID
    private Map<Long, List<Category>> buildCategoryMap(List<Category> categories) {
        Map<Long, List<Category>> categoryMap = new HashMap<>();

        // Группируем категории по родительским ID
        for (Category category : categories) {
            long parentId = category.getParent() != null ? category.getParent().getId() : 0; // Если нет родителя, то parentId = 0
            categoryMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(category);
        }

        return categoryMap;
    }

    // Метод для преобразования карты категорий в текстовое представление
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

    // Метод для удаления категории и её дочерних категорий по имени
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
    // Метод для добавления дочерней категории к родительской
    public void addElement(String parentCategoryName, String childCategoryName, long chatId) {
        // Находим родительскую категорию
        Optional<Category> optionalParentCategory = Optional.ofNullable(categoryRepository.findByName(parentCategoryName));

        if (optionalParentCategory.isEmpty()) {
            telegramBot.sendMessage(String.valueOf(chatId), "Родительская категория не найдена");
            return;
        }

        // Создаем новую дочернюю категорию
        Category childCategory = new Category();
        childCategory.setName(childCategoryName);
        childCategory.setParent(optionalParentCategory.get());

        // Сохраняем дочернюю категорию в базу данных
        categoryRepository.save(childCategory);

        // Используем экземпляр telegramBot для отправки сообщения пользователю
        telegramBot.sendMessage(String.valueOf(chatId), "Категория '" + childCategoryName + "' добавлена к категории '" + parentCategoryName + "'");
    }

}




