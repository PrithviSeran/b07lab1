

public class Polynomial{
    double[] coeffecients;

    Polynomial(){
        this.coeffecients = new double[1];
        this.coeffecients[0] = 0;
    }

    Polynomial(double[]coeffecients_in){
        this.coeffecients = new double[coeffecients_in.length];

        for (int i = 0; i < coeffecients_in.length; i++){
            this.coeffecients[i] = coeffecients_in[i];
        }

    }

    public Polynomial add(Polynomial pol1){

        int lowest_length = Math.min(pol1.coeffecients.length, coeffecients.length);
        int highest_length = Math.max(pol1.coeffecients.length, coeffecients.length);
        double[] new_array = new double[highest_length];

        for (int i = 0; i < lowest_length; i++){
            new_array[i] = this.coeffecients[i] + pol1.coeffecients[i];
        }

        if (highest_length == pol1.coeffecients.length){
            for (int i = lowest_length; i < highest_length; i++){
                new_array[i] = pol1.coeffecients[i];
            }
        }
        else{
            for (int i = lowest_length; i < highest_length; i++){
                new_array[i] = this.coeffecients[i];
            }
        }


        Polynomial result = new Polynomial(new_array);

        return result;

    }

    public double evaluate(double x){
        double sum = 0.0;
        for (int i = 0; i < coeffecients.length; i++){
            sum = sum + this.coeffecients[i]*(Math.pow(x, i));
        }

        return sum;
    }

    public boolean hasRoot(double val){
        double result = this.evaluate(val);

        if (result == 0.0){
            return true;
        }

        return false;
    }
}