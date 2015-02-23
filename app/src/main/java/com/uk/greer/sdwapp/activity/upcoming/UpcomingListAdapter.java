package com.uk.greer.sdwapp.activity.upcoming;

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
 * Created by greepau on 19/02/2015.
 */
public class UpcomingListAdapter extends ArrayAdapter<TimeTrial> {
    private final Context context;
    private final List<TimeTrial> timeTrialList;
    private final SimpleDateFormat dateFormat;

    static String DATE_FORMAT="EEE d-MMM-yyyy HH:mm";

    public UpcomingListAdapter(Context context, List<TimeTrial> timeTrialList) {
        super(context, R.layout.fragment_upcoming_listitem, timeTrialList);
        this.context = context;
        this.timeTrialList = timeTrialList;
        this.dateFormat = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_upcoming_listitem, parent, false);
        TextView eventName = (TextView) rowView.findViewById(R.id.ttName);
        TextView eventCourse = (TextView) rowView.findViewById(R.id.ttCourse);
        TextView eventDate = (TextView) rowView.findViewById(R.id.ttDate);
        TextView eventNo = (TextView) rowView.findViewById(R.id.ttEventNo);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        TimeTrial tt = timeTrialList.get(position);
        eventName.setText(tt.getName());
        eventCourse.setText(tt.getCourse());
        eventDate.setText(dateFormat.format(tt.getEventDate()));

        eventNo.setText(String.valueOf(tt.getEventNo()));
        imageView.setImageResource(R.drawable.tt);

        //TODO; This has to be a bug in the Android SDk, the OnItemClick delegate expects an INT
        rowView.setId((int)tt.getId());

        return rowView;
    }
}
