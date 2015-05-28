package group8.com.application.Foundation;

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

    /**
     * the adapter for the grid view
     * and it's items
     *
     * @param context
     * @param mylist container for medals
     */

    public MedalAdapter(Context context, List<Medal> mylist) {
        super(context, R.layout.medal_item, mylist);

        mContext = context;
        this.mylist = mylist;
    }

    /** Core method
     *
     * Method necessary for the grid view
     *
     * @param position of the item in grid view
     * @param convertView the view
     * @param parent parent view group
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Medal medal = getItem(position);

        //holder for the medals
        MedalViewHolder holder;

        //if the convert view does not exist , create one
        //inflates a the grid view
        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.medal_item, parent, false);

            holder = new MedalViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.medal_image);
            holder.title = (TextView)convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        }
        else{
            holder = (MedalViewHolder) convertView.getTag();
        }

        holder.populate(medal, ((MedalsView) mContext).isLvBusy());

        return convertView;
    }

    /**
     * inner class used for holding
     * the medal items
     */
    static class MedalViewHolder {
        public ImageView img;
        public TextView title;

        //method for populating the grid view
        void populate(Medal m, boolean isBusy) {
            title.setText(m.title);

            if (m.isAchieved && !isBusy){
                //set medal image if it's achieved
                img.setImageResource(m.img_url);

            }
            else {
                // set default image
                img.setImageResource(R.drawable.locked);
            }
        }
    }
}
