import java.io.IOException;

public class Driver {

	public static void main(String [] args) {


		Polynomial test6 = new Polynomial(new double[] {1.12, -2, 3}, new int[] {4, 3, 2});

        Polynomial test7 = new Polynomial(new double[] {-4.5, 99}, new int[] {1, 99});

        
        Polynomial result3 = test7.multiply(test6);

        try{
            result3.saveToFile("c.txt");

        }
        catch (IOException ex){
            System.out.println("Error Occured");
        }

        System.out.println(result3.evaluate(-23.5));

        result3 = test6.multiply(test7);

        System.out.println(result3.evaluate(-23.5));

        result3 = test6.add(test7);

        System.out.println(result3.evaluate(23.5));

        result3 = test7.add(test6);

        System.out.println(result3.evaluate(23.5));


        try {
            result3.saveToFile("b.txt");

        }
        catch (IOException ex){
            System.out.println("Error Occured");
        }
        


        //Output:

        //-1.9888575968676583E143
        //-1.9888575968676583E143
        //5.387081668148581E137
        //5.387081668148581E137
        // open c.txt: "110.88000000000001x103-198.0x102+297.0x101-5.040000000000001x5+9.0x4-13.5x3"
        //open b.txt: "1.12x4-2.0x3+3.0x2-4.5x1+99.0x99"
		
	}
}