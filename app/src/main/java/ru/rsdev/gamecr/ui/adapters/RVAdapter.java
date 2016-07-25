package ru.rsdev.gamecr.ui.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ru.rsdev.gamecr.R;
import ru.rsdev.gamecr.data.model.Person;



public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        TextView population;
        TextView dateStart;
        TextView numberAnswer;


        RelativeLayout relativeLayout;


                PersonViewHolder(View itemView) {
                    super(itemView);
                    cv = (CardView)itemView.findViewById(R.id.cv);
                    relativeLayout = (RelativeLayout)itemView.findViewById(R.id.card_cont);
                    personName = (TextView)itemView.findViewById(R.id.person_name);
                    personAge = (TextView)itemView.findViewById(R.id.person_age);
                    population = (TextView)itemView.findViewById(R.id.txt_population);
                    dateStart = (TextView)itemView.findViewById(R.id.txt_date_start);
                    numberAnswer = (TextView)itemView.findViewById(R.id.txt_number);
        }
    }

    List<Person> persons;

    public RVAdapter(List<Person> persons){
        this.persons = persons;
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

        personViewHolder.personName.setText(persons.get(position).name);
        personViewHolder.personAge.setText(persons.get(position).age);
        personViewHolder.population.setText(persons.get(position).population);
        personViewHolder.dateStart.setText(persons.get(position).dateStart);
        personViewHolder.numberAnswer.setText(persons.get(position).numberAnswer);

        //personViewHolder.cv.setCardBackgroundColor(Color.RED);

        if(persons.size() % 2 != 0) {

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
        return persons.size();
    }
}
