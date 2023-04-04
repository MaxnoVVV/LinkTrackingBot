package ru.tinkoff.edu.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.*;

public class Tests {
    @Test
    void StackOverFlowSuccessParseTest()
    {
        Parser parser = Parser.link(new BaseParser(),new GitHubParser(),new StackOverFlowParser());


        String test1 = "https://stackoverflow.com/questions/75909392/using-mtls-for-open-banking-in-java-springboot";
        String test2 = "https://stackoverflow.com/questions/75909382/jetpack-compose-passing-a-viewmodel-between-multiple-pages-but-removing-when-out";
        String test3 = "https://stackoverflow.com/questions/75909377/memwa-chart-on-r-multivariate-ewma-chart/";

        ParseResult res1 = (StackOverFlowResult) parser.parse(test1);
        ParseResult res2 = (StackOverFlowResult) parser.parse(test2);
        ParseResult res3 = (StackOverFlowResult) parser.parse(test3);

        Assertions.assertInstanceOf(StackOverFlowResult.class,res1);
        Assertions.assertInstanceOf(StackOverFlowResult.class,res2);
        Assertions.assertInstanceOf(StackOverFlowResult.class,res3);

        Assertions.assertEquals(((StackOverFlowResult) res1).id(),75909392);
        Assertions.assertEquals(((StackOverFlowResult) res2).id(),75909382);
        Assertions.assertEquals(((StackOverFlowResult) res3).id(),75909377);

    }

    @Test
    void GitHubSuccessParseTest()
    {
        Parser parser = Parser.link(new BaseParser(),new StackOverFlowParser(),new GitHubParser());

        String test1 = "https://github.com/MaxnoVVV/TinkoffBot";
        String test2 = "https://github.com/pengrad/java-telegram-bot-api";
        String test3 = "https://github.com/ggerganov/llama.cpp/";

        ParseResult res1 = parser.parse(test1);
        ParseResult res2 = parser.parse(test2);
        ParseResult res3 = parser.parse(test3);

        GitHubResult actualres1 = new GitHubResult("MaxnoVVV","TinkoffBot");
        GitHubResult actualres2 = new GitHubResult("pengrad","java-telegram-bot-api");
        GitHubResult actualres3 = new GitHubResult("ggerganov","llama.cpp");


        Assertions.assertInstanceOf(GitHubResult.class,res1);
        Assertions.assertInstanceOf(GitHubResult.class,res2);
        Assertions.assertInstanceOf(GitHubResult.class,res3);


        Assertions.assertEquals(((GitHubResult) res1),actualres1);
        Assertions.assertEquals(((GitHubResult) res2),actualres2);
        Assertions.assertEquals(((GitHubResult) res3),actualres3);


    }

    @Test
    void ParserInvalidArgsTest()
    {
        Parser parser = Parser.link(new BaseParser(),new GitHubParser(),new StackOverFlowParser());
        String test1 = "https://githubhub.com/MaxnoVVV/TinkoffBot";
        String test2 = "https://github.com/MaxnoVVV/";
        String test5 = "https://git.hub.com/MaxnoVVV/TinkoffBot";
        String test6 = "https://git/hub.com/MaxnoVVV/TinkoffBot";

        String test3 = "https://stackoverflow.com/questions/questions/75912000/haskell-find-the-element";
        String test4 = "https://stackoverflow.com/stackoverflow.com/questions/75912000/haskell-find-the-element";

        ParseResult res1 = parser.parse(test1);
        ParseResult res2 = parser.parse(test2);
        ParseResult res3 = parser.parse(test3);
        ParseResult res4 = parser.parse(test4);
        ParseResult res5 = parser.parse(test5);
        ParseResult res6 = parser.parse(test6);

        Assertions.assertNull(res1);
        Assertions.assertNull(res2);
        Assertions.assertNull(res3);
        Assertions.assertNull(res4);
        Assertions.assertNull(res5);
        Assertions.assertNull(res6);
    }
}
