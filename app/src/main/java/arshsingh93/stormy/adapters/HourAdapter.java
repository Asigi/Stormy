package arshsingh93.stormy.adapters;

import android.support.v7.widget.RecyclerView;
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

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(HourViewHolder hourViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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



















