package com.inti3e.model.webcam;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovementManager {

	private Process recordingProcess;
	private boolean recording;
	
	public MovementManager() {
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
	
	public void stopRecording() {
		if (recording) {
			recordingProcess.destroy();
			recording = false;
		}
	}
}
