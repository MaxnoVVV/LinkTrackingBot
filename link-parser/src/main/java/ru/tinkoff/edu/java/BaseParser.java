package ru.tinkoff.edu.java;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public final class BaseParser implements Parser{
    public static ParseResult parse(String s)
    {
        String regex = "^(https://)(([\\w-_\\.@#$%&\\?\\*|])*[/])*([\\w-_\\.@#$%&\\?\\*])*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if(!matcher.matches()) return null;
        return GitHubParser.parse(s);
    }
}
