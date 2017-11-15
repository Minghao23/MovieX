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

@WebServlet("/modifyPassword")
public class modifyPassword extends HttpServlet {

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
        String userName = req.getParameter("inputUsername");
        String password = req.getParameter("inputNewPassword");
        Users user;
        String msg = null;
        try {
            user = usersDao.getUserByUserName(userName);
            user.setPassword(password);
            usersDao.update(user);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("user.jsp?UserName=" + userName).forward(req, resp);
    }
}

