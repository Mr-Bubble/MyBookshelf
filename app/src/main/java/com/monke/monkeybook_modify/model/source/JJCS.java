package com.monke.monkeybook_modify.model.source;

import android.util.Log;

import com.monke.basemvplib.BaseModelImpl;
import com.monke.monkeybook_modify.bean.BookContentBean;
import com.monke.monkeybook_modify.bean.BookShelfBean;
import com.monke.monkeybook_modify.bean.ChapterListBean;
import com.monke.monkeybook_modify.bean.SearchBookBean;
import com.monke.monkeybook_modify.model.impl.IStationBookModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class JJCS extends BaseModelImpl implements IStationBookModel {

    public static final String TAG = "九九藏书";

    @Override
    public Observable<List<SearchBookBean>> findBook(String url, int page) {
        return null;
    }

    @Override
    public Observable<List<SearchBookBean>> searchBook(String content, int page) {
        try {
            Document document = Jsoup.connect("http://www.99lib.net/book/search.php?type=all&keyword="+content+"&page=" +page).get();
            Element list_box = document.getElementsByClass("list_box").get(0);
            List<SearchBookBean> searchBookList = new ArrayList<>();
            for (Element e:list_box.children()){
                SearchBookBean searchBookBean = new SearchBookBean();
                searchBookBean.setTag(TAG);
                searchBookBean.setWeight(Integer.MAX_VALUE);
                searchBookBean.setOrigin(TAG);
                searchBookBean.setKind(e.select("h4").get(1).select("a").get(0).text());
                searchBookBean.setName(e.select("h2").get(0).text());
                searchBookBean.setAuthor(e.select("h4").get(0).select("a").get(0).text());
                searchBookBean.setNoteUrl(e.select("a").get(0).attr("href"));
                searchBookBean.setLastChapter(null);
                searchBookBean.setCoverUrl(e.select("img").get(0).attr("src"));
                searchBookBean.setIntroduce(e.getElementsByClass("intro").get(0).text());
                Log.e(TAG, "searchBook: "+22222);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Observable<BookShelfBean> getBookInfo(BookShelfBean bookShelfBean) {
        return null;
    }

    @Override
    public Observable<List<ChapterListBean>> getChapterList(BookShelfBean bookShelfBean) {
        return null;
    }

    @Override
    public Observable<BookContentBean> getBookContent(Scheduler scheduler, String durChapterUrl, int durChapterIndex) {
        return null;
    }
}
