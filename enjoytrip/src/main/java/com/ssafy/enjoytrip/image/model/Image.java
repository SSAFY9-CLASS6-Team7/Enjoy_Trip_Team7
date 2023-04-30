package com.ssafy.enjoytrip.image.model;

import lombok.Data;

@Data
public class Image {
	private int imageId;
	private int type;
	private int data_id;
	private char thumbnail;
	private String saveFolder;
	private String originalFile;
	private String saveFile;
}
