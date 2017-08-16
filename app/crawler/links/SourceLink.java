package crawler.links;


import java.net.URI;


public class SourceLink extends Link
{
    public SourceLink(Domain domain, URI link)
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
        return "Link(" + link + ")";
    }
}
