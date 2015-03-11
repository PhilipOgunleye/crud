/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolclass;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author philip
 */
public class SchoolSchedule {
    
    private List classes = new ArrayList();

    public List getClasses() {
        return classes;
    }
    public void addClass(SchoolClass schoolClass)
{
        classes.add(schoolClass);
}
}
