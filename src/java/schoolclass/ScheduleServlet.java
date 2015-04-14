/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolclass;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Type", "text/plain-text");
        try (PrintWriter out = response.getWriter()) {
            if (!request.getParameterNames().hasMoreElements()) {
                out.println(getResults("SELECT * FROM schoolschedule"));
            } else {
                int id = Integer.parseInt(request.getParameter("productID"));
                out.println(getResults("SELECT * FROM schoolschedule WHERE title = ?", String.valueOf(id)));
            }
        } catch (IOException ex) {
            Logger.getLogger(SampleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getResults(String query, String... params) {
        StringBuilder sb = new StringBuilder();
        try (Connection conn = credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            ResultSet rs = pstmt.executeQuery();
            sb.append("[");
            while (rs.next()) {
                sb.append(String.format("{ \"day\" : %s , \"Title\" : \"%s\", \"Starttime\" : \"%s\", \"Endtime\" : %s }" + ",\n", rs.getInt("day"), rs.getString("Title"), rs.getString("Starttime"), rs.getInt("Endtime")));
                //sb.append(", ");

            }

            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append("]");
        } catch (SQLException ex) {
            Logger.getLogger(SampleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

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
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        Set<String> keySet = request.getParameterMap().keySet();
        try (PrintWriter out = response.getWriter()) {
            if (keySet.contains("title") && keySet.contains("starttime") && keySet.contains("endtime") && keySet.contains("day")) {
                // There are some parameters                
                String Title = request.getParameter("title");
                String Starttime = request.getParameter("starttime");
                String Endtime = request.getParameter("endtime");
                String Day = request.getParameter("day");
                doUpdate("UPDATE schoolschedule SET  title=?, starttime=?, endtime=? WHERE day=?", Title, Starttime, Endtime, Day);
            } else {
                // There are no parameters at all
                out.println("Error: Not enough data to input. Please use a URL of the form /servlet?Title=XXX&Starttime=XXX&Endtime=XXX");
            }
        } catch (IOException ex) {
            Logger.getLogger(SampleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
     @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Set<String> keySet = request.getParameterMap().keySet();
        try (PrintWriter out = response.getWriter()) {
            if (keySet.contains("productID")) {
                // There are some parameters                
                String productID = request.getParameter("schoolschedule");
                String[] schoolschedule = null;
                doUpdate("DELETE from product WHERE schoolschedule=?", schoolschedule);
            } else {
                // There are no parameters at all
                out.println("Error: Not enough data to input. Please use a URL of the form /servlet?Title=XXX&Starttime=XXX&Endtime=XXX");
            }
        } catch (IOException ex) {
            Logger.getLogger(SampleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 private int doUpdate(String query, String... params) {
        int numChanges = 0;
        Connection conn = credentials.getConnection();
        
            try {
                PreparedStatement pstmt = conn.prepareStatement(query);
                for (int i = 1; i <= params.length; i++) {
                    pstmt.setString(i, params[i - 1]);
                }
                numChanges = pstmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        return numChanges;
    }
}
