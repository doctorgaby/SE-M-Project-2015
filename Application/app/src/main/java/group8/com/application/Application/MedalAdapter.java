package group8.com.application.Application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import group8.com.application.Model.Medal;
import group8.com.application.R;
import group8.com.application.UI.MedalsView;

/**
 * Created by Kristiyan on 5/12/2015.
 */
public class MedalAdapter extends ArrayAdapter<Medal> {

    private Context mContext;
    List<Medal> mylist;

    public MedalAdapter(Context context, List<Medal> mylist) {
        super(context, R.layout.medal_item, mylist);

        mContext = context;
        this.mylist = mylist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Medal medal = getItem(position);

        MedalViewHolder holder;

        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.medal_item, parent, false);

            //
            holder = new MedalViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.medal_image);
            holder.title = (TextView)convertView.findViewById(R.id.title);

            //
            convertView.setTag(holder);
        }
        else{
            holder = (MedalViewHolder) convertView.getTag();
        }

        //

        holder.populate(medal, ((MedalsView) mContext).isLvBusy());

        //
        return convertView;
    }




    static class MedalViewHolder {
        public ImageView img;
        public TextView title;

        void populate(Medal m) {
            title.setText(m.title);

            //

        }

        void populate(Medal m, boolean isBusy) {
            title.setText(m.title);

            if (!isBusy){
                // download from internet
                img.setImageResource(m.img_url);

            }
            else{
                // set default image
                img.setImageResource(m.img_url);
            }
        }
    }

}
