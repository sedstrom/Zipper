package com.example.witch.app;

import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindWhen;
import android.view.View;

class ErrorDataHasParameters {

    @Data
    String text(String parameter) {
        return "text";
    }
}
