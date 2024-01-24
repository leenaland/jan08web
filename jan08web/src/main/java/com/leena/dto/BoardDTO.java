package com.leena.dto;

import com.leena.util.Util;

//리스트
public class BoardDTO {
	private int no, count, comment; //컬럼명이랑 똑같이 안맞춰도 됨, 어차피 나중에 다시 만들어 주니까
	private String title, content, write, date, mid, ip;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWrite() {
		return write;
	}
	public void setWrite(String write) {
		this.write = write;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	//01.23 ip 추가, 가리기 메서드 추가
	public String getIp() {
		return Util.ipMasking(ip);
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
