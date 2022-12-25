package com.telegramm.bot.repositories;


import com.telegramm.bot.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRep extends JpaRepository<UserEntity,Long> {

}
