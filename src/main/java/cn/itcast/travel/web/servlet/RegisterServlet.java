package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
