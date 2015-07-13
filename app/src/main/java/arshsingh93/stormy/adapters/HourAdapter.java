package arshsingh93.stormy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import arshsingh93.stormy.R;
import arshsingh93.stormy.weather.Hour;

/**
 * Created by Student on 7/10/2015.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Hour[] myHours;

    public HourAdapter(Hour[] hours) {
        myHours = hours;
    }


    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent,false);
        HourViewHolder viewHolder = new HourViewHolder(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(myHours[position]);
    }

    @Override
    public int getItemCount() {
        return myHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {

        public TextView myTimeLabel;
        public TextView mySummaryLabel;
        public TextView myTemperatureLabel;
        public ImageView myIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            myTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mySummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            myTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            myIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);

        }

        public void bindHour(Hour hour) {
            myTimeLabel.setText(hour.getHour());
            mySummaryLabel.setText(hour.getSummary());
            myTemperatureLabel.setText(hour.getTemperature() + "");
            myIconImageView.setImageResource(hour.getIconId());

        }


    }
}



















