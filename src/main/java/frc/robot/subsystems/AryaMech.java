package frc.robot.subsystems;

import javax.swing.text.StyledEditorKit.BoldAction;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class AryaMech {
    private Timer timer;
    private Solenoid shortSolenoid;
    private Solenoid longSolenoid;

    private double longOut;
    private double longIn;
    private double shortOut;
    private double shortIn;

    private boolean out;
    
    public AryaMech(Solenoid shortSolenoid, Solenoid longSolenoid){
        this.shortSolenoid = shortSolenoid;
        this.longSolenoid = longSolenoid;

        //ik this looks sus but its for ease of change
        shortIn = 40;
        longOut = shortIn + 5;
        longIn = longOut + 15;

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
        //pumps end on an in
        else if (timer.hasElapsed(shortIn)){
            shortSolenoid.set(false);
        }
        //tnt explodes, minecraft tnt sound plays
        else if (timer.hasElapsed(longOut)){
            longSolenoid.set(true);
        }
        //reset
        else if (timer.advanceIfElapsed(longIn)){
            longSolenoid.set(false);
        }


    }
    
}
