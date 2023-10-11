package com.Dima1302.TelegramCategoryBot;


import com.Dima1302.TelegramCategoryBot.messageSender.MessageSender;
import com.Dima1302.TelegramCategoryBot.service.CategoryService;
import com.Dima1302.TelegramCategoryBot.listener.TelegramCategoryBot;
import com.Dima1302.TelegramCategoryBot.repository.CategoryRepository;
import com.Dima1302.TelegramCategoryBot.сommand.CommandContainer;
import com.Dima1302.TelegramCategoryBot.сommand.HelpCommand;
import com.Dima1302.TelegramCategoryBot.сommand.StartCommand;
import com.Dima1302.TelegramCategoryBot.сommand.ViewTreeCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.context.SpringBootTest;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TelegramCategoryBotTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MessageSender messageSender;
    @Mock
    private CommandContainer commandContainer;

    @InjectMocks
    private TelegramCategoryBot telegramBot;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnUpdateReceived_StartCommand() {
        Update update = createUpdateWithMessage("/start", 123456L);

        when(commandContainer.getCommand("/start")).thenReturn(new StartCommand(messageSender));

        telegramBot.onUpdateReceived(update);

        // Вместо проверки execute, проверьте, что messageSender был вызван с ожидаемыми параметрами
        verify(messageSender).sendMessage("123456", "Привет, я ваш бот!");
    }

    @Test
    public void testOnUpdateReceived_HelpCommand() {
        Update update = createUpdateWithMessage("/help", 123456L);

        when(commandContainer.getCommand("/help")).thenReturn(new HelpCommand(messageSender));

        telegramBot.onUpdateReceived(update);

        // Вместо проверки execute, проверьте, что messageSender был вызван с ожидаемыми параметрами
        verify(messageSender).sendMessage("123456", "Список доступных команд: /viewTree, /addElement, /removeElement");
    }

    @Test
    public void testOnUpdateReceived_ViewTreeCommand() {
        Update update = createUpdateWithMessage("/viewTree", 123456L);

        // Создаем мок для categoryService
        CategoryService categoryService = mock(CategoryService.class);

        // Замокаем метод generateCategoryTree()
        when(categoryService.generateCategoryTree()).thenReturn("Ожидаемый текст для команды /viewTree");

        // Передаем мок categoryService в команду ViewTreeCommand
        when(commandContainer.getCommand("/viewTree")).thenReturn(new ViewTreeCommand(messageSender, categoryService));

        telegramBot.onUpdateReceived(update);

        // Проверяем, что messageSender был вызван с ожидаемыми параметрами
        verify(messageSender).sendMessage("123456", "Ожидаемый текст для команды /viewTree");
    }


    private Update createUpdateWithMessage(String messageText, long chatId) {
        Chat chat = new Chat();
        chat.setId(chatId);

        Message message = new Message();
        message.setText(messageText);
        message.setChat(chat);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }
}
