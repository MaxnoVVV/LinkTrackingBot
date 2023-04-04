package ru.tinkoff.edu.java.scrapper.web.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    String account_id;
    int reputation;
    String user_id;
    int acceptance_rate;
    String display_name;
}
