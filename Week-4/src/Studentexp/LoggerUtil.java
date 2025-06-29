package Studentexp;
import java.util.logging.*;
public class LoggerUtil {
	private Logger logger;
	private FileHandler fileHandler;
	//constructor
	public LoggerUtil() {
		try {
			// create logger
			logger=Logger.getLogger("StudentLogger");
			// write logs if true
			fileHandler=new FileHandler("student.log",true);
			fileHandler.setFormatter(new SimpleFormatter());
			//add filehandler to logger
			logger.addHandler(fileHandler);
		}
		catch(Exception e) {
			System.out.println("logger error:"+e.getMessage());
			
		}
	}
	public Logger getLogger() {
		return logger;
	}

}
