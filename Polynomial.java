import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

public class Polynomial{
    double[] coefficients;
    int[] exponents;


    Polynomial(){
        this.coefficients = new double[1];
        this.coefficients[0] = 0;

        this.exponents = new int[1];
        this.exponents[0] = 0;
    }

    Polynomial(double[]coeffecients_in, int[]exponents_in){

        this.coefficients = new double[coeffecients_in.length];
        this.exponents = new int[exponents_in.length];


        for (int i = 0; i < coeffecients_in.length; i++){
            this.coefficients[i] = coeffecients_in[i];
        }

        for (int i = 0; i < exponents_in.length; i++){
            this.exponents[i] = exponents_in[i];
        }
        
        //coefficients_in;
        //this.exponents = exponents_in;

    }

    
    public Polynomial(File file) throws IOException{
        BufferedReader scanner = new BufferedReader(new FileReader(file));
        String line = scanner.readLine();
        scanner.close();

        ArrayList<Double> coeffsList = new ArrayList<>();
        ArrayList<Integer> expsList = new ArrayList<>();

        String[] terms = line.split("(?=[+-])"); // Split by + or - while keeping them

        for (String term : terms) {
            term = term.trim();
            if (term.equals("")) continue;

            if (term.contains("x")) {
                String[] parts = term.split("x");

                double coeff;
                if (parts[0].equals("+") || parts[0].equals("") || parts[0].equals("-")) {
                    coeff = parts[0].equals("-") ? -1 : 1;
                } else {
                    coeff = Double.parseDouble(parts[0]);
                }

                int exp;
                if (parts.length == 1 || parts[1].equals("")) {
                    exp = 1;
                } else {
                    exp = Integer.parseInt(parts[1].replace("^", ""));
                }

                coeffsList.add(coeff);
                expsList.add(exp);
            } else {
                coeffsList.add(Double.parseDouble(term));
                expsList.add(0);
            }
        }

        this.coefficients = new double[coeffsList.size()];
        this.exponents = new int[expsList.size()];

        for (int i = 0; i < coeffsList.size(); i++) {
            this.coefficients[i] = coeffsList.get(i);
            this.exponents[i] = expsList.get(i);
        }
    }

    public void saveToFile(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new File(fileName));
        String sb = "";
        String total = "";

        for (int i = 0; i < this.coefficients.length; i++) {
            double coeff = this.coefficients[i];
            int exp = this.exponents[i];

            if (coeff > 0 && i != 0) {
                sb = sb + "+";
            }
            if (exp == 0) {
                sb = sb + coeff;
            } 
            else if (exp == 1) {
                if (coeff == 1) {
                    sb = sb + "x";
                } else if (coeff == -1) {
                    sb = sb + "-x";
                } else {
                    sb = sb + coeff + "x";
                }
            }   
            else {
                if (coeff == 1) {
                    sb = sb + "x" + exp;
                } else if (coeff == -1) {
                    sb = sb + "-x" + exp;
                } else {
                    sb = sb + coeff + "x" + exp;
                }
            }

        }


        //System.out.println(total);



        writer.println(sb);
        writer.close();
    
    }
    

    public Polynomial add(Polynomial pol1) {
        int newLength = this.coefficients.length + pol1.coefficients.length;
        double[] tempCoefficients = new double[newLength];
        int[] tempExponents = new int[newLength];
        int index = 0;

        // Copy this polynomial's terms
        for (int i = 0; i < this.coefficients.length; i++) {
            tempCoefficients[index] = this.coefficients[i];
            tempExponents[index] = this.exponents[i];
            index++;
        }

        // Add the terms from the other polynomial
        for (int i = 0; i < pol1.coefficients.length; i++) {
            boolean found = false;
            for (int j = 0; j < this.coefficients.length; j++) {
                if (pol1.exponents[i] == this.exponents[j]) {
                    tempCoefficients[j] += pol1.coefficients[i];
                    found = true;
                    break;
                }
            }
            if (!found) {
                tempCoefficients[index] = pol1.coefficients[i];
                tempExponents[index] = pol1.exponents[i];
                index++;
            }
        }

        // Remove zero coefficients and create the resulting polynomial
        int nonZeroCount = 0;
        for (int i = 0; i < index; i++) {
            if (tempCoefficients[i] != 0) {
                nonZeroCount++;
            }
        }

        double[] finalCoefficients = new double[nonZeroCount];
        int[] finalExponents = new int[nonZeroCount];
        int finalIndex = 0;

        for (int i = 0; i < index; i++) {
            if (tempCoefficients[i] != 0) {
                finalCoefficients[finalIndex] = tempCoefficients[i];
                finalExponents[finalIndex] = tempExponents[i];
                finalIndex++;
            }
        }

        return new Polynomial(finalCoefficients, finalExponents);
    }

    public double evaluate(double x) {
        double sum = 0.0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return sum;
    }

    public boolean hasRoot(double val) {
        return evaluate(val) == 0.0;
    }

    public Polynomial multiply(Polynomial pol1) {
        int newLength = this.coefficients.length * pol1.coefficients.length;
        double[] tempCoefficients = new double[newLength];
        int[] tempExponents = new int[newLength];
        int index = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < pol1.coefficients.length; j++) {
                double newCoeff = this.coefficients[i] * pol1.coefficients[j];
                int newExp = this.exponents[i] + pol1.exponents[j];
                
                boolean found = false;
                for (int k = 0; k < index; k++) {
                    if (tempExponents[k] == newExp) {
                        tempCoefficients[k] += newCoeff;
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    tempCoefficients[index] = newCoeff;
                    tempExponents[index] = newExp;
                    index++;
                }
            }
        }

        int nonZeroCount = 0;
        for (int i = 0; i < index; i++) {
            if (tempCoefficients[i] != 0) {
                nonZeroCount++;
            }
        }

        double[] finalCoefficients = new double[nonZeroCount];
        int[] finalExponents = new int[nonZeroCount];
        int finalIndex = 0;

        for (int i = 0; i < index; i++) {
            if (tempCoefficients[i] != 0) {
                finalCoefficients[finalIndex] = tempCoefficients[i];
                finalExponents[finalIndex] = tempExponents[i];
                finalIndex++;
            }
        }

        return new Polynomial(finalCoefficients, finalExponents);
    }
}