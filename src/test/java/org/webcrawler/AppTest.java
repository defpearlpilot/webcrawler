package org.webcrawler;


import org.junit.Test;

import java.net.URL;

import static junit.framework.TestCase.assertNotNull;


class AppTest
{
    @Test
    void main()
    {
        URL resource = App.class.getResource("/index.html");
        assertNotNull(resource);
    }

}