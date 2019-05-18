package com.wyy.wanandroidcilent.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

//自定义圆形ImageView
public class CircleImageView extends AppCompatImageView {
    private float width;
    private float height;
    private float radius;   //半径
    private Paint paint;    //画笔
    private Matrix matrix;  //设置缩放
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        radius = Math.min(width,height) / 2;          //计算半径
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();  //获取图片
        if (drawable == null){
            super.onDraw(canvas);
            return;
        }else{
            BitmapShader shader = initBitmapShader(drawable);   //获取着色器
            paint.setShader(shader);                            //为画笔设置着色器
            canvas.drawCircle(width/2,height/2,radius,paint);
            return;
        }
    }

    /**
     * 将图片加载为bitmap对象，进一步得到着色器，返回着色器
     * @param drawable
     * @return
     */
    private BitmapShader initBitmapShader(Drawable drawable){
        Bitmap bitmap = null;
        if (drawable instanceof  BitmapDrawable){
            BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
            bitmap = bitmapDrawable.getBitmap();
        }else {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(drawableWidth,drawableHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0,0,drawableWidth,drawableHeight);
            drawable.draw(canvas);
        }

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        final float scale = Math.max(width/bitmap.getWidth(),height/bitmap.getHeight());
        matrix.setScale(scale,scale);
        shader.setLocalMatrix(matrix);
        return  shader;
    }
}
