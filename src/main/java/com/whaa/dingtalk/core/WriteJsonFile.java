package com.whaa.dingtalk.core;

import com.alibaba.fastjson.JSON;
import com.whaa.dingtalk.bean.RegisterDingTalk;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wangzelong 2019/3/28 15:22
 */
@Component
public class WriteJsonFile {

    public String writeToken(String fromServer) {
        TokenProcessor tokenProcessor = TokenProcessor.getInstance();
        String token = tokenProcessor.generateTokeCode();
        RegisterDingTalk dingTalk = new RegisterDingTalk();
        dingTalk.setFromServer(fromServer);
        dingTalk.setAuthToken(token);
        List<RegisterDingTalk> registerDingTalks = readFile();
        registerDingTalks.add(dingTalk);
        if (writeFile(registerDingTalks)) {
            return token;
        }
        return "fail";
    }

    public boolean writeFile(List<RegisterDingTalk> list) {
        try {
            String json = JSON.toJSONString(list);
            String path = System.getProperty("user.dir") + "\\token.json";
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<RegisterDingTalk> readFile() {
        String path = System.getProperty("user.dir") + "\\token.json";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String jsonStr;
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            if (!StringUtils.isEmpty(jsonStr)) {
                List<RegisterDingTalk> res = null;
                try {
                    res = JSON.parseArray(jsonStr, RegisterDingTalk.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
