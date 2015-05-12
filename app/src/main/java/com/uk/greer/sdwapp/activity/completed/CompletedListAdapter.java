package com.uk.greer.sdwapp.activity.completed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Shows the individual List Items - Referenced in UpcomingListFragment
 */
public class CompletedListAdapter extends ArrayAdapter<TimeTrial> {
    private final Context context;
    private final List<TimeTrial> timeTrialList;
    private final SimpleDateFormat dateFormat;

    static String DATE_FORMAT="EEE d-MMM-yyyy HH:mm";

    public CompletedListAdapter(Context context, List<TimeTrial> timeTrialList) {

        super(context, R.layout.main_upcoming_listitem_fragment, timeTrialList);
        this.context = context;
        this.timeTrialList = timeTrialList;
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.main_completed_listitem_fragment, parent, false);
        TextView eventName = (TextView) rowView.findViewById(R.id.stdName);
        TextView eventCourse = (TextView) rowView.findViewById(R.id.ttCourse);
        TextView eventDate = (TextView) rowView.findViewById(R.id.ttDate);
        TextView eventNo = (TextView) rowView.findViewById(R.id.ttEventNo);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        TimeTrial tt = timeTrialList.get(position);
        if ( tt!=null ) {
            eventName.setText(tt.getName());
            eventCourse.setText(tt.getCourse());
            if (tt.getEventDate() != null)
                eventDate.setText(dateFormat.format(tt.getEventDate()));
            else
                eventDate.setText("");

            eventNo.setText(String.valueOf(tt.getEventNo()));
            imageView.setImageResource(R.drawable.tt_green);

            //TODO; This has to be a bug in the Android SDk, the OnItemClick delegate expects an INT
            rowView.setId((int) tt.getId());
        }
        return rowView;
    }
}
