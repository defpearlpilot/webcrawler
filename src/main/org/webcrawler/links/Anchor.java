package org.webcrawler.links;


import java.net.URI;


public class Anchor extends Link
{
    public Anchor(Domain domain, URI link)
    {
        super(domain, link);
    }


    @Override
    public String toString()
    {
        return "Anchor(" + link + ")";
    }
}
