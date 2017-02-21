package org.webcrawler.links;


import java.net.URI;
import java.util.HashSet;
import java.util.Set;


public class Domain extends Link
{
    private static final Set<URI> visited = new HashSet<>();


    public Domain(URI domain)
    {
        super(null, domain);
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


    public boolean contains(String ref)
    {
        boolean contains = ref.contains(link.getHost());

        System.out.println("Checking domain: " + ref + " " + link.getHost() + " " + contains);
        return contains;
    }


    public boolean canVisit(URI uri)
    {
        return !visited.contains(uri);
    }


    public boolean visitURI(URI uri)
    {
        boolean didVisit = visited.add(uri);
        System.out.println("Visiting " + uri + ": " + didVisit);

        return didVisit;
    }

}
