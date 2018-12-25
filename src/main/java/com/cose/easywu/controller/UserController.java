package com.cose.easywu.controller;

import com.alibaba.fastjson.JSONObject;
import com.cose.easemob.lmc.service.TalkDataService;
import com.cose.easemob.lmc.service.impl.TalkDataServiceImpl;
import com.cose.easemob.lmc.service.impl.TalkHttpServiceImplApache;
import com.cose.easemob.lmc.tool.JsonTool;
import com.cose.easywu.exception.UserException;
import com.cose.easywu.po.User;
import com.cose.easywu.service.UserService;
import com.cose.easywu.utils.CommonUtils;
import com.cose.easywu.utils.email.Mail;
import com.cose.easywu.utils.email.MailUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    // 通过构造方法注入http请求业务以实现数据业务
    TalkDataService talkDataService = new TalkDataServiceImpl(new TalkHttpServiceImplApache());

    // 获取环信需要的昵称、头像信息
    @RequestMapping("/hxInfo")
    public @ResponseBody String hxInfo(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String u_id = jsonObject.getString("u_id");
        User user = userService.getUserInfo(u_id);
        jsonObject = new JSONObject();
        if (user != null) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "SUCCESS");
            jsonObject.put("nick", user.getU_nick());
            jsonObject.put("photo", user.getU_photo());
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "FAIL");
            jsonObject.put("nick", " ");
            jsonObject.put("photo", " ");
        }

        try {
            return URLEncoder.encode(jsonObject.toJSONString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 修改头像
    @RequestMapping("/editPhoto")
    public @ResponseBody String editPhoto(HttpServletRequest request) {
        String content; // 返回给客户端的内容

        try {
            request.setCharacterEncoding("utf-8");  //设置编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory(10*1024*1024, new File("E:/temp"));//设置缓存大小和临时目录
        //得到解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //设置单个文件最大值为10*1024*1024
        sfu.setFileSizeMax(10*1024*1024);

        // 设置保存路径
        String str = request.getServletContext().getRealPath("/");
        int loc = str.indexOf("target");
        String savepath = str.substring(0, loc) + "user_photo";

        //使用sfu去解析request对象，得到List<FileItem>
        try {
            List<FileItem> fileItemList = sfu.parseRequest(new ServletRequestContext(request));
            int photoIndex = -1;
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < fileItemList.size(); i++) {
                if (fileItemList.get(i).isFormField()) {
                    map.put(fileItemList.get(i).getFieldName(), fileItemList.get(i).getString("utf-8"));
                } else { // 图片类型
                    photoIndex = i;
                }
            }

            // 得到用户id
            String u_id = map.get("u_id");

            //设置图片名称：u_id + 随机16位uuid + 图片后缀
            int begin = fileItemList.get(photoIndex).getName().indexOf(".");
            String suffix = fileItemList.get(1).getName().substring(begin); //截取图片后缀
            String filename = u_id + CommonUtils.uuid(16) + suffix;

            if (!(filename.toLowerCase().endsWith("png") || filename.toLowerCase().endsWith("jpg") || filename.toLowerCase().endsWith("jpeg"))) {
                // 图片格式错误
                content = "{'code':'0', 'msg':'图片格式错误，上传失败'}";
                try {
                    return URLEncoder.encode(content, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return content;
            }

            //使用目录和文件名称创建目标文件
            File destFile = new File(savepath, filename);
            //保存上传文件到目标文件位置
            fileItemList.get(photoIndex).write(destFile);

            // 删除原头像
            String oldPhoto = userService.getUserInfo(u_id).getU_photo();
            if (oldPhoto != null) {
                File file = new File(savepath, oldPhoto);
                if (file.exists()) {
                    file.delete();
                }
            }

            // 保存到服务器
            userService.savePhoto(u_id, filename);

            // 返回成功信息和图片名给客户端
            content = "{'code':'1', 'msg':'" + filename + "'}";
            try {
                return URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return content;
        } catch (Exception e){
            if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
                // 图片尺寸过大
                content = "{'code':'0', 'msg':'图片尺寸超过10MB，上传失败'}";
                try {
                    return URLEncoder.encode(content, "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                return content;
            }
        }

        return null;
    }

    // 修改性别
    @RequestMapping("/editSex")
    public @ResponseBody String editSex(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String u_id = jsonObject.getString("u_id");
        int u_sex = jsonObject.getInteger("u_sex");
        userService.editSex(u_id, u_sex);
        String content = "{'code':'1', 'msg':'性别修改成功'}";
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 修改密码
    @RequestMapping("/editPwd")
    public @ResponseBody String editPwd(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String u_email = jsonObject.getString("u_email");
        String u_newpwd = jsonObject.getString("u_pwd");
        String u_oldpwd = jsonObject.getString("u_oldpwd");
        boolean success = userService.editPwd(u_email, u_newpwd, u_oldpwd);
        String content = "";
        if (success) {
            content = "{'code':'1', 'msg':'修改密码成功'}";
        } else {
            content = "{'code':'0', 'msg':'原密码错误'}";
        }
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

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

    // 重置密码
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
    public @ResponseBody String regist(@RequestBody String json) throws Exception {
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

        // 注册环信
        talkDataService.userSave(user.getU_id(), user.getU_id(), user.getU_nick());

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
