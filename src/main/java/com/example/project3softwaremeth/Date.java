package com.example.project3softwaremeth;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * Date class to be able to compare and check valid dates from user input
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int TWENTYONE = 2021;

    // the months
    public static final int January = 1;
    public static final int February = 2;
    public static final int March = 3;
    public static final int April = 4;
    public static final int May = 5;
    public static final int June = 6;
    public static final int July = 7;
    public static final int August = 8;
    public static final int September = 9;
    public static final int November = 10;
    public static final int October = 11;
    public static final int December = 12;


    /**
     *  take 'mm/dd/yyyy' and create a Date object
     * @param date that is read and tokenized
     */
    public Date(String date) {
        try {
            StringTokenizer st = new StringTokenizer(date, "/");

            this.month = Integer.parseInt(st.nextToken());
            this.day = Integer.parseInt(st.nextToken());
            this.year = Integer.parseInt(st.nextToken());
        } catch (Exception E) {

        }
    }
    /**
     * take an instance from calendar class and intilize date using it
     */
    public Date() {

        Calendar calendar = Calendar.getInstance();

        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);

    } // create an object with today’s date (see Calendar class)
    /**
     * getter method to return the year
     * @return
     */
    public int getYear() {
        return year;
    }
    /**
     * getter method to return the month
     * @return
     */
    public int getMonth() {
        return month;
    }
    /**
     * getter method to return the day
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * boolean method that checks if the date is valid and returns true or false
     * @return
     */
    public boolean isValid() {

        Date curDate = new Date();
        int curYear = curDate.getYear();
        int curMonth = curDate.getMonth() + 1; // need to add one, since in the calendar class jan is intilized as 0
        int curDay = curDate.getDay();

        

        // this is just checking if the date is greater than the current date
        if (this.year > curYear || this.year < TWENTYONE) {
            return false;
        } else if ((this.month > curMonth && this.year == curYear) || this.month <= 0||this.month>12) {
            return false;
        } else if ((this.day > curDay && this.month == curMonth && this.year == curYear) || this.day <= 0) {
            return false;
        }

        if (this.month == February) {
            if (isLeapYear(this.year)) { // if it is a leap year, day cannot be greater than 29
                if (this.day <= 29) {
                    return true;
                }
            } else if (this.day <= 28) { // if not cannot be greater than 28
                return true;
            }
        } else if (this.month == January || this.month == March || this.month == May || this.month == July
                || this.month == August || this.month == October || this.month == December) {
            if (this.day <= 31) { // this months only have 31 days
                return true;
            }
        } else if (this.day <= 30) { // if its not febuary and not one of the previous months it has 30 days
            return true;
        }

        return false; // if it doesnt meet the criteria must be invalid

    }
    /**
     * boolean method to check if a given year is a leap year
     * @param year
     * @return
     */
    public boolean isLeapYear(int year) { // checks to see if leap year or not

        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * this method is checks two dates and returns the difference as an int between them.
     */
    @Override
    public int compareTo(Date date) {
        int diff = this.year - date.year;

        if (diff != 0) {
            return diff;
        }

        diff = this.month - date.month;

        if (diff != 0) {
            return diff;
        }

        return this.day - date.day;
    }

    /**
     * this method prints out a date object that is readable by human
     */

    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

}