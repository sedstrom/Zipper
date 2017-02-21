package com.example.zipper;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.BindToView;

public class MainViewModel {

    @BindTo(R.id.main_activity_fragment_container)
    public final Fragment fragment;

    @BindTo(R.id.main_activity_toolbar)
    public final boolean displayHomeAsUp;

    @BindToView(id = R.id.main_activity_toolbar, view = Toolbar.class, set = "title")
    public final String title;

    public MainViewModel(Fragment fragment, boolean displayHomeAsUp, String title) {
        this.fragment = fragment;
        this.displayHomeAsUp = displayHomeAsUp;
        this.title = title;
    }
}
