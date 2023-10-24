package frc.robot.subsystems;

import javax.swing.text.StyledEditorKit.BoldAction;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class AryaMech {
    private Timer timer;
    private Solenoid shortSolenoid;
    private Solenoid longSolenoid;

    private double longOut;
    private double longIn;
    private double shortOut;
    private double shortIn;

    private boolean out;
    
    NetworkTableInstance inst;
    NetworkTable table;
    DoublePublisher audioPub;
    private Timer audio_timer;
    private final double time_to_wait_before_turning_off_audio_signal;

    public AryaMech(Solenoid shortSolenoid, Solenoid longSolenoid){
        this.shortSolenoid = shortSolenoid;
        this.longSolenoid = longSolenoid;

        //ik this looks sus but its for ease of change
        shortIn = 40;
        longOut = shortIn + 5;
        longIn = longOut + 15;

        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("audio");
        audioPub = table.getDoubleTopic("trigger_audio").publish();
        audio_timer = new Timer();
        time_to_wait_before_turning_off_audio_signal = 1.0;


        timer= new Timer();
        timer.start();

        out = true;

    }

    public void periodic() {
        //skeleton pumps thing to set off tnt
        if(timer.get() < 40 && timer.get() % 2 == 0){
            shortSolenoid.set(out);
            out = !out;
        }

        if(audio_timer.hasElapsed(time_to_wait_before_turning_off_audio_signal)){
            audioPub.set(0.0); // Set the value on networktables to zero so we don't restart the audio
            audio_timer.stop();
            audio_timer.reset();
        }

        //pumps end on an in
        else if (timer.hasElapsed(shortIn)){
            shortSolenoid.set(false);
        }
        //tnt explodes, minecraft tnt sound plays
        else if (timer.hasElapsed(longOut)){
            
            audioPub.set(1.0); // this publishes the trigger signal to networktables, which gets picked up by the driverstation python script
            audio_timer.reset(); // setting a timer so that we can stop sending the signal after an appropriate amount of time
            audio_timer.start(); // if we don't stop sending the trigger signal, the audio might start playing again
            
            longSolenoid.set(true);
        }
        //reset
        else if (timer.advanceIfElapsed(longIn)){
            longSolenoid.set(false);
        }


    }
    
}
