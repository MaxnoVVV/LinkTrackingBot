package ru.tinkoff.edu.java;
import ru.tinkoff.edu.java.ParseResult;
public sealed interface Parser permits GitHubParser, StackOverFlowParser,BaseParser {
    public static ParseResult parse(String url) {
        return null;
    }
}
