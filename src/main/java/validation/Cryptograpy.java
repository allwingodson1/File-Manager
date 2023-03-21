package validation;

public class Cryptograpy {
	public static String encript(String name) {
		var ans = "";
		for(int i=0;i<name.length();i++) {
			char charecter = name.charAt(i);
			int random;
			random = (int) (Math.random()*(127-((charecter>0)?charecter:-1*charecter)));
			ans += (char) (charecter+random);
			ans += (char) (-1*random);
		}
		return ans;
	}

	public static String decript(String name) {
		var ans = "";
		for(int i=0;i<name.length();i++) {
			int value = name.charAt(i);
			i++;
			int random = -1*(name.charAt(i));
			value -= random;
			ans+=(char)value;
		}
		return ans;
	}
}
