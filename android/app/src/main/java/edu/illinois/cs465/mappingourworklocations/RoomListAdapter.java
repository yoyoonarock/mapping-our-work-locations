package edu.illinois.cs465.mappingourworklocations;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Space;
import android.widget.TextView;

/**
 * Created by Phantomhive on 5/2/2016.
 */
public class RoomListAdapter extends ArrayAdapter<String> {
    String[] roomNames;
    String[] roomNotes;
    String[] roomFaves;
    String[] roomAvails;

    Context context;
    private LayoutInflater inflater = null;
    public RoomListAdapter(Context context, int resource, String[] names, String[]rooms, String[]times, String[]avails) {
        super(context, resource);
        roomNames = names;
        roomNotes = rooms;
        roomFaves = times;
        roomAvails = avails;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder
    {
        TextView one;
        TextView two;
        CheckBox three;
        Space four;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.eventlist, null);
        holder.one = (TextView) rowView.findViewById(R.id.RoomName);
        holder.two = (TextView) rowView.findViewById(R.id.RoomNote);
        holder.three = (CheckBox) rowView.findViewById(R.id.RoomFavorite);
        holder.four = (Space) rowView.findViewById(R.id.AvailabilitySquare);
        holder.one.setText(roomNames[position]);
        holder.two.setText(roomNotes[position]);

        if(roomAvails[position] == "1") {
            holder.three.setChecked(true);
        }
        else {
            holder.three.setChecked(false);
        }

        if(roomAvails[position] == "1") {
            holder.four.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        else if(roomAvails[position] == "2") {
            holder.four.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
        }
        else {
            holder.four.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to event detail activity
            }
        });
        return rowView;
    }

}
