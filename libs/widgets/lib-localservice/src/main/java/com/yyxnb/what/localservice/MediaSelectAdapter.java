package com.yyxnb.what.localservice;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.yyxnb.what.image.ImageHelper;
import com.yyxnb.what.localservice.bean.LocalMedia;
import com.yyxnb.what.localservice.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class MediaSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private LayoutInflater mInflater;

    private LocalConfig mConfig;

    /**
     * 数据源
     */
    private List<LocalMedia> mData;

    /**
     * 已选图片列表
     */
    private List<LocalMedia> mSelectlist = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MediaSelectAdapter(Context mContext, LocalConfig mConfig, List<LocalMedia> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mConfig = mConfig;
        this.mInflater = LayoutInflater.from(mContext);

//        mSelectlist = ImageSelectObservable.getInstance().getSelectImages();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_media_select_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        LocalMedia media = mData.get(position);
        media.setPosition(viewHolder.getAdapterPosition());

//        Glide.with(mContext).load(media.getPath()).centerCrop().into(viewHolder.ivPic);
        ImageHelper.displayImage(media.getPath(), viewHolder.ivPic);
        nitifyCheckChanged(viewHolder, media);

        viewHolder.tvDuration.setVisibility(media.getMimeType().contains("video") ? View.VISIBLE : View.GONE);
        viewHolder.tvDuration.setText(DateUtils.timeParse(media.getDuration()));

        /*点击监听*/
        initSelectOnClickListener(viewHolder.tvChecked, media, viewHolder.getAdapterPosition());
        initOnItemClickListener(viewHolder);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPic;
        public TextView tvChecked;
        public TextView tvDuration;

        public ViewHolder(View convertView) {
            super(convertView);

            ivPic = convertView.findViewById(R.id.ivPic);
            tvChecked = convertView.findViewById(R.id.tvSelect);
            tvDuration = convertView.findViewById(R.id.tvDuration);
        }
    }

    /**
     * 选择按钮更新
     *
     * @param viewHolder ViewHolder
     * @param media      LocalMedia
     */
    private void nitifyCheckChanged(ViewHolder viewHolder, LocalMedia media) {
        if (mConfig.isIsSelectSingle()) { //单选模式，不显示选择按钮
            viewHolder.tvChecked.setVisibility(View.INVISIBLE);
        } else {
            if (mSelectlist.contains(media)) {  //当已选列表里包括当前item时，选择状态为已选，并显示在选择列表里的位置
                viewHolder.tvChecked.setSelected(true);
                viewHolder.tvChecked.setText(String.valueOf(media.getSelectPosition()));
                viewHolder.ivPic.setColorFilter(ContextCompat.getColor
                        (mContext, R.color.image_overlay_true), PorterDuff.Mode.SRC_ATOP);
                zoomOut(viewHolder.ivPic);
            } else {
                viewHolder.tvChecked.setSelected(false);
                viewHolder.tvChecked.setText("");
                viewHolder.ivPic.setColorFilter(ContextCompat.getColor
                        (mContext, R.color.image_overlay_false), PorterDuff.Mode.SRC_ATOP);
                zoomIn(viewHolder.ivPic);
            }
        }
    }


    /**
     * 选择按钮点击监听
     *
     * @param view     点击view
     * @param media    对应的实体类
     * @param position 点击位置
     */
    private void initSelectOnClickListener(View view, final LocalMedia media, final int position) {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectlist.contains(media)) { //点击的item为已选过的图片时，删除
                    mSelectlist.remove(media);
                    subSelectPosition();
                } else { //不在选择列表里，添加
                    if (mSelectlist.size() >= mConfig.getMaxCount()) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.msg_amount_limit), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mSelectlist.add(media);
                    media.setSelectPosition(mSelectlist.size());
                }

                //通知点击项发生了改变
                notifyItemChanged(position);

                if (onItemClickListener != null) { //回调，页面需要展示选择的图片张数
                    onItemClickListener.onItemClick(v, -1);
                }
            }
        };

        view.setOnClickListener(listener);

    }

    /**
     * item点击监听，多选时查看大图，单选时返回选择图片
     * <p>
     * //     * @param view     点击view
     * //     * @param position 点击位置
     */
    private void initOnItemClickListener(final ViewHolder viewHolder) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
                }
            }
        };
        viewHolder.ivPic.setOnClickListener(listener);
    }


    /**
     * 更新选择的顺序
     */
    private void subSelectPosition() {
        for (int index = 0, len = mSelectlist.size(); index < len; index++) {
            LocalMedia media = mSelectlist.get(index);
            media.setSelectPosition(index + 1);
            notifyItemChanged(media.getPosition());
        }

    }

    /**
     * 所有选择的图片
     *
     * @return List<ImageFolderBean>
     */
    public List<LocalMedia> getSelectlist() {
        return mSelectlist;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private void zoomIn(View v) {
        ViewCompat.animate(v).setDuration(300L).scaleX(1.0f).scaleY(1.0f).start();
    }

    private void zoomOut(View v) {
        ViewCompat.animate(v).setDuration(300L).scaleX(0.9f).scaleY(0.9f).start();
    }

}
