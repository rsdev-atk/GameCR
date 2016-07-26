package ru.rsdev.gamecr.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.data.model.City;



public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView region;
        TextView population;
        TextView dateStart;
        TextView numberAnswer;


        RelativeLayout relativeLayout;


                PersonViewHolder(View itemView) {
                    super(itemView);
                    cv = (CardView)itemView.findViewById(R.id.cv);
                    relativeLayout = (RelativeLayout)itemView.findViewById(R.id.card_cont);
                    name = (TextView)itemView.findViewById(R.id.txt_name_city);
                    region = (TextView)itemView.findViewById(R.id.txt_region_city);
                    population = (TextView)itemView.findViewById(R.id.txt_population_city);
                    dateStart = (TextView)itemView.findViewById(R.id.txt_date_start_city);
                    numberAnswer = (TextView)itemView.findViewById(R.id.txt_number);
        }
    }

    List<City> mCities;

    public RVAdapter(List<City> cities){
        this.mCities = cities;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_left, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int position) {

        personViewHolder.name.setText(mCities.get(position).name);
        personViewHolder.region.setText(mCities.get(position).region);
        personViewHolder.population.setText(mCities.get(position).population);
        personViewHolder.dateStart.setText(mCities.get(position).dateStart);
        personViewHolder.numberAnswer.setText(mCities.get(position).numberAnswer);

        //personViewHolder.cv.setCardBackgroundColor(Color.RED);

        if(mCities.size() % 2 != 0) {

            if (position % 2 == 0) {
                personViewHolder.cv.setX(150);
            } else {
                personViewHolder.cv.setX(20);
            }
        }else {
            if (position % 2 == 0) {
                personViewHolder.cv.setX(20);
            } else {
                personViewHolder.cv.setX(150);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}
