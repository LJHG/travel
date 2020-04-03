package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet{

    /**
     * Activate the user
     * @param req
     * @param resp
     * @throws IOException
     */
    public void activate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        UserService userService = new UserServiceImpl();
        boolean activateSuccess = userService.activate(code);
        if(activateSuccess){
            //激活成功
            resp.getWriter().write("激活成功，<a href='http://localhost/travel/login.html'>登陆</a>");
        }
        else{
            resp.getWriter().write("激活失败");
        }
    }


    /**
     * Current user exits
     * @param req
     * @param resp
     * @throws IOException
     */
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //删除session
        req.getSession().invalidate();

        resp.sendRedirect(req.getContextPath() + "/login.html");
    }

    /**
     * Get current user in the session
     * @param req
     * @param resp
     * @throws IOException
     */
    public void getCurrentUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * Login
     * @param req
     * @param resp
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserService userService = new UserServiceImpl();
        ResultInfo loginInfo = userService.login(username,password);
        if(loginInfo.isFlag())
        {
            //add into session
            req.getSession().setAttribute("user",loginInfo.getData());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(loginInfo);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * Register
     * @param req
     * @param resp
     * @throws IOException
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResultInfo resultInfo = new ResultInfo();
        //获取前端信息
        Map<String,String[]> paramsMap = req.getParameterMap();


        //判断验证码
        String checkCode = req.getParameter("check");
        String realCheckCode = (String) req.getSession().getAttribute("CHECKCODE_SERVER");
        req.getSession().removeAttribute("CHECKCODE_SERVER");

        if(!realCheckCode.equalsIgnoreCase(checkCode))
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            //turn resultinfo into json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;

        }


        User registerUser = new User();
        try {
            BeanUtils.populate(registerUser,paramsMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //将user进行注册
        UserService userService = new UserServiceImpl();
        boolean successRegister =  userService.RegisterUser(registerUser);
        //创建要返回到前台的数据


        if(successRegister)
        {
            //因为是ajax，所以不能直接在这里进行跳转，要告诉前台页面使用js来跳转
            resultInfo.setFlag(true);
        }
        else
        {
            //用户已存在
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户已存在!");
        }

        //turn resultinfo into json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    public void test(HttpServletRequest req, HttpServletResponse resp)
    {
        System.out.println("测试分发");
    }
}
