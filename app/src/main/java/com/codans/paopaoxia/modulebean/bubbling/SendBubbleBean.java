package com.codans.paopaoxia.modulebean.bubbling;

import java.io.BufferedInputStream;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/17 17:47
 * 修改人：胡继春
 * 修改时间：2016/3/17 17:47
 * 修改备注；
 */
/**
 * "Latitude" : 30.31280396740668,
 "Subject" : "时尚巴莎",                //标题
 "Longitude" : 120.1672042211474,
 "LifeHours" : 24,                     //生命周期
 "TaskId" : "",
 "Password" : "",                      //访问密码
 "Token" : "bb0d5382e307485683fd1de87fb00e399xw8t4yw",
 "Reward" : 0,                          //悬赏金额
 "ThreadId" : "",
 "MoveFlag" : 0,
 "Content" : "【好看的衣服需要好的身材】",//内容
 "Visibility" : 2000,                   //可见度（米）
 IsRemote" : 1
 */
public class SendBubbleBean {
    public newBubble Data;
    public BufferedInputStream FileName;
    public class newBubble{
        public String Latitude;
        public String Subject;
        public String Longitude;
        public String LifeHours;
        public String TaskId;
        public String Password;
        public String Token;
        public String Reward;
        public String ThreadId;
        public String MoveFlag;
        public String Content;
        public String Visibility;
        public String IsRemote;
    }
}
