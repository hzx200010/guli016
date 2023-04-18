package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    //发送短信的方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("cn-hangzhou", "LTAI5tNC2rpbmAqie6j16YeQ", "mHAsfmcrW8Xnni6HAy0LAZNEyhMi3l");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
      //  CommonRequest request = new CommonRequest();
        SendSmsRequest request = new SendSmsRequest();
        //request.setProtocol(ProtocolType.HTTPS);
      /*  request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");*/

        //设置发送相关的参数
     /*   request.putQueryParameter("PhoneNumbers",phone); //手机号
        request.putQueryParameter("SignName","我的谷粒在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_180051135"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换json数据传递*/

        request.setSignName("阿里云短信测试");
        request.setTemplateCode("SMS_154950909");
        request.setPhoneNumbers("13006050458");
        request.setTemplateParam("{\"code\":\"1234\"}");

        try {
            //最终发送
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }





}
