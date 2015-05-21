package group8.com.application.Foundation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import group8.com.application.Application.Controller;
import group8.com.application.R;

public class RankingAdapter extends SimpleAdapter {
    //private int color = Color.RED;
    int resource;
    String username;
    Context context;
    List<String> friendList;

    public RankingAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to, String username) {
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
        TextView rp = (TextView) view.findViewById(R.id.rankingPosition);
        TextView tp = (TextView) view.findViewById(R.id.totalPoints);
        TextView dis = (TextView) view.findViewById(R.id.distraction);
        TextView fu = (TextView) view.findViewById(R.id.fuel);
        TextView br = (TextView) view.findViewById(R.id.brake);
        TextView sp = (TextView) view.findViewById(R.id.speed);

        ImageView iv = (ImageView) view.findViewById(R.id.friendIcon);
        String currentUser = (String)userTxt.getText();
        if (currentUser.equals(username)) {
            view.setBackgroundColor(resource);
            view.setActivated(true);
            userTxt.setTextColor(Color.WHITE);
            rp.setTextColor(Color.WHITE);
            tp.setTextColor(Color.WHITE);
            dis.setTextColor(Color.WHITE);
            fu.setTextColor(Color.WHITE);
            br.setTextColor(Color.WHITE);
            sp.setTextColor(Color.WHITE);
        }
        else {
            view.setActivated(false);
            view.setBackgroundColor(resource);
            userTxt.setTextColor(Color.parseColor("#5d5d5d"));
            rp.setTextColor(Color.parseColor("#333000"));
            tp.setTextColor(Color.parseColor("#333000"));
            dis.setTextColor(Color.parseColor("#acacac"));
            fu.setTextColor(Color.parseColor("#acacac"));
            br.setTextColor(Color.parseColor("#acacac"));
            sp.setTextColor(Color.parseColor("#acacac"));
        }
        if (friendList.contains(currentUser)) {
            Log.d("Contains in list;" , currentUser);
            iv.setVisibility(View.VISIBLE);
        }
        else {
            iv.setVisibility(View.INVISIBLE);
        }
        rp.setText(Integer.toString(position + 1) + ".");
        return view;
    }
}
