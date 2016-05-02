package edu.illinois.cs465.mappingourworklocations;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Space;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Phantomhive on 5/2/2016.
 */
public class RoomListAdapter extends BaseAdapter
{
    Room [] rooms;
    Context context;
    private LayoutInflater inflater = null;

    public RoomListAdapter(Context c, Room [] r) {
        context = c;
        rooms = r;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rooms.length;
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
        ToggleButton three;
        Space four;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.item_room, null);

        holder.one = (TextView) rowView.findViewById(R.id.RoomName);
        holder.two = (TextView) rowView.findViewById(R.id.RoomNote);
        holder.three = (ToggleButton) rowView.findViewById(R.id.RoomFavorite);
        holder.four = (Space) rowView.findViewById(R.id.AvailabilitySquare);

        String roomname = rooms[position].building + " " + rooms[position].roomNumber;
        holder.one.setText(roomname);
        holder.two.setText(rooms[position].note);

        if(rooms[position].favorite.equals("1"))
        {
            holder.three.setChecked(true);
        }
        else
        {
            holder.three.setChecked(false);
        }

        if(rooms[position].availability.equals("1")) {
            holder.four.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        else if(rooms[position].availability.equals("2")) {
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
