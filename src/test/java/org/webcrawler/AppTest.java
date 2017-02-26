package org.webcrawler;


import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class AppTest
{
    @org.junit.jupiter.api.Test
    void main()
    {
        URL resource = App.class.getResource("/index.html");
        assertNotNull(resource);
    }

}