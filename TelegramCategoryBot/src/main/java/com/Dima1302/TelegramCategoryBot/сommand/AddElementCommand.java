package com.Dima1302.TelegramCategoryBot.сommand;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import com.Dima1302.TelegramCategoryBot.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Команда для добавления дочерней категории к родительской категории.
 */
public class AddElementCommand implements Command {
    private final CategoryService categoryService;
    private final MessageSender messageSender;

    /**
     * Конструктор класса AddElementCommand.
     *
     * @param messageSender   Объект для отправки сообщений.
     * @param categoryService Сервис для управления категориями.
     */
    public AddElementCommand(MessageSender messageSender, CategoryService categoryService) {
        this.messageSender = messageSender;
        this.categoryService = categoryService;
    }


    @Override
    public void execute(Update update) {
        // Логика обработки команды /addElement
        long chatId = update.getMessage().getChatId();
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length >= 3) {
            String parentCategory = parts[1];
            String childCategory = parts[2];
            categoryService.addElement(parentCategory, childCategory, chatId);
        } else {
            messageSender.sendMessage(String.valueOf(chatId), "Использование: /addElement <родительская категория> <дочерняя категория>");
        }
    }
}
