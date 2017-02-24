package org.webcrawler;


import javaslang.control.Try;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.webcrawler.links.Domain;
import org.webcrawler.links.Link;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CrawlerSupportTest
{
    @Test
    void extractAnchors()
    {
        Collection<Link> images = Try.of(() ->
                                         {
                                             URI uri = new URI("http://example.com");
                                             Domain domain = new Domain(uri);

                                             URL resource = CrawlerSupport.class.getResource("/anchors.html");
                                             File imageFile = new File(resource.toURI());
                                             Try<Document> document = JsoupSupport.openFile(imageFile);

                                             return document.map(doc -> CrawlerSupport.extractAnchors(doc, domain))
                                                            .get();
                                         })
                                     .get();

        Iterator<Link> imageItr = images.iterator();
        assertTrue(imageItr.hasNext());

        Link next = imageItr.next();
        assertEquals("test.html", next.getLink().toString());
    }


    @Test
    void extractLinks()
    {
        Collection<Link> images = Try.of(() ->
                                         {
                                             URI uri = new URI("http://example.com");
                                             Domain domain = new Domain(uri);

                                             URL resource = CrawlerSupport.class.getResource("/links.html");
                                             File imageFile = new File(resource.toURI());
                                             Try<Document> document = JsoupSupport.openFile(imageFile);

                                             return document.map(doc -> CrawlerSupport.extractLinks(doc, domain))
                                                            .get();
                                         })
                                     .get();

        Iterator<Link> imageItr = images.iterator();
        assertTrue(imageItr.hasNext());

        Link next = imageItr.next();
        assertEquals("test.html", next.getLink().toString());
    }


    @Test
    void extractScripts()
    {
        Collection<Link> images = Try.of(() ->
                                         {
                                             URI uri = new URI("http://example.com");
                                             Domain domain = new Domain(uri);

                                             URL resource = CrawlerSupport.class.getResource("/script.html");
                                             File imageFile = new File(resource.toURI());
                                             Try<Document> document = JsoupSupport.openFile(imageFile);

                                             return document.map(doc -> CrawlerSupport.extractScripts(doc, domain))
                                                            .get();
                                         })
                                     .get();

        Iterator<Link> imageItr = images.iterator();
        assertTrue(imageItr.hasNext());

        Link next = imageItr.next();
        assertEquals("test.js", next.getLink().toString());
    }


    @Test
    void extractImages()
    {
        Collection<Link> images = Try.of(() ->
                                         {
                                             URI uri = new URI("http://example.com");
                                             Domain domain = new Domain(uri);

                                             URL resource = CrawlerSupport.class.getResource("/images.html");
                                             File imageFile = new File(resource.toURI());
                                             Try<Document> document = JsoupSupport.openFile(imageFile);

                                             return document.map(doc -> CrawlerSupport.extractImages(doc, domain))
                                                            .get();
                                         })
                                     .get();

        Iterator<Link> imageItr = images.iterator();
        assertTrue(imageItr.hasNext());

        Link next = imageItr.next();
        assertEquals("test.jpg", next.getLink().toString());
    }

}