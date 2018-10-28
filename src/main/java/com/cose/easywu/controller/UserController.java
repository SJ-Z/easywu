package com.cose.easywu.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cose.easywu.exception.UserException;
import com.cose.easywu.po.User;
import com.cose.easywu.service.UserService;
import com.cose.easywu.utils.CommonUtils;
import com.cose.easywu.utils.email.Mail;
import com.cose.easywu.utils.email.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Properties;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 个人中心
    @RequestMapping("/personalCenter")
    public @ResponseBody String personalCenter(@RequestBody String u_id) {
        // 查询数据
        User user = userService.getUserInfo(u_id);
        // ...

        // 封装数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        String content = jsonObject.toJSONString();
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 修改密码
    @RequestMapping("/resetPwd")
    public @ResponseBody String resetPwd(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String u_email = jsonObject.getString("u_email");
        String u_pwd = jsonObject.getString("u_pwd");
        boolean success = userService.resetPwd(u_email, u_pwd);
        String content = "";
        if (success) {
            content = "{'code':'1', 'msg':'修改密码成功'}";
        } else {
            content = "{'code':'0', 'msg':'修改密码失败'}";
        }
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 验证邮箱验证码
    @RequestMapping("/checkVerifyCode")
    public @ResponseBody String checkVerifyCode(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String u_email = jsonObject.getString("u_email");
        String u_code = jsonObject.getString("u_code");
        boolean correct = userService.checkEmailCode(u_email, u_code);
        String content = "";
        if (correct) {
            content = "{'code':'1', 'msg':'成功'}";
        } else {
            content = "{'code':'0', 'msg':'验证码错误'}";
        }
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 找回密码
    @RequestMapping("/findPwd")
    public @ResponseBody String findPwd(@RequestBody String email) {
        boolean exist = userService.checkEmailExist(email);
        String content = "";
        if (exist) {
            content = "{'code':'1', 'msg':'邮箱存在'}";
        } else {
            content = "{'code':'0', 'msg':'邮箱不存在'}";
            try {
                return URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // 存储邮件验证码
        String code = CommonUtils.uuid(6);
        userService.saveEmailCode(email, code);
        // 发邮件
        sendEmail(email, null, code, "findPwd"); // 发送验证码邮件

        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 登录
    @RequestMapping("/login")
    public @ResponseBody String login(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String u_email = jsonObject.getString("u_email");
        String u_pwd = jsonObject.getString("u_pwd");
        try {
            String u_id = userService.login(u_email, u_pwd);
            String content = "{'code':'1', 'msg':'登录成功', 'u_id':'" + u_id + "'}";
            try {
                return URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (UserException e) {
            e.printStackTrace();
            String content = "{'code':'0', 'msg':'" + e.getMessage() + "', 'u_id':''}";
            try {
                return URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    // 注册
    @RequestMapping("/regist")
    public @ResponseBody String regist(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        User user = new User();
        user.setU_email(jsonObject.getString("u_email"));
        user.setU_pwd(jsonObject.getString("u_pwd"));
        user.setU_nick(jsonObject.getString("u_nick"));

        boolean emailExist = userService.checkEmailExist(user.getU_email());
        if (emailExist) {
            String content = "{'code':'0', 'msg':'该邮箱已存在'}";
            try {
                return URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        boolean nickExist = userService.checkNickExist(user.getU_nick());
        if (nickExist) {
            String content = "{'code':'0', 'msg':'该昵称已被注册'}";
            try {
                return URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // 补全用户信息
        user.setU_id(CommonUtils.uuid(16));
        user.setU_code(CommonUtils.uuid(6));
        user.setU_state(0);
        userService.regist(user); // 注册用户
        sendEmail(user.getU_email(), user.getU_id(), user.getU_code(), "regist"); // 发送激活邮件

        String content = "{'code':'1', 'msg':'注册成功'}";
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/activeEmail/{id}/{code}")
    public ModelAndView activeEmail(@PathVariable("id") String id, @PathVariable("code") String code) {
        userService.updateUserState(id, code, 1);

        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("activeSuc");

        return modelAndView;
    }

    private void sendEmail(String u_email, String u_id, String u_code, String action) {
        /** 发邮件
         * 准备配置文件
         */
        try {
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
            String host = props.getProperty("host"); //获取服务器主机
            String uname = props.getProperty("uname"); //获取用户名
            String pwd = props.getProperty("pwd"); //获取密码
            String from = props.getProperty("from"); //获取发件人
            String to = u_email; //获取收件人
            String title;
            String content;

            if (action.equals("regist")) {
                title = props.getProperty("title_regist"); //获取主题
                content = props.getProperty("registContent"); //获取邮件内容;
                content = MessageFormat.format(content, u_id, u_code); //替换占位符
            } else if (action.equals("findPwd")) {
                title = props.getProperty("title_findPwd"); //获取主题
                content = props.getProperty("findPwdContent"); //获取邮件内容;
                content = MessageFormat.format(content, u_code); //替换占位符
            } else {
                throw new RuntimeException("程序不该执行到此处！");
            }

            Session session = MailUtils.createSession(host, uname, pwd);
            Mail mail = new Mail(from, to, title, content); //创建邮件对象
            try {
                MailUtils.send(session, mail); //发邮件
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
