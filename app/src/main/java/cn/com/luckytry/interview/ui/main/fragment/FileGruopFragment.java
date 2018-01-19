package cn.com.luckytry.interview.ui.main.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterViewMoudle;
import cn.com.luckytry.interview.ui.diyFile.ContentActivity;
import cn.com.luckytry.interview.ui.widget.DividerItemDecoration;
import cn.com.luckytry.interview.util.LUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileGruopFragment extends Fragment {

    private Context mContext;
    private RelativeLayout loadLayout;
    private RecyclerView mRv;
    private List<InterViewMoudle> data = new ArrayList<>();
    private MyAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_gruop, container, false);
        initView(view);
        return view;
    }



    private void initView(View view) {
        loadLayout = (RelativeLayout) view.findViewById(R.id.ll_rl_loading);
        mRv = (RecyclerView) view.findViewById(R.id.rv_gruops);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));

        mRv.setAdapter(adapter = new MyAdapter(mContext,R.layout.item_interview,data) );

    }

    private void getGroupData(String groupName) {
        if("star".equals(groupName)){
            data = DataSupport.where("isStar = ?", "1").find(InterViewMoudle.class);
        }else{
            data = DataSupport.where("groupname = ?", groupName).find(InterViewMoudle.class);
        }

        if(data.size() == 0){
            return;        }
        adapter = new MyAdapter(mContext,R.layout.item_interview,data);
        mRv.setAdapter(adapter);
        mRv.setVisibility(View.VISIBLE);
        loadLayout.clearAnimation();
        loadLayout.setVisibility(View.GONE);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadLayout.getParent().recomputeViewAttributes(loadLayout);
        if(loadLayout!=null){
            loadLayout = null;
        }
        LUtil.e("onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LUtil.e("onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LUtil.e("onDestroy");

    }

    public void updateData(String groupName){

        getGroupData(groupName);
    }

    class MyAdapter extends CommonAdapter<InterViewMoudle>{
        private List<InterViewMoudle> data;
        public MyAdapter(Context context, int layoutId, List<InterViewMoudle> datas) {
            super(context, layoutId, datas);
            this.data = datas;
        }

        @Override
        protected void convert(ViewHolder holder, final InterViewMoudle interViewMoudle, int position) {
            LUtil.e(interViewMoudle.getFileName());
           if(interViewMoudle.isRead()){
               holder.setTextColor(R.id.tv_Interview, Color.BLUE);
           }else{
               holder.setTextColor(R.id.tv_Interview, Color.BLACK);
           }
            holder.setText(R.id.tv_Interview,interViewMoudle.getFileName());
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ContentActivity.class);
                    intent.putExtra("tag",interViewMoudle.getGroupName());
                    intent.putExtra("name",interViewMoudle.getFileName());
                    getContext().startActivity(intent);
                }
            });
        }


    }
}
