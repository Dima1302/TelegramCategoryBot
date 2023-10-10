package com.Dima1302.TelegramCategoryBot.listener;

import com.Dima1302.TelegramCategoryBot.service.CategoryService;
import com.Dima1302.TelegramCategoryBot.repository.CategoryRepository;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.slf4j.Logger;


public class TelegramCategoryBot extends TelegramLongPollingBot {

    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(TelegramCategoryBot.class);
    // Конструктор класса, принимающий репозиторий категорий для инициализации сервиса
    public TelegramCategoryBot(CategoryRepository categoryRepository) {
        this.categoryService = new CategoryService(categoryRepository, this);
    }
    // Метод для получения имени бота
    @Override
    public String getBotUsername() {
        return "treecategory_bot";
    }
    // Метод для получения токена бота
    @Override
    public String getBotToken() {
        return "6292690781:AAFx5wPsdBZp-ynwMsux8_-4LCldGZGNYsA";
    }
    // Обработчик команд в Telegram
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if ("/start".equals(text)) {
                sendMessage(String.valueOf(chatId), "Привет, я ваш бот!");
            } else if ("/help".equals(text)) {
                sendMessage(String.valueOf(chatId), "Список доступных команд: /viewTree, /addElement, /removeElement");
            } else if ("/viewTree".equals(text)) {
                String categoryTreeText = categoryService.generateCategoryTree();
                sendMessage(String.valueOf(chatId), categoryTreeText);
            } else if (text.startsWith("/addElement")) {
                String[] parts = text.split(" ");
                if (parts.length >= 2) {
                    String parentCategory = parts[1];
                    String childCategory = parts[2];
                    categoryService.addElement(parentCategory, childCategory, chatId);
                } else {
                    sendMessage(String.valueOf(chatId), "Использование: /addElement <родительская категория> <дочерняя категория>");
                }
            } else if (text.startsWith("/removeElement")) {
                String category = text.replace("/removeElement", "").trim();
                categoryService.removeElement(category);
            } else {
                sendMessage(String.valueOf(chatId), "Извините, я не понял вашей команды.");
            }
        }
    }
    // Метод для отправки сообщения пользователю по указанному чату
    public void sendMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            logger.error("Ошибка при отправке сообщения: " + e.getMessage(), e);
            // Вернуть информацию об ошибке пользователю
            sendMessage(chatId, "Произошла ошибка при отправке сообщения. Пожалуйста, попробуйте ещё раз.");
        }
    }
}







