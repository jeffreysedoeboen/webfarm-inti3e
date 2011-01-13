package com.inti3e.model.webcam;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class RecordManager.
 */
public class RecordManager {
	
	/** The RecordManager uniqueInstance variable */
	private static RecordManager uniqueInstance = null;
	
	/** The recording process. */
	private Process recordingProcess;
	
	/** The recording. */
	private boolean recording;
	
	/** The startByUser */
	private boolean startByUser;
	
	private RecordManager() {
		recordingProcess = null;
		recording = false;
		startByUser = false;
	}
	
	public static synchronized RecordManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new RecordManager();
		}
		return uniqueInstance;
	}
	
	/**
	 * Start recording.
	 */
	private void startRecording() {
		if (!recording) {
			recording = true;

			DateFormat dfmt = new SimpleDateFormat( "yyyy-MM-dd-[HH-mm-ss]" ); 			
			String filename = dfmt.format(new Date()) + ".ogg";

			try {
				recordingProcess = Runtime.getRuntime().exec("cvlc -vvv http://localhost:8088/stream.ogg --demux=dump --demuxdump-file=webapps/project.webfarm/videos/" + filename + " &");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("recording");
		}
	}
	
	/**
	 * Stop recording.
	 */
	private void stopRecording() {
		if (recording) {
			recordingProcess.destroy();
			recording = false;
			System.out.println("stop recording");
		}
	}
	
	public void startRecordingByUser() {
		synchronized(this) {
			startByUser = true;
			startRecording();
		}
	}
	
	public void startRecordingAtMovement() {
		synchronized(this) {
			System.out.println("start recording by movement");
			startRecording();
		}
	}
	
	public void stopRecordingByUser() {
		synchronized(this) {
			startByUser = false;
			stopRecording();
		}
	}
	
	public void stopRecordingAtMovement() {
		synchronized(this) {
			if (!startByUser) {
				System.out.println("stop recording by movement");
				stopRecording();
			}
		}
	}
	
	/**
	 * Get recording.
	 */
	public boolean getRecording() {
		synchronized(this) {
			return startByUser && recording;
		}
	}
}
