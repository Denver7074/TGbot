package com.telegramm.bot.controllers;

import com.telegramm.bot.configuration.BotConfig;
import com.telegramm.bot.models.UserEntity;
import com.telegramm.bot.services.BotService;
import com.telegramm.bot.services.UserService;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Component
@Slf4j
public class Controller extends TelegramLongPollingBot {

    private final BotConfig config;
    private final BotService service;
    private final UserService userService;
    private String HELP_TEXT = "Bot can...";
    @Override
    public String getBotUsername() {
        return config.getName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public Controller(BotConfig config,BotService service,UserService userService)  {
        this.config = config;
        this.service = service;
        this.userService = userService;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","Start bot"));
        listOfCommands.add(new BotCommand("/help","Information about bot"));
        listOfCommands.add(new BotCommand("/me","Информация обо мне"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }catch (TelegramApiException e){
            log.error("Error commands: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            handleMessage(update.getMessage());
        }
    }
    private void handleMessage(Message message){
            Long chatId = message.getChatId();
            String userName = message.getChat().getUserName();
            String command = message.getText();
            String answer = "Sorry, incorrect command";
            switch (command){
                case "/start":
                    answer = EmojiParser.parseToUnicode("Hello, " + message.getChat().getUserName() + ", nice to meet you!" + " :blush:");
                    userService.registrationUser(message);
                    doIt(chatId,answer,command,userName);
                    break;
                case "/help":
                    //answer = "The bot can simplify working moments in the laboratory";
                    answer = "Бот умеет";
                    doIt(chatId,answer,command,userName);
                    break;
                case "Температура в Москве":
                    answer = "Сейчас только градусник куплю";
                    doIt(chatId,answer,command,userName);
                    break;
                case "Текущее время":
                    Calendar calendar = new GregorianCalendar();
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    answer = EmojiParser.parseToUnicode("Московское время: " + String.valueOf(df.format(calendar.getTime())) + " :clock3:");
                    doIt(chatId,answer,command,userName);
                    break;
                case "Получить информацию":
                    try {
                        execute(service.information(chatId));
                    }catch (TelegramApiException e){
                        log.error("Error: " + e.getMessage());
                    }
                    break;
                case "/me":
                    break;
                default:
                    doIt(chatId,answer,command,userName);
                    break;
            }
    }
    private void doIt(Long chatId, String textToSend,String command, String userName){
        try {
            execute(service.sendMessage(chatId,textToSend,command,userName));
        }catch (TelegramApiException e){
            log.error("Error: " + e.getMessage());
        }
    }

}
