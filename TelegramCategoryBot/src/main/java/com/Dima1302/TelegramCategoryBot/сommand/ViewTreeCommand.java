package com.Dima1302.TelegramCategoryBot.сommand;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import com.Dima1302.TelegramCategoryBot.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Команда для отображения древовидной структуры категорий.
 */
public class ViewTreeCommand implements Command {
    private final CategoryService categoryService;
    private final MessageSender messageSender;

    /**
     * Инициализирует команду с объектами для отправки сообщений и обработки категорий.
     *
     * @param messageSender   Объект для отправки сообщений.
     * @param categoryService Объект для обработки категорий.
     */
    public ViewTreeCommand(MessageSender messageSender, CategoryService categoryService) {
        this.messageSender = messageSender;
        this.categoryService = categoryService;
    }

    /**
     * Выполняет логику обработки команды /viewTree, генерирует древовидную структуру категорий и отправляет её в сообщении.
     *
     * @param update Объект с информацией о входящем сообщении.
     */
    @Override
    public void execute(Update update) {
        // Логика обработки команды /viewTree
        long chatId = update.getMessage().getChatId();
        String categoryTreeText = categoryService.generateCategoryTree();
        messageSender.sendMessage(String.valueOf(chatId), categoryTreeText);
    }
}
