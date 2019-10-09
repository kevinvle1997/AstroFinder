package com.example.kevin.astrofinder;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.kevin.astrofinder.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NasaDataActivity extends Activity {
    public String urlString = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=TelnlOGwM3XnnnIXFVi9iCT7G2E446LYpJdul88u";
    public String urlStringFinal = urlString.replace("\\", "%5C");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_data);
        TextView mData = (TextView) findViewById(R.id.nasa_text);
        mData.setText("Hello");
        downloadData(urlString);
    }

    private void downloadData(String urlString) {
//check to see if device is connected to the internet before proceeding to download image:
        ConnectivityManager connMgr
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data:
            new NasaDataActivity.DownloadDataTask().execute(); // this will cause syntax error
        } else {
            // display error:

        }//end if else
    }

    class DownloadDataTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {

        }

        protected String doInBackground(Void... urls) {


            try {

                URL url = new URL(urlStringFinal);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            TextView mData = (TextView) findViewById(R.id.nasa_text);
            mData.setText(response);
            sortData(response);
        }

    }


    public class Asteroid {
        private String ID;
        private String name;
        private String minDiameter;
        private String maxDiameter;
        private String potentialHazard;
        private String closeApproachDate;
        private String velocity;
        private String missDistance;

        public Asteroid(String numID, String Name, String mDiameter, String mxDiameter, String hazard, String cADate, String velo, String mDistance){
            ID = numID;
            name = Name;
            minDiameter = mDiameter;
            maxDiameter = mxDiameter;
            potentialHazard = hazard;
            closeApproachDate = cADate;
            velocity = velo;
            missDistance = mDistance;

        }
        public String getName(){
            String rawLine = this.name;
            String[] lineSegments = rawLine.split("\\s+",3);
            return lineSegments[2];
        }

        public String getID(){
            String rawLine = this.ID;
            String[] lineSegments = rawLine.split("\\s+",3);
            String ID = lineSegments[2];
            String finalID = ID.substring(1, ID.length()-1);
            return finalID;
        }
        public String getMinDiameter(){
            String rawLine = this.minDiameter;
            String[] lineSegments = rawLine.split("\\s+",3);
            String minD = lineSegments[2];
            return minD;
        }
        public String getMaxDiameter(){
            String rawLine = this.maxDiameter;
            String[] lineSegments = rawLine.split("\\s+",3);
            String maxD = lineSegments[2];
            return maxD;
        }
        public boolean getPotentialHazard(){
            return this.potentialHazard.contains("true");
        }
        public String getCloseApproachDate(){
            String rawLine = this.closeApproachDate;
            String[] lineSegments = rawLine.split("\\s+",3);
            String date = lineSegments[2];
            return date;
        }

        public String getVelocity(){
            String rawLine = this.velocity;
            String[] lineSegments = rawLine.split("\\s+",3);
            return lineSegments[2];

        }

        public String getMissDistance() {
            String rawLine = this.missDistance;
            String[] lineSegments = rawLine.split("\\s+",3);
            return lineSegments[2];
        }
    }
    private void sortData (String data){


        ArrayList<Asteroid> asteroidGroup = new ArrayList<Asteroid>();

        String[] dataLines = data.split("\n");
        int dataNumLines = dataLines.length;
        for (int x = 0; x < dataNumLines; x++){
            String dataLine = dataLines[x];
            if (dataLine.contains("neo_reference_id")){
                String asteroidName = dataLines[x + 1];
                String minDiameter = dataLines[x + 6];
                String maxDiameter = dataLines[x + 7];
                String hazard = dataLines[x + 21];
                String closeApproach = dataLines[x + 24];
                String velocity = dataLines[x + 27];
                String missDistance = dataLines[x + 35];
                asteroidGroup.add(new Asteroid(dataLines[x], asteroidName,minDiameter,maxDiameter,hazard,closeApproach,velocity,missDistance));

            }
        }
        Log.d("ID", asteroidGroup.get(0).getID());
        Log.d("Name", asteroidGroup.get(0).getName());
        Log.d("Min", asteroidGroup.get(0).getMinDiameter());
        Log.d("Max", asteroidGroup.get(0).getMaxDiameter());
        Log.d("Close", asteroidGroup.get(0).getCloseApproachDate());
        Log.d("Velo", asteroidGroup.get(0).getVelocity());
        Log.d("Miss", asteroidGroup.get(0).getMissDistance());
    }
}
