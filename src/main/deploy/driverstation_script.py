# BEFORE RUNNING THIS:
# run 'python3 -m pip install pynetworktables' to install the module needed for networktables comms
# run 'python3 -m pip install pygame' to install the module needed for sound playback

from networktables import NetworkTables # <--- this will raise an error when in the java env, create a python venv on DS and run this there
import pygame

NetworkTables.initialize(server='10.1.92.2') 

audio_table = NetworkTables.getTable("audio")

pygame.mixer.init()
sound = pygame.mixer.Sound('explosion_sound.wav')

old_trigger_value = 0.0
debug = False

print("GRT HH Driverstation script initialized and running.")

while True:
    trigger_value = audio_table.getNumber("trigger_audio", -1.0)
    if debug:
        print("Trigger value received: ", trigger_value)
    
    if old_trigger_value == 0.0 and trigger_value == 1.0:
        sound.play()
    
    old_trigger_value = trigger_value
        

        
    
    
