package com.monke.monkeybook.view.adapter;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.monke.monkeybook.BitIntentDataManager;
import com.monke.monkeybook.R;
import com.monke.monkeybook.bean.BookSourceBean;
import com.monke.monkeybook.dao.BookSourceBeanDao;
import com.monke.monkeybook.dao.DbHelper;
import com.monke.monkeybook.help.BookshelfHelp;
import com.monke.monkeybook.help.MyItemTouchHelpCallback;
import com.monke.monkeybook.model.BookSourceManage;
import com.monke.monkeybook.view.activity.BookSourceActivity;
import com.monke.monkeybook.view.activity.SourceEditActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by GKF on 2017/12/22.
 * 书源Adapter
 */

public class BookSourceAdapter extends RecyclerView.Adapter<BookSourceAdapter.MyViewHolder> {
    private List<BookSourceBean> dataList;
    private List<BookSourceBean> allDataList;
    private BookSourceActivity activity;
    private int index;

    private MyItemTouchHelpCallback.OnItemTouchCallbackListener itemTouchCallbackListener = new MyItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {

        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            Collections.swap(dataList, srcPosition, targetPosition);
            notifyItemMoved(srcPosition, targetPosition);
            notifyItemChanged(srcPosition);
            notifyItemChanged(targetPosition);
            activity.saveDate(dataList);
            return true;
        }
    };

    public BookSourceAdapter(BookSourceActivity activity) {
        this.activity = activity;
        dataList = new ArrayList<>();
    }

    public void resetDataS(List<BookSourceBean> bookSourceBeanList) {
        this.dataList = bookSourceBeanList;
        notifyDataSetChanged();
        activity.upDateSelectAll();
        activity.upSearchView(dataList.size());
        activity.upGroupMenu();
    }

    private void allDataList(List<BookSourceBean> bookSourceBeanList) {
        this.allDataList = bookSourceBeanList;
        notifyDataSetChanged();
        activity.upDateSelectAll();
    }

    public List<BookSourceBean> getDataList() {
        return dataList;
    }

    public List<BookSourceBean> getSelectDataList() {
        List<BookSourceBean> selectDataS = new ArrayList<>();
        for (BookSourceBean data : dataList) {
            if (data.getEnable()) {
                selectDataS.add(data);
            }
        }
        return selectDataS;
    }

    public MyItemTouchHelpCallback.OnItemTouchCallbackListener getItemTouchCallbackListener() {
        return itemTouchCallbackListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_source, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (TextUtils.isEmpty(dataList.get(position).getBookSourceGroup())) {
            holder.cbView.setText(dataList.get(position).getBookSourceName());
        } else {
            holder.cbView.setText(String.format("%s (%s)", dataList.get(position).getBookSourceName(), dataList.get(position).getBookSourceGroup()));
        }
        holder.cbView.setChecked(dataList.get(position).getEnable());
        holder.cbView.setOnClickListener((View view) -> {
            dataList.get(position).setEnable(holder.cbView.isChecked());
            activity.saveDate(dataList.get(position));
            activity.upDateSelectAll();
        });
        holder.editView.getDrawable().mutate();
        holder.editView.getDrawable().setColorFilter(activity.getResources().getColor(R.color.tv_text_default), PorterDuff.Mode.SRC_ATOP);
        holder.editView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, SourceEditActivity.class);
            String key = String.valueOf(System.currentTimeMillis());
            intent.putExtra("data_key", key);
            try {
                BitIntentDataManager.getInstance().putData(key, dataList.get(position).clone());
            } catch (CloneNotSupportedException e) {
                BitIntentDataManager.getInstance().putData(key, dataList.get(position));
                e.printStackTrace();
            }
            activity.startActivityForResult(intent, BookSourceActivity.EDIT_SOURCE);
        });
        holder.delView.getDrawable().mutate();
        holder.delView.getDrawable().setColorFilter(activity.getResources().getColor(R.color.tv_text_default), PorterDuff.Mode.SRC_ATOP);
        holder.delView.setOnClickListener(view -> {
            activity.delBookSource(dataList.get(position));
            dataList.remove(position);
            notifyDataSetChanged();
        });
        holder.topView.getDrawable().mutate();
        holder.topView.getDrawable().setColorFilter(activity.getResources().getColor(R.color.tv_text_default), PorterDuff.Mode.SRC_ATOP);
        holder.topView.setOnClickListener(view -> {
            allDataList(BookSourceManage.getAllBookSource());
            BookSourceBean moveData = dataList.get(position);
            int maxWeight = DbHelper.getInstance().getmDaoSession().getBookSourceBeanDao().queryBuilder()
                    .orderRaw("-WEIGHT ASC").limit(1).unique().getWeight();
            moveData.setWeight(maxWeight + 1);
            BookshelfHelp.saveBookSource(moveData);
            dataList.remove(position);
            notifyItemInserted(0);
            dataList.add(0,moveData);
            notifyItemRemoved(position + 1);

            if (dataList.size() != allDataList.size()){
                for (int i = 0;i < allDataList.size();i++){
                    if (moveData.equals(allDataList.get(i))){
                        index = i;
                        break;
                    }
                }
                BookSourceBean moveDataA = allDataList.get(index);
                allDataList.remove(index);
                notifyItemInserted(0);
                allDataList.add(0,moveDataA);
                notifyItemRemoved(index + 1);
            }
            notifyDataSetChanged();
            activity.saveDate(dataList);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbView;
        ImageView editView;
        ImageView delView;
        ImageView topView;

        MyViewHolder(View itemView) {
            super(itemView);
            cbView = itemView.findViewById(R.id.cb_book_source);
            editView = itemView.findViewById(R.id.iv_edit_source);
            delView = itemView.findViewById(R.id.iv_del_source);
            topView = itemView.findViewById(R.id.iv_top_source);
        }
    }
}
