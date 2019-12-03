package TestCode;

public class ExternalMethodsCheckTestCode {

	 @Override
	 public String toString(){
	   return "dumbo";
	 }
	 
	 public void theMethod() {
	   double d = Math.cos(Math.PI);
	   // Math.cos(Math.PI);
	   ExternalMethodsCheckTestCode myMain = new ExternalMethodsCheckTestCode();
	   // Local method reference
	   String test = this.toString();
	   // External method reference
	    System.out.println(test);
	 }
}
