package com.example.kevin.astrofinder;

import android.util.Log;

import java.util.ArrayList;

public class Asteroid {
    public String ID;
    public String name;
    public String minDiameter;
    public String maxDiameter;
    public String potentialHazard;
    public String closeApproachDate;
    public String velocity;
    public String missDistance;
    public int index;
    private ArrayList<Asteroid> list;

    public Asteroid(int Index,String numID, String Name, String mDiameter, String mxDiameter, String hazard, String cADate, String velo, String mDistance){

        ID = numID;
        name = Name;
        minDiameter = mDiameter;
        maxDiameter = mxDiameter;
        potentialHazard = hazard;
        closeApproachDate = cADate;
        velocity = velo;
        missDistance = mDistance;
        index = Index;

    }
    public String getName(){
        String rawLine = this.name;
        String[] lineSegments = rawLine.split("\\s+",3);
        String finalName = lineSegments[2].substring(3,lineSegments[2].length() - 2);

        return finalName;
    }

    public String getID(){
        String rawLine = this.ID;
        String[] lineSegments = rawLine.split("\\s+",3);
        String ID = lineSegments[2];
        String words = ID.replaceAll("\\p{P}", "");
        return words;
    }
    public String getMinDiameter(){
        String rawLine = this.minDiameter;
        String[] lineSegments = rawLine.split("\\s+",3);
        String minD = lineSegments[2];
        String finalMin = minD.substring(2,lineSegments[2].length() - 2);
        return finalMin;
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
        String finalDate = date.substring(3,lineSegments[2].length() - 2);
        return finalDate;
    }

    public String getVelocity(){
        String rawLine = this.velocity;
        String[] lineSegments = rawLine.split("\\s+",3);
        String finalVelo = lineSegments[2].substring(3,lineSegments[2].length() - 2);

        return finalVelo;

    }

    public String getMissDistance() {
        String rawLine = this.missDistance;
        String[] lineSegments = rawLine.split("\\s+",3);
        String finalMiss = lineSegments[2].substring(3,lineSegments[2].length() - 1);

        return finalMiss;
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<Asteroid> getList() {
        return list;
    }
}
