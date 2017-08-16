package crawler.links;


import java.net.URI;


public class Anchor extends Link
{
    public Anchor(Domain domain, URI link)
    {
        super(domain, link);
    }


    @Override
    public boolean isTraversable()
    {
        return true;
    }


    @Override
    public String toString()
    {
        return "Anchor(" + link + ")";
    }
}
