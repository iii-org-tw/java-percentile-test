package org.ioxcenter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Arrays;
import org.apache.commons.math4.stat.descriptive.rank.Percentile;
import org.junit.Assert;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
	    BufferedReader br = new BufferedReader(
	    new InputStreamReader(
	    new FileInputStream(scanner.next())
	   )
	);

	    String line;
	    double[] doubleValues;
	    double[] weights = new double[29];
	    for ( int i = 0; i < 29 ;i++) {
	        weights[i] = 1; 
	    }
	    Percentile p = new Percentile().withEstimationType(Percentile.EstimationType.R_7);
	    double q;
	    //System.out.println(Arrays.toString(weights));
	    int count = 0;
	    while ( (line = br.readLine()) != null ) {
	       doubleValues = Arrays.stream(line.split(","))
	                            .mapToDouble(Double::parseDouble)
	                            .toArray();
	       q =Math.random()*100;
	       try {
	           checkEqual(doubleValues, weights, p, q);
	       }
	       catch (AssertionError e){
	    	   System.out.println(count);
	    	   System.out.println(q);
	    	   System.out.println(p.evaluate(doubleValues, weights, q));
	    	   System.out.println(p.evaluate(doubleValues, q));
	    	   break;
	       }
	       count += 1;
	       if (count%100000 == 0) {
	    	   System.out.println(count + " data have been checked");
	       }
	    }
	    System.out.println("Pass");
	 }
	 public static void checkEqual(double[] values, double[] weights, Percentile p, double q) {
	     p.setData(values, weights);
	     Assert.assertEquals(p.evaluate(values, weights, q), p.evaluate(values, q), 1.0e-13);
	     Assert.assertEquals(p.evaluate(values, weights, 0, values.length, q),
	        		         p.evaluate(values, 0, values.length, q),
	        		         1.0e-13);

	    }
}
