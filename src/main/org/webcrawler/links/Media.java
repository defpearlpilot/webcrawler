package org.webcrawler.links;


import java.net.URI;


public class Media extends Link
{
    public Media(Domain domain, URI link)
    {
        super(domain, link);
    }


    @Override
    public boolean isTraversable()
    {
        return false;
    }


    @Override
    public String toString()
    {
        return "Media(" + link + ")";
    }
}
