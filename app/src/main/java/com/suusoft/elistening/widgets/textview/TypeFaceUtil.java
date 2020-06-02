package com.suusoft.elistening.widgets.textview;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by phamv on 2/28/2017.
 */

public class TypeFaceUtil {

    private static TypeFaceUtil instance;

    public Typeface bold, normal, italic, boldItalic, light, lightItalic;

    public static TypeFaceUtil getInstant(){
        if (instance == null) {
            instance = new TypeFaceUtil();
        }

        return instance;
    }

    public void initTypeFace(Context context){
        instance.bold = Typeface.createFromAsset(context.getAssets(), TextFontConfig.FONT_BOLT);
        instance.normal = Typeface.createFromAsset(context.getAssets(), TextFontConfig.FONT_REGULAR);
        instance.italic = Typeface.createFromAsset(context.getAssets(), TextFontConfig.FONT_ITALIC);
        instance.boldItalic = Typeface.createFromAsset(context.getAssets(), TextFontConfig.FONT_BOLT_ITALIC);
        instance.light = Typeface.createFromAsset(context.getAssets(), TextFontConfig.FONT_LIGHT);
        instance.lightItalic = Typeface.createFromAsset(context.getAssets(), TextFontConfig.FONT_LIGHT_ITALIC);
    }

}
