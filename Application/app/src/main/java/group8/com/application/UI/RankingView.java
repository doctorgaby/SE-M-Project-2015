package group8.com.application.UI;
import group8.com.application.Application.Controller;
import group8.com.application.Application.Session;
import group8.com.application.Foundation.RankingAdapter;
import group8.com.application.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import group8.com.application.Model.ConstantData;

public class RankingView extends ListActivity {
    private ArrayList<HashMap<String, String>> rankingList;
    private ArrayList<HashMap<String, String>> searchResults;
    private Button changeView;
    private EditText searchTxt;
    private boolean allView;
    private String userClicked = "";
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_display);
        searchTxt = (EditText) findViewById(R.id.searchTxt);
        changeView = (Button) findViewById(R.id.changeView);
        changeView.setText("Friends");
        allView = true;
        lv = (ListView) findViewById(android.R.id.list);
        loadRanking();

        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allView) {
                    allView = false;
                    loadRanking();
                } else {
                    allView = true;
                    loadRanking();
                }
            }
        });
        searchResults = new ArrayList<>();
        searchTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                int textlength = searchTxt.getText().length();
                String searchString= searchTxt.getText().toString();
                searchResults.clear();
                String attr;
                for (int i = 0; i < rankingList.size(); i++)
                {

                    attr = rankingList.get(i).get(ConstantData.TAG_USERNAME).trim();
                    Log.i("attr", attr);

                    if (textlength  <= attr.length())
                    {

                        if (searchString.equalsIgnoreCase(attr.substring(0,textlength)))
                        {
                            searchResults.add(rankingList.get(i));
                        }
                    }
                }
                updateList(searchResults);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    public void updateJSONdata() {
        if (allView) {
            rankingList = Controller.getAllRankings();
        } else {
            rankingList = Controller.getFriendsRankings();
        }
    }

    private void updateList(ArrayList<HashMap<String, String>> list) {
        ListAdapter adapter = new RankingAdapter(this, list,
                R.layout.single_post, new String[]{ConstantData.TAG_SPEED, ConstantData.TAG_FUEL,
                ConstantData.TAG_BRAKE, ConstantData.TAG_DISTRACTION, ConstantData.TAG_USERNAME, "totalPoints", "position"}
                , new int[]{R.id.speed, R.id.fuel, R.id.brake, R.id.distraction, R.id.username, R.id.totalPoints, R.id.rankingPosition}, Session.getUserName());
        setListAdapter(adapter);
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                userClicked = rankingList.get(position).get(ConstantData.TAG_USERNAME);
                ImageView iv = (ImageView) view.findViewById(R.id.friendIcon);
                if ((!userClicked.equals(Session.getUserName()) && (iv.getVisibility() == View.INVISIBLE || !allView))){
                    view.setSelected(true);
                    doOptionDialogue(view);
                } else {
                    userClicked = "";
                }
            }
        });
    }

    public void loadRanking() {
        updateJSONdata();
        updateList(rankingList);
        if (allView) {
            changeView.setText("Friends");
        } else {
            changeView.setText("All");
        }
    }

    public void addFriend(String userClicked) {
        int success = 0;
        String message = "Error with JSON.";
        if (allView) {
            JSONObject json = Controller.addFriend(userClicked);
            try {
                success = json.getInt(ConstantData.TAG_SUCCESS);
                message = json.getString(ConstantData.TAG_MESSAGE);
            } catch (JSONException e) {
                Log.e("RankingView" , "JSON Error");
            }
            if (success == 1) {
                Toast toast = Toast.makeText(getApplicationContext(), "You just added: " + userClicked + " as a friend.", Toast.LENGTH_SHORT);
                toast.show();
                loadRanking();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            JSONObject json = Controller.removeFriend(userClicked);
            try {
                success = json.getInt(ConstantData.TAG_SUCCESS);
                message = json.getString(ConstantData.TAG_MESSAGE);
            } catch (JSONException e) {
                Log.e("RankingView" , "JSON Error");
            }
            if (success == 1) {
                Toast toast = Toast.makeText(getApplicationContext(), "You just removed: " + userClicked + " as a friend.", Toast.LENGTH_SHORT);
                toast.show();
                loadRanking();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        lv.clearFocus();
        this.userClicked = "";
    }

    public void closeDialog() {
        userClicked = "";
        lv.clearChoices();
        lv.requestLayout();
    }

    public void doOptionDialogue(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RankingView.this);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        String choice;
        String text;
        if (allView)
            choice = "Add";
        else
            choice = "Remove";
        if (allView)
            text = "Add " + userClicked + " to your friends?";
        else
            text = "Remove " + userClicked + " from your friends?";
        builder.setTitle(text);
       /* TextView textView = new TextView(RankingView.this);
        textView.setText(text);
        builder.setCustomTitle(textView);*/
        builder.setIcon(R.drawable.addfriendicon);
        builder.setPositiveButton(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addFriend(userClicked);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface dialog) {
                closeDialog();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.START;
        wmlp.x = view.getLeft();
        wmlp.y = view.getTop();
        dialog.getWindow().setAttributes(wmlp);
        dialog.show();
    }
}
