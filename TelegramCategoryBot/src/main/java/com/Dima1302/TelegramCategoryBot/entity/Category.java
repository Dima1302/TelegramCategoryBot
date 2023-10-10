package com.Dima1302.TelegramCategoryBot.entity;

import jakarta.persistence.*;

@Entity //  сущность
@Table(name = "categories") // Имя таблицы в базе данных
public class Category {
    // Геттер для получения идентификатора
    public Long getId() {
        return id;
    }
    // Сеттер для установки идентификатора
    public void setId(Long id) {
        this.id = id;
    }

    @Id // Аннотация, обозначающая поле как первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Аннотация для автоматической генерации значения ключа
    private Long id; // Идентификатор категории

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // Геттер для получения родительской категории
    public Category getParent() {
        return parent;
    }
    // Сеттер для установки родительской категории
    public void setParent(Category parent) {
        this.parent = parent;
    }

    private String name;

    @ManyToOne// Аннотация, обозначающая отношение "многие к одному"
    @JoinColumn(name = "parent_id")// Указание на столбец, который связывает с родительской категорией
    private Category parent; // Родительская категория

}
