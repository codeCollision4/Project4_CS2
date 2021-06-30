package com.company;

//Generic Hash Map class
public class GenericHashMap<K,V> {

    //Variables
    private int size = 13;  //Starting size of array
    private int count;  //Keeps track of elements in table
    private Object[] table; //Holds objects, index is determined by double hashing
    private static double loadFactor = 0.5;   //Load factor for rehashing
    private double lambda; //If lambda > loadFactor then rehash


    //Default Constructor
    public GenericHashMap() {
        count = 0;
        lambda = 0;
        table = new Object[size];
    }

    //Direct Size Constructor
    public GenericHashMap(int s) {
        count = 0;
        lambda = 0;
        table = new Object[s];
        size = s;
    }

    //Getters
    public int getCount() {return count;}
    public int getSize() {return size;}
    public Object[] getTable() {return table;}

    //Gets value based on key
    @SuppressWarnings({"unchecked"})
    public V get(K key) {

        //Double hashing
        int hash1 = Math.abs(key.hashCode()) % 11;
        int hash2 = 13 - Math.abs(key.hashCode()) % 13;

        //Probing with an iteration loop
        for(int i = 0; i < size; i++){
            //Checking if the object at the index is a Pair obj
            if(table[(hash1 + (i*hash2)) % size] instanceof Pair){
                //Typecasts to pair to get the value associated with the key out of the object array
                Pair<K,V> hold = (Pair<K,V>)table[(hash1 + (i*hash2)) % size];
                //If the keys match return the value at that index
                if(hold.getKey().equals(key)){
                    return hold.getValue();
                }
            }
        }
        //If key is not found then null is returned
        return null;
    }

    //Set Load Factor
    public void setLoadFactor(double lf){
        //Only setting if number given is less than 1, load cant be more than 1 with this implementation
        if(lf < 1) {
            loadFactor = lf;
        }
        else{
            System.out.println("Load factor for this implementation must be less than 1.0");
        }
    }

    //Put function
    public void put(K key,V value){

        //Creating Pair object
        Pair<K,V> bucket = new Pair<>(key,value);

        //Checking Load Factor and if rehash is needed
        lambdaCalc();
        if(lambda > loadFactor){
            nextPrime();
            reHash();
        }

        //Double hashing
        int hash1 = Math.abs(key.hashCode()) % 11;
        int hash2 = 13 - Math.abs(key.hashCode()) % 13;

        //Probing
        int i = 0;  //Counter variable
        while(table[(hash1 + (i*hash2)) % table.length] != null){   //Loops until empty bucket is found, based on double hash
            i++;    //Moving to next bucket based on jump sequence from hash2
        }

        //Putting bucket into array
        table[(hash1 + (i*hash2)) % table.length] = bucket;

        //Resetting counter
        i = 0;

        //Incrementing counter
        count++;
    }

    //Print Hash Map
    public void printHash(){
        for(int i = 0; i < table.length; i++){
            if(table[i] == null){
                System.out.println(i);  //Printing underscore if no value
            }
            else{
                System.out.println(table[i].toString()); //Printing value otherwise
            }

        }
        //Printing new line
        System.out.println();
    }
    /********************
     * Helper Functions **
     *********************/

    //Lambda Calc
    private void lambdaCalc(){lambda = count/(double)size;}

    //Getting next prime
    private void nextPrime(){

        int newSize = (size*2) + 1; //Starting prime check at number after doubling

        /*
        Loop starting at first prime of 2, will increment and divide by i.
        If i is a divisor of the size then size will be incremented and that new number will be divided by i after
        resetting the counter back to 2.
        */
        for(int i = 2; i < newSize; i++){   //loops thru possible divisors up until itself
            if(newSize % i == 0){   //checks if i is a divisor
                newSize++;  //next number to check
                i = 2;      //restarting divisors back at 2
            }
        }

        size = newSize; //Setting size to next prime found

    }//End of nextPrime

    //Rehashing elements
    private void reHash(){

        //Creating new array with new size
        Object[] temp = new Object[size];

        //Looping thru old table and rehashing each value
        for (Object o : table) {
            //Rehash values that exist, ignoring null
            if (o != null) {

                //Double hash
                int hash1 = o.hashCode() % 7;
                int hash2 = 13 - Math.abs(o.hashCode()) % 13;

                //Probing
                int k = 0;  //Counter variable
                while (temp[(hash1 + (k * hash2)) % temp.length] != null) {   //Loops until empty bucket is found, based on double hash
                    k++;    //Moving to next bucket based on jump sequence from hash2
                }

                //Putting bucket into new array
                temp[(hash1 + (k * hash2)) % temp.length] = o;

                //Resetting counter
                k = 0;
            }
        }
        
        //Setting table to new array
        table = temp;
        
    }//End of rehash
}

/***********************************
*** Container Class for Hash Map ***
 ***********************************/
class Pair<K,V> {

    //Variables
    private K key;
    private V value;

    //Constructor
    public Pair(){}
    public Pair(K k, V v){
        key = k;
        value = v;
    }

    //Getters

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    //Setters

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    //toString
    @Override
    public String toString(){
        return key + "=" + value;
    }
}