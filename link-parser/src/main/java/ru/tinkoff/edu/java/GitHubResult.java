package ru.tinkoff.edu.java;

public final class GitHubResult implements  ParseResult{
    public String user;
    public String repository;
    public GitHubResult(String user,String repository)
    {
        this.repository = repository;
        this.user = user;
    }
}
