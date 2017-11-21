package cn.com.luckytry.interview.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.Events;
import cn.com.luckytry.interview.bean.InterViewInfo;
import cn.com.luckytry.interview.util.LUtil;

/**
 * 有分类title的 ItemDecoration
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class TitleItemDecoration extends RecyclerView.ItemDecoration {
//    private List<InterviewBean> mDatas;
    private List<InterViewInfo> mDatas;
    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect



    private int mTitleHeight;//title的高
//    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
//    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private static int mTitleFontSize;//title字体大小
    private Context mContext;


    public TitleItemDecoration(Context context, List<InterViewInfo> datas) {
        super();
        this.mDatas = new ArrayList<>();
        this.mDatas.addAll(datas);
//        this.mDatas = datas;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
//        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);
                    LUtil.e("position："+ position+" "+mDatas.get(position).getType());
                } else {//其他的通过判断

                    if (null != mDatas.get(position).getType() && !mDatas.get(position).getType().equals(mDatas.get(position - 1).getType())) {
                        LUtil.e("position："+ position+" "+mDatas.get(position).getType()+"  前一个Type："+mDatas.get(position - 1).getType());
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        LUtil.e("drawTitleArea："+position);
        mPaint.setColor(mContext.getResources().getColor(R.color.type_bg));
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(mContext.getResources().getColor(R.color.type_text));
/*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;*/

        mPaint.getTextBounds(mDatas.get(position).getType(), 0, mDatas.get(position).getType().length(), mBounds);
        c.drawText(mDatas.get(position).getType(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {//最后调用 绘制在最上层

        if(mDatas.size()>0){
            int pos = ((LinearLayoutManager)(parent.getLayoutManager())).findFirstVisibleItemPosition();
            String tag = mDatas.get(pos).getType();
            //View child = parent.getChildAt(pos);
            View child = parent.findViewHolderForLayoutPosition(pos).itemView;//出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView
            mPaint.setColor(mContext.getResources().getColor(R.color.type_bg));
            c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
            mPaint.setColor(mContext.getResources().getColor(R.color.type_text));
            mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
            c.drawText(tag, child.getPaddingLeft(),
                    parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
                    mPaint);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
                Events<Integer> event = new Events();
                event.setContent(position);
                EventBus.getDefault().post(position);
            } else {//其他的通过判断
                if (null != mDatas.get(position).getType() && !mDatas.get(position).getType().equals(mDatas.get(position - 1).getType())) {
                    outRect.set(0, mTitleHeight, 0, 0);//不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                    Events<Integer> event = new Events();
                    event.setContent(position);
                    EventBus.getDefault().post(position);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

}
