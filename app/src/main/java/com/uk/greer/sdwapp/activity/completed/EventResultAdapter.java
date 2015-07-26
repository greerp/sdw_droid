package com.uk.greer.sdwapp.activity.completed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.config.BundleProperty;
import com.uk.greer.sdwapp.domain.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by greepau on 05/07/2015.
 */
public class EventResultAdapter extends ArrayAdapter<Result> {
    private final Context context;
    private final List<Result> resultList;
    private final BundleProperty.COMPETITION compType;

    public EventResultAdapter(Context context, List<Result> resultList,
                              BundleProperty.COMPETITION compType) {

        super(context, R.layout.completed_event_resultitem_fragment, resultList);
        this.context = context;
        this.resultList = resultList;
        this.compType = compType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.completed_event_resultitem_fragment, parent, false);

        Result result = resultList.get(position);
        if ( result!=null ) {

            TextView resPosition = (TextView) rowView.findViewById(R.id.resPosition);
            TextView resName = (TextView) rowView.findViewById(R.id.resName);
            TextView resTime = (TextView) rowView.findViewById(R.id.resTime);
            TextView resPoints = (TextView) rowView.findViewById(R.id.resPoints);

            rowView.setId((int) result.getId());
            resName.setText(result.getFirstName() + " " + result.getLastName());

            if ( compType== BundleProperty.COMPETITION.SCRATCH){
                if ( result.getStatus().equalsIgnoreCase("FIN")) {
                    resPosition.setText(String.valueOf(result.getScrpos()));
                    resTime.setText(formatTime(result.getTime()));
                    resPoints.setText(String.valueOf(result.getScrpts()));
                }
                else {
                    resPosition.setText(result.getStatus());
                    resTime.setText("");
                    resPoints.setText("");
                }

            }
            else {
                if ( result.getStatus().equalsIgnoreCase("FIN")) {
                    resPosition.setText(String.valueOf(result.getHcppos()));
                    resTime.setText(formatTime(result.getTime()-result.getHandicap()));
                    resPoints.setText(String.valueOf(result.getHcppts()));
                }
                else {
                    resPosition.setText(result.getStatus());
                    resTime.setText("");
                    resPoints.setText("");
                }
            }
        }
        return rowView;
    }

    private String formatTime(long timeValue){
        Date date = new Date((timeValue*1000));
        String timeString = new SimpleDateFormat("HH:mm:ss").format(date);
        if ( timeString.startsWith("00") )
            return  timeString.substring(3);
        else
            return timeString;

    }
}
