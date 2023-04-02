package ru.tinkoff.edu.java.scrapper.web.DTO.Clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StackOverFlowResponse {
    public Item[] items;
}
