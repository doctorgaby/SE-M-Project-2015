package group8.com.application;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.Uint8;
import android.widget.TextView;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.distraction.LightMode;
import com.swedspot.vil.distraction.StealthMode;
import com.swedspot.vil.policy.AutomotiveCertificate;

public class DriverDistraction extends Activity implements Runnable{
	Object controller; // controller

	public DriverDistraction(Object controller){
		this.controller = controller;
	}

	public void run(){ // new thread
		new AutomotiveListener() { // Listener that observes the Signals @Override
	
		@Override
		public void receive(final AutomotiveSignal automotiveSignal) {}

		@Override
		public void timeout(int i) {
			System.out.println("Timed out");
		}

		@Override
		public void notAllowed(int i) {
			System.out.println("Operation not allowed");
		}

		new DriverDistractionListener() { // Observe driver distraction level @Override
			public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
				this.controller.driverDistractionLevelChanged(driverDistractionLevel);
			}
		}
	}
	}
}