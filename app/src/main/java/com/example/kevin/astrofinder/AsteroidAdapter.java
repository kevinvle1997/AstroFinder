package com.example.kevin.astrofinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AsteroidAdapter extends ArrayAdapter<Asteroid> {
    MainActivity variables = new MainActivity();
    private boolean cCustomary = variables.mCustomary;
    private Context mContext;
    private NasaDataActivity mData = new NasaDataActivity();
    List<Asteroid> asteroidList = mData.asteroidGroup;

    public AsteroidAdapter(@NonNull Context context, List<Asteroid> list) {
        super(context, 0 , list);
        mContext = context;
        asteroidList = list;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
            Asteroid currentAsteroid = asteroidList.get(position);


            ImageView asteroidGIF = (ImageView) listItem.findViewById(R.id.land_image);
            TextView asteroidName = (TextView) listItem.findViewById(R.id.land_name);
            TextView passbyDate = (TextView) listItem.findViewById(R.id.land_date);
            TextView asteroidVelo = (TextView) listItem.findViewById(R.id.land_velocity);
            TextView asteroidDiameter = (TextView) listItem.findViewById(R.id.land_dia);
            TextView asteroidMD = (TextView) listItem.findViewById(R.id.land_miss_distance);
            asteroidName.setText("Name: " + currentAsteroid.getName());
            passbyDate.setText("Approach Date: " +currentAsteroid.getCloseApproachDate());


        if (cCustomary){
            String velo = "Velocity: " +currentAsteroid.getVelocity() + " mph";
            String MD = "Miss Distance: " + currentAsteroid.getMissDistance() + " miles";
            String Diameter = "Diameter: " + currentAsteroid.getMinDiameter() + " miles";
            asteroidVelo.setText(velo);
            asteroidMD.setText(MD);
            asteroidDiameter.setText(Diameter);

        }

        else {
            Double Dvelo = Double.parseDouble(currentAsteroid.getVelocity()) * 1.60934;
            String Vkm = Double.toString(Dvelo);
            String velo = "Velocity: " + Vkm + " kph";

            Double DMD = Double.parseDouble(currentAsteroid.getMissDistance()) * 1.60934;
            String VMD = Double.toString(DMD);
            String MD = "Miss Distance: " + VMD + " kilometers";

            Double dDia = Double.parseDouble(currentAsteroid.getMinDiameter()) * 1.60934;
            String dkm = Double.toString(dDia);
            String Diameter = "Diameter: " + dkm + " kilometers";
            asteroidVelo.setText(velo);
            asteroidMD.setText(MD);
            asteroidDiameter.setText(Diameter);
        }
            if (currentAsteroid.getPotentialHazard()){
                asteroidGIF.setImageResource(R.drawable.comet_red);
            }
            else{
                asteroidGIF.setImageResource(R.drawable.comet_green);

            }

        return listItem;
    }
}
