package ru.tinkoff.edu.java;

public abstract class Parser {
    private Parser next;

    public abstract ParseResult parse(String url);

    public static Parser link(Parser first,Parser... chain)
    {
        Parser head = first;

        for(Parser nextParser : chain)
        {
            head.next = nextParser;
            head = nextParser;
        }

        head.next = null;
        return first;
    }

    protected ParseResult parseNext(String url)
    {
        if(next == null) return null;

        return next.parse(url);
    }



}
