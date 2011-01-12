package com.inti3e.model.webcam;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.inti3e.database.DBmanager;

public class RecordManager {

	private static RecordManager uniqueInstance=null;
	private Process recordingProcess;
	private boolean recording;
	
	private RecordManager() {
		recordingProcess = null;
		recording = false;
	}
	
	public void startRecording() {
		if (!recording) {
			DateFormat dfmt = new SimpleDateFormat( "yyyyMMdd-hhmmss" ); 			
			String filename = dfmt.format(new Date()) + ".ogg";
			
//			try {
//				recordingProcess = Runtime.getRuntime().exec("cvlc -vvv http://localhost:8088 --demux=dump --demuxdump-file=" + filename);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			recording = true;
		}
	}
	
	public static synchronized RecordManager getInstance() {
		if (uniqueInstance==null) {
			uniqueInstance = new RecordManager();
		}
		return uniqueInstance;
	}
	
	public boolean getRecording() {
		return recording;
	}
	
	public void stopRecording() {
		if (recording) {
			recordingProcess.destroy();
			recording = false;
		}
	}
}
