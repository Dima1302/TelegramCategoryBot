package com.Dima1302.TelegramCategoryBot;


import com.Dima1302.TelegramCategoryBot.service.CategoryService;
import com.Dima1302.TelegramCategoryBot.listener.TelegramCategoryBot;
import com.Dima1302.TelegramCategoryBot.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TelegramCategoryBotTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TelegramCategoryBot telegramBot;


    @Before
    public void setUp() {
        // Инициализируем моки перед каждым тестом
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnUpdateReceived_StartCommand() throws TelegramApiException {
        Update update = createUpdateWithMessage("/start", 123456L);

        // Создаем шпиона (spy) на основе реального объекта
        TelegramCategoryBot spyBot = spy(telegramBot);

        // Заменяем метод execute на заглушку, которая ничего не делает
        doAnswer(invocation -> null).when(spyBot).execute(any(SendMessage.class));

        spyBot.onUpdateReceived(update);

        // Проверяем, что execute был вызван с ожидаемыми параметрами
        verify(spyBot, times(1)).execute(any(SendMessage.class));
    }


    @Test
    public void testOnUpdateReceived_HelpCommand() throws TelegramApiException {
        Update update = createUpdateWithMessage("/help", 123456L);

        // Создаем шпиона (spy) на основе реального объекта
        TelegramCategoryBot spyBot = spy(telegramBot);

        // Заменяем метод execute на заглушку, которая ничего не делает
        doAnswer(invocation -> null).when(spyBot).execute(any(SendMessage.class));

        spyBot.onUpdateReceived(update);

        // Проверяем, что execute был вызван с ожидаемыми параметрами
        verify(spyBot, times(1)).execute(any(SendMessage.class));


    }

    @Test
    public void testOnUpdateReceived_ViewTreeCommand() throws TelegramApiException {
        // Создаем заглушку для CategoryRepository
        CategoryRepository categoryRepository = mock(CategoryRepository.class);

        // Создаем заглушку для TelegramCategoryBot
        TelegramCategoryBot telegramBot = new TelegramCategoryBot(categoryRepository);
        // Создаем шпиона (spy) на основе реального объекта
        TelegramCategoryBot spyBot = spy(telegramBot);


        // Создаем объект CategoryService, передавая в него categoryRepository и telegramBot
        CategoryService categoryService = new CategoryService(categoryRepository, telegramBot);

        // Мокируем вызов generateCategoryTree
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(/* Здесь добавьте ожидаемые объекты Category */));


        // Создаем объект Update с текстовым сообщением "/viewTree"
        Update update = createUpdateWithMessage("/viewTree", 123456L);

        // Заменяем метод execute на заглушку, которая ничего не делает
        doAnswer(invocation -> null).when(spyBot).execute(any(SendMessage.class));

        spyBot.onUpdateReceived(update);

        // Проверяем, что execute был вызван с ожидаемыми параметрами
        verify(spyBot, times(1)).execute(any(SendMessage.class));


    }


    @Test
    public void testSendMessage() throws TelegramApiException {
        String chatId = "123456";
        String text = "Тестовое сообщение";

        // Создаем экземпляр TelegramCategoryBot с необходимыми зависимостями
        TelegramCategoryBot spyBot = spy(telegramBot);

        // Создаем mock для объекта SendMessage
        SendMessage sendMessageMock = mock(SendMessage.class);

        // Заменяем метод execute на имитацию выполнения
        doAnswer(invocation -> {
            SendMessage sendMessageArg = invocation.getArgument(0);
            assertEquals(chatId, sendMessageArg.getChatId());
            assertEquals(text, sendMessageArg.getText());
            return null;
        }).when(spyBot).execute(any(SendMessage.class));

        spyBot.sendMessage(chatId, text);

        // Проверяем, что метод execute был вызван
        verify(spyBot).execute(any(SendMessage.class));
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
