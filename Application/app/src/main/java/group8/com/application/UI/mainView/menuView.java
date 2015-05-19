package group8.com.application.UI.mainView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.R;
import group8.com.application.UI.mainView.fragments.continueFinishFragment;
import group8.com.application.UI.mainView.fragments.login_reg_menu_fragment;
import group8.com.application.UI.mainView.fragments.start_menu_fragment;


public class menuView extends FragmentActivity {

    public static Context mContext;
    public static Context getContext(){

        return mContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mContext= getBaseContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);
        Controller.initMeasurements();
        if (findViewById(R.id.menuViewFrame) != null) {

            //Checks if there is already someone logged in.
            SharedPreferences sp = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Fragment fragment;
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if (Session.isPaused()) {
                fragment = new continueFinishFragment();
                transaction.replace(R.id.menuViewFrame, fragment, ConstantData.TAG_CONTINUEFINISHFRAGMENT);
            }
            else if (!sp.getString("username", "").equals("")) {
                Session.restart(sp.getString("username", ""));
                fragment = new start_menu_fragment();
                transaction.replace(R.id.menuViewFrame, fragment, ConstantData.TAG_STARTFRAGMENT);
            } else {
                fragment = new login_reg_menu_fragment();
                transaction.replace(R.id.menuViewFrame, fragment, ConstantData.TAG_LOGINREGISTERFRAGMENT);
            }
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed () {
        Fragment loginFrag = getFragmentManager().findFragmentByTag(ConstantData.TAG_LOGINREGISTERFRAGMENT);
        Fragment startFrag = getFragmentManager().findFragmentByTag(ConstantData.TAG_STARTFRAGMENT);
        Fragment continueFinishFrag = getFragmentManager().findFragmentByTag(ConstantData.TAG_CONTINUEFINISHFRAGMENT);
        if (loginFrag != null && loginFrag.isVisible()) {
            moveTaskToBack(true);
        }
        else if ((startFrag != null || continueFinishFrag != null) && (startFrag.isVisible() || continueFinishFrag.isVisible())) {
            //MAYBE ASK IF USER WANTS TO LOG OUT
            moveTaskToBack(true);
        } else {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            fm.popBackStack();
            transaction.commit();
        }
    }
}
