#include <dht.h>
#include <Wire.h>

#define SLAVE_ADDRESS 0x04

#define LIGHTSENSORPIN A0
#define MOISTURESENSORPIN A1
#define RELATIVEHUMIDITY_PIN 7
dht DHT; // To read the Relative Moisture Sensor (Display Humidity/Temperature)

// Last captured values
float readingLight = 0;
float moisture = 0;
int chk = 0;
int temp = 0;
int humidity = 0;

void setup() {

  Serial.begin(9600);

  pinMode(LIGHTSENSORPIN,  INPUT);
  pinMode(MOISTURESENSORPIN,  INPUT);
  pinMode(RELATIVEHUMIDITY_PIN,  INPUT);

  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(publishData);
}

void loop() {
  // Gather data
  readingLight = analogRead(LIGHTSENSORPIN);
  moisture = analogRead(MOISTURESENSORPIN);
  chk = DHT.read11(RELATIVEHUMIDITY_PIN);
  temp = DHT.temperature;
  humidity = DHT.humidity;
  // Refresh data capturing timeout
  delay(2000);
}

void publishData() {
  char bufferChar[32];
  String message = "m:" + String(int(moisture)) + ",l:" + String(int(readingLight)) + ",h:" + String(humidity) + ",t:" + String(temp) + ";";
  message.toCharArray(bufferChar, 32);
  Wire.write(bufferChar);
}

void receiveData(int byteCount) {
  // TODO actionable
}