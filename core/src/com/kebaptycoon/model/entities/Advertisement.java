package com.kebaptycoon.model.entities;

import java.util.Date;

import com.kebaptycoon.model.entities.Customer.Type;

public class Advertisement {
	
	public static enum Quality {
		Low(1),
		Medium(2),
		High(3);

		private int value;    
		
		private Quality(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public static enum Duration {
		Day(1),
		Week(7),
		Month(30);

		private int value;    
		
		private Duration(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public static enum Platform {
		Newspaper(1),
		Internet(2),
		Billboard(3),
		Television(5);

		private int value;    
		
		private Platform(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	Quality quality;
	Duration duration;
	Platform platform;
	Customer.Type focus;
	Date startDate;
	
	public Advertisement(Quality quality, Duration duration, Platform platform, Type focus, Date startDate) {
		this.quality = quality;
		this.duration = duration;
		this.platform = platform;
		this.focus = focus;
		this.startDate = startDate;
	}

	public Quality getQuality() {
		return quality;
	}

	public Duration getDuration() {
		return duration;
	}

	public Platform getPlatform() {
		return platform;
	}

	public Customer.Type getFocus() {
		return focus;
	}

	public Date getStartDate() {
		return startDate;
	}
	
}
