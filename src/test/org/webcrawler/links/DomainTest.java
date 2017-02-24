package org.webcrawler.links;


import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Created by andrew on 2/21/17.
 */
class DomainTest
{
    @Test
    void isTraversable()
    {
        try
        {
            URI example = new URI("http://example.com");
            assertTrue(new Domain(example).isTraversable());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    void getDomain()
    {
        try
        {
            URI example = new URI("http://example.com");
            Domain domain = new Domain(example);
            assertTrue(domain == domain.getDomain());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    void canTraverse()
    {
        try
        {
            URI example = new URI("http://example.com");
            Domain domain = new Domain(example);

            assertTrue(domain.canTraverse(new URI("http://example.com/page")));
            assertFalse(domain.canTraverse(new URI("http://otherdomain.com/")));
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    void canVisit()
    {
        try
        {
            URI example = new URI("http://example.com");
            Domain domain = new Domain(example);

            URI sub = new URI("http://example.com/page");
            assertTrue(domain.canVisit(sub));
            domain.visitURI(sub);
            assertFalse(domain.canVisit(sub));
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    void visitURI()
    {
        try
        {
            URI example = new URI("http://example.com");
            Domain domain = new Domain(example);

            URI sub = new URI("http://example.com/page");
            assertTrue(domain.visitURI(sub));
            assertFalse(domain.visitURI(sub));
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

    }

}