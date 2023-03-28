package ru.tinkoff.edu.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GitHubParser extends Parser {
    private static final Pattern pattern = Pattern.compile("^(https://github\\.com/)([^/]*)/([^/]*)/*$");

    public ParseResult parse(String url) {
        Matcher matcher = pattern.matcher(url);

        if (!matcher.find()) {
            return parseNext(url);
        }

        String[] splitResult = matcher.group(0).split("/");
        return new GitHubResult(splitResult[splitResult.length - 1], splitResult[splitResult.length - 2]);

    }

}
