package com.devices;

public class TestDevices {

	public static void main(String[] args) {
		Charger usb1 = new Charger();
		usb1.type = "micro-usb";
		
		Cat steve = new Cat();
		steve.name = "Steve";
		steve.inpudent = true;
		
		Smartphone nexus = new Smartphone("Nexus 5X");
		nexus.os = "Android 5.2";
		nexus.bluetooth = true;
		nexus.batteryCapacity = 2700;
		nexus.internalMemory = 16.0;
		nexus.chargerType = "micro-usb";
		nexus.chargeDevice(usb1, 3600);

		Smartphone edge = new Smartphone();
		edge.name = "Galaxy S6 edge";
		edge.bluetooth = true;
		edge.batteryCapacity = 2600;
		edge.internalMemory = 32.0;
		edge.chargerType = "micro-usb";
		edge.chargeDevice(usb1, 3600);

		Headset voyager520 = new Headset();
		voyager520.name = "Voyager 520";
		

		System.out.println(nexus.getDescription());
		nexus.call("88125553311", 3000);
		System.out.println(nexus.getDescription());
		nexus.chargeDevice(usb1, 3600);
		System.out.println(nexus.getDescription());
		steve.chewCharger(usb1);
		nexus.chargeDevice(usb1, 3600);
		nexus.downloadData(1.5);
		System.out.println(nexus.getDescription());
		System.out.println(edge.getDescription());
		edge.acceptData(nexus, 1.1);
		System.out.println(edge.getDescription());
		System.out.println(voyager520.checkPairing());
		
		voyager520.pair(nexus);
		System.out.println("1. " + nexus.name);
		nexus = null;
//		System.out.println("2. " + nexus.name);
		System.out.println("3. " + voyager520.pairedPhone.name);
//		System.out.println(System.identityHashCode(nexus));
	}

}
