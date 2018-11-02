package com.monke.monkeybook_modify.presenter.contract;

import android.app.Activity;

import com.monke.basemvplib.impl.IPresenter;
import com.monke.basemvplib.impl.IView;
import com.monke.monkeybook_modify.bean.BookShelfBean;
import com.monke.monkeybook_modify.bean.BookmarkBean;
import com.monke.monkeybook_modify.bean.ChapterListBean;
import com.monke.monkeybook_modify.bean.SearchBookBean;
import com.monke.monkeybook_modify.presenter.ReadBookPresenterImpl;

public interface ReadBookContract {
    interface View extends IView {

        /**
         * @return Book标志
         */
        String getNoteUrl();

        Boolean getAdd();

        void setAdd(Boolean isAdd);

        void changeSourceFinish(BookShelfBean book);

        /**
         * 开始加载
         */
        void startLoadingBook();

        void setHpbReadProgressMax(int count);

        void initChapterList();

        void showMenu();

        void openBookFromOther();

        void chapterChange(ChapterListBean chapterListBean);

        void onMediaButton();

        void toast(String msg);

        /**
         * 更新朗读状态
         */
        void upAloudState(int state);

        void upAloudTimer(String timer);

        void speakIndex(int index);

        void refresh(boolean recreate);

        void finish();
    }

    interface Presenter extends IPresenter {

        int getOpen_from();

        BookShelfBean getBookShelf();

        void saveProgress();

        String getChapterTitle(int chapterIndex);

        void addToShelf(final ReadBookPresenterImpl.OnAddListener addListner);

        void removeFromShelf();

        void initData(Activity activity);

        void openBookFromOther(Activity activity);

        void addDownload(int start, int end);

        void changeBookSource(SearchBookBean searchBookBean);

        void saveBookmark(BookmarkBean bookmarkBean);

        void delBookmark(BookmarkBean bookmarkBean);

        void disableDurBookSource();
    }
}
