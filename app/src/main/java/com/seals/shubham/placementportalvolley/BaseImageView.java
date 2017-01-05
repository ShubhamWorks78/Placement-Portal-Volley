package com.seals.shubham.placementportalvolley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by shubham on 1/4/2017.
 */

public abstract class BaseImageView extends ImageView{

    private final String TAG = BaseImageView.class.getCanonicalName();

    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    private Bitmap mBitmap;
    private WeakReference<Bitmap> mWeakBitmap;
    protected Context mContext;
    private Paint mPaint;
    public BaseImageView(Context context) {
        super(context);
        sharedConstructor(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sharedConstructor(context);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        sharedConstructor(context);
    }

    public void sharedConstructor(Context context){
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void invalidate(){
        mWeakBitmap = null;
        if(mBitmap != null){
            mBitmap.recycle();
        }
        super.invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas){
        if(!isInEditMode()){
            int i = canvas.saveLayer(0.0f ,0.0f ,getWidth() ,getHeight(),
                    null,Canvas.ALL_SAVE_FLAG);
            try{
                Bitmap bitmap = mWeakBitmap != null ? mWeakBitmap.get() : null;

                if(bitmap == null || bitmap.isRecycled()){
                    Drawable drawable = getDrawable();
                    if(drawable != null){
                        bitmap = Bitmap.createBitmap(getWidth(),getHeight(),
                                Bitmap.Config.ARGB_8888);
                        Canvas bitmapCanvas = new Canvas(bitmap);
                        drawable.setBounds(0,0,getWidth(), getHeight());
                        drawable.draw(bitmapCanvas);

                        if(mBitmap==null || mBitmap.isRecycled()){
                            mBitmap = getBitmap();
                        }

                        mPaint.reset();
                        mPaint.setFilterBitmap(false);
                        mPaint.setXfermode(sXfermode);

                        bitmapCanvas.drawBitmap(mBitmap, 0.0f, 0.0f ,mPaint);

                        mWeakBitmap = new WeakReference<>(bitmap);
                    }
                }

                if(bitmap != null){
                    mPaint.setXfermode(null);

                    canvas.drawBitmap(bitmap, 0.0f, 0.0f ,mPaint);
                }
            }catch (Exception E){
                System.gc();

                Log.e(TAG,String.format("Failed to draw Id :: %s,Error Occurred :: %s",getId(),E.toString()));
            }
            finally {
                canvas.restoreToCount(i);
            }
        }
    }
    public abstract Bitmap getBitmap();
}
