package com.custchina;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 2024/9/24
 * 栋dong
 */
@RestController
public class
eventController {
    @Value("${qqBot.apiUrl}")
    private String qqBotApiUrl;
    private List<Long> adminList = new ArrayList<>();

    public boolean checkAdmin(Long adminId) {
        for (Long admin : adminList) {
            if (adminId.equals(admin)) {
                return true;
            }
        }
        return false;
    }
    public void getAdminList(String groupId) {
        String body = HttpRequest.get(qqBotApiUrl + BotApi.getGroupMemberList+"?group_id="+groupId).execute().body();
        JSONObject jsonObject = new JSONObject(body);
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            JSONObject number = data.getJSONObject(i);
            String role = number.getStr("role");
            if (role.equals("admin") || role.equals("owner")) {
                if(checkAdmin(number.getLong("user_id"))){

                }else {
                    adminList.add(number.getLong("user_id"));
                }
            }
        }
    }
    public JSONArray getGroupNumber(String groupId){
        String body = HttpRequest.get(qqBotApiUrl + BotApi.getGroupMemberList+"?group_id="+groupId).execute().body();
        JSONObject jsonObject = new JSONObject(body);
        JSONArray data = jsonObject.getJSONArray("data");
        return data;
    }
    @PostMapping()
    public void post(@RequestBody LinkedHashMap<String, Object> event) {

        try {
            String raw_message = (String) event.get("raw_message");
            if (regexUtil.checkKick(raw_message)) {
                long qq_sender = Long.parseLong(event.get("user_id").toString());
                getAdminList(event.get("group_id").toString());
                if (!checkAdmin(qq_sender)) {
                    HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                            .form("group_id", event.get("group_id"))
                            .form("message", "你没权限别乱玩")
                            .execute();
                    return;
                }
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
                HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                        .form("group_id", event.get("group_id"))
                        .form("message", "踢人成功，正在清理过往消息")
                        .execute();
                //获取过往消息
                JSONObject msghis = new JSONObject();
                msghis.put("group_id", event.get("group_id"));
                String msgHistory = HttpRequest.post(qqBotApiUrl + BotApi.getGroupMsgHistory).body(msghis.toString()).execute().body();
                int index = 0;
                while (true) {
                    JSONObject temp = new JSONObject(msgHistory);
                    JSONArray msgJson = temp.getJSONObject("data").getJSONArray("messages");
                    if (msgJson.isEmpty()) {
                        break;
                    }
                    for (int i = 0; i < msgJson.size(); i++) {
                        JSONObject msg = msgJson.getJSONObject(i);
                        Long userId = msg.getJSONObject("sender").getLong("user_id");
                        if (userId == qq) {
                            index++;
                            HttpRequest.post(qqBotApiUrl + BotApi.deleteMsg + "?message_id=" + msg.getLong("message_id")).execute();
                        }
                    }
                    msghis.set("message_seq", msgJson.getJSONObject(0).getLong("message_id"));
                    msgHistory = HttpRequest.post(qqBotApiUrl + BotApi.getGroupMsgHistory).body(msghis.toString()).execute().body();
                }
                HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                        .form("group_id", event.get("group_id"))
                        .form("message", "共清理" + index + "条")
                        .execute();
            }else if(ReUtil.isMatch("^\\[CQ:at,qq=1920704410,name=.*?\\] 检查群昵称$",raw_message)){
                long qq_sender = Long.parseLong(event.get("user_id").toString());
                getAdminList(event.get("group_id").toString());
                if (!checkAdmin(qq_sender)) {
                    HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                            .form("group_id", event.get("group_id"))
                            .form("message", "你没权限别乱玩")
                            .execute();
                    return;
                }
                HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                        .form("group_id", event.get("group_id"))
                        .form("message", "正在检查，请稍候" )
                        .execute();
                int ok = 0;
                int error = 0;
                JSONArray groupNumber = getGroupNumber(event.get("group_id").toString()+"&&no_cache=true");
                for (int i = 0; i < groupNumber.size(); i++) {
                    String name = groupNumber.getJSONObject(i).getStr("nickname");
                    String card = groupNumber.getJSONObject(i).getStr("card");
                    long qqid = groupNumber.getJSONObject(i).getLong("user_id");
                    if(checkAdmin(qqid)){
                        continue;

                    }
                   if(checkCard(name)||checkCard(card)){
                       ok++;
                   }else {
                       error++;
                       HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                               .form("group_id", event.get("group_id"))
                               .form("message", "[CQ:at,qq="+qqid+",name="+name+"] ,请立刻修改群昵称" )
                               .execute();
                   }
                }
                HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                        .form("group_id", event.get("group_id"))
                        .form("message", "群成员中共有"+error+"个不符合规范的，"+ok+"个正确的" )
                        .execute();
            }
            else if(ReUtil.isMatch("^\\[CQ:at,qq=1920704410,name=.*?\\] 踢出未改名成员$",raw_message)){
                long qq_sender = Long.parseLong(event.get("user_id").toString());
                getAdminList(event.get("group_id").toString());
                if (!checkAdmin(qq_sender)) {
                    HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                            .form("group_id", event.get("group_id"))
                            .form("message", "你没权限别乱玩")
                            .execute();
                    return;
                }
                HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                        .form("group_id", event.get("group_id"))
                        .form("message", "正在移除，请稍候" )
                        .execute();
                int error = 0;
                JSONArray groupNumber = getGroupNumber(event.get("group_id").toString()+"&&no_cache=true");
                for (int i = 0; i < groupNumber.size(); i++) {
                    String name = groupNumber.getJSONObject(i).getStr("nickname");
                    String card = groupNumber.getJSONObject(i).getStr("card");
                    long qqid = groupNumber.getJSONObject(i).getLong("user_id");
                    if(checkAdmin(qqid)){
                        continue;
                    }
                    if(checkCard(name)||checkCard(card)){

                    }else {
                        error++;
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("group_id", event.get("group_id"));
                        jsonObject.set("user_id", qqid);
                        jsonObject.set("reject_add_request", false);
                        //踢人
                        HttpRequest.post(qqBotApiUrl + BotApi.setGroupKick)
                                .body(jsonObject.toString())
                                .execute();
                    }
                }
                HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
                        .form("group_id", event.get("group_id"))
                        .form("message", "群成员中共有"+error+"个不符合规范的，已移除" )
                        .execute();
            }
        } catch (Exception e) {

        }
        System.out.println(event.toString());
    }
    public boolean checkCard(String name){
        return ReUtil.isMatch("\\d{2}\\d{2}\\d{3}[A-Z]?\\d{2}[\\u4e00-\\u9fa5]{2,}",name) ||
                ReUtil.isMatch("\\d{2}\\d{2}\\d{3}[A-Z]?\\d{2}-[\\u4e00-\\u9fa5]{2,}",name)||
                ReUtil.isMatch("\\d{2}\\d{2}\\d{3}[A-Z]?\\d{2} [\\u4e00-\\u9fa5]{2,}",name);
    }
    @Scheduled(cron = "0 0/30 * * * ?")
    public void sendMessage() {
//        HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
//                .form("group_id", "717965543")
//                .form("message", "请在24小时内修改为学号+姓名格式（例如220911133张三），请严格按照格式，否则将会直接移除群聊")
//                .execute();
//        HttpRequest.post(qqBotApiUrl + BotApi.sendGroupMsg)
//                .form("group_id", "296741406")
//                .form("message", "请在24小时内修改为学号+姓名格式（例如220911133张三），请严格按照格式，否则将会直接移除群聊")
//                .execute();
    }

}
