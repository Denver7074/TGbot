package com.telegramm.bot.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "chatId")
    private Long chatId;
    @Column(name = "userName")
    private String userName;
    @Column(name = "name")
    private String name;
    @Column(name = "date_Registrtion")
    private Timestamp registration;

    @Override
    public String toString() {
        return "UserEntity{" +
                ", chatId=" + chatId +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", registration=" + registration +
                '}';
    }
}
