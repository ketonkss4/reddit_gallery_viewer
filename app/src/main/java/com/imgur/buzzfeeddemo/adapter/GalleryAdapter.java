package com.imgur.buzzfeeddemo.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.imgur.buzzfeeddemo.BR;
import com.imgur.buzzfeeddemo.R;
import com.imgur.buzzfeeddemo.models.Gallery;
import com.imgur.buzzfeeddemo.models.GalleryItem;
import com.imgur.buzzfeeddemo.util.RefreshCompleteListener;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Kevin Moturi on 6/3/2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final GalleryItem.GalleryItemClickListener clickListener;
    private List<GalleryItem> galleryItems = new ArrayList<>();

    public GalleryAdapter(GalleryItem.GalleryItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.gallary_card, parent, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewDataBinding viewDataBinding = holder.getViewDataBinding();
        GalleryItem galleryItem = galleryItems.get(position);
        viewDataBinding.setVariable(BR.galleryItem, galleryItem);
        viewDataBinding.getRoot().setOnClickListener(clickListener.onItemClick(galleryItem));
    }

    @Override
    public int getItemCount() {
        return (null != galleryItems ? galleryItems.size() : 0);
    }

    public void setData(List<GalleryItem> galleryItems) {
        this.galleryItems = galleryItems;
    }

    public void refreshGallery(String subreddit, RefreshCompleteListener refreshCompleteListener) {
        Gallery.requestData(subreddit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gallery -> {
                    setData(gallery.getItems());
                    notifyDataSetChanged();
                    refreshCompleteListener.onRefreshComplete();
                }, throwable -> {
                    refreshCompleteListener.onError(throwable);
                    Log.e(GalleryAdapter.class.getSimpleName(), throwable.getMessage());
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;

        public ViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            viewDataBinding = dataBinding;
            viewDataBinding.executePendingBindings();
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }
}
