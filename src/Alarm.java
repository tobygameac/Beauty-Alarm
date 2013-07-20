public class Alarm {
	private boolean on = false;
	private long hour;
	private long minute;

	void setOpen(boolean on) {
		this.on = on;
	}

	void setHour(long hour) {
		this.hour = hour;
	}

	void setMinute(long minute) {
		this.minute = minute;
	}
	
	boolean getOpen(){
		return this.on;
	}

	long getHour() {
		return this.hour;
	}

	long getMinute() {
		return this.minute;
	}

}
