package com.Dima1302.TelegramCategoryBot.listener;

import com.Dima1302.TelegramCategoryBot.category.CategoryTree;
import com.Dima1302.TelegramCategoryBot.repository.CategoryRepository;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramCategoryBot extends TelegramLongPollingBot {

    private final CategoryTree categoryTree;

    public TelegramCategoryBot(CategoryRepository categoryRepository) {
        this.categoryTree = new CategoryTree(categoryRepository);
    }

    @Override
    public String getBotUsername() {
        return "treecategory_bot";
    }

    @Override
    public String getBotToken() {
        return "6292690781:AAFx5wPsdBZp-ynwMsux8_-4LCldGZGNYsA";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if ("/start".equals(text)) {
                sendMessage(Long.parseLong(String.valueOf(chatId)), "Привет, я ваш бот!"); // Преобразуем chatId в строку
            } else if ("/help".equals(text)) {
                sendMessage(Long.parseLong(String.valueOf(chatId)), "Список доступных команд: /viewTree, /addElement, /removeElement"); // Преобразуем chatId в строку
            } else if ("/viewTree".equals(text)) {
                String categoryTreeText = categoryTree.generateCategoryTree();
                sendMessage(Long.parseLong(String.valueOf(chatId)), categoryTreeText); // Преобразуем chatId в строку
            } else if (text.startsWith("/addElement")) {
                String[] parts = text.split(" ");
                if (parts.length >= 2) {
                    String parentCategory = parts[1];
                    String childCategory = parts[2];
                    categoryTree.addElement(parentCategory, childCategory); // Меняем эту строку
                } else {
                    sendMessage(Long.parseLong(String.valueOf(chatId)), "Использование: /addElement <родительская категория> <дочерняя категория>"); // Преобразуем chatId в строку
                }
            } else if (text.startsWith("/removeElement")) {
                String category = text.replace("/removeElement", "").trim();
                categoryTree.removeElement(category); // Меняем эту строку
            } else {
                sendMessage(Long.parseLong(String.valueOf(chatId)), "Извините, я не понял вашей команды."); // Преобразуем chatId в строку
            }
        }
    }


    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}










