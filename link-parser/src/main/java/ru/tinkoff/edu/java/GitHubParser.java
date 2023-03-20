package ru.tinkoff.edu.java;

public final class GitHubParser implements  Parser{
    public static ParseResult parse(String s)
    {
        if(!s.contains("github"))
        {
            return StackOverFlowParser.parse(s);
        }

        String result = s.substring(s.indexOf("github"));
        String[] splitResult = result.split("/");

        if(splitResult.length < 3) return null;
        else return  new GitHubResult(splitResult[1],splitResult[2]);

    }
}
