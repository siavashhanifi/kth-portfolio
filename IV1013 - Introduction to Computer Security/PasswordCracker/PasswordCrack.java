import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PasswordCrack {

	public static void main(String[] args) throws FileNotFoundException {
		String[] dictionary = loadLinesFromFile(args[0]);
		String[] gcosLines = loadLinesFromFile(args[1]);
		for(String gcos : gcosLines)
			System.out.println(crackPassword(gcos, dictionary));
	}

	private static String crackPassword(String gcos, String[] dictionary) {
		String salt = deriveSalt(gcos);
		String encryptedPassword = deriveEncryptedPassword(gcos);
		String name = deriveName(gcos);
		String surname = deriveSurname(gcos);
		String[] seededDictionary = seedDictionary(name, surname, dictionary);

		for(String word : seededDictionary) {
			//System.out.println(word);
			if(JCrypt.crypt(salt, word).compareTo(encryptedPassword) == 0) {
				System.out.println(word);
				return word;
			}
		}
		return null;
	}

	private static String[] seedDictionary(String name, String surname, String[] dictionary) {
		String[] seedCombinations = generateSeeds(name, surname);
		String[] seedsAndDictionary = new String[dictionary.length + seedCombinations.length];
		System.arraycopy(dictionary, 0, seedsAndDictionary, 0, dictionary.length);
		System.arraycopy(seedCombinations, 0, seedsAndDictionary, dictionary.length, seedCombinations.length);
		return seedsAndDictionary;
	}

	private static String[] generateSeeds(String name, String surname) {
		String nameSurname = name.concat(surname);
		String[] namePermutations = permute(name);
		String[] surnamePermutations = permute(surname);
		String[] nameSurnamePermutations = permute(nameSurname);
		
		int totalPermutations = namePermutations.length + surnamePermutations.length + nameSurnamePermutations.length;
		String[] seeds = new String[totalPermutations];
		System.arraycopy(namePermutations, 0, seeds, 0, namePermutations.length);  
		System.arraycopy(surnamePermutations, 0, seeds, namePermutations.length, surnamePermutations.length);  
		System.arraycopy(nameSurnamePermutations, 0, seeds, namePermutations.length+surnamePermutations.length, nameSurnamePermutations.length);
		return seeds;
	}


	static String[] permute(String input) {
		int n = input.length(); 
		// Number of permutations is 2^n 
		int max = 1 << n; 
		String[] permutations = new String[max];
		// Converting string to lower case 
		input = input.toLowerCase(); 
		// Using all subsequences and permuting them 
		for(int i = 0;i < max; i++) 
		{ 
			char combination[] = input.toCharArray(); 

			// If j-th bit is set, we convert it to upper case 
			for(int j = 0; j < n; j++) 
			{ 
				if(((i >> j) &  1) == 1) 
					combination[j] = (char) (combination[j]-32); 
			} 
			// Printing current combination 
			permutations[i] = new String(combination);
		} 
		return permutations;
	} 
	
	private static String deriveSurname(String gcos) {
		return gcos.split(":")[4].split(" ")[1];
	}

	private static String deriveName(String gcos) {
		return gcos.split(":")[4].split(" ")[0];
	}

	private static String deriveEncryptedPassword(String gcos) {
		String secondSection= gcos.split(":")[1];
		return secondSection.substring(2,11);
	}

	private static String deriveSalt(String gcos) {
		String secondSection= gcos.split(":")[1];
		return secondSection.substring(0,2);
	}

	private static String[] loadLinesFromFile(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(filename));
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}

		String[] arr = lines.toArray(new String[0]);
		return arr;
	}


}
