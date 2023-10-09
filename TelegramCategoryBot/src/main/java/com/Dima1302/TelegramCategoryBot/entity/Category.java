package com.Dima1302.TelegramCategoryBot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

}
