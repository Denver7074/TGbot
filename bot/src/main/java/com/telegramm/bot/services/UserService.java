package com.telegramm.bot.services;

import com.telegramm.bot.models.UserEntity;
import com.telegramm.bot.repositories.UserRep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRep rep;
    public void registrationUser(Message message){
        if(rep.findById(message.getChatId()).isEmpty()){
            UserEntity user = new UserEntity();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user.setUserName(message.getChat().getUserName());
            user.setName(message.getChat().getFirstName());
            user.setChatId(message.getChatId());
            user.setRegistration(timestamp);
            rep.save(user);
            log.info("add new user: " + user.getUserName());
        }
    }
    public void deleteInformation(Message message){
        if (rep.findById(message.getChatId()).isEmpty()){
            rep.deleteById(message.getChatId());
        }
    }
//    public String aboutMe(Long id){
//        if (rep.findById(id).isEmpty()){
//            rep.findById(id)
//            return user.toString();
//        }
//    }
}
