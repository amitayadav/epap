package com.auction.model.views;

import org.apache.commons.net.util.Base64;

import com.auction.commons.util.DateHelper;
import com.auction.model.entity.MessageCenter;

public class MessageCenterView {

	private Integer fromId;
	private Integer toId;
	private String message;
	private String messageTimestamp;
	private String attachment;
	private String attachmentType;

	public MessageCenterView() {

	}

	public MessageCenterView(MessageCenter entity) {
		try {
			if (null != entity && null != entity.getMessageId()) {
				this.fromId = entity.getAccountProfileByFromAccountId().getAccountId();
				this.toId = entity.getAccountProfileByFromAccountId().getAccountId();
				this.message = entity.getMessage();
				this.messageTimestamp = DateHelper.dateToStringAmPm(entity.getMessageTimestamp());
				this.attachmentType = entity.getAttachmentType();
				byte[] imgBytes = null;
				try {
					if (entity.getAttachment() != null) {
						imgBytes = entity.getAttachment().getBytes(1, (int) entity.getAttachment().length());
						this.attachment = new String(Base64.encodeBase64(imgBytes), "UTF-8");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageTimestamp() {
		return messageTimestamp;
	}

	public void setMessageTimestamp(String messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

}