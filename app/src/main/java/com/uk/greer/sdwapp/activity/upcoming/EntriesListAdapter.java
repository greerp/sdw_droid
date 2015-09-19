package com.uk.greer.sdwapp.activity.upcoming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.Entry;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Shows the individual List Items - Referenced in UpcomingListFragment
 */
public class EntriesListAdapter extends ArrayAdapter<Entry> {
    private final Context context;
    private final List<Entry> entryList;
    private final SimpleDateFormat dateFormat;

    static String DATE_FORMAT="EEE d-MMM-yyyy";

    public EntriesListAdapter(Context context, List<Entry> entryList) {

        super(context, R.layout.upcoming_entries_listitem_fragment, entryList);
        this.context = context;
        this.entryList = entryList;
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.upcoming_entries_listitem_fragment, parent, false);
        TextView eventName = (TextView) rowView.findViewById(R.id.resName);
        TextView eventEntryDate= (TextView) rowView.findViewById(R.id.resDate);

        Entry entry = entryList.get(position);
        if ( entry!=null ) {
            eventName.setText(entry.getFirstName() + " " + entry.getLastName());
            if (entry.getSignUpDate() != null)
                eventEntryDate.setText(dateFormat.format(entry.getSignUpDate()));
            else
                eventEntryDate.setText("");

            //TODO; This has to be a bug in the Android SDk, the OnItemClick delegate expects an INT
            rowView.setId((int) entry.getId());
        }
        return rowView;
    }
}
