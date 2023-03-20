package ru.tinkoff.edu.java;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverFlowParser implements Parser{
    public static StackOverFlowResult parse(String s)
    {
        if(!s.contains("stackoverflow") || !s.contains("questions")) return  null;


        Matcher matcher = Pattern.compile("(questions/)\\d*").matcher(s);
        int result;

        if(!matcher.find()) return null;

        try {
            result = Integer.parseInt(matcher.group(0).substring(matcher.group(0).indexOf("/") + 1));
        }
        catch (Exception e)
        {
            return null;
        }
        return new StackOverFlowResult(result);
    }
}
