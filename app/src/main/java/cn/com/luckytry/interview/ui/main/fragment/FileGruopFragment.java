package cn.com.luckytry.interview.ui.main.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.bean.InterViewMoudle;
import cn.com.luckytry.interview.ui.diyFile.ContentActivity;
import cn.com.luckytry.interview.ui.widget.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileGruopFragment extends Fragment {

    private static String FILE_KEY = "file_key";
    private Context mContext;
    private String mGroupName;
    private View loadLayout;
    private RecyclerView mRv;
    public FileGruopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGroupName = getArguments().getString(FILE_KEY);
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
        loadLayout = view.findViewById(R.id.ll_rl_loading);
        mRv = (RecyclerView) view.findViewById(R.id.rv_gruops);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        mRv.setAdapter(new CommonAdapter<InterViewMoudle>(mContext,R.layout.item_interview,new ArrayList<InterViewMoudle>()) {

            @Override
            protected void convert(ViewHolder holder, InterViewMoudle interViewMoudle, int position) {

            }
        });


    }

    class GroupAdapter extends CommonAdapter<InterViewMoudle>{

        private List<InterViewMoudle> data;
        public GroupAdapter(Context context, int layoutId, List<InterViewMoudle> datas) {
            super(context, layoutId, datas);
            this.data = datas;
        }

        @Override
        protected void convert(ViewHolder holder, final InterViewMoudle interViewMoudle, int position) {
            holder.setText(R.id.tvInterview, interViewMoudle.getFileName());
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ContentActivity.class);
                    intent.putExtra("Id", interViewMoudle.getId());
                    mContext.startActivity(intent);

                }
            });
        }

        public void updateData(List<InterViewMoudle> data){
            this.data = data;
            notifyDataSetChanged();
        }
    }

}
