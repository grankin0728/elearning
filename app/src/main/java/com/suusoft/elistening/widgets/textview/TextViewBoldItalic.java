package com.suusoft.elistening.widgets.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewBoldItalic extends TextView {

    public TextViewBoldItalic(Context context) {
        super(context);

        this.setTypeface(TypeFaceUtil.getInstant().italic);
    }

    public TextViewBoldItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(TypeFaceUtil.getInstant().italic);
    }

    public TextViewBoldItalic(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(TypeFaceUtil.getInstant().italic);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
