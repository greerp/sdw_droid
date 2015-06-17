package com.uk.greer.sdwapp.activity.standing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uk.greer.sdwapp.R;
import com.uk.greer.sdwapp.domain.Standing;
import com.uk.greer.sdwapp.domain.TimeTrial;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Shows the individual List Items - Referenced in UpcomingListFragment
 */
public class SeasonStandingListAdapter extends ArrayAdapter<Standing> {
    private final Context context;
    private final List<Standing> standingsList;

    public SeasonStandingListAdapter(Context context, List<Standing> standingList) {

        super(context, R.layout.main_season_standing_listitem_fragment, standingList);
        this.context = context;
        this.standingsList = standingList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.main_season_standing_listitem_fragment, parent, false);

        Standing std = standingsList.get(position);
        if ( std!=null ) {
            TextView stdName = (TextView) rowView.findViewById(R.id.stdName);
            TextView stdHCap = (TextView) rowView.findViewById(R.id.stdHandicapPts);
            TextView stdScr = (TextView) rowView.findViewById(R.id.stdScratchPts);
            rowView.setId((int) std.getUserId());

            stdName.setText(std.getFirstName() + " " + std.getLastName());
            stdScr.setText( String.valueOf(std.getScrpts()));
            stdHCap.setText(String.valueOf(std.getHcppts()));

//            TextView stdDnf = (TextView) rowView.findViewById(R.id.stdDnf);
//            TextView stdEnt = (TextView) rowView.findViewById(R.id.stdEntered);
//            TextView stdDns = (TextView) rowView.findViewById(R.id.stdDns);
//            TextView stdFin = (TextView) rowView.findViewById(R.id.stdFinished);
//            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//            imageView.setImageResource(R.drawable.ic_action_user);
//            stdScr.setText( "Scr:"+String.valueOf(std.getScrpts()));
//            stdHCap.setText("Hcp:"+String.valueOf(std.getHcppts()));
//            stdEnt.setText("Ent:"+String.valueOf(std.getEntered()));
//            stdFin.setText("Fin:"+String.valueOf(std.getFin()));
//            stdDns.setText("Dns:"+String.valueOf(std.getDns()));
//            stdDnf.setText("Dnf:"+String.valueOf(std.getDnf()));
        }
        return rowView;
    }
}
