package com.Dima1302.TelegramCategoryBot.сommand;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Команда для вывода списка доступных команд.
 */
public class HelpCommand implements Command {
    private final MessageSender messageSender;

    /**
     * Инициализирует команду с объектом для отправки сообщений.
     *
     * @param messageSender Объект для отправки сообщений.
     */
    public HelpCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Выполняет логику обработки команды /help и отправляет список доступных команд пользователю.
     *
     * @param update Объект с информацией о входящем сообщении.
     */
    @Override
    public void execute(Update update) {
        // Логика обработки команды /help
        long chatId = update.getMessage().getChatId();
        messageSender.sendMessage(String.valueOf(chatId), "Список доступных команд: /viewTree, /addElement, /removeElement");
    }
}
