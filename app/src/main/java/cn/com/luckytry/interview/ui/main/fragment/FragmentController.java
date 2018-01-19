package cn.com.luckytry.interview.ui.main.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * 主界面Fragment控制器
 * Created by 魏兴 on 2017/12/28.
 */

public class FragmentController {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentController controller;
    private static boolean isReload;

    public static FragmentController getInstance(FragmentActivity activity, int containerId, boolean isReload) {
        FragmentController.isReload = isReload;
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        if (isReload) {

            fragments.add(new FileGruopFragment());
            fragments.add(new AboutFragment());
//            fragments.add(new AttentionFragment());
//            fragments.add(new MeFragment());

            FragmentTransaction ft = fm.beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                ft.add(containerId, fragments.get(i), "" + i);
            }
            ft.commit();

        } else {
            for (int i = 0; i < 5; i++) {
                fragments.add( fm.findFragmentByTag(i+""));
            }
        }
    }


    public void showFragment(int position,String group) {
        if(position == 0){
            FileGruopFragment fragment = (FileGruopFragment) showFragment(position);
            fragment.updateData(group);
        }else{
            showFragment(position);
        }

    }

    private Fragment showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);

        FragmentTransaction ft = fm.beginTransaction();
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.show(fragment);
        ft.commitAllowingStateLoss();
        return fragment;
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public void removeFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.remove(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }
    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
