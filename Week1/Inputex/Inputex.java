import java.util.Scanner;
public class Inputex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		
		System.out.print("Enter the first integer: ");
		int n1=sc.nextInt();
		
		System.out.print("Enter the second integer: ");
		int n2=sc.nextInt();
		
		System.out.print("Enter a Floating-point number: ");
		double f=sc.nextDouble();
		
		System.out.print("Enter a single character: ");
		char ch=sc.next().charAt(0);
		
		System.out.print("Enter a boolean value(true/false): ");
		boolean b=sc.nextBoolean();
		sc.nextLine();
		System.out.print("Enter your name: ");
		String name=sc.nextLine();
		
		int sum=n1+n2;
		int diff=n1-n2;
		int product=n1*n2;
		double mf=f*2;
		char c=(char)(ch+1);
		boolean opposite=!b;
		
		System.out.println("Sum of "+n1+" and "+n2+" is: "+sum);
		System.out.println("Difference between "+n1+" and "+n2+" is: "+diff);
		System.out.println("Product of "+n1+" and "+n2+" is: "+product);
		System.out.println(f+" multiplied by 2 is: "+mf);
		System.out.println("The next character after "+ch+" is: "+c);
		System.out.println("The opposite of "+b+" is: "+opposite);
		System.out.println("Hello, "+name+"!");
		sc.close();
	}

}

