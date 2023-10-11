package com.Dima1302.TelegramCategoryBot.сommand;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Команда для обработки команды /start и отправки приветственного сообщения.
 */
public class StartCommand implements Command {
    private final MessageSender messageSender;

    /**
     * Инициализирует команду с объектом для отправки сообщений.
     *
     * @param messageSender Объект для отправки сообщений.
     */
    public StartCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Выполняет логику обработки команды /start и отправляет приветственное сообщение пользователю.
     *
     * @param update Объект с информацией о входящем сообщении.
     */
    @Override
    public void execute(Update update) {
        // Логика обработки команды /start
        long chatId = update.getMessage().getChatId();
        messageSender.sendMessage(String.valueOf(chatId), "Привет, я ваш бот!");
    }
}
