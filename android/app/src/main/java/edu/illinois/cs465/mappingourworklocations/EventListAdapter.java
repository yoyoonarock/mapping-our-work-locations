package edu.illinois.cs465.mappingourworklocations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Phantomhive on 5/2/2016.
 */
public class EventListAdapter extends BaseAdapter {

    Event [] events;
    Context context;
    private LayoutInflater inflater = null;

    public EventListAdapter(Context c, Event [] e)
    {
        context = c;
        events = e;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return events.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView one;
        TextView two;
        TextView three;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.item_event, null);
        holder.one = (TextView) rowView.findViewById(R.id.EventTitle);
        holder.two = (TextView) rowView.findViewById(R.id.EventLocation);
        holder.three = (TextView) rowView.findViewById(R.id.EventTime);

        holder.one.setText(events[position].eventName);

        String roomname = events[position].building + " " + events[position].roomNumber;
        holder.two.setText(roomname);
        holder.three.setText(events[position].startTime);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to event detail activity
            }
        });

        return rowView;
    }


}
