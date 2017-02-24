package org.webcrawler;


import java.net.URL;


class AppTest
{
    @org.junit.jupiter.api.Test
    void main()
    {
        URL resource = App.class.getResource("/index.html");
        System.out.println(resource);
    }

}