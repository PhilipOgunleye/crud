/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolclass;

/**
 *
 * @author philip
 */
public class SchoolClass {
    private String title;
    private int startTime;
    private int endTime;
    private int day;
    
    public SchoolClass(String title, int startTime, int endTime, int day){
this.title = title;
this.startTime = startTime;
this.endTime = endTime;
this.day = day;

}

    public String getTitle() {
        return title;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getDay() {
        return day;
    }
    
}
