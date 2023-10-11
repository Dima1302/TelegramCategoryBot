package com.Dima1302.TelegramCategoryBot.сommand;

import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import com.Dima1302.TelegramCategoryBot.service.CategoryService;

import java.util.HashMap;
import java.util.Map;

/**
 * Контейнер команд, предоставляющий доступ к командам по их идентификаторам.
 */
public class CommandContainer {
    private final Map<String, Command> commands = new HashMap<>();

    /**
     * Инициализирует контейнер команд и связывает команды с соответствующими идентификаторами.
     *
     * @param messageSender   Объект для отправки сообщений.
     * @param categoryService Сервис для работы с категориями.
     */
    public CommandContainer(MessageSender messageSender, CategoryService categoryService) {
        commands.put("/start", new StartCommand(messageSender));
        commands.put("/help", new HelpCommand(messageSender));
        commands.put("/viewTree", new ViewTreeCommand(messageSender, categoryService));
        commands.put("/addElement", new AddElementCommand(messageSender, categoryService));
        commands.put("/removeElement", new RemoveElementCommand(messageSender, categoryService));
    }

    /**
     * Получает команду по её идентификатору.
     *
     * @param commandIdentifier Идентификатор команды.
     * @return Команда, соответствующая указанному идентификатору, или команда по умолчанию, если команда не найдена.
     */
    public Command getCommand(String commandIdentifier) {
        return commands.getOrDefault(commandIdentifier, commands.get("/unknown"));
    }
}
