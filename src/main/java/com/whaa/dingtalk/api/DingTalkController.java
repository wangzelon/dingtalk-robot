package com.whaa.dingtalk.api;

import com.taobao.api.ApiException;
import com.whaa.dingtalk.core.SendMsgDingtalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by wangzelong 2019/3/22 16:20
 */
@RestController
@RequestMapping("/dingTalk")
public class DingTalkController {
    @Autowired
    private SendMsgDingtalk msgDingtalk;

    @PostMapping("/sendErrorNote")
    public String sendErrorNote(String type, String title, String text) {
        try {
            msgDingtalk.send(title, text, "", "", type);
        } catch (ApiException e) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/sendWarnNote")
    public String sendWarnNote(String type, String title, String text, String messageUrl) {
        try {
            msgDingtalk.send(title, text, messageUrl, "", type);
        } catch (ApiException e) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/sendNote")
    public String sendNote(String type, String text) {
        try {
            msgDingtalk.send("", text, "", "", type);
        } catch (ApiException e) {
            return "fail";
        }
        return "success";
    }
}
