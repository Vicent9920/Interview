package cn.com.luckytry.interview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterviewBean;
import cn.com.luckytry.interview.diycode.ContentActivity;

/**
 * Created by asus on 2017/9/4.
 */
public class MyAdapter extends CommonAdapter<InterviewBean> {

    private Context mContext;
    private List<InterviewBean> data;
    private Toast toast;

    public MyAdapter( Context context, int layoutId, List<InterviewBean> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        this.data = datas;
        toast = Toast.makeText(mContext,"",Toast.LENGTH_SHORT);
    }

    public void setData(List<InterviewBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, final InterviewBean interviewBean, int position) {
        holder.setText(R.id.tvInterview, interviewBean.getName());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interviewBean.isCanLoad()) {
                    Intent intent = new Intent(mContext, ContentActivity.class);
                    intent.putExtra("url", interviewBean.getAdress());
                    intent.putExtra("name", interviewBean.getName());
                    intent.putExtra("tag", interviewBean.getTag());
                    mContext.startActivity(intent);
                } else {
                    toast.setText(interviewBean.getName() + " 文章内容正在编写中！");
                    toast.show();
                }
            }
        });
    }
}
