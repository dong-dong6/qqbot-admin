package com.custchina;

public class BotApi {

    // 获取 bot 账号信息
    public static String getLoginInfo = "get_login_info";

    // 发送消息
    public static String sendMsg = "send_msg";

    // 发送群聊消息
    public static String sendGroupMsg = "send_group_msg";

    // 发送好友消息
    public static String sendPrivateMsg = "send_private_msg";

    // 获取消息详情
    public static String getMsg = "get_msg";

    // 撤回消息
    public static String deleteMsg = "delete_msg";

    // 发送好友赞
    public static String sendLike = "send_like";

    // 获取好友列表
    public static String getFriendList = "get_friend_list";

    // 处理好友请求
    public static String setFriendAddRequest = "set_friend_add_request";

    // 获取群列表
    public static String getGroupList = "get_group_list";

    // 获取群信息
    public static String getGroupInfo = "get_group_info";

    // 获取群成员列表
    public static String getGroupMemberList = "get_group_member_list";

    // 获取群成员信息
    public static String getGroupMemberInfo = "get_group_member_info";

    // 获取群荣誉信息
    public static String getGroupHonorInfo = "get_group_honor_info";

    // 设置群组专属头衔
    public static String setGroupSpecialTitle = "set_group_special_title";

    // 群匿名禁言
    public static String setGroupAnonymousBan = "set_group_anonymous_ban";

    // 开关群匿名
    public static String setGroupAnonymous = "set_group_anonymous";

    // 处理加群请求
    public static String setGroupAddRequest = "set_group_add_request";

    // 退群
    public static String setGroupLeave = "set_group_leave";

    // 群踢人
    public static String setGroupKick = "set_group_kick";

    // 群禁言
    public static String setGroupBan = "set_group_ban";

    // 全群禁言
    public static String setGroupWholeBan = "set_group_whole_ban";

    // 设置管理员
    public static String setGroupAdmin = "set_group_admin";

    // 设置群名片
    public static String setGroupCard = "set_group_card";

    // 设置群名
    public static String setGroupName = "set_group_name";

    // 获取陌生人信息
    public static String getStrangerInfo = "get_stranger_info";

    // 获取版本信息
    public static String getVersionInfo = "get_version_info";

    // 获取状态
    public static String getStatus = "get_status";

    // 检查能否发送图片
    public static String canSendImage = "can_send_image";

    // 检查能否发送语音
    public static String canSendRecord = "can_send_record";

    // 获取图片详情
    public static String getImage = "get_image";

    // 获取语音文件
    public static String getRecord = "get_record";

    // 获取文件详情
    public static String getFile = "get_file";

    // 获取 Cookies
    public static String getCookies = "get_cookies";

    // 获取 CSRF Token
    public static String getCsrfToken = "get_csrf_token";

    // 获取 QQ 相关接口凭证
    public static String getCredentials = "get_credentials";

    // 重启 OneBot 实现
    public static String setRestart = "set_restart";

    // 清理缓存
    public static String cleanCache = "clean_cache";

    // 发送合并转发 (好友)
    public static String sendPrivateForwardMsg = "send_private_forward_msg";

    // 发送合并转发 (群聊)
    public static String sendGroupForwardMsg = "send_group_forward_msg";

    // 获取群消息历史记录
    public static String getGroupMsgHistory = "get_group_msg_history";

    // 获取合并转发内容
    public static String getForwardMsg = "get_forward_msg";

    // 上传群文件
    public static String uploadGroupFile = "upload_group_file";

    // 上传私聊文件
    public static String uploadPrivateFile = "upload_private_file";

    // 下载文件到缓存目录
    public static String downloadFile = "download_file";

    // 设置精华消息
    public static String setEssenceMsg = "set_essence_msg";

    // 移出精华消息
    public static String deleteEssenceMsg = "delete_essence_msg";

    // 删除群文件
    public static String deleteGroupFile = "delete_group_file";

    // 创建群文件文件夹
    public static String createGroupFileFolder = "create_group_file_folder";

    // 删除群文件文件夹
    public static String deleteGroupFolder = "delete_group_folder";

    // 获取群系统消息
    public static String getGroupSystemMsg = "get_group_system_msg";

    // 获取群 @全体成员 剩余次数
    public static String getGroupAtAllRemain = "get_group_at_all_remain";

    // 获取群根目录文件列表
    public static String getGroupRootFiles = "get_group_root_files";

    // 发送群公告
    public static String sendGroupNotice = "_send_group_notice";

    // 标记消息已读
    public static String markMsgAsRead = "mark_msg_as_read";

    // 获取群子目录文件列表
    public static String getGroupFilesByFolder = "get_group_files_by_folder";

    // 设置头像
    public static String setQqAvatar = "set_qq_avatar";

    // 获取已过滤的加群通知
    public static String getGroupIgnoreAddRequest = "get_group_ignore_add_request";

    // 转发单条信息到私聊
    public static String forwardFriendSingleMsg = "forward_friend_single_msg";

    // 转发单条信息到群聊
    public static String forwardGroupSingleMsg = "forward_group_single_msg";

    // 设置消息的表情回应
    public static String setMsgEmojiLike = "set_msg_emoji_like";

    // 获取好友分类列表
    public static String getFriendsWithCategory = "get_friends_with_category";

    // 设置自身在线状态
    public static String setOnlineStatus = "set_online_status";

    // 获取自身点赞列表
    public static String getProfileLike = "get_profile_like";

    // 发送合并转发消息
    public static String sendForwardMsg = "send_forward_msg";

    // 获取收藏表情
    public static String fetchCustomFace = "fetch_custom_face";

    // 获取好友历史消息记录
    public static String getFriendMsgHistory = "get_friend_msg_history";

    // 获取表情回应列表
    public static String fetchEmojiLike = "fetch_emoji_like";
}
