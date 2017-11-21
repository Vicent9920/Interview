package cn.com.luckytry.interview.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.com.luckytry.interview.R;
import cn.com.luckytry.interview.util.SharedPrefsUtil;

/**
 * Created by 魏兴 on 2017/8/27.
 */

public class SetDialog {
    private Context mContext;
    private View mView;
    private TextView tvInfo,txt_cancel;
    private Dialog dialog;

    public SetDialog(Context context){
        this.mContext = context;
    }

    /**
     *
     * @return
     */
    public SetDialog builder() {
        // 获取Dialog布局
        mView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog, null);

        // 获取自定义Dialog布局中的控件
        tvInfo = (TextView) mView.findViewById(R.id.message);
        Drawable rightDrawable;
        final boolean isImage = SharedPrefsUtil.getValue(mContext,"BlockNetworkImage",false,false);
        if(isImage){
            rightDrawable = mContext.getResources().getDrawable(R.drawable.ic_checked);
        }else{
            rightDrawable = mContext.getResources().getDrawable(R.drawable.ic_checknorml);
        }

        rightDrawable.setBounds(0, 0, rightDrawable.getIntrinsicWidth(), rightDrawable.getIntrinsicHeight());
        tvInfo.setCompoundDrawables(null, null, rightDrawable, null);
        txt_cancel = (TextView) mView.findViewById(R.id.no);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPrefsUtil.putValue(mContext,"BlockNetworkImage",(!isImage));
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        dialog.setContentView(mView);

        return this;
    }

    public void show(){
        dialog.show();
    }
}
