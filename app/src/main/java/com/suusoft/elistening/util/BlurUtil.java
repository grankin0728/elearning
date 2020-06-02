//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.support.v8.renderscript.Allocation;
//import android.support.v8.renderscript.Element;
//import android.support.v8.renderscript.RenderScript;
//import android.support.v8.renderscript.ScriptIntrinsicBlur;
//
///**
// * Created by Suusoft on 3/23/2017.
// */
//
//public class BlurUtil {
//
//    private static final float BITMAP_SCALE = 0.4f;
//    private static final float BLUR_RADIUS = 7.0f;
//
//    public static Bitmap blur(Context context, Bitmap img) {
//        int mWidth = Math.round(img.getWidth() * BITMAP_SCALE);
//        int mHeight = Math.round(img.getHeight() * BITMAP_SCALE);
//        Bitmap givenBitmap = Bitmap.createScaledBitmap(img, mWidth, mHeight, false);
//        Bitmap takenBitmap = Bitmap.createBitmap(givenBitmap);
//        RenderScript rs = RenderScript.create(context);
//        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//        Allocation tmpIN = Allocation.createFromBitmap(rs, givenBitmap);
//        Allocation tmpOut = Allocation.createFromBitmap(rs, takenBitmap);
//        intrinsicBlur.setRadius(BLUR_RADIUS);
//        intrinsicBlur.setInput(tmpIN);
//        intrinsicBlur.forEach(tmpOut);
//        tmpOut.copyTo(takenBitmap);
//        return takenBitmap;
//    }
//
//}
