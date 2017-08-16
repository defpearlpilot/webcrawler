package crawler;


import javaslang.control.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.net.URI;
import java.net.URL;



public class JsoupSupport
{
    public static Try<Document> openFile(File file)
    {
        return Try.of(() -> Jsoup.parse(file, "UTF-8"));
    }



    public static Try<Document> connectBuffer(URL url)
    {
        return Try.of(() -> {
            String location = url.toString();
            return Jsoup.connect(location).get();
        });
    }


    public static Try<Document> connectBuffer(URI uri)
    {
        return Try.of(() -> {
            String location = uri.toURL().toString();
            return Jsoup.connect(location).get();
        });
    }
}
