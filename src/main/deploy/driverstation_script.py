# Kevin will strangle me for putting this file here-- I know. 
# Deal with and save it to the DS computer to be run whenever the HH is on. 

# BEFORE RUNNING THIS:
# run 'python3 -m pip install pynetworktables' to install the module needed for networktables comms
# run 'python3 -m pip install pygame' to install the module needed for sound playback

import time
from networktables import NetworkTables # <--- this will raise an error when in the java env, create a python venv on DS and run this there
import pygame
from numpy import random

NetworkTables.initialize(server='10.1.92.255') # I forgot, what are robot IPs again?

audio_table = NetworkTables.getTable("audio")

pygame.mixer.init()
sound1 = pygame.mixer.Sound('explode1.mp3') # in the process of pestering arya for audio files
sound2 = pygame.mixer.Sound('explode2.mp3') # in the process of pestering arya for audio files
sound3 = pygame.mixer.Sound('explode3.mp3') # in the process of pestering arya for audio files
sound4 = pygame.mixer.Sound('explode4.mp3') # in the process of pestering arya for audio files 
the_voicemail = pygame.mixer.Sound('voicemail.mp3') # in case arya doesn't send audio files, use blackmail audio

sounds = [sound1, sound2, sound3, sound4]

while True:
    trigger_value = audio_table.getNumber("trigger_audio", 0.0)
    print("trigger:", trigger_value)
    
    if trigger_value == 1.0:
        sound_num = random(0,4)
        sounds[sound_num].play()
        

        
    
    
