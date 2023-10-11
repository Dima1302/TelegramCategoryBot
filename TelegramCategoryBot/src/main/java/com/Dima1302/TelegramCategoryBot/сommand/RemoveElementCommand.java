package com.Dima1302.TelegramCategoryBot.сommand;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import com.Dima1302.TelegramCategoryBot.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Команда для удаления элемента категории.
 */
public class RemoveElementCommand implements Command {
    private final CategoryService categoryService;
    private final MessageSender messageSender;

    /**
     * Инициализирует команду с объектами для отправки сообщений и обработки категорий.
     *
     * @param messageSender   Объект для отправки сообщений.
     * @param categoryService Объект для обработки категорий.
     */
    public RemoveElementCommand(MessageSender messageSender, CategoryService categoryService) {
        this.messageSender = messageSender;
        this.categoryService = categoryService;
    }

    /**
     * Выполняет логику обработки команды /removeElement и удаляет указанный элемент категории.
     *
     * @param update Объект с информацией о входящем сообщении.
     */
    @Override
    public void execute(Update update) {
        // Логика обработки команды /removeElement
        long chatId = update.getMessage().getChatId();
        String categoryName = update.getMessage().getText().replace("/removeElement", "").trim();
        categoryService.removeElement(categoryName);
    }
}
