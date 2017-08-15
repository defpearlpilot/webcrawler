package org.webcrawler.links;


import javaslang.control.Try;
import org.junit.Test;

import java.net.URI;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;


class LinkSupportTest
{
    @Test
    void toURI()
    {
        Try<URI> maybeURI = LinkSupport.toURI("http://valid.com");
        assertTrue(maybeURI.isSuccess());
    }

    @Test
    void toURL()
    {
        Try<URL> validURL = LinkSupport.toURL("http://valid.com");
        assertTrue(validURL.isSuccess());

        Try<URL> invalidURL = LinkSupport.toURL("invalid.com");
        assertTrue(invalidURL.isFailure());
    }

}