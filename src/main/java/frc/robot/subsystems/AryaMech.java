package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.leds.LEDLayer;
import frc.robot.subsystems.leds.LEDStrip;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class AryaMech extends SubsystemBase{
    private Timer timer;
    private Solenoid shortSolenoid;
    private Solenoid longSolenoid;

    private double longOut;
    private double longIn;
    private double shortOut;
    private double shortIn;
    
    NetworkTableInstance inst;
    NetworkTable table;
    DoublePublisher audioPub;
    private Timer audio_timer;
    private final double time_to_wait_before_turning_off_audio_signal;

    private final AnalogOutput ledStrip;
    private final int LED_PWM_PORT = 0;
    private Timer ledTimer;

    public AryaMech(Solenoid shortSolenoid, Solenoid longSolenoid){
        this.shortSolenoid = shortSolenoid;
        this.longSolenoid = longSolenoid;

        //ik this looks sus but its for ease of change
        shortOut = 0;
        shortIn = shortOut + 40;
        longOut = 5;
        // longOut = shortIn + 5;
        longIn = longOut + 15;

        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("audio");
        audioPub = table.getDoubleTopic("trigger_audio").publish();
        audio_timer = new Timer();
        time_to_wait_before_turning_off_audio_signal = 1.0;

        ledStrip = new AnalogOutput(LED_PWM_PORT);
        ledTimer = new Timer();

        timer= new Timer();
        timer.start();

    }

    public void periodic() {

        if(ledTimer.hasElapsed(0.25)){
            ledStrip.setVoltage(0.0);
            ledTimer.stop();
            ledTimer.reset();
            ledTimer.start();
        }
        
        // audioPub.set(1.0); // this publishes the trigger signal to networktables, which gets picked up by the driverstation python script
        if(audio_timer.hasElapsed(time_to_wait_before_turning_off_audio_signal)){
            audioPub.set(0.0); // Set the value on networktables to zero so we don't restart the audio
            audio_timer.stop();
            audio_timer.reset();
        }

        if(timer.hasElapsed(shortOut)){
            shortSolenoid.set(true);
        }
        else if (timer.hasElapsed(shortIn)){
            shortSolenoid.set(false);
        }
        else if (timer.hasElapsed(longOut)){
            System.out.println("SETTING TRIGGER");
            audioPub.set(1.0); // this publishes the trigger signal to networktables, which gets picked up by the driverstation python script
            audio_timer.reset(); // setting a timer so that we can stop sending the signal after an appropriate amount of time
            audio_timer.start(); // if we don't stop sending the trigger signal, the audio might start playing again

            ledStrip.setVoltage(5.0);
            ledTimer.reset();
            ledTimer.start();
            
            longSolenoid.set(true);
        }
        else if (timer.advanceIfElapsed(longIn)){
            longSolenoid.set(false);
            ledStrip.setVoltage(0.0);
        }

    }
    
}
