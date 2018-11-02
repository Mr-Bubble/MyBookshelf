package com.monke.monkeybook_modify.widget.refreshview.expandablerecyclerview.bean;

import java.util.List;

/**
 * author：Drawthink
 * describe:
 * date: 2017/5/22
 * T 为group数据对象
 * S 为child数据对象
 */

public class GroupItem<T, S> extends BaseItem {

    /**
     * head data
     */
    private T groupData;

    /**
     * childDatas
     */
    private List<S> childDatas;

    /**
     * 是否展开,  默认展开
     */
    private boolean isExpand = true;


    public GroupItem(T groupData, List<S> childDatas, boolean isExpand) {
        this.groupData = groupData;
        this.childDatas = childDatas;
        this.isExpand = isExpand;
    }

    /**
     * 返回是否是父节点
     */
    @Override
    public boolean isParent() {
        return true;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void onExpand() {
        isExpand = !isExpand;
    }

    public boolean hasChilds() {
        if (getChildDatas() == null || getChildDatas().isEmpty()) {
            return false;
        }
        return true;
    }

    public List<S> getChildDatas() {
        return childDatas;
    }

    public void setChildDatas(List<S> childDatas) {
        this.childDatas = childDatas;
    }

    public void removeChild(int childPosition) {

    }

    public T getGroupData() {
        return groupData;
    }
}