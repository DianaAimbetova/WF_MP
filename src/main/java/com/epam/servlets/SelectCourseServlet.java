package com.epam.servlets;


import com.epam.CourseType;
import com.epam.services.CourseService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(
        name = "selectcourseservlet",
        urlPatterns = "/SelectCourses"
)
public class SelectCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String courseType = req.getParameter("Type");

        CourseService courseService = new CourseService();
        CourseType l = CourseType.valueOf(courseType);

        List courseNames = courseService.getAvailableCourses(l);

        req.setAttribute("courses", courseNames);
        RequestDispatcher view = req.getRequestDispatcher("result.jsp");
        view.forward(req, resp);

    }
}