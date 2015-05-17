package group8.com.application.Foundation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import group8.com.application.Application.Controller;
import group8.com.application.Application.Database.DBHandler;
import group8.com.application.R;

/**
 * Created by kikedaddy on 11/05/15.
 */
public class CustomAdapter extends SimpleAdapter {
    //private int color = Color.RED;
    int resource;
    String username;
    Context context;
    List<String> friendList;

        public CustomAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to, String username) {
            super(context, items, resource, from, to);
            this.resource = resource;
            this.context = context;
            this.username = username;
            friendList = Controller.eventGetAllFriends();
            Log.d("FriendsList:", friendList.toString());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView userTxt = (TextView) view.findViewById(R.id.username);
            ImageView iv = (ImageView) view.findViewById(R.id.friendIcon);
            String currentUser = (String)userTxt.getText();
            if (currentUser.equals(username)) {
                view.setBackgroundColor(context.getResources().getColor(R.color.user_color));
            } else {
                view.setBackgroundColor(resource);
            }
            TextView tv = (TextView) view.findViewById(R.id.rankingPosition);
            if (friendList.contains(currentUser)) {
                Log.d("Contains in list;" , currentUser);
                iv.setVisibility(View.VISIBLE);
            }
            else {
                iv.setVisibility(View.INVISIBLE);
            }
            tv.setText(Integer.toString(position +1)+".");
            return view;
        }
}
