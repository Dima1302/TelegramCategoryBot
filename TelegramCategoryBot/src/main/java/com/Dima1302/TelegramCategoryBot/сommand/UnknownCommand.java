package com.Dima1302.TelegramCategoryBot.сommand;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Команда для обработки неизвестных команд и отправки сообщения с инструкциями.
 */
public class UnknownCommand implements Command {

    private final MessageSender messageSender;

    /**
     * Инициализирует команду с объектом для отправки сообщений.
     *
     * @param messageSender Объект для отправки сообщений.
     */
    public UnknownCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Выполняет логику обработки неизвестной команды и отправляет сообщение с инструкциями.
     *
     * @param update Объект с информацией о входящем сообщении.
     */
    @Override
    public void execute(Update update) {
        // Реализация для неизвестной команды
        long chatId = update.getMessage().getChatId();
        messageSender.sendMessage(String.valueOf(chatId), "Неизвестная команда. Используйте /help для получения списка доступных команд.");
    }
}
