package ru.tinkoff.edu.java.bot.web.bot.commands;

import lombok.Getter;


public record SuccessCommandResponse(String text, boolean isForceReply) implements CommandResponse {
}
