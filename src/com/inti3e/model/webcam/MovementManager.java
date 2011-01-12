/*
 * Project: project.webfarm
 * Created By: INTI3e
 * Created At: 12-jan-2011 11:37:00
 */
package com.inti3e.model.webcam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class MovementManager.
 */
public class MovementManager {

	/** The recording process. */
	private Process recordingProcess;
	
	/** The recording. */
	private boolean recording;
	
	/**
	 * Instantiates a new movement manager.
	 */
	public MovementManager() {
		recordingProcess = null;
		recording = false;
	}
	
	/**
	 * Start recording.
	 */
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
	
	/**
	 * Stop recording.
	 */
	public void stopRecording() {
		if (recording) {
			recordingProcess.destroy();
			recording = false;
		}
	}
}
