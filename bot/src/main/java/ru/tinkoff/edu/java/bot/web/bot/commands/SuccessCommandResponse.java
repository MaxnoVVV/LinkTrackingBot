package ru.tinkoff.edu.java.bot.web.bot.commands;


public record SuccessCommandResponse(String text, boolean isForceReply) implements CommandResponse {

}
