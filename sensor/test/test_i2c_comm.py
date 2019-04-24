import RPi.GPIO as gpio
from smbus2 import SMBus, i2c_msg
from smbus2 import SMBusWrapper
import smbus
import time
import sys

# bus = smbus.SMBus(1)
address = 0x04
bus = smbus.SMBus(1);

def main():
    gpio.setmode(gpio.BCM)
    # status = False
    while 1:
        # Send any message
        write = i2c_msg.write(address, [40, 50])
        receivedMessage = ""
        data_received_from_Arduino = bus.read_i2c_block_data(address, 0, 32)
        for i in range(len(data_received_from_Arduino)):
            if chr(data_received_from_Arduino[i]) == ";":
                break;
            receivedMessage += chr(data_received_from_Arduino[i])

        print(receivedMessage.encode('utf-8'))
        # with SMBusWrapper(1) as bus:
        #   bus.i2c_rdwr(write, read)
        time.sleep(1)


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print "Interrupted"
        gpio.cleanup()
        sys.exit(0)
