
public class hypothesis {


	public double h_prior;
	public double h_cherry;
	public double h_lime;
	public hypothesis(double prior,double cherry,double lime)	{
		round(prior,5);
		 h_prior=prior;
		 round(cherry,5);
		 h_cherry=cherry;
		 round(lime,5);
		 h_lime=lime;
		// System.out.println("H_prior:"+h_prior);

	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    //long tmp = Math.round(value);
	    return (double) value / factor;
	}

}
