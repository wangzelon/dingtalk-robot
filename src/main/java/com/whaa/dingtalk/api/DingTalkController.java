package com.whaa.dingtalk.api;

import com.taobao.api.ApiException;
import com.whaa.dingtalk.core.SendMsgDingtalk;
import com.whaa.dingtalk.core.WriteJsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    @Autowired
    private WriteJsonFile writeJsonFile;


    @PostMapping("/sendErrorNote")
    public String sendErrorNote(String title, String text, String atUsers, String serverUrl) {
        try {
            if (StringUtils.isEmpty(title) || StringUtils.isEmpty(text) || StringUtils.isEmpty(serverUrl)) {
                return "fail";
            }
            msgDingtalk.send(title, text, null, atUsers, "markdown", serverUrl);
        } catch (ApiException e) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/sendWarnNote")
    public String sendWarnNote(String title, String text, String messageUrl, String atUsers, String serverUrl) {
        try {
            if (StringUtils.isEmpty(title) || StringUtils.isEmpty(text) || StringUtils.isEmpty(serverUrl)) {
                return "fail";
            }
            msgDingtalk.send(title, text, messageUrl, atUsers, "link", serverUrl);
        } catch (ApiException e) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/sendNote")
    public String sendNote(String text, String atUsers, String serverUrl) {
        try {
            if (StringUtils.isEmpty(text) || StringUtils.isEmpty(serverUrl)) {
                return "fail";
            }
            msgDingtalk.send(null, text, null, atUsers, "text", serverUrl);
        } catch (ApiException e) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/registerServer")
    public String registerServer(String fromServer) {
        return writeJsonFile.writeToken(fromServer);
    }

}
