package com.sherpa.sherpa;

/**
 * Created by mohnishkadakia on 11/1/15.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sherpa.sherpa.custom.CustomActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class UserList is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class UserList extends CustomActivity
{
    /** The Chat list. */
    private ArrayList<ParseUser> uList;
    /** The user. */
    public static ParseUser user;

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        //getActionBar().setDisplayHomeAsUpEnabled(false);
        user = ParseUser.getCurrentUser();
        user.put("online", true);
        user.saveInBackground();
        //uList = new ArrayList<ParseUser>();
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUserList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        updateUserStatus(false);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        updateUserStatus(true);
        loadUserList();
    }

    /**
     * Update user status.
     *
     * @param online
     *            true if user is online
     */
    private void updateUserStatus(boolean online)
    {
        user.put("online", online);
        user.saveInBackground();
    }

    /**
     * Load list of users.
     */
    private void loadUserList()
    {
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_loading));
        //ListView list = (ListView) findViewById(R.id.list);
        /*ParseUser.getQuery().whereNotEqualTo("username", user.getUsername())
                .findInBackground(new FindCallback<ParseUser>() {

                    @Override
                    public void done(List<ParseUser> li, ParseException e)
                    {
                        dia.dismiss();
                        if (li != null)
                        {
                            if (li.size() == 0)
                                Toast.makeText(UserList.this,
                                        R.string.msg_no_user_found,
                                        Toast.LENGTH_SHORT).show();

                            uList = new ArrayList<ParseUser>(li);
                            ListView list = (ListView) findViewById(R.id.list);
                            list.setAdapter(new UserAdapter());
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0,
                                                        View arg1, int pos, long arg3) {
                                    startActivity(new Intent(UserList.this,
                                            Chat.class).putExtra(
                                            Const.EXTRA_DATA, uList.get(pos)
                                                    .getUsername()));
                                }
                            });
                        } else {
                            Utils.showDialog(
                                    UserList.this,
                                    getString(R.string.err_users) + " "
                                            + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });*/
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Chat");   //gets the chat object
        query.whereEqualTo("receiver", ParseUser.getCurrentUser().getUsername())
                .findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list1, ParseException e) {
                        if (list1 != null) {
                            if (list1.size() == 0) {
                                Toast.makeText(UserList.this,
                                        R.string.msg_no_user_found,
                                        Toast.LENGTH_SHORT).show();
                            }
                            final ListView list = (ListView) findViewById(R.id.list);
                            dia.dismiss();
                            final ArrayList<String> senders = new ArrayList<>();
                            for (ParseObject chat : list1) {
                                if (!senders.contains(chat.getString("sender"))) {
                                    senders.add(chat.getString("sender"));
                                }
                            }
                            ParseQuery<ParseUser> query1 = ParseUser.getQuery();
                            query1.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername())
                                    .findInBackground(new FindCallback<ParseUser>() {
                                        @Override
                                        public void done(List<ParseUser> list2, ParseException e) {
                                            uList = new ArrayList<>();
                                            for (ParseUser u : list2) {
                                                if (senders.contains(u.getUsername())) {
                                                    uList.add(u);
                                                }
                                            }
                                            list.setAdapter(new UserAdapter());
                                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    navigateToChat(position, list);
                                                }
                                            });
                                        }
                                    });
                        }
                        else {
                            dia.dismiss();
                            Toast.makeText(UserList.this,
                                    R.string.msg_no_user_found,
                                    Toast.LENGTH_SHORT).show();
                        }
            }

        });
            //list.setAdapter(new UserAdapter());

        }

    private void navigateToChat(int position, ListView list) {
        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("username", uList.get(position).getUsername());
        startActivity(intent);
    }

    /**
                 * The Class UserAdapter is the adapter class for User ListView. This
                 * adapter shows the user name and it's only online status for each item.
                 */
        private class UserAdapter extends BaseAdapter {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            return uList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public ParseUser getItem(int arg0) {
            return uList.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2) {
            if (v == null)
                v = getLayoutInflater().inflate(R.layout.chat_item, null);

            ParseUser c = getItem(pos);
            TextView lbl = (TextView) v;
            lbl.setText(c.getString("firstname") + " " + c.getString("lastname"));
            lbl.setCompoundDrawablesWithIntrinsicBounds(
                    c.getBoolean("online") ? R.drawable.ic_online
                            : R.drawable.ic_offline, 0, R.drawable.arrow, 0);

            return v;
        }

    }
}
