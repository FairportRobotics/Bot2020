package frc.team578.robot.utils;

/**
 * ColorStates
 */
public class ColorManager {


    // ! This is the current color the robot sees
    // * Not to be confused with getCurrentColor which returns the color that the FRC Control Panel sees
    private static WheelColor seenColor = null;
    // # This is the color given during the competition to match.
    private static WheelColor chosenColor = null;

    public static WheelColor getSeenColor() {
        return seenColor;
    }

    /**
     * Takes the current seen color and returns the current color that the FRC Panel can detect.
     * 
     * @return the current color that the FRC Control Panel can detect
     * @return null if there is no current color.
     */
    public static WheelColor getPanelColor() {
        if(seenColor == null) {
            return null;
        }
        
        switch(seenColor) {
            case Red:
                return WheelColor.Blue;
            case Green:
                return WheelColor.Yellow;
            case Blue:
                return WheelColor.Red;
            case Yellow:
                return WheelColor.Green;
        }

        return null;
    }

    public static void setCurrentColor(WheelColor color) {
        seenColor = color;
    }

    /**
     * @param string - The string given to the robot by first to determine what color will be used
     * 
     * Performs logic on the color string and sets the chosenColor variable to the corresponding chosen color.
     */
    public static void setChosenColor(String cCode) {
        // ! Code needs to be put in here!
        switch(cCode) {
            case "R":
                chosenColor = WheelColor.Red;
                break;
            case "G":
                chosenColor = WheelColor.Green;
                break;
            case "B":
                chosenColor = WheelColor.Blue;
                break;
            case "Y":
                chosenColor = WheelColor.Yellow;
                break;
            default:
                System.out.println("Invalid code of " + cCode + " provided");
                break;
        }
    }

    /**
     * Checks to see if the current selected color matches the color FRC wants us to use
     * @return true if the chosenColor and getCurrentColor() match
     * @return false otherwise
     */
    public static boolean CurrentColorMatches() {
        if(getPanelColor() != null && chosenColor != null) {
            if(getPanelColor().equals(chosenColor)) {
                return true;
            }
        }

        return false;
    }
} 