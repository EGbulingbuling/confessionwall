/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/3/24 18:33:53                           */
/*==============================================================*/


drop table if exists answer;

drop table if exists follow;

drop table if exists message;

drop table if exists notice;

drop table if exists post;

drop table if exists treasure;

drop table if exists user;

drop table if exists visitor;

drop table if exists sign_in;

drop table if exists activate;

drop table if exists post_like;

/*==============================================================*/
/* Table: answer                                                */
/*==============================================================*/
create table answer
(
   answer_id            int not null auto_increment comment '回复id',
   respondent_id     	int comment '被回复者id',
   post_id              int not null comment '帖子id',
   user_id              int not null comment '用户id',
   answer_content       text not null comment '回复内容',
   answer_liked_count   int not null default 0 comment '回复被赞次数',
   answer_create_time   timestamp not null default CURRENT_TIMESTAMP comment '回复创建时间',
   primary key (answer_id)
);

alter table answer comment '回复表';

/*==============================================================*/
/* Table: follow                                                */
/*==============================================================*/
create table follow
(
   follow_id            int not null auto_increment comment '关注id',
   follower_id          int not null comment '关注者id',
   followed_id          int not null comment '被关注者id',
   followed_time        timestamp not null default CURRENT_TIMESTAMP comment '关注时间',
   primary key (follow_id)
);

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
   message_id           int not null auto_increment comment '消息id',
   sender_id            int comment '发送者id',
   receiver_id          int not null comment '接受者id',
   message_content      varchar(1000) not null comment '消息内容',
   message_state        tinyint not null default 1 comment '消息状态',
   message_create_time  timestamp not null default CURRENT_TIMESTAMP comment '消息发送时间',
   primary key (message_id)
);

alter table message comment '消息，多对多';

/*==============================================================*/
/* Table: notice                                                */
/*==============================================================*/
create table notice
(
   notice_id            int not null auto_increment comment '公告id',
   title                varchar(30) not null comment '公告标题',
   notice_content       text not null comment '公告内容',
   notice_visited_count int not null default 0 comment '公告访问数',
   notice_create_time   timestamp not null default CURRENT_TIMESTAMP comment '公告创建时间',
   primary key (notice_id)
);

/*==============================================================*/
/* Table: post                                                  */
/*==============================================================*/
create table post
(
   post_id              int not null auto_increment comment '帖子id',
   user_id              int not null comment '用户id',
   post_type            tinyint not null comment '帖子类型',
   post_abstract        varchar(50) COMMENT '帖子摘要',
   post_content         text not null comment '帖子内容',
   post_visited_count   int not null default 0 comment '帖子访问数',
   answer_count         smallint not null default 0 comment '回复数',
   post_liked_count     int not null default 0 comment '帖子被赞次数',
   post_state           tinyint not null default 1 comment '帖子状态',
   post_create_time     timestamp not null default CURRENT_TIMESTAMP comment '帖子创建时间',
   primary key (post_id)
);

/*==============================================================*/
/* Table: treasure                                              */
/*==============================================================*/
create table treasure
(
   treasure_id          int not null auto_increment comment '收藏id',
   user_id              int not null comment '用户id',
   post_id              int not null comment '帖子id',
   treasure_time        timestamp not null default CURRENT_TIMESTAMP comment '收藏时间',
   primary key (treasure_id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_id              int not null auto_increment comment '用户id',
   account              varchar(50) not null comment '账号',
   password             varchar(32) not null comment '密码',
   salt                 varchar(36) not null comment '盐',
   nickname             varchar(20) not null comment '昵称',
   autograph            varchar(140) comment '签名',
   sex                  tinyint not null comment '性别',
   head                 varchar(500) not null comment '头像',
   experience           smallint not null default 0 comment '经验',
   signed_count         smallint not null default 0 comment '连续签到次数',
   phone                char(11) comment '手机号',
   user_state           tinyint not null default 1 comment '用户状态',
   blocked_count        tinyint not null default 0 comment '被封号次数',
   user_created_time    timestamp not null default CURRENT_TIMESTAMP comment '用户创建时间',
   last_login_time      timestamp not null default CURRENT_TIMESTAMP comment '上一次登录时间',
   primary key (user_id),
   key AK_account (account),
   unique key AK_nickname (nickname),
   unique key AK_phone (phone)
);

/*==============================================================*/
/* Table: visitor                                               */
/*==============================================================*/
create table visitor
(
   visit_id             int not null auto_increment comment '访问id',
   visitor_id           int not null comment '访问者id',
   visited_id           int not null comment '被访者id',
   visited_time         timestamp not null default CURRENT_TIMESTAMP comment '访问时间',
   primary key (visit_id)
);

/*==============================================================*/
/* Table: sign_in                                               */
/*==============================================================*/
create table sign_in
(
   sign_id              int not null auto_increment comment '签到id',
   user_id              int not null comment '签到者id',
   sign_time            timestamp not null default CURRENT_TIMESTAMP comment '签到时间',
   primary key (sign_id)
);

/*==============================================================*/
/* Table: activate                                               */
/*==============================================================*/
create table activate
(
   activate_id          int not null auto_increment comment '激活id',
   user_id              int not null comment '激活者id',
   activation_code      varchar(36) not null comment '激活码',
   activate_state       tinyint not null default 1 comment '激活状态',             
   activate_time        timestamp not null default CURRENT_TIMESTAMP comment '激活建立时间',
   primary key (activate_id)
);

/*==============================================================*/
/* Table: post_like                                               */
/*==============================================================*/
CREATE TABLE post_like  (
	like_id 			int(11) NOT NULL AUTO_INCREMENT COMMENT '赞id',
	user_id 			int(11) NOT NULL COMMENT '点赞者id',
	post_id 			int(11) NOT NULL COMMENT '帖子id',
	like_time 			timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '点赞时间',
	PRIMARY KEY (`like_id`)
);

alter table visitor comment '访客关系多对多';

alter table answer add constraint FK_answer_user_r foreign key (respondent_id)
      references user (user_id) on delete restrict on update restrict;

alter table answer add constraint FK_post_answer_r foreign key (post_id)
      references post (post_id) on delete restrict on update restrict;

alter table answer add constraint FK_user_answer_r foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table follow add constraint FK_user_user_follow foreign key (follower_id)
      references user (user_id) on delete restrict on update restrict;

alter table follow add constraint FK_user_user_follow2 foreign key (followed_id)
      references user (user_id) on delete restrict on update restrict;

alter table message add constraint FK_user_user_message foreign key (sender_id)
      references user (user_id) on delete restrict on update restrict;

alter table message add constraint FK_user_user_message2 foreign key (receiver_id)
      references user (user_id) on delete restrict on update restrict;

alter table post add constraint FK_user_post_posting foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table treasure add constraint FK_treasure foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table treasure add constraint FK_treasure2 foreign key (post_id)
      references post (post_id) on delete restrict on update restrict;

alter table visitor add constraint FK_user_user_visitor foreign key (visitor_id)
      references user (user_id) on delete restrict on update restrict;

alter table visitor add constraint FK_user_user_visitor2 foreign key (visited_id)
      references user (user_id) on delete restrict on update restrict;
	  
alter table sign_in add constraint FK_user_sign_r foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table activate add constraint FK_user_activate_r foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;
	  
alter table post_like add constraint FK_post_like_r foreign key (post_id)
      references post (post_id) on delete restrict on update restrict;
	  
alter table post_like add constraint FK_user_like_r foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;
