package servlet;
/**
 * Created by hu_minghao on 2/28/17.
 */

import dal.UsersDao;
import model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signup")
public class signup extends HttpServlet {

    protected UsersDao usersDao;

    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String inputusername = req.getParameter("inputUserName");
        String inputpassword = req.getParameter("inputPassword");
        String inputfirstname = req.getParameter("inputFirstName");
        String inputlastname = req.getParameter("inputLastName");
        String inputemail = req.getParameter("inputEmail");
        String msg = null;
        Users user = new Users(inputusername,inputfirstname,inputlastname,inputemail,inputpassword);
        try {
            user = usersDao.create(user);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("msg", msg);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}

