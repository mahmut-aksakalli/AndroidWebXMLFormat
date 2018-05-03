package hr.ferit.mahmutaksakalli.androidwebxmlformat.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class SearchResult {


    @Element(name = "channel") private Channel channel;

    @Root(name = "channel", strict = false)
    public static class Channel{

        @ElementList(name = "item", inline = true) private List<NewsInfo> news;

        public List<NewsInfo> getNews() {
            return news;
        }

    }

    public Channel getChannel() {
        return channel;
    }

}
