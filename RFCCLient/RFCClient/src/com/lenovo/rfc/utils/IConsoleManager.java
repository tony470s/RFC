package com.lenovo.rfc.utils;

import java.util.ArrayList;
import java.util.List;

public class IConsoleManager {
	public interface OnDataUpdateListener {
		public void onDataUpdate();
	}

	private List<OnDataUpdateListener> dataUpdateListeners = new ArrayList<IConsoleManager.OnDataUpdateListener>();

	public void addOnDataUpdateListener(OnDataUpdateListener listener) {
		this.dataUpdateListeners.add(listener);
	}

	public void notifyDataUpdate() {
		for (OnDataUpdateListener listener : dataUpdateListeners) {
			listener.onDataUpdate();
		}
	}
}
