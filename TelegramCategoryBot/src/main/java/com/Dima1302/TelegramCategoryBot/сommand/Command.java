package com.Dima1302.TelegramCategoryBot.сommand;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс для определения команд, которые могут быть выполнены в боте.
 */
public interface Command {
    /**
     * Выполняет команду на основе обновления (Update) из Telegram.
     *
     * @param update Обновление, содержащее информацию о команде.
     */
    void execute(Update update);

}
