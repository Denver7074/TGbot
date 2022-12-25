package com.telegramm.bot.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BotService {

    public SendMessage sendMessage(Long chatId, String textToSend, String textCommand,String name){
        SendMessage message = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Температура в Москве");
        row.add("Текущее время");
        row.add("Получить информацию");
        keyboardRows.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setChatId(chatId);
        message.setText(textToSend);
        log.info(textCommand + " " + name);
        return message;
    }
    public SendMessage information(Long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Какой сайт вы хотите открыть?");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        var buttonFsa = new InlineKeyboardButton();
        buttonFsa.setText("Перейти на сайт ФСА");
        buttonFsa.setCallbackData("FSA");
        buttonFsa.setUrl("https://fsa.gov.ru/");
        var buttonArshin = new InlineKeyboardButton();
        buttonArshin.setText("Перейти на сайт ФГИС Аршин");
        buttonArshin.setCallbackData("Arshin");
        buttonArshin.setUrl("https://fgis.gost.ru/fundmetrology/registry");
        row.add(buttonFsa);
        row.add(buttonArshin);
        rowInline.add(row);
        markupInline.setKeyboard(rowInline);
        message.setReplyMarkup(markupInline);
        return message;
    }
}
