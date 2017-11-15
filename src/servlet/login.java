package servlet;
/**
 * Created by hu_minghao on 2/28/17.
 */

import dal.*;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/login")
public class login extends HttpServlet {

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
        //List<Movies> movieList;
        Users user;
        String msg = " ";
        try {
            user = usersDao.getUserByUserName(inputusername);
            if(user == null){
                msg = "Incorrect username";
            }else{
                String password = user.getPassword();
                if(!password.equals(inputpassword)){
                    msg = "Incorrect password";
                }else{
                    //msg = "Welcome! " + user.getFirstName() + " " +user.getLastName() + "!";
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                    return;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("msg", msg);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}

