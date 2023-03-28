package ru.tinkoff.edu.java;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class BaseParser extends Parser {
    private static final Pattern pattern = Pattern.compile("^(https://)(([\\w-_\\.@#$%=&\\?\\*|])*[/])*([\\w-_\\.@#=$%&\\?\\*])*$");

    public ParseResult parse(String url) {
        Matcher matcher = pattern.matcher(url);

        if (!matcher.matches()) return null;
        return parseNext(url);
    }
}
