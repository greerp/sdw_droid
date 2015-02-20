package com.uk.greer.sdwapp.activity.present_tt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.util.List;

/**
 * Created by greepau on 19/02/2015.
 */
public class PresentListAdapter extends ArrayAdapter<TimeTrial> {
    private final Context context;
    private final List<TimeTrial> timeTrialList;

    public PresentListAdapter(Context context, List<TimeTrial> timeTrialList) {
        super(context, R.layout.fragment_future_event, timeTrialList);
        this.context = context;
        this.timeTrialList = timeTrialList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_future_event, parent, false);
        TextView eventName = (TextView) rowView.findViewById(R.id.firstLine);
        TextView description = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        TimeTrial tt = timeTrialList.get(position);
        eventName.setText(tt.getName());
        description.setText(tt.getCourse());

        imageView.setImageResource(R.drawable.ic_launcher);

        return rowView;
    }
}
