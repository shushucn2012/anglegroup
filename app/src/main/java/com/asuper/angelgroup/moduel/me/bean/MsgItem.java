package com.asuper.angelgroup.moduel.me.bean;

import java.io.Serializable;

public class MsgItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;// 消息id
	private Long activityId;// 游戏id
	private String content;// 消息内容
	private Long receiverId;// 消息接收者id
	private String receiverName;// 消息接收者姓名
	private String receiverPicture;// 消息接收者头像
	private Long senderId;// 消息发送者id
	private String senderName;// 消息发送者名称
	private String senderPicture;// 消息发送者头像
	private String status;// 消息状态，0未读；1已读
	private String time;// 消息时间
	private Integer type;// 消息类型
	private CommentVO commentVO;
	private String title;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPicture() {
		return receiverPicture;
	}

	public void setReceiverPicture(String receiverPicture) {
		this.receiverPicture = receiverPicture;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderPicture() {
		return senderPicture;
	}

	public void setSenderPicture(String senderPicture) {
		this.senderPicture = senderPicture;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public CommentVO getCommentVO() {
		return commentVO;
	}

	public void setCommentVO(CommentVO commentVO) {
		this.commentVO = commentVO;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
