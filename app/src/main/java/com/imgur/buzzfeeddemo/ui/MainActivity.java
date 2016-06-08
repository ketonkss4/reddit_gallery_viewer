package com.imgur.buzzfeeddemo.ui;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.imgur.buzzfeeddemo.AppConfig;
import com.imgur.buzzfeeddemo.BR;
import com.imgur.buzzfeeddemo.R;
import com.imgur.buzzfeeddemo.adapter.GalleryAdapter;
import com.imgur.buzzfeeddemo.databinding.ActivityMainBinding;
import com.imgur.buzzfeeddemo.models.GalleryItem;
import com.imgur.buzzfeeddemo.util.RefreshCompleteListener;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, GalleryItem.GalleryItemClickListener {

    private GalleryAdapter adapter;
    private ActivityMainBinding activityMainBinding;
    private View bottomSheet;
    private BottomSheetBehavior<View> behavior;
    private ViewDataBinding bottomSheetBinding;
    private String currentSubreddit = AppConfig.DEFAULT_SUBREDDIT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RecyclerView recyclerView = activityMainBinding.galleryGrid;

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new GalleryAdapter(this);
            bottomSheet = activityMainBinding.getRoot().findViewById(R.id.bottom_sheet);
            bottomSheetBinding = DataBindingUtil.bind(bottomSheet);
            recyclerView.setAdapter(adapter);
            activityMainBinding.refreshLayout.setOnRefreshListener(this);
            activityMainBinding.refreshLayout.setRefreshing(true);
            setupBottomSheet();
            activityMainBinding.fab.setOnClickListener(v -> promptNewSubredditInput());
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    scrollBottomSheetDown();
                }
            });
            onRefresh();
        }

    }



    @Override
    public void onRefresh() {
        if (adapter != null)
            adapter.refreshGallery(currentSubreddit, new RefreshCompleteListener() {
                @Override
                public void onRefreshComplete() {
                    activityMainBinding.refreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(Throwable throwable) {
                    MainActivity.this.onError(throwable);
                }
            });

    }

    private void promptNewSubredditInput() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View promptsView = inflater.inflate(R.layout.subreddit_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final EditText subredditInput = (EditText) promptsView.findViewById(R.id.subreddit_input);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, id) -> {
                            currentSubreddit = subredditInput.getText().toString();
                            onRefresh();
                        })
                .setNegativeButton("Cancel",
                        (dialog, id) -> {
                            dialog.cancel();
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setupBottomSheet() {
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.v("On Bottom Sheet Slide", "State: "+behavior.getState()+" offset: "+slideOffset);
                if (slideOffset == 0) activityMainBinding.fab.show();
            }
        });
        hideBottomSheet();
    }

    private void scrollBottomSheetUp() {
        bottomSheet.post(() -> behavior.setState(BottomSheetBehavior.STATE_EXPANDED));
    }

    private void hideBottomSheet() {
        bottomSheet.post(() -> behavior.setState(BottomSheetBehavior.STATE_HIDDEN));
    }

    private void scrollBottomSheetDown() {
        bottomSheet.post(() -> behavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
    }

    @Override
    public View.OnClickListener onItemClick(GalleryItem item) {
        return v -> {
            bottomSheetBinding.setVariable(BR.galleryItem, item);
            bottomSheetBinding.notifyPropertyChanged(BR.galleryItem);
            ImageView itemImage = (ImageView) bottomSheetBinding.getRoot().findViewById(R.id.item_img);
            itemImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            scrollBottomSheetUp();
            activityMainBinding.fab.hide();
        };
    }

    public void onError(Throwable throwable) {
        Log.v(MainActivity.class.getSimpleName(), throwable.getMessage());
        Snackbar.make(activityMainBinding.getRoot(), throwable.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

}
