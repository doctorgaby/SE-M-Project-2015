package group8.com.application.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import group8.com.application.Model.ConstantData;
import group8.com.application.R;

/**
 * Created by kikedaddy on 14/05/15.
 */
public class login_reg_menu_fragment extends Fragment implements View.OnClickListener {
    Button login;
    Button register;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_reg_menu_fragment, container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login = (Button) view.findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        register = (Button) view.findViewById(R.id.registerButton);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        String tag;
        switch(v.getId()){
            case R.id.loginButton:
                fragment = new loginFragment();
                transaction.addToBackStack(null);
                tag = ConstantData.TAG_LOGINFRAGMENT;
                break;
            case R.id.registerButton:
                fragment = new registerFragment();
                transaction.addToBackStack(null);
                tag = ConstantData.TAG_REGISTERFRAGMENT;
                break;
            default:
                fragment = new login_reg_menu_fragment();
                tag = ConstantData.TAG_LOGINREGISTERFRAGMENT;
        }

        transaction.replace(R.id.menuViewFrame, fragment, tag);
        transaction.commit();
    }
}
