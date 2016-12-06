package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import static android.R.attr.value;

/**
 * @author 曾定
 * Created by Ding on 2016/12/2.
 */

public class SlidingMenu extends HorizontalScrollView {
    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuWidth;
    //dp
    private int mMenuR=50;
    private boolean once;
    /*
    未使用自定义属性时，调用context，attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs){
        super(context,attrs);
        WindowManager wm= (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics outMerics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMerics);
        mScreenWidth=outMerics.widthPixels;
        //把dp转换为px
        mMenuR= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources()
                .getDisplayMetrics());
    }

    @Override
    /*
     *设置子View的宽和高
     * 设置自己的宽和高
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once){
            mWapper= (LinearLayout) getChildAt(0);
            mMenu= (ViewGroup) mWapper.getChildAt(0);
            mContent= (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth= mMenu.getLayoutParams().width=mScreenWidth-mMenuR;
            mContent.getLayoutParams().width=mScreenWidth;
            once=true;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    //设置偏移量，将menu隐藏
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
             this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX=getScrollX();
                if(scrollX >= mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                }else {
                    this.smoothScrollTo(0,0);
                }

                return true;

        }
        return super.onTouchEvent(ev);
    }
}
