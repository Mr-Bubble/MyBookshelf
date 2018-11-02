package com.monke.monkeybook_modify.widget.page;

public class Enum {
    /**
     * Created by newbiechen on 2018/2/5.
     * 作用：翻页动画的模式
     */
    public enum PageMode {
        COVER, SIMULATION, SLIDE, SCROLL, NONE
    }

    public enum PageStatus {
        LOADING, FINISH, ERROR, EMPTY, CATEGORY_EMPTY, CHANGE_SOURCE,
    }
}
