package frc.robot.subsystems;

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
    
    public AryaMech(Solenoid shortSolenoid, Solenoid longSolenoid){
        this.shortSolenoid = shortSolenoid;
        this.longSolenoid = longSolenoid;

        //ik this looks sus but its for ease of change
        shortOut = 0;
        shortIn = shortOut + 40;
        longOut = shortIn + 5;
        longIn = longOut + 15;

        timer= new Timer();
        timer.start();

    }

    public void periodic() {
        if(timer.hasElapsed(shortOut)){
            shortSolenoid.set(true);
        }
        else if (timer.hasElapsed(shortIn)){
            shortSolenoid.set(false);
        }
        else if (timer.hasElapsed(longOut)){
            longSolenoid.set(true);
        }
        else if (timer.advanceIfElapsed(longIn)){
            longSolenoid.set(false);
        }


    }
    
}
