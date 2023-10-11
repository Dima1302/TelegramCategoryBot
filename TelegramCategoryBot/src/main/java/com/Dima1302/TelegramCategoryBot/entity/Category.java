package com.Dima1302.TelegramCategoryBot.entity;

import jakarta.persistence.*;

/**
 * Класс, представляющий сущность "Категория" в базе данных.
 */
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    /**
     * Получение идентификатора категории.
     *
     * @return Идентификатор категории.
     */
    public Long getId() {
        return id;
    }

    /**
     * Установка идентификатора категории.
     *
     * @param id Новый идентификатор категории.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Получение имени категории.
     *
     * @return Имя категории.
     */
    public String getName() {
        return name;
    }

    /**
     * Установка имени категории.
     *
     * @param name Новое имя категории.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получение родительской категории.
     *
     * @return Родительская категория.
     */
    public Category getParent() {
        return parent;
    }

    /**
     * Установка родительской категории.
     *
     * @param parent Новая родительская категория.
     */
    public void setParent(Category parent) {
        this.parent = parent;
    }
}
