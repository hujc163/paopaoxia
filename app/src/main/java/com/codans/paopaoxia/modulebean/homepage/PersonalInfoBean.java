package com.codans.paopaoxia.modulebean.homepage;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/16 15:46
 * 修改人：胡继春
 * 修改时间：2016/3/16 15:46
 * 修改备注；
 */
public class PersonalInfoBean {
    public Memberif MemberInfo;
    public boolean Success;
    public String ErrorMessage;
    public int ErrorNumber;

    public class Memberif{
        public String MemberId;
        public String Mobile;
        public String OpenId;
        public String Name;
        public String MemberNo;
        public int Gender;
        public String Checkintime;
        public int Exp;
        public int Hp;
        public int Visibility;
        public int Love;
        public int Mp;
        public int Wealth;
        public String LastActiveTime;
        public int LoginCount;
        public String HeadImage;
        public Object NickName;
        public String WeixinHeadImage;
        public boolean IsVip;
        public int Level;
        public int Rank;
        public int TotalRank;
        public int TodayThreadNum;
        public int TodayPostNum;
        public int TodayFavoriteNum;
        public int TodayScore;
    }
}
