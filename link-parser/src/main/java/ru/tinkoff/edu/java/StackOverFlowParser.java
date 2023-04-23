package ru.tinkoff.edu.java;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverFlowParser extends Parser {
    private static final Pattern pattern = Pattern.compile("^(https://stackoverflow\\.com/questions/)(\\d*)/*");

    public ParseResult parse(String url) {
        try {
            Matcher matcher = pattern.matcher(url);

            if (!matcher.find()) {
                return parseNext(url);
            }

            Matcher tempmatcher = Pattern.compile("/\\d+/").matcher(matcher.group(0));
            tempmatcher.find();

            return new StackOverFlowResult(tempmatcher.group(0).substring(1, tempmatcher.group(0).length() - 1));
        } catch (Exception e) {
            return parseNext(url);
        }

    }


}
