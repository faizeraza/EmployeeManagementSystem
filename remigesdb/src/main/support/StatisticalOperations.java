package com.assignments.demo.support;

import java.util.ArrayList;
import java.util.Collections;

public class StatisticalOperations {

    protected ArrayList<Float> numbers;

    public StatisticalOperations(ArrayList<Float> numbers) {
        this.numbers = numbers;
    }

    public int getCount() {                          // Returns Number of elements in the list 

        return numbers.size();

    }

    public float getMean() {                         // calculates the mean value
        float sum = 0;

        for (float number : numbers) {                  // each number will be added into sum variable
            sum += number;                              // for each loop is the better way to go with

        }
        return sum / (float) numbers.size();

    }

    public float getMaximum() {                         // method to find the maximum number among given list of numbers

        float max = numbers.get(0);

        for (int index = 1; index < numbers.size(); index++) { // from index 1 compare every element with max 
            float current = numbers.get(index);
            if (max < current) {                            // if max if smaller we found new maximun number 
                max = current;                           // set max to current number
            }
        }

        return max;

    }

    public float getMinimum() {

        float min = numbers.get(0);                          // assume first number as smallest for now
        for (int index = 1; index < numbers.size(); index++) {   // from index 1 compare every element with min
            float current = numbers.get(index);
            if (min > current) {                              // if min if greater we found new maximun number 
                min = current;                             // set max to current number
            }
        }
        return min;

    }

    public float getSum() {
        float sum = 0;                              // to store sum

        for (float number : numbers) {                  // each number will be added into sum variable
            sum += number;                              // for each loop is the better way to go with

        }
        return sum;
    }

    public ArrayList<Float> sortList() {
        Collections.sort(numbers); // Sort the list using Collections.sort
        return numbers; // Return the sorted list
    }

    public int getEvenCount() {
        int count = 0; // counter for even

        for (float value : numbers) {

            //Check weather the value is even or not 
            // Increament the count If True
            if (value % 2 == 0) {

                count++;

            }

        }

        return count;

    }

    public int getOddCount() {
        int count = 0;    // counter for odd

        for (float value : numbers) {
            //Check weather the value is even or not 
            // Increament the count If True
            if (value % 2 != 0) {
                count++;
            }
        }
        return count;
    }
}
