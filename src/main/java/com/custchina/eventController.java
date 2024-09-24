package com.custchina;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * 2024/9/24
 * 栋dong
 */
@RestController
public class eventController {
    @Value("${qqBot.apiUrl}")
    private String qqBotApiUrl;

    @PostMapping()
    public String post(@RequestBody LinkedHashMap<String, Object> event) {
        try {
            String raw_message = (String) event.get("raw_message");
            if (regexUtil.checkKick(raw_message)) {
                //获取要踢的人的qq号
                long qq = Long.parseLong(ReUtil.get(regexUtil.getQQRegex, raw_message, 1));
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("group_id", event.get("group_id"));
                jsonObject.set("user_id", qq);
                jsonObject.set("reject_add_request", false);
                //踢人
                HttpRequest.post(qqBotApiUrl + BotApi.setGroupKick)
                        .body(jsonObject.toString())
                        .execute();
//                String listUrl = qqBotApiUrl + BotApi.getGroupMemberList + "?group_id=" +event.get("group_id")+"&no_cache=true";
//                String numberList = HttpRequest.post(listUrl)
//                        .execute().body();
//                JSONObject number = new JSONObject(numberList);
//                JSONArray data = number.getJSONArray("data");
//                for (int i = 0; i < data.size(); i++) {
//                    String userId = (String) data.getJSONObject(i).get("user_id");
//                }
                HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                        .form("group_id", event.get("group_id"))
                        .form("message", "踢人成功，正在清理过往消息")
                        .execute();
                HttpRequest.post(qqBotApiUrl + BotApi.getGroupMsgHistory).execute();//TODO

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(event.toString());

        return "success";
    }
}
