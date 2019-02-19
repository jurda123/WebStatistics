
public class Occurence {
 String key;
 String value;
 
 public Occurence( String key, String value) {
	 this.key = key;
	 this.value = value;
	 }
 @Override
 public String toString() {
	 return "key:" + key + ",value:" + value;
 }
 }
