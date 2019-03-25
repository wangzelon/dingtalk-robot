package com.whaa.dingtalk.core;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author whaa
 * created by wangzelong 2019/3/22 15:20
 */
@Component
public class SendMsgDingtalk {

    public final static String TYPE_TEXT = "text";

    public final static String TYPE_LINK = "link";

    public final static String TYPE_MARKDOWN = "markdown";

    public final static String SERVER_URL = "https://oapi.dingtalk.com/robot/send?access_token=a32cf467eeff197b2cba3d4ed93f3372e82bb6917f0e2e7af1124721b4b693e7";

    public void send(String title, String text, String messageUrl, String atUsers, String type, String serverUrl) throws ApiException {
        DingTalkClient client;
        if (StringUtils.isEmpty(serverUrl)) {
            client = new DefaultDingTalkClient(SERVER_URL);
        } else {
            client = new DefaultDingTalkClient(serverUrl);
        }
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        if (TYPE_TEXT.equals(type)) {
            setTypeText(request, text);
        }
        if (TYPE_LINK.equals(type)) {
            setTypeLink(request, atUsers, messageUrl, title, text);
        }
        if (TYPE_MARKDOWN.equals(type)) {
            setTypeMarkdown(request, title, text, atUsers);
        }
        client.execute(request);
    }

    private void setTypeText(OapiRobotSendRequest request, String content) {
        request.setMsgtype(TYPE_TEXT);
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
    }

    private void setTypeLink(OapiRobotSendRequest request, String atUsers, String messageUrl, String title, String content) {
        changeRequest(request, atUsers);
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(messageUrl);
        link.setPicUrl("http://admin.myopshop.com/static/ico/OPSHOP-ICON.ico");
        link.setTitle(title);
        link.setText(content);
        request.setLink(link);
    }

    private void setTypeMarkdown(OapiRobotSendRequest request, String title, String text, String atUsers) {
        changeRequest(request, atUsers);
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(text);
        request.setMarkdown(markdown);
    }

    private void changeRequest(OapiRobotSendRequest request, String atUsers) {
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        if (StringUtils.isEmpty(atUsers)) {
            at.setAtMobiles(Arrays.asList(atUsers.split(",")));
        } else {
            at.setIsAtAll("true");
        }
        request.setAt(at);
        request.setMsgtype(TYPE_MARKDOWN);
    }
}
