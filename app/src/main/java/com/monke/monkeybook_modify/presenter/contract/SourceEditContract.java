package com.monke.monkeybook_modify.presenter.contract;

import android.graphics.Bitmap;
import android.net.Uri;

import com.monke.basemvplib.impl.IPresenter;
import com.monke.basemvplib.impl.IView;
import com.monke.monkeybook_modify.bean.BookSourceBean;

public interface SourceEditContract {
    interface Presenter extends IPresenter {

        void saveSource(BookSourceBean bookSource, BookSourceBean bookSourceOld);

        void copySource(BookSourceBean bookSourceBean);

        void pasteSource();

        void setText(String bookSourceStr);

        Bitmap encodeAsBitmap(String str);

        void analyzeBitmap(Uri uri);
    }

    interface View extends IView {

        void setText(BookSourceBean bookSourceBean);

        String getBookSourceStr();

        void saveSuccess();

        void toast(String msg);
    }
}
