package com.Dima1302.TelegramCategoryBot.listener;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import com.Dima1302.TelegramCategoryBot.service.CategoryService;
import com.Dima1302.TelegramCategoryBot.сommand.Command;
import com.Dima1302.TelegramCategoryBot.сommand.CommandContainer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

/**
 * Класс, представляющий бота для категоризации и выполнения команд в Telegram.
 * <p>
 * Этот бот способен обрабатывать команды, начинающиеся с префикса '/' и вызывать соответствующие
 * обработчики команд, реализованные с использованием классов-команд и хранящихся в CommandContainer.
 *
 * @author Васильченко Дмитрий
 */

public class TelegramCategoryBot implements LongPollingBot {
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(TelegramCategoryBot.class);
    private final MessageSender messageSender;

    private final CommandContainer commandContainer;
    private static final String COMMAND_PREFIX = "/";

    @Value("${BotUsername}")
    private String botUsername;

    @Value("${BotToken}")
    private String botToken;

    /**
     * Конструктор класса TelegramCategoryBot.
     *
     * @param categoryService  Сервис для работы с категориями.
     * @param messageSender    Объект для отправки сообщений в Telegram.
     * @param commandContainer Контейнер команд для обработки входящих сообщений.
     */
    public TelegramCategoryBot(CategoryService categoryService, MessageSender messageSender, CommandContainer commandContainer) {
        this.categoryService = categoryService;
        this.messageSender = messageSender;
        this.commandContainer = commandContainer;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if (text.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = text.split(" ")[0].toLowerCase();
                Command command = commandContainer.getCommand(commandIdentifier);
                command.execute(update);
            } else {
                messageSender.sendMessage(String.valueOf(chatId), "Извините, я не понял вашей команды.");
            }
        }
    }


    @Override
    public BotOptions getOptions() {
        return null;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
