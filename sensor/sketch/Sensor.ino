#include <dht.h>

#define LIGHTSENSORPIN A0 
#define MOISTURESENSORPIN A1 
#define RELATIVEHUMIDITY_PIN 7 
dht DHT; // To read the Relative Moisture Sensor (Display Humidity/Temperature)

void setup() {
  pinMode(LIGHTSENSORPIN,  INPUT);  
  pinMode(MOISTURESENSORPIN,  INPUT);  
  pinMode(RELATIVEHUMIDITY_PIN,  INPUT);  

  Serial.begin(9600);
}

void loop() {
  float readingLight = analogRead(LIGHTSENSORPIN);
  float moisture = analogRead(MOISTURESENSORPIN);
  int chk = DHT.read11(RELATIVEHUMIDITY_PIN);
  toJsonFormat(readingLight, moisture, DHT.temperature, DHT.humidity); 
  delay(2000);
}

void toJsonFormat(float lightVal, float moisture, int temperature, int humidity) { 
  Serial.println("{'light': "+ String(lightVal) + ", " + 
                  "'moisture': " + String(moisture) + ", " + 
                  "'temperature': " + String(temperature) + ", " +
                  "'humidity': " + String(humidity) + " }");
}
