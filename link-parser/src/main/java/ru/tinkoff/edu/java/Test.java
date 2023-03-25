package ru.tinkoff.edu.java;

public class Test {
    public static void main(String[] args)
    {
        Parser parser = Parser.link(new BaseParser(),new GitHubParser(),new StackOverFlowParser());

        String test1 = "https://github.com/sanyarnd/tinkoff-java-course-2022/";
        String test2 = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";
        String test3 = "https://stackoverflow.com/search?q=unsupported%20link";

        ParseResult test1res = parser.parse(test1);
        ParseResult test2res = parser.parse(test2);
        ParseResult test3res = parser.parse(test3);

        System.out.println(test1res);
        System.out.println(test2res);
        System.out.println(test3res);

    }
}
