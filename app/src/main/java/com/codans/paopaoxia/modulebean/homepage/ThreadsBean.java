package com.codans.paopaoxia.modulebean.homepage;

import java.util.List;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/12 17:24
 * 修改人：胡继春
 * 修改时间：2016/3/12 17:24
 * 修改备注；
 */
public class ThreadsBean {
    public List<mThreads> Threads;
    public String UnreadMessageCount;
    public String TodoTaskCount;
    public String SpecTask;
    public Object Notify;
    public String Success;
    public String ErrorMessage;
    public String ErrorNumber;

    public class mThreads {
     //概要信息
       public String Summary;
     //经度
        public float Longitude;
     //纬度
        public float Latitude;
     //踩踏次数
        public int DislikeCount;
     //声明周期
        public int LeftHours;
     //可见度
        public int Visibility;
     //悬赏额
        public float Reward;
     //悬赏类型
        public int RewardType;
     //悬赏状态
        public int RewardStatus;
     //边框颜色
        public int BorderColor;
     //图片数量
        public int PhotoCount;
     //视频数量
        public int VideoCount;
     //
        public boolean IsSelf;
     //是否点过赞
        public boolean Liked;

        public boolean Disliked;
     //是否是远程泡泡
        public boolean IsRemote;
     //是否参与
        public boolean IsMember;
     //是否是移动的泡泡
        public boolean IsMove;
     //任务Id
        public String TaskId;
     //移动状态
        public int MoveFlag;
     //是否推荐
        public boolean IsRecommended;
     //是否是收藏的泡泡
        public boolean IsFavorite;
     //这个泡泡的未读消息树
        public int UnReadPosts;
     //类型
        public int Type;
     //移动的泡泡经过的地方
        public List<mSpots> Spots;
     //移动历史
        public Object MoveHistory;
     //帖子
        public List<mPosts> Posts;
     //微信阅读者
        public List<mWechatReaders> WechatReaders;
     //微信评论者
        public List<mWechatReviews> WechatReviews;
     //泡泡的任务连接
        public Object TaskLink;
     //泡泡的过期时间
        public String ExpireTime;
     //作者昵称
        public String NickName;
     //连接网址
        public String Url;
     //联系电话
        public String Phone;
     //限制类型
        public int Forbid;
     //话题编号
        public String ThreadId;
     //会员编号
        public String MemberId;
     //会员姓名
        public String MemberName;
     //会员头像地址
        public String MemberHeadImageUrl;
     //主题
        public String Subject;
     //查看次数
        public int Hits;
     //参与者数量
        public int MemberCount;
     //帖子数量
        public int PostCount;
     //点赞数量
        public int LikeCount;
     //创建时间
        public String Checkintime;
     //踩踏次数
        public float Distance;
     //图标地址
        public String IconUrl;
    }

    public class mSpots{

    }

   public class mPosts{

   }

 public class mWechatReaders{}

 public class mWechatReviews{}
}
