package org.webcrawler.links;


import java.net.URI;
import java.util.HashSet;
import java.util.Set;


public class Domain extends Link
{
    private final Set<URI> visited = new HashSet<>();


    public Domain(URI domain)
    {
        super(null, domain);
    }


    @Override
    public boolean isTraversable()
    {
        return true;
    }


    protected Domain getDomain()
    {
        return this;
    }


    @Override
    public String toString()
    {
        return "Domain(" + link + ")";
    }


    public boolean canTraverse(URI uri)
    {
        return uri.toString().contains(link.getHost());
    }


    public boolean canVisit(URI uri)
    {
        return !visited.contains(uri);
    }


    public boolean visitURI(URI uri)
    {
        return visited.add(uri);
    }

}
