package Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public Log(){}

    public void println(String action){

        System.out.println(
                (new SimpleDateFormat("[HH:mm:ss] ")).format(new Date()) + action
        );

    }

}
