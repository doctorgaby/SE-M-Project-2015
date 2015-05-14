package group8.com.application.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import group8.com.application.Application.Session;
import group8.com.application.Model.ConstantData;
import group8.com.application.R;

/**
 * Created by kikedaddy on 14/05/15.
 */
public class menuView extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);
        if (findViewById(R.id.menuViewFrame) != null) {

            if (savedInstanceState != null) {
                return;
            }

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
