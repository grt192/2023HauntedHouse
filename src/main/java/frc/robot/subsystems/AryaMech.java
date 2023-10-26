package frc.robot.subsystems;

import javax.swing.text.StyledEditorKit.BoldAction;

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
    private final Solenoid ledStrip;

    private double SHORT_OUT = 40;
    private double END = SHORT_OUT + 12;
    private double FLASH_INTERVAL = 0.1;
    private double SHORT_INTERVAL_IN = 0.2;
    private double SHORT_INTERVAL_OUT= 2.0;


    private boolean out;
    
    NetworkTableInstance inst;
    NetworkTable table;
    DoublePublisher audioPub;
    private Timer audio_timer;
    private final double time_to_wait_before_turning_off_audio_signal;
    private boolean s40 = false;
    private boolean s41 = false;
    private boolean s46 = false;
    private boolean led = false;


    private Timer ledTimer;
    private Timer pumpTimer;

    public AryaMech(Solenoid shortSolenoid, Solenoid longSolenoid, Solenoid ledStrip){
        this.shortSolenoid = shortSolenoid;
        this.longSolenoid = longSolenoid;
        this.ledStrip = ledStrip;

        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("audio");
        audioPub = table.getDoubleTopic("trigger_audio").publish();
        audio_timer = new Timer();
        audio_timer.start();
        pumpTimer = new Timer();
        time_to_wait_before_turning_off_audio_signal = 1.0;

        ledTimer = new Timer();

        timer = new Timer();
        timer.start();

        out = true;

    }

    public void periodic() {
        //skeleton pumps thing to set off tnt
        
        if(timer.get() < SHORT_OUT && pumpTimer.advanceIfElapsed(out ? SHORT_INTERVAL_OUT : SHORT_INTERVAL_IN)){ 
            shortSolenoid.set(out);
            out = !out;
        }

        if(timer.hasElapsed(SHORT_OUT) && !s40){
            shortSolenoid.set(true);
            s40 = true;
        }
        if(timer.hasElapsed(SHORT_OUT + 1) && !s41){
            shortSolenoid.set(false);
            s41 = true;
        }
        
        // audioPub.set(1.0); // this publishes the trigger signal to networktables, which gets picked up by the driverstation python script
        if(timer.hasElapsed(SHORT_OUT + 2)){
            System.out.println("SETTING TRIGGER");
            audioPub.set(1.0); // Set the value on networktables to zero so we don't restart the audio
        }

        if(timer.hasElapsed(SHORT_OUT + 6) && !s46){
            longSolenoid.set(true);
            s46 = true;
            ledTimer.reset();
            ledTimer.start();
        }

        if(timer.hasElapsed(SHORT_OUT + 6) && ledTimer.advanceIfElapsed(FLASH_INTERVAL)){
            led = !led;
            ledStrip.set(led);
        }

        if (timer.advanceIfElapsed(END)){
            longSolenoid.set(false);
            ledStrip.set(false);
            s40 = false;
            s41 = false;
            s46 = false;
            ledTimer.stop();
            audioPub.set(0.0);
        }
        if(audio_timer.advanceIfElapsed(.05)){
            System.out.println("SHORT: " + shortSolenoid.get() + "  LONG: " + longSolenoid.get());
        }

    }
    
}
