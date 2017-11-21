package cn.com.luckytry.interview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterViewInfo;
import cn.com.luckytry.interview.ui.diyFile.ContentActivity;

/**
 * Created by asus on 2017/9/4.
 */
public class MyAdapter extends CommonAdapter<InterViewInfo> {

    private Context mContext;

    private Toast toast;

    public MyAdapter( Context context, int layoutId, List<InterViewInfo> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
        toast = Toast.makeText(mContext,"",Toast.LENGTH_SHORT);
    }

//    public void setData(List<InterviewBean> data) {
//        this.data = data;
//        notifyDataSetChanged();
//    }

    @Override
    protected void convert(ViewHolder holder, final InterViewInfo interviewBean, int position) {
        holder.setText(R.id.tvInterview, interviewBean.getName());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interviewBean.getFile_Type() == 1){
                    Intent intent = new Intent(mContext, ContentActivity.class);
                    intent.putExtra("Id", interviewBean.getObjectId());
                    intent.putExtra("name", interviewBean.getName());
                    intent.putExtra("tag", interviewBean.getType());
                    mContext.startActivity(intent);
                }

            }
        });
    }
}
