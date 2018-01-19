package cn.com.luckytry.interview;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment with a Google +1 button.
 */
public class PlusOneFragment extends Fragment implements View.OnClickListener {

    private View tvContent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plus_one, container, false);

        view.findViewById(R.id.btn_test).setOnClickListener(this);
        tvContent = view.findViewById(R.id.ll_rl_loading);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvContent.setVisibility(View.VISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                tvContent.setVisibility(View.GONE);
            }
        },2000);
        // Refresh the state of the +1 button each time the activity receives focus.
    }


    @Override
    public void onClick(View v) {
        getActivity().recreate();
    }
}
