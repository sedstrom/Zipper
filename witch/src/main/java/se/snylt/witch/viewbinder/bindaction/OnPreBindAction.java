package se.snylt.witch.viewbinder.bindaction;

import android.view.View;

public interface OnPreBindAction<V extends View, Value> extends BindAction {
    void onPreBind(V view, Value value, PreBindDone preBindDone);
}