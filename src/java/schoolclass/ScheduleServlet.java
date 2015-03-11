/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolclass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author philip
 */

@WebServlet("/Schedule")
public class ScheduleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            java.io.IOException {
        String title = request.getParameter("title");
        int starttime = Integer.parseInt(request.getParameter("starttime"));
        int endtime = Integer.parseInt(request.getParameter("endtime"));
        String[] days = request.getParameterValues("day");

        SchoolSchedule schedule
                = (SchoolSchedule) request.getSession(true).getAttribute("schoolschedule");
        if (schedule == null) {
            schedule = new SchoolSchedule();
        }
        if (days != null) {
            for (int i = 0; i < days.length; i++) {
                String dayString = days[i];
                int day;
                if (dayString.equalsIgnoreCase("SUN")) {
                    day = 0;
                } else if (dayString.equalsIgnoreCase("MON")) {
                    day = 1;
                } else if (dayString.equalsIgnoreCase("TUE")) {
                    day = 2;
                } else if (dayString.equalsIgnoreCase("WED")) {
                    day = 3;
                } else if (dayString.equalsIgnoreCase("THU")) {
                    day = 4;
                } else if (dayString.equalsIgnoreCase("FRI")) {
                    day = 5;
                } else {
                    day = 6;
                }

                SchoolClass clazz = new SchoolClass(title, starttime, endtime, day);
                schedule.addClass(clazz);
            }
        }
        request.getSession().setAttribute("schoolschedule", schedule);
        getServletContext().getRequestDispatcher("/Schedule.jsp").forward(request, response);

    }

}
