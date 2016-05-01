package com.kebaptycoon.model.entities;

public class Advertisement {

    private final static float BASE_MULTIPLIER = 0.001f;
    private final static float FOCUS_MULTIPLIER = 0.01f;

	public static enum Quality {
		Low(1),
		Medium(2),
		High(3);

		private float value;
		
		private Quality(float value) {
			this.value = value;
		}
		
		public float getValue() {
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

		private float value;
		
		private Platform(float value) {
			this.value = value;
		}
		
		public float getValue() {
			return value;
		}
	}
	
	Quality quality;
	Duration duration;
	Platform platform;
	CustomerType focus;
    int elapsedDuration;
	
	public Advertisement(Quality quality, Duration duration, Platform platform, CustomerType focus) {

		this.quality = quality;
		this.duration = duration;
		this.platform = platform;
		this.focus = focus;
		this.elapsedDuration = 0;
	}

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setFocus(CustomerType focus) {
        this.focus = focus;
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

	public CustomerType getFocus() {
		return focus;
	}

    public int getElapsedDuration() {
        return elapsedDuration;
    }

	public boolean isExpired() {
		return elapsedDuration >= duration.getValue();
	}

    public float getAbsoluteEffect(CustomerType type) {
        float mul = (type == this.focus) ? FOCUS_MULTIPLIER : BASE_MULTIPLIER;

        return quality.getValue() * platform.getValue() * mul;
    }

    public void incrementElapsedDuration() {
        elapsedDuration++;
    }
}
