//Dax Collison
//DXC200000

package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    //Print Leaders
    public static void printLeaders(double[] values, ArrayList<String> first, ArrayList<String> second, ArrayList<String> third, int yes){
        String firstPlace;
        if(yes == 1) {
            firstPlace = String.format("%.3f\t", values[0]);  //Starts output string with lead stat
        }
        else{
            firstPlace = String.valueOf((int)values[0]) + "\t";  //Starts output string with lead stat
        }
        //Looping thru array of strings for first
        for(int i = 0; i < first.size(); i++){
            firstPlace += first.get(i); //Appending name to string
            if(i < first.size() - 1){
                firstPlace += ", "; //Append ", " only if not last name
            }
        }
        //Print 1st Place
        System.out.println(firstPlace);

        //SecondPlace
        if(first.size() < 3){
            String secondPlace;
            if(yes == 1) {
                secondPlace = String.format("%.3f\t", values[1]);   //If there is less than three 1st place then start second place string
            }
            else{
                secondPlace = String.valueOf((int)values[1]) + "\t";   //If there is less than three 1st place then start second place string
            }
            //Looping thru array of strings for second
            for(int i = 0; i < second.size(); i++){
                secondPlace += second.get(i); //Appending name to string
                if(i < second.size() - 1){
                    secondPlace += ", "; //Append ", " only if not last name
                }
            }
            //Print 2nd Place
            System.out.println(secondPlace);
        }//end of 2nd place

        //ThirdPlace
        if(first.size() + second.size() < 3){
            String thirdPlace;
            if(yes == 1){
                thirdPlace = String.format("%.3f\t",values[2]);    //if there is less than three total between 1st and 2nd make third string
            }
            else{
                thirdPlace = String.valueOf((int)values[2]) + "\t";
            }
            //Looping thru array of strings for third
            for(int i = 0; i < third.size(); i++){
                thirdPlace += third.get(i); //Appending name to string
                if(i < third.size() - 1){
                    thirdPlace += ", "; //Append ", " only if not last name
                }
            }
            //Print 3rd Place
            System.out.println(thirdPlace);
        }//End of 3rd Place
        System.out.println();
    }
    //Gets all first place players
    public static void getLeaderStrings(ArrayList<String> first,ArrayList<String> second,ArrayList<String> third,ArrayList<Player> away, ArrayList<Player> home,Comparator<Player> stat,double[] place){
        //Gets based on comparator passed in
        /*
        Bat Avg Section
         */
        if(stat instanceof PlayerBatAvgComparator){
            //Counter Variables
            int k = 0;
            int l = 0;
            int j = 0;
            int h = 0;
            /*
            1st Place
             */
            /*  First Place
            Away Section
             */

            first.add(0,away.get(0).getName()); //Getting the name of the first player since it will be main leader
            for(int i = 1; i < away.size(); i++){
                //Grabbing any players that share 1st place
                if(away.get(i).batAvgCalc() == away.get(0).batAvgCalc()){
                    first.add(away.get(i).getName()); //Putting name into
                }
            }


            /*
            End of Away Section
             */

            /*
            Home Section
             */
            //Creating a home first array
            ArrayList<String> homeLeaders = new ArrayList<>();
            homeLeaders.add(0,home.get(0).getName()); //Same as above just for home
            for(int i = 1; i < home.size(); i++){
                //Grabbing any players that share 1st place
                if(home.get(i).batAvgCalc() == home.get(0).batAvgCalc()){
                    homeLeaders.add(home.get(i).getName()); //Putting name into
                }
            }
            /*
            end of home section
             */

            /*
            Home and Away Check/Merge Section
             */
            //Now checking to see if home needs to be included
            //If this is true than home is used as the leader and there are only ties on home side
            if(home.get(0).batAvgCalc() > away.get(0).batAvgCalc()){
                Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                first = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                place[0] = home.get(0).batAvgCalc();    //Putting first place value into array
            }
            //Otherwise if they equal than they need to be sorted and
            else if(home.get(0).batAvgCalc() == away.get(0).batAvgCalc()){
                Collections.sort(first);    //Sorting away side
                Collections.sort(homeLeaders);    //Sorting home side
                first.addAll(homeLeaders);    //Merges the two into first, away then home
                place[0] = home.get(0).batAvgCalc();    //Putting first place value into array
            }
            else{
                //Otherwise away has the leaders
                Collections.sort(first);
                place[0] = away.get(0).batAvgCalc();    //Putting first place value into array
            }
            /*
            End of Home and Away Check Merge
             */

            /*
            2nd Place
             */
            //Second Place only if there are less than 3 leaders total from either side
            if(first.size() < 3){

                /*      Second Place
                Away Section
                 */
                //Loop to get second place value
                while(away.get(k).batAvgCalc() == away.get(0).batAvgCalc() && away.get(k) != null){
                    k++;    //increment until non first place value found
                }
                //Putting second place into array
                second.add(0,away.get(k).getName());
                //Looping thru to find other second place values
                for(int i = k+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).batAvgCalc() == away.get(k).batAvgCalc()){
                        second.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for second
                while(home.get(l).batAvgCalc() == home.get(0).batAvgCalc() && home.get(l) != null){
                    l++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(l).getName()); //Same as above just for home
                for(int i = l+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).batAvgCalc() == home.get(l).batAvgCalc()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(l).batAvgCalc() > away.get(k).batAvgCalc()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    second = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[1] = home.get(l).batAvgCalc();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(l).batAvgCalc() == away.get(k).batAvgCalc()){
                    Collections.sort(second);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    second.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[1] = home.get(l).batAvgCalc();    //Putting first place value into array
                }
                else{
                    //Otherwise second is only away leader
                    Collections.sort(second);
                    place[1] = away.get(k).batAvgCalc();    //Putting first place value into array
                }
                /*
                End of Home Section
                 */

            }//End of Second Place

            /*
            3rd Place
             */
            //Third Place if needed
            if(first.size() + second.size() < 3){

                /*      Third Place
                Away Section
                 */
                //Loop to get third place value
                while(away.get(j) != null && (away.get(j).batAvgCalc() == away.get(0).batAvgCalc() || away.get(j).batAvgCalc() == away.get(k).batAvgCalc())){
                    j++;    //increment until non first or second place value found
                }
                //Putting third place into array
                third.add(0,away.get(j).getName());
                //Looping thru to find other second place values
                for(int i = j+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).batAvgCalc() == away.get(j).batAvgCalc()){
                        third.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for third
                while(home.get(h) != null && (home.get(h).batAvgCalc() == home.get(0).batAvgCalc() || home.get(h).batAvgCalc() == home.get(l).batAvgCalc())){
                    h++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(h).getName()); //Same as above just for home
                for(int i = h+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).batAvgCalc() == home.get(h).batAvgCalc()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                /*
                Merge/Check
                 */
                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(h).batAvgCalc() > away.get(j).batAvgCalc()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    third = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[2] = home.get(h).batAvgCalc();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(h).batAvgCalc() == away.get(j).batAvgCalc()){
                    Collections.sort(third);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    third.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[2] = home.get(h).batAvgCalc();    //Putting first place value into array
                }
                else{
                    //Otherwise away are leaders
                    Collections.sort(third);
                    place[2] = away.get(j).batAvgCalc();    //Putting first place value into array
                }
                /*
                End of Check/merge
                 */
            }//End of Third Place
        }//End of batting average leaders

        /*
        On Base Percentage Section
         */
        if(stat instanceof PlayerOnBasePerComparator){
            //Counter Variables
            int k = 0;
            int l = 0;
            int j = 0;
            int h = 0;
            /*
            1st Place
             */
            /*  First Place
            Away Section
             */

            first.add(0,away.get(0).getName()); //Getting the name of the first player since it will be main leader
            for(int i = 1; i < away.size(); i++){
                //Grabbing any players that share 1st place
                if(away.get(i).onBasePer() == away.get(0).onBasePer()){
                    first.add(away.get(i).getName()); //Putting name into
                }
            }


            /*
            End of Away Section
             */

            /*
            Home Section
             */
            //Creating a home first array
            ArrayList<String> homeLeaders = new ArrayList<>();
            homeLeaders.add(0,home.get(0).getName()); //Same as above just for home
            for(int i = 1; i < home.size(); i++){
                //Grabbing any players that share 1st place
                if(home.get(i).onBasePer() == home.get(0).onBasePer()){
                    homeLeaders.add(home.get(i).getName()); //Putting name into
                }
            }
            /*
            end of home section
             */

            /*
            Home and Away Check/Merge Section
             */
            //Now checking to see if home needs to be included
            //If this is true than home is used as the leader and there are only ties on home side
            if(home.get(0).onBasePer() > away.get(0).onBasePer()){
                Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                first = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                place[0] = home.get(0).onBasePer();    //Putting first place value into array
            }
            //Otherwise if they equal than they need to be sorted and
            else if(home.get(0).onBasePer() == away.get(0).onBasePer()){
                Collections.sort(first);    //Sorting away side
                Collections.sort(homeLeaders);    //Sorting home side
                first.addAll(homeLeaders);    //Merges the two into first, away then home
                place[0] = home.get(0).onBasePer();    //Putting first place value into array
            }
            else{
                //Otherwise away has the leaders
                Collections.sort(first);
                place[0] = away.get(0).onBasePer();    //Putting first place value into array
            }
            /*
            End of Home and Away Check Merge
             */

            /*
            2nd Place
             */
            //Second Place only if there are less than 3 leaders total from either side
            if(first.size() < 3){

                /*      Second Place
                Away Section
                 */
                //Loop to get second place value
                while(away.get(k).onBasePer() == away.get(0).onBasePer() && away.get(k) != null){
                    k++;    //increment until non first place value found
                }
                //Putting second place into array
                second.add(0,away.get(k).getName());
                //Looping thru to find other second place values
                for(int i = k+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).onBasePer() == away.get(k).onBasePer()){
                        second.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for second
                while(home.get(l).onBasePer() == home.get(0).onBasePer() && home.get(l) != null){
                    l++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(l).getName()); //Same as above just for home
                for(int i = l+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).onBasePer() == home.get(l).onBasePer()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(l).onBasePer() > away.get(k).onBasePer()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    second = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[1] = home.get(l).onBasePer();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(l).onBasePer() == away.get(k).onBasePer()){
                    Collections.sort(second);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    second.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[1] = home.get(l).onBasePer();    //Putting first place value into array
                }
                else{
                    //Otherwise second is only away leader
                    Collections.sort(second);
                    place[1] = away.get(k).onBasePer();    //Putting first place value into array
                }
                /*
                End of Home Section
                 */

            }//End of Second Place

            /*
            3rd Place
             */
            //Third Place if needed
            if(first.size() + second.size() < 3){

                /*      Third Place
                Away Section
                 */
                //Loop to get third place value
                while(away.get(j) != null && (away.get(j).onBasePer() == away.get(0).onBasePer() || away.get(j).onBasePer() == away.get(k).onBasePer())){
                    j++;    //increment until non first or second place value found
                }
                //Putting third place into array
                third.add(0,away.get(j).getName());
                //Looping thru to find other second place values
                for(int i = j+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).onBasePer() == away.get(j).onBasePer()){
                        third.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for third
                while(home.get(h) != null && (home.get(h).onBasePer() == home.get(0).onBasePer() || home.get(h).onBasePer() == home.get(l).onBasePer())){
                    h++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(h).getName()); //Same as above just for home
                for(int i = h+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).onBasePer() == home.get(h).onBasePer()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                /*
                Merge/Check
                 */
                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(h).onBasePer() > away.get(j).onBasePer()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    third = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[2] = home.get(h).onBasePer();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(h).onBasePer() == away.get(j).onBasePer()){
                    Collections.sort(third);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    third.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[2] = home.get(h).onBasePer();    //Putting first place value into array
                }
                else{
                    //Otherwise away are leaders
                    Collections.sort(third);
                    place[2] = away.get(j).onBasePer();    //Putting first place value into array
                }
                /*
                End of Check/merge
                 */
            }//End of Third Place
        }//End of on base percentage

        /*
        Hits Section
         */
        if(stat instanceof PlayerHitsComparator){
            //Counter Variables
            int k = 0;
            int l = 0;
            int j = 0;
            int h = 0;
            /*
            1st Place
             */
            /*  First Place
            Away Section
             */

            first.add(0,away.get(0).getName()); //Getting the name of the first player since it will be main leader
            for(int i = 1; i < away.size(); i++){
                //Grabbing any players that share 1st place
                if(away.get(i).getHit() == away.get(0).getHit()){
                    first.add(away.get(i).getName()); //Putting name into
                }
            }


            /*
            End of Away Section
             */

            /*
            Home Section
             */
            //Creating a home first array
            ArrayList<String> homeLeaders = new ArrayList<>();
            homeLeaders.add(0,home.get(0).getName()); //Same as above just for home
            for(int i = 1; i < home.size(); i++){
                //Grabbing any players that share 1st place
                if(home.get(i).getHit() == home.get(0).getHit()){
                    homeLeaders.add(home.get(i).getName()); //Putting name into
                }
            }
            /*
            end of home section
             */

            /*
            Home and Away Check/Merge Section
             */
            //Now checking to see if home needs to be included
            //If this is true than home is used as the leader and there are only ties on home side
            if(home.get(0).getHit() > away.get(0).getHit()){
                Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                first = new ArrayList<String>(homeLeaders);
                place[0] = home.get(0).getHit();    //Putting first place value into array
            }
            //Otherwise if they equal than they need to be sorted and
            else if(home.get(0).getHit() == away.get(0).getHit()){
                Collections.sort(first);    //Sorting away side
                Collections.sort(homeLeaders);    //Sorting home side
                first.addAll(homeLeaders);    //Merges the two into first, away then home
                place[0] = home.get(0).getHit();    //Putting first place value into array
            }
            else{
                //Otherwise away has the leaders
                Collections.sort(first);
                place[0] = away.get(0).getHit();    //Putting first place value into array
            }
            /*
            End of Home and Away Check Merge
             */

            /*
            2nd Place
             */
            //Second Place only if there are less than 3 leaders total from either side
            if(first.size() < 3){

                /*      Second Place
                Away Section
                 */
                //Loop to get second place value
                while(away.get(k).getHit() == away.get(0).getHit() && away.get(k) != null){
                    k++;    //increment until non first place value found
                }
                //Putting second place into array
                second.add(0,away.get(k).getName());
                //Looping thru to find other second place values
                for(int i = k+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getHit() == away.get(k).getHit()){
                        second.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for second
                while(home.get(l).getHit() == home.get(0).getHit() && home.get(l) != null){
                    l++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(l).getName()); //Same as above just for home
                for(int i = l+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getHit() == home.get(l).getHit()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(l).getHit() > away.get(k).getHit()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    second = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[1] = home.get(l).getHit();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(l).getHit() == away.get(k).getHit()){
                    Collections.sort(second);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    second.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[1] = home.get(l).getHit();    //Putting first place value into array
                }
                else{
                    //Otherwise second is only away leader
                    Collections.sort(second);
                    place[1] = away.get(k).getHit();    //Putting first place value into array
                }
                /*
                End of Home Section
                 */

            }//End of Second Place

            /*
            3rd Place
             */
            //Third Place if needed
            if(first.size() + second.size() < 3){

                /*      Third Place
                Away Section
                 */
                //Loop to get third place value
                while(away.get(j) != null && (away.get(j).getHit() == away.get(0).getHit() || away.get(j).getHit() == away.get(k).getHit())){
                    j++;    //increment until non first or second place value found
                }
                //Putting third place into array
                third.add(0,away.get(j).getName());
                //Looping thru to find other second place values
                for(int i = j+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getHit() == away.get(j).getHit()){
                        third.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for third
                while(home.get(h) != null && (home.get(h).getHit() == home.get(0).getHit() || home.get(h).getHit() == home.get(l).getHit())){
                    h++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(h).getName()); //Same as above just for home
                for(int i = h+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getHit() == home.get(h).getHit()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                /*
                Merge/Check
                 */
                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(h).getHit() > away.get(j).getHit()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    third = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[2] = home.get(h).getHit();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(h).getHit() == away.get(j).getHit()){
                    Collections.sort(third);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    third.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[2] = home.get(h).getHit();    //Putting first place value into array
                }
                else{
                    //Otherwise away are leaders
                    Collections.sort(third);
                    place[2] = away.get(j).getHit();    //Putting first place value into array
                }
                /*
                End of Check/merge
                 */
            }//End of Third Place
        }//End of hits leaders

        /*
        Walks Section
         */
        if(stat instanceof PlayerWalksComparator){
            //Counter Variables
            int k = 0;
            int l = 0;
            int j = 0;
            int h = 0;
            /*
            1st Place
             */
            /*  First Place
            Away Section
             */

            first.add(0,away.get(0).getName()); //Getting the name of the first player since it will be main leader
            for(int i = 1; i < away.size(); i++){
                //Grabbing any players that share 1st place
                if(away.get(i).getWalk() == away.get(0).getWalk()){
                    first.add(away.get(i).getName()); //Putting name into
                }
            }


            /*
            End of Away Section
             */

            /*
            Home Section
             */
            //Creating a home first array
            ArrayList<String> homeLeaders = new ArrayList<>();
            homeLeaders.add(0,home.get(0).getName()); //Same as above just for home
            for(int i = 1; i < home.size(); i++){
                //Grabbing any players that share 1st place
                if(home.get(i).getWalk() == home.get(0).getWalk()){
                    homeLeaders.add(home.get(i).getName()); //Putting name into
                }
            }
            /*
            end of home section
             */

            /*
            Home and Away Check/Merge Section
             */
            //Now checking to see if home needs to be included
            //If this is true than home is used as the leader and there are only ties on home side
            if(home.get(0).getWalk() > away.get(0).getWalk()){
                Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                first = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                place[0] = home.get(0).getWalk();    //Putting first place value into array
            }
            //Otherwise if they equal than they need to be sorted and
            else if(home.get(0).getWalk() == away.get(0).getWalk()){
                Collections.sort(first);    //Sorting away side
                Collections.sort(homeLeaders);    //Sorting home side
                first.addAll(homeLeaders);    //Merges the two into first, away then home
                place[0] = home.get(0).getWalk();    //Putting first place value into array
            }
            else{
                //Otherwise away has the leaders
                Collections.sort(first);
                place[0] = away.get(0).getWalk();    //Putting first place value into array
            }
            /*
            End of Home and Away Check Merge
             */

            /*
            2nd Place
             */
            //Second Place only if there are less than 3 leaders total from either side
            if(first.size() < 3){

                /*      Second Place
                Away Section
                 */
                //Loop to get second place value
                while(away.get(k).getWalk() == away.get(0).getWalk() && away.get(k) != null){
                    k++;    //increment until non first place value found
                }
                //Putting second place into array
                second.add(0,away.get(k).getName());
                //Looping thru to find other second place values
                for(int i = k+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getWalk() == away.get(k).getWalk()){
                        second.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for second
                while(home.get(l).getWalk() == home.get(0).getWalk() && home.get(l) != null){
                    l++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(l).getName()); //Same as above just for home
                for(int i = l+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getWalk() == home.get(l).getWalk()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(l).getWalk() > away.get(k).getWalk()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    second = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[1] = home.get(l).getWalk();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(l).getWalk() == away.get(k).getWalk()){
                    Collections.sort(second);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    second.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[1] = home.get(l).getWalk();    //Putting first place value into array
                }
                else{
                    //Otherwise second is only away leader
                    Collections.sort(second);
                    place[1] = away.get(k).getWalk();    //Putting first place value into array
                }
                /*
                End of Home Section
                 */

            }//End of Second Place

            /*
            3rd Place
             */
            //Third Place if needed
            if(first.size() + second.size() < 3){

                /*      Third Place
                Away Section
                 */
                //Loop to get third place value
                while(away.get(j) != null && (away.get(j).getWalk() == away.get(0).getWalk() || away.get(j).getWalk() == away.get(k).getWalk())){
                    j++;    //increment until non first or second place value found
                }
                //Putting third place into array
                third.add(0,away.get(j).getName());
                //Looping thru to find other second place values
                for(int i = j+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getWalk() == away.get(j).getWalk()){
                        third.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for third
                while(home.get(h) != null && (home.get(h).getWalk() == home.get(0).getWalk() || home.get(h).getWalk() == home.get(l).getWalk())){
                    h++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(h).getName()); //Same as above just for home
                for(int i = h+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getWalk() == home.get(h).getWalk()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                /*
                Merge/Check
                 */
                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(h).getWalk() > away.get(j).getWalk()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    third = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[2] = home.get(h).getWalk();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(h).getWalk() == away.get(j).getWalk()){
                    Collections.sort(third);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    third.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[2] = home.get(h).getWalk();    //Putting first place value into array
                }
                else{
                    //Otherwise away are leaders
                    Collections.sort(third);
                    place[2] = away.get(j).getWalk();    //Putting first place value into array
                }
                /*
                End of Check/merge
                 */
            }//End of Third Place
        }//End of walk leaders

        /*
        StrikeOuts Section
         */
        if(stat instanceof PlayerStikeoutsComparator){
            //Counter Variables
            int k = 0;
            int l = 0;
            int j = 0;
            int h = 0;
            /*
            1st Place
             */
            /*  First Place
            Away Section
             */

            first.add(0,away.get(0).getName()); //Getting the name of the first player since it will be main leader
            for(int i = 1; i < away.size(); i++){
                //Grabbing any players that share 1st place
                if(away.get(i).getStrikeOut() == away.get(0).getStrikeOut()){
                    first.add(away.get(i).getName()); //Putting name into
                }
            }


            /*
            End of Away Section
             */

            /*
            Home Section
             */
            //Creating a home first array
            ArrayList<String> homeLeaders = new ArrayList<>();
            homeLeaders.add(0,home.get(0).getName()); //Same as above just for home
            for(int i = 1; i < home.size(); i++){
                //Grabbing any players that share 1st place
                if(home.get(i).getStrikeOut() == home.get(0).getStrikeOut()){
                    homeLeaders.add(home.get(i).getName()); //Putting name into
                }
            }
            /*
            end of home section
             */

            /*
            Home and Away Check/Merge Section
             */
            //Now checking to see if home needs to be included
            //If this is true than home is used as the leader and there are only ties on home side
            if(home.get(0).getStrikeOut() > away.get(0).getStrikeOut()){
                Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                first = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                place[0] = home.get(0).getStrikeOut();    //Putting first place value into array
            }
            //Otherwise if they equal than they need to be sorted and
            else if(home.get(0).getStrikeOut() == away.get(0).getStrikeOut()){
                Collections.sort(first);    //Sorting away side
                Collections.sort(homeLeaders);    //Sorting home side
                first.addAll(homeLeaders);    //Merges the two into first, away then home
                place[0] = home.get(0).getStrikeOut();    //Putting first place value into array
            }
            else{
                //Otherwise away has the leaders
                Collections.sort(first);
                place[0] = away.get(0).getStrikeOut();    //Putting first place value into array
            }
            /*
            End of Home and Away Check Merge
             */

            /*
            2nd Place
             */
            //Second Place only if there are less than 3 leaders total from either side
            if(first.size() < 3){

                /*      Second Place
                Away Section
                 */
                //Loop to get second place value
                while(away.get(k).getStrikeOut() == away.get(0).getStrikeOut() && away.get(k) != null){
                    k++;    //increment until non first place value found
                }
                //Putting second place into array
                second.add(0,away.get(k).getName());
                //Looping thru to find other second place values
                for(int i = k+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getStrikeOut() == away.get(k).getStrikeOut()){
                        second.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for second
                while(home.get(l).getStrikeOut() == home.get(0).getStrikeOut() && home.get(l) != null){
                    l++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(l).getName()); //Same as above just for home
                for(int i = l+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getStrikeOut() == home.get(l).getStrikeOut()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(l).getStrikeOut() > away.get(k).getStrikeOut()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    second = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[1] = home.get(l).getStrikeOut();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(l).getStrikeOut() == away.get(k).getStrikeOut()){
                    Collections.sort(second);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    second.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[1] = home.get(l).getStrikeOut();    //Putting first place value into array
                }
                else{
                    //Otherwise second is only away leader
                    Collections.sort(second);
                    place[1] = away.get(k).getStrikeOut();    //Putting first place value into array
                }
                /*
                End of Home Section
                 */

            }//End of Second Place

            /*
            3rd Place
             */
            //Third Place if needed
            if(first.size() + second.size() < 3){

                /*      Third Place
                Away Section
                 */
                //Loop to get third place value
                while(away.get(j) != null && (away.get(j).getStrikeOut() == away.get(0).getStrikeOut() || away.get(j).getStrikeOut() == away.get(k).getStrikeOut())){
                    j++;    //increment until non first or second place value found
                }
                //Putting third place into array
                third.add(0,away.get(j).getName());
                //Looping thru to find other second place values
                for(int i = j+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getStrikeOut() == away.get(j).getStrikeOut()){
                        third.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for third
                while(home.get(h) != null && (home.get(h).getStrikeOut() == home.get(0).getStrikeOut() || home.get(h).getStrikeOut() == home.get(l).getStrikeOut())){
                    h++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(h).getName()); //Same as above just for home
                for(int i = h+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getStrikeOut() == home.get(h).getStrikeOut()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                /*
                Merge/Check
                 */
                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(h).getStrikeOut() > away.get(j).getStrikeOut()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    third = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[2] = home.get(h).getStrikeOut();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(h).getStrikeOut() == away.get(j).getStrikeOut()){
                    Collections.sort(third);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    third.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[2] = home.get(h).getStrikeOut();    //Putting first place value into array
                }
                else{
                    //Otherwise away are leaders
                    Collections.sort(third);
                    place[2] = away.get(j).getStrikeOut();    //Putting first place value into array
                }
                /*
                End of Check/merge
                 */
            }//End of Third Place
        }//End of strikeouts leaders

        /*
        Hit By Pitch Section
         */
        if(stat instanceof PlayerHitByPitchComparator){
            //Counter Variables
            int k = 0;
            int l = 0;
            int j = 0;
            int h = 0;
            /*
            1st Place
             */
            /*  First Place
            Away Section
             */

            first.add(0,away.get(0).getName()); //Getting the name of the first player since it will be main leader
            for(int i = 1; i < away.size(); i++){
                //Grabbing any players that share 1st place
                if(away.get(i).getPitchHits() == away.get(0).getPitchHits()){
                    first.add(away.get(i).getName()); //Putting name into
                }
            }


            /*
            End of Away Section
             */

            /*
            Home Section
             */
            //Creating a home first array
            ArrayList<String> homeLeaders = new ArrayList<>();
            homeLeaders.add(0,home.get(0).getName()); //Same as above just for home
            for(int i = 1; i < home.size(); i++){
                //Grabbing any players that share 1st place
                if(home.get(i).getPitchHits() == home.get(0).getPitchHits()){
                    homeLeaders.add(home.get(i).getName()); //Putting name into
                }
            }
            /*
            end of home section
             */

            /*
            Home and Away Check/Merge Section
             */
            //Now checking to see if home needs to be included
            //If this is true than home is used as the leader and there are only ties on home side
            if(home.get(0).getPitchHits() > away.get(0).getPitchHits()){
                Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                first = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                place[0] = home.get(0).getPitchHits();    //Putting first place value into array
            }
            //Otherwise if they equal than they need to be sorted and
            else if(home.get(0).getPitchHits() == away.get(0).getPitchHits()){
                Collections.sort(first);    //Sorting away side
                Collections.sort(homeLeaders);    //Sorting home side
                first.addAll(homeLeaders);    //Merges the two into first, away then home
                place[0] = home.get(0).getPitchHits();    //Putting first place value into array
            }
            else{
                //Otherwise away has the leaders
                Collections.sort(first);
                place[0] = away.get(0).getPitchHits();    //Putting first place value into array
            }
            /*
            End of Home and Away Check Merge
             */

            /*
            2nd Place
             */
            //Second Place only if there are less than 3 leaders total from either side
            if(first.size() < 3){

                /*      Second Place
                Away Section
                 */
                //Loop to get second place value
                while(away.get(k).getPitchHits() == away.get(0).getPitchHits() && away.get(k) != null){
                    k++;    //increment until non first place value found
                }
                //Putting second place into array
                second.add(0,away.get(k).getName());
                //Looping thru to find other second place values
                for(int i = k+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getPitchHits() == away.get(k).getPitchHits()){
                        second.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for second
                while(home.get(l).getPitchHits() == home.get(0).getPitchHits() && home.get(l) != null){
                    l++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(l).getName()); //Same as above just for home
                for(int i = l+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getPitchHits() == home.get(l).getPitchHits()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(l).getPitchHits() > away.get(k).getPitchHits()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    second = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[1] = home.get(l).getPitchHits();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(l).getPitchHits() == away.get(k).getPitchHits()){
                    Collections.sort(second);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    second.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[1] = home.get(l).getPitchHits();    //Putting first place value into array
                }
                else{
                    //Otherwise second is only away leader
                    Collections.sort(second);
                    place[1] = away.get(k).getPitchHits();    //Putting first place value into array
                }
                /*
                End of Home Section
                 */

            }//End of Second Place

            /*
            3rd Place
             */
            //Third Place if needed
            if(first.size() + second.size() < 3){

                /*      Third Place
                Away Section
                 */
                //Loop to get third place value
                while(away.get(j) != null && (away.get(j).getPitchHits() == away.get(0).getPitchHits() || away.get(j).getPitchHits() == away.get(k).getPitchHits())){
                    j++;    //increment until non first or second place value found
                }
                //Putting third place into array
                third.add(0,away.get(j).getName());
                //Looping thru to find other second place values
                for(int i = j+1; i < away.size(); i++){
                    //Grabbing any players that share 2nd place
                    if(away.get(i).getPitchHits() == away.get(j).getPitchHits()){
                        third.add(away.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Away Section
                 */

                /*
                Home Section
                 */
                //Loop for third
                while(home.get(h) != null && (home.get(h).getPitchHits() == home.get(0).getPitchHits() || home.get(h).getPitchHits() == home.get(l).getPitchHits())){
                    h++;    //increment until non first place value found
                }
                homeLeaders.clear();    //Resetting list
                homeLeaders.add(0,home.get(h).getName()); //Same as above just for home
                for(int i = h+1; i < home.size(); i++){
                    //Grabbing any players that share 1st place
                    if(home.get(i).getPitchHits() == home.get(h).getPitchHits()){
                        homeLeaders.add(home.get(i).getName()); //Putting name into
                    }
                }
                /*
                End of Home Section
                 */

                /*
                Merge/Check
                 */
                //Now checking to see if home needs to be included
                //If this is true than home is used as the leader and there are only ties on home side
                if(home.get(h).getPitchHits() > away.get(j).getPitchHits()){
                    Collections.sort(homeLeaders);    //Sorting by names, they all have the same stat
                    third = new ArrayList<String>(homeLeaders); //Moving over sorted array to first
                    place[2] = home.get(h).getPitchHits();    //Putting first place value into array
                }
                //Otherwise if they equal than they need to be sorted and
                else if(home.get(h).getPitchHits() == away.get(j).getPitchHits()){
                    Collections.sort(third);    //Sorting away side
                    Collections.sort(homeLeaders);    //Sorting home side
                    third.addAll(homeLeaders);    //Merges the two into first, away then home
                    place[2] = home.get(h).getPitchHits();    //Putting first place value into array
                }
                else{
                    //Otherwise away are leaders
                    Collections.sort(third);
                    place[2] = away.get(j).getPitchHits();    //Putting first place value into array
                }
                /*
                End of Check/merge
                 */
            }//End of Third Place
        }//End of hit by pitch leaders

    }//End of Leader Strings


    //Returns a string key-value pair Hash map for the Statistics keyfile
    public static GenericHashMap<String, String> createStatHashMap() throws FileNotFoundException {
        //Creating hash map object for stats
        GenericHashMap<String, String> statHash = new GenericHashMap<>();

        //Scanner for key file
        File keyfile = new File("keyfile.txt");
        Scanner kFile = new Scanner(keyfile);

        //Creating regex for key-value pairs
        String line = "";
        String value = "";
        String key = "";
        Pattern type = Pattern.compile("## [a-zA-Z\\s]* ##"); //Regex for ## OUTS ## lines to grab word, this is a value
        Matcher check = type.matcher(line); //will check for matches of types regex expression, this is the value of the succeeding keys

        //Looping thru file to parse into hash map
        while (kFile.hasNext()) {

            //Read in line from file
            line = kFile.nextLine();

            //Checking if line is empty to skip
            if (line.isEmpty()) {
                continue;
            }
            //Giving matcher line to check
            check.reset(line);

            //If the matcher finds a match
            if (check.find()) {
                //Set value string to match
                line = line.replaceAll(" ", ""); //Removing spaces
                value = line.replaceAll("#", ""); //Breaking off the ## chars
            } else {

                //If a match is not found then the line read in is a key
                key = line;

                //If a key is found it is used to place into the hash table with its associated value
                statHash.put(key, value);

            }


        }

        //Returning newly created table with mappings
        return statHash;
    }

    //Updates a player object with string stat given from hash
    public static void statUpdate(Player player, String stat){

        /*
        Bulky String If Statement Processor
         */

        //OUTS String
        if(stat.equals("OUTS")){
            player.addOut();
        }
        //STRIKEOUT String
        if(stat.equals("STRIKEOUT")){
            player.addStrikeOut();
        }
        //HITS String
        if(stat.equals("HITS")){
            player.addHit();
        }
        //WALK String
        if(stat.equals("WALK")){
            player.addWalk();
        }
        //SACRIFICE String
        if(stat.equals("SACRIFICE")){
            player.addSacFly();
        }
        //HIT BY PITCH String
        if(stat.equals("HITBYPITCH")){
            player.addPitchHits();
        }
        //ERRORS String
        if(stat.equals("ERRORS")){
            player.addError();
        }
    }

    public static void main(String[] args) throws IOException {

        //Prompting user for filename
        System.out.println("Please enter your file name.");

        //Creating scanners for console and then creating a file
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        File file = new File(fileName);


        //Checking if file exists and looping until valid file given
        while (!file.exists()) {
            System.out.println("File not found. Please enter a valid file name.");
            fileName = scanner.nextLine();
            file = new File(fileName);
        }

        //Hardcoded file name
        //File file = new File("sample_playbyplay.txt");

        //Creating scanner for file
        Scanner input = new Scanner(file);

        //Create Stats Hash Map, using String for both keys and values
        GenericHashMap<String, String> statHash = createStatHashMap();

        //Create Away Hash Map String for key and Player for value
        GenericHashMap<String, Player> awayHash = new GenericHashMap<>();

        //Create Home Hash Map String for key and Player for value
        GenericHashMap<String, Player> homeHash = new GenericHashMap<>();

        //Loop thru play by play file to update players and their stats
        while (input.hasNext()) {

            /*
            Parsing Line
             */

            //Home or Away Team flag
            String homeAway = input.next();
            //Getting player name
            String name = input.next();
            //Getting play
            String play = input.next();
            //Moving scanner to next line
            if(input.hasNext())
                input.nextLine();

            /*
            End of Parsing Line
             */

            /*
            Decision Tree for Home/Away Tables
            There is a second decision depending
            on if the name of the player is already in the table.
             */
            if(homeAway.equals("A")){
                //Searching Away Hash Table
                //If this returns null then the name is not in the table and will need to be created
                if(awayHash.get(name) == null){
                    Player temp = new Player(name); //Creating new player object to add to hash table
                    statUpdate(temp, statHash.get(play));  //Updating stat in newly created player using statHashTable and the key given from play by play
                    awayHash.put(name, temp);       //Putting newly made object into hash map with its key being the name
                }
                else{
                    //Otherwise name exists and we can just update current object
                    statUpdate(awayHash.get(name), statHash.get(play));   //Updates the players stat, player already exists
                }
            }
            else{
                //Searching Home Hash table
                //If this returns null then the name is not in the table and will need to be created
                if(homeHash.get(name) == null){
                    Player temp = new Player(name); //Creating new player object to add to hash table
                    statUpdate(temp, statHash.get(play));  //Updating stat in newly created player using statHashTable and the key given from play by play
                    homeHash.put(name, temp);       //Putting newly made object into hash map with its key being the name
                }
                else{
                    //Otherwise name exists and we can just update current object
                    statUpdate(homeHash.get(name), statHash.get(play));   //Updates the players stat, player already exists
                }
            }

            /*
            End of Decision tree
             */
        }

        /*
        Sorting and Outputting
         */

        //Sorting by making a new ArrayList of the Player Objects from the two hash tables
        //Order does not matter since it was originally used for its lookup speed
        ArrayList<Player> awayTeam = new ArrayList<>(awayHash.getSize());   //Initializing with hash size
        ArrayList<Player> homeTeam = new ArrayList<>(homeHash.getSize());

        //Iterating thru hashtable and putting into array list, for away team
        for(int i = 0; i < awayHash.getTable().length; i++){
            //Only doing anything if the value exists
            if(awayHash.getTable()[i] != null) {
                //Casting around object runtime classification, Pair is the only thing that can be a container in this HashMap
                Pair<String, Player> hold = (Pair<String, Player>) awayHash.getTable()[i];
                //Now actually adding to Array List by accessing the Pairs value
                awayTeam.add(hold.getValue());  //This is the player object being accessed
            }
        }

        //Home Team version
        for(int i = 0; i < homeHash.getTable().length; i++){
            //Only doing something if value exists
            if(homeHash.getTable()[i] != null) {
                //Casting around object runtime classification, Pair is the only thing that can be a container in this HashMap
                Pair<String, Player> hold = (Pair<String, Player>) homeHash.getTable()[i];
                //Now actually adding to Array List by accessing the Pairs value
                homeTeam.add(hold.getValue());  //This is the player object being accessed
            }
        }

        //Sorting Array Lists
        Collections.sort(awayTeam);
        Collections.sort(homeTeam);

        //Outputting Box Score
        System.out.println("AWAY");
        for(Player p: awayTeam){
            if(p.getName().length() < 4){
                System.out.println(p.getName()+ "\t" + "\t" + "\t" + p.toString());
            }
            else if(p.getName().length() > 6){
                System.out.println(p.getName()+ "\t" + p.toString());
            }
            else{
                System.out.println(p.getName() + "\t" + "\t" + p.toString());
            }
        }
        System.out.println("\nHOME");
        for(Player p: homeTeam){
            if(p.getName().length() < 4){
                System.out.println(p.getName()+ "\t" + "\t" + "\t" + p.toString());
            }
            else if(p.getName().length() > 6){
                System.out.println(p.getName()+ "\t" + p.toString());
            }
            else{
                System.out.println(p.getName() + "\t" + "\t" + p.toString());
            }
        }
        System.out.println();

        /*
        End of Sorting and Outputting for box score
         */

        /*
        Sorting and Outputting Leaders
         */


        //Sorting List Based on Each stat to find leaders
        System.out.println("LEAGUE LEADERS");
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        ArrayList<String> third = new ArrayList<>();
        double[] placeValue = {0.000,0.000,0.000};

        int yes = 1;
        //Batting Average
        System.out.println("BATTING AVERAGE");
        Collections.sort(awayTeam, new PlayerBatAvgComparator());
        Collections.reverse(awayTeam);
        Collections.sort(homeTeam, new PlayerBatAvgComparator());
        Collections.reverse(homeTeam);
        getLeaderStrings(first,second,third, awayTeam,homeTeam, new PlayerBatAvgComparator(),placeValue);
        printLeaders(placeValue,first,second,third, yes);    //Prints leaders
        first.clear();
        second.clear();
        third.clear();

        //On Base Percentage
        System.out.println("ON-BASE PERCENTAGE");
        Collections.sort(awayTeam, new PlayerOnBasePerComparator());
        Collections.reverse(awayTeam);
        Collections.sort(homeTeam, new PlayerOnBasePerComparator());
        Collections.reverse(homeTeam);
        getLeaderStrings(first,second,third, awayTeam,homeTeam, new PlayerOnBasePerComparator(),placeValue);
        printLeaders(placeValue,first,second,third, yes);    //Prints leaders
        first.clear();
        second.clear();
        third.clear();

        yes = 0;
        //Hits
        System.out.println("HITS");
        Collections.sort(awayTeam, new PlayerHitsComparator());
        Collections.reverse(awayTeam);
        Collections.sort(homeTeam, new PlayerHitsComparator());
        Collections.reverse(homeTeam);
        getLeaderStrings(first,second,third, awayTeam,homeTeam, new PlayerHitsComparator(),placeValue);
        printLeaders(placeValue,first,second,third, yes);    //Prints leaders
        first.clear();
        second.clear();
        third.clear();

        //Walks
        System.out.println("WALKS");
        Collections.sort(awayTeam, new PlayerWalksComparator());
        Collections.reverse(awayTeam);
        Collections.sort(homeTeam, new PlayerWalksComparator());
        Collections.reverse(homeTeam);
        getLeaderStrings(first,second,third, awayTeam,homeTeam, new PlayerWalksComparator(),placeValue);
        printLeaders(placeValue,first,second,third, yes);    //Prints leaders
        first.clear();
        second.clear();
        third.clear();

        //StrikeOuts
        System.out.println("STRIKEOUTS");
        Collections.sort(awayTeam, new PlayerStikeoutsComparator());
        Collections.reverse(awayTeam);
        Collections.sort(homeTeam, new PlayerStikeoutsComparator());
        Collections.reverse(homeTeam);
        getLeaderStrings(first,second,third, awayTeam,homeTeam, new PlayerStikeoutsComparator(),placeValue);
        printLeaders(placeValue,first,second,third, yes);    //Prints leaders
        first.clear();
        second.clear();
        third.clear();

        //Hit By Pitch
        System.out.println("HIT BY PITCH");
        Collections.sort(awayTeam, new PlayerHitByPitchComparator());
        Collections.reverse(awayTeam);
        Collections.sort(homeTeam, new PlayerHitByPitchComparator());
        Collections.reverse(homeTeam);
        getLeaderStrings(first,second,third, awayTeam,homeTeam, new PlayerHitByPitchComparator(),placeValue);
        printLeaders(placeValue,first,second,third, yes);    //Prints leaders
        first.clear();
        second.clear();
        third.clear();
    }
}

