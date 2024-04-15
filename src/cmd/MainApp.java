package cmd;
//Mario Kart Wii MAX Course Time Estimator v1.0, by ViveTheModder
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainApp 
{
	static final int zeroASCII = 48;
	static final int courseCNT = 32;
	public static int roundUpToMultiOf5(int num)
	{
		if (num%5!=0) num = num-(num%5)+5;
		return num;
	}
	public static String getEstimatedTime(String inputTime)
	{
		char[] inputArray = inputTime.toCharArray();
		int m = (inputArray[0]-zeroASCII)*10+(inputArray[1]-zeroASCII);
		int s = (inputArray[3]-zeroASCII)*10+(inputArray[4]-zeroASCII);
		int ms = (inputArray[6]-zeroASCII)*100+(inputArray[7]-zeroASCII)*10+(inputArray[8]-zeroASCII);
		
		if (ms>=500) s++;
		int time = 60*m + s;
		
		if (time>180) time=roundUpToMultiOf5(time)+15;
		else if (time>=120) time=roundUpToMultiOf5(time)+10;
		else if (time>=95) time=roundUpToMultiOf5(time+5);
		else time=roundUpToMultiOf5(time);
		
		return (time/60)+"m"+(time%60)+"s";
	}
	public static void main(String[] args) throws IOException
	{
		File inputTxt = new File("times.txt");
		String[] inputTimes = new String[courseCNT];
		String[] outputTimes = new String[courseCNT];
		Scanner sc = new Scanner(inputTxt); int i;
		
		if (!inputTxt.exists()) System.exit(1);
		for (i=0; i<courseCNT; i++)
			inputTimes[i] = sc.nextLine();
		for (i=0; i<courseCNT; i++)
			outputTimes[i] = getEstimatedTime(inputTimes[i]);

		File courseTxt = new File("names.txt");
		String[] courseNames = new String[courseCNT];
		sc = new Scanner(courseTxt);
		for (i=0; i<courseCNT; i++)
			courseNames[i] = sc.nextLine();
		
		String outputString = "[Course Name]  [Estimated Time]\n";
		for (i=0; i<courseCNT; i++)
			outputString += String.format("%-23s   %s\n",courseNames[i],outputTimes[i]);
		System.out.println(outputString);
		
		File outputTxt = new File("output.txt");
		FileWriter outputWriter = new FileWriter(outputTxt);
		outputWriter.write(outputString);
		sc.close(); outputWriter.close();
	}
}