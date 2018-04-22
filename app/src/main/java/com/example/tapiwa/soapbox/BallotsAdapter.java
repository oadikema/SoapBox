package com.example.tapiwa.soapbox;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tapiwa on 4/22/18.
 */
public class BallotsAdapter extends RecyclerView.Adapter<BallotsAdapter.ViewHolder> {
    private String[] mBallotsArray;
    private Context context;
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mBallotTitle;
        public ViewHolder(View v) {
            super(v);
            mBallotTitle = v.findViewById(R.id.ballot_title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BallotsAdapter(String[] BallotsArray, Context context, Activity activity) {
        mBallotsArray = BallotsArray;
        this.context = context;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BallotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ballot_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBallotTitle.setText(mBallotsArray[position]);


        holder.mBallotTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                        //creating a popup menu
                        PopupMenu popup = new PopupMenu(context, holder.mBallotTitle);
                        //inflating menu from xml resource
                        popup.inflate(R.menu.sepc_departments);
                        //adding click listener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.Art:
                                        Fragment fragment = new ArtandHistoryFragment();
                                        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                                        break;
                                    case R.id.French:
                                        //handle menu2 click
                                        break;
                                    case R.id.English:
                                        //handle menu3 click
                                    case R.id.Classics:
                                        //handle menu3 click
                                        break;
                                }
                                return false;
                            }
                        });
                        //displaying the popup
                        popup.show();


            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBallotsArray.length;
    }
}