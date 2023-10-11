package com.Dima1302.TelegramCategoryBot.messageSender;


import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Класс, представляющий объект для отправки сообщений пользователю через Telegram API.
 */
public class MessageSender extends DefaultAbsSender {
    @Value("${BotToken}")
    private String botToken;

    /**
     * Конструктор класса, принимающий параметры для настройки отправителя сообщений.
     *
     * @param options Параметры настройки отправителя сообщений.
     */
    protected MessageSender(DefaultBotOptions options) {
        super(options);
    }

    /**
     * Получение токена бота.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Метод для отправки сообщения пользователю по указанному чату.
     *
     * @param chatId Идентификатор чата, куда следует отправить сообщение.
     * @param text   Текст сообщения.
     */
    public void sendMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


