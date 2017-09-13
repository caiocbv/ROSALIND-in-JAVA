import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
/*
 * Create By CAIO BEZERRA VIANA
 *  
 * Rosalind is a platform for learning bioinformatics and programming through problem solving. Find More at:
	http://rosalind.info

	This project has anwsers from these problems:

	1) http://rosalind.info/problems/dna/ 
	2) http://rosalind.info/problems/rna/
	3) http://rosalind.info/problems/revc/
	4) http://rosalind.info/problems/hamm/
	5) http://rosalind.info/problems/prot/
	6) http://rosalind.info/problems/cons/
	7) http://rosalind.info/problems/orf/

 */
public class Main {
	public static void main(String[] args) {
		System.out.println("choose the problem: ");
		System.out.println("1) http://rosalind.info/problems/dna/");
		System.out.println("2) http://rosalind.info/problems/rna/");
		System.out.println("3) http://rosalind.info/problems/revc/");
		System.out.println("4) http://rosalind.info/problems/hamm/");
		System.out.println("5) http://rosalind.info/problems/prot/");
		System.out.println("6) http://rosalind.info/problems/cons/");
		System.out.println("7) http://rosalind.info/problems/orf/");
		System.out.println("8) http://rosalind.info/problems/ksim/");
		
		Scanner s = new Scanner(System.in);
		int x = s.nextInt();
		switch(x){
			case 1:
				Main.dna();
				break;

			case 2:
				Main.rna();
				break;

			case 3:
				Main.revc();
				break;

			case 4:
				Main.hammi();
				break;

			case 5:
				Main.prot();
				break;

			case 6:
				Main.cons();
				break;

			case 7:
				Main.orf();
				break;

			case 8:
				Main.ksim();
				break;
		}
		
	}

	private static void ksim() {
		try {
			Scanner sc = new Scanner(new File("entrada8.txt"));
			String s = sc.nextLine();
			int k = Integer.parseInt(s);
			String motif = sc.nextLine();
			s = sc.nextLine();
			sc.close();
			
			PrintWriter saida = new PrintWriter(new File("saida8.txt"));
			
			for(int i = 0; i < s.length();i+=3){
				String temp = "";
				for(int j = i; j < s.length();j++){
					temp = temp + s.charAt(j);
					int d = Main.EditDistance(motif, temp);
					//System.out.println("A Distancia é Tal: " + d + " Na posicao: i = " + (i+1) + " j = "+ (j+1));
					if(d <= k){
						saida.println((i+1) + " "+  (j+1));
					}
				}
				
			}
			
			saida.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static int EditDistance(String s1, String s2) {
		if (s1 == null) {
            throw new NullPointerException("s1 must not be null");
        }

        if (s2 == null) {
            throw new NullPointerException("s2 must not be null");
        }

        if (s1.equals(s2)) {
            return 0;
        }

        if (s1.length() == 0) {
            return s2.length();
        }

        if (s2.length() == 0) {
            return s1.length();
        }

        // create two work vectors of integer distances
        int[] v0 = new int[s2.length() + 1];
        int[] v1 = new int[s2.length() + 1];
        int[] vtemp;

        // initialize v0 (the previous row of distances)
        // this row is A[0][i]: edit distance for an empty s
        // the distance is just the number of characters to delete from t
        for (int i = 0; i < v0.length; i++) {
            v0[i] = i;
        }

        for (int i = 0; i < s1.length(); i++) {
            // calculate v1 (current row distances) from the previous row v0
            // first element of v1 is A[i+1][0]
            //   edit distance is delete (i+1) chars from s to match empty t
            v1[0] = i + 1;

            // use formula to fill in the rest of the row
            for (int j = 0; j < s2.length(); j++) {
                int cost = 1;
                if (s1.charAt(i) == s2.charAt(j)) {
                    cost = 0;
                }
                v1[j + 1] = Math.min(
                        v1[j] + 1,              // Cost of insertion
                        Math.min(
                                v0[j + 1] + 1,  // Cost of remove
                                v0[j] + cost)); // Cost of substitution
            }

            // copy v1 (current row) to v0 (previous row) for next iteration
            //System.arraycopy(v1, 0, v0, 0, v0.length);

            // Flip references to current and previous row
            vtemp = v0;
            v0 = v1;
            v1 = vtemp;

        }

        return v0[s2.length()];
	}

	private static void orf() {
		try {
			Scanner sc = new Scanner(new File("entrada7.txt"));
			String s = sc.nextLine();
			System.out.println("Executou: " + s);
			s = sc.nextLine();
			sc.close();
			
			String s2 = "";
			for(int i = 0; i<s.length();i++){
				switch(s.charAt(i)){
				case 'a':
					s2 = 't' + s2;
					break;
				case 'A':
					s2 = 'T' + s2;
					break;
				case 'c':
					s2 = 'g' + s2;
					break;
				case 'C':
					s2 = 'G' + s2;
					break;
				case 'g':
					s2 = 'c' + s2;
					break;
				case 'G':
					s2 = 'C' + s2;
					break;
				case 't':
					s2 = 'a' + s2;
					break;
				case 'T':
					s2 = 'A' + s2;
					break;
				}
			}
			
			s = s.replaceAll("T", "U");
			s2 = s2.replaceAll("T", "U");
			
			PrintWriter saida = new PrintWriter(new File("saida7.txt"));
			ArrayList<String> out = new ArrayList<String>();
			//saida.println(prot(s));
			for(int i = 0; i < s.length()-2;i++){
				switch(""+s2.charAt(i)+s2.charAt(i+1)+s2.charAt(i+2)){
				case "AUG":
					String temp = prot(s2.substring(i, s2.length()));
					if(temp != null){
						out.add(temp);
					}
					break;
				}
				switch(""+s.charAt(i)+s.charAt(i+1)+s.charAt(i+2)){
				case "AUG":
					String temp = prot(s.substring(i, s.length()));
					if(temp != null){
						out.add(temp);
					}
					break;
				}
				
			}
			HashSet<String> unique = new HashSet<>(out);
			for (String value : unique) {
				   saida.println(value);
			}
			
			saida.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static String prot(String s) {
		String out = "";
		boolean stop = false;
		for(int i = 0; i < s.length();i+=3){
			String temp = "";
			switch(GlossaryRNAcodonTable("" + s.charAt(i) + s.charAt(i+1) + s.charAt(i+2))){
				case "Stop":
					stop = true;
				break;
				default:
					temp = GlossaryRNAcodonTable("" + s.charAt(i) + s.charAt(i+1) + s.charAt(i+2));
					break;
			}
			out = out + temp;
			if(stop){
				break;
			}
		}
		if(stop){
		return out;
		}else return null;
	}

	private static void cons() {
		int A[] = new int[1];
		int C[] = new int[1];
		int G[] = new int[1];
		int T[] = new int[1];
		try {
			Scanner sc = new Scanner(new File("entrada6.txt"));
			String s = "";
			
			boolean first = true;
			try{
			while(true){
				s = sc.nextLine();
				System.out.println("Executando: " + s);
				s = sc.nextLine();
				
				if(first){
					A = new int[s.length()];
					C = new int[s.length()];
					G = new int[s.length()];
					T = new int[s.length()];
					for(int i = 0; i<s.length();i++){
						A[i] = 0;
						C[i] = 0;
						G[i] = 0;
						T[i] = 0;
					}
					first = false;
				}
				
			
				for(int i = 0; i < s.length();i++){
					switch(s.charAt(i)){
					case 'a':
					case 'A':
						A[i]++;
						break;
					case 'c':
					case 'C':
						C[i]++;
						break;
					case 'g':
					case 'G':
						G[i]++;
						break;
					case 't':
					case 'T':
						T[i]++;
						break;
					}
				}
			}
			}catch(Exception e){
				System.err.println(e);
			}
			
			String out ="";
			
			for(int i = 0; i < s.length();i++){
				if(A[i] > C[i]){
					if(A[i] > G[i]){
						if(A[i] > T[i]){
							out = out + "A";
						}else{
							out = out + "T";
						}
					}else if(G[i] > T[i]){
						out = out + "G";
					}else{
						out = out + "T";
					}
				}else if(C[i] >  G[i]){
					if(C[i] > T[i]){
						out = out + "C";
					}else{
						out = out + "T";
					}
				}else if(G[i] > T[i]){
					out = out + "G";
				}else{
					out = out + "T";
				}
			}
			
			PrintWriter saida = new PrintWriter(new File("saida6.txt"));
			saida.println(out);
			saida.print("A:");
			for(int i = 0; i < s.length(); i++){
				saida.printf(" %d",A[i]);
			}
			saida.println();
			saida.print("C:");
			for(int i = 0; i < s.length(); i++){
				saida.printf(" %d",C[i]);
			}
			saida.println();
			saida.print("G:");
			for(int i = 0; i < s.length(); i++){
				saida.printf(" %d",G[i]);
			}
			saida.println();
			saida.print("T:");
			for(int i = 0; i < s.length(); i++){
				saida.printf(" %d",T[i]);
			}
			saida.close();
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static String GlossaryRNAcodonTable(String s){
		/*
		UUU F      CUU L      AUU I      GUU V
		UUC F      CUC L      AUC I      GUC V
		UUA L      CUA L      AUA I      GUA V
		UUG L      CUG L      AUG M      GUG V
		UCU S      CCU P      ACU T      GCU A
		UCC S      CCC P      ACC T      GCC A
		UCA S      CCA P      ACA T      GCA A
		UCG S      CCG P      ACG T      GCG A
		UAU Y      CAU H      AAU N      GAU D
		UAC Y      CAC H      AAC N      GAC D
		UAA Stop   CAA Q      AAA K      GAA E
		UAG Stop   CAG Q      AAG K      GAG E
		UGU C      CGU R      AGU S      GGU G
		UGC C      CGC R      AGC S      GGC G
		UGA Stop   CGA R      AGA R      GGA G
		UGG W      CGG R      AGG R      GGG G
		*/
		String out = "";
		
		switch(s){
		case "UUU": out = "F";
			break;
		case "CUU": out = "L";
			break;
		case "AUU": out = "I";
			break;
		case "GUU": out = "V";
			break;
		case "UUC": out = "F";
			break;
		case "CUC": out = "L";
			break;
		case "AUC": out = "I";
			break;      
		case "GUC": out = "V";
			break;
		case "UUA": out = "L";
			break;      
		case "CUA": out = "L";
			break;      
		case "AUA": out = "I";
			break;      
		case "GUA": out = "V";
			break;
		case "UUG": out = "L";
			break;      
		case "CUG": out = "L";
			break;      
		case "AUG": out = "M";
			break;      
		case "GUG": out = "V";
			break;
		case "UCU": out = "S";
			break;      
		case "CCU": out = "P";
			break;      
		case "ACU": out = "T";
			break;      
		case "GCU": out = "A";
			break;
		case "UCC": out = "S";
			break;      
		case "CCC": out = "P";
			break;      
		case "ACC": out = "T";
			break;      
		case "GCC": out = "A";
			break;
		case "UCA": out = "S";
			break;      
		case "CCA": out = "P";
			break;      
		case "ACA": out = "T";
			break;      
		case "GCA": out = "A";
			break;
		case "UCG": out = "S";
			break;      
		case "CCG": out = "P";
			break;      
		case "ACG": out = "T";
			break;      
		case "GCG": out = "A";
			break;
		case "UAU": out = "Y";
			break;      
		case "CAU": out = "H";
			break;      
		case "AAU": out = "N";
			break;      
		case "GAU": out = "D";
			break;
		case "UAC": out = "Y";
			break;      
		case "CAC": out = "H";
			break;      
		case "AAC": out = "N";
			break;      
		case "GAC": out = "D";
			break;
		case "UAA": out = "Stop";
			break;   
		case "CAA": out = "Q";
			break;      
		case "AAA": out = "K";
			break;      
		case "GAA": out = "E";
			break;
		case "UAG": out = "Stop";
			break;   
		case "CAG": out = "Q";
			break;      
		case "AAG": out = "K";
			break;      
		case "GAG": out = "E";
			break;
		case "UGU": out = "C";
			break;      
		case "CGU": out = "R";
			break;      
		case "AGU": out = "S";
			break;      
		case "GGU": out = "G";
			break;
		case "UGC": out = "C";
			break;      
		case "CGC": out = "R";
			break;      
		case "AGC": out = "S";
			break;      
		case "GGC": out = "G";
			break;
		case "UGA": out = "Stop";
			break;   
		case "CGA": out = "R";
			break;      
		case "AGA": out = "R";
			break;      
		case "GGA": out = "G";
			break;
		case "UGG": out = "W";
			break;      
		case "CGG": out = "R";
			break;      
		case "AGG": out = "R";
			break;      
		case "GGG": out = "G";
			break;
		}
		
		return out;
	}
	
	private static void prot() {
		try {
			Scanner sc = new Scanner(new File("entrada5.txt"));
			String s = sc.nextLine();
			
			sc.close();
			
			String out = "";
			
			for(int i = 0; i < s.length();i+=3){
				String temp = "";
				switch(GlossaryRNAcodonTable("" + s.charAt(i) + s.charAt(i+1) + s.charAt(i+2))){
					case "Stop":
					break;
					default:
						temp = GlossaryRNAcodonTable("" + s.charAt(i) + s.charAt(i+1) + s.charAt(i+2));
						break;
				}
				out = out + temp;
			}
			
			PrintWriter saida = new PrintWriter(new File("saida5.txt"));
			saida.printf("%s",out);
			saida.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void hammi() {
		try {
			Scanner sc = new Scanner(new File("entrada4.txt"));
			String s1 = sc.nextLine();
			String s2 = sc.nextLine();
			sc.close();
			
			int hammingDistance = 0;
			
			for(int i = 0; i < s1.length();i++){
				if(s1.charAt(i) != s2.charAt(i)){
					hammingDistance++;
				}
			}
			
			PrintWriter saida = new PrintWriter(new File("saida4.txt"));
			saida.printf("%d",hammingDistance);
			saida.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void revc() {
		try {
			Scanner sc = new Scanner(new File("entrada3.txt"));
			String s = sc.nextLine();
			sc.close();
			String stringDeSaida = "";
			for(int i = 0; i<s.length();i++){
				switch(s.charAt(i)){
				case 'a':
					stringDeSaida = 't' + stringDeSaida;
					break;
				case 'A':
					stringDeSaida = 'T' + stringDeSaida;
					break;
				case 'c':
					stringDeSaida = 'g' + stringDeSaida;
					break;
				case 'C':
					stringDeSaida = 'G' + stringDeSaida;
					break;
				case 'g':
					stringDeSaida = 'c' + stringDeSaida;
					break;
				case 'G':
					stringDeSaida = 'C' + stringDeSaida;
					break;
				case 't':
					stringDeSaida = 'a' + stringDeSaida;
					break;
				case 'T':
					stringDeSaida = 'A' + stringDeSaida;
					break;
				}
			}
			
			PrintWriter saida = new PrintWriter(new File("saida3.txt"));
			saida.printf("%s",stringDeSaida);
			saida.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void rna() {
		try {
			Scanner sc = new Scanner(new File("entrada2.txt"));
			String s = sc.nextLine();
			sc.close();
			
			s = s.replaceAll("t", "u");
			s = s.replaceAll("T", "U");
			
			PrintWriter saida = new PrintWriter(new File("saida2.txt"));
			saida.printf("%s",s);
			saida.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void dna() {
		try {
			Scanner sc = new Scanner(new File("entrada1.txt"));
			String s = sc.nextLine();
			sc.close();
			int a = 0;
			int c = 0;
			int g = 0;
			int t = 0;
			
			for(int i = 0; i<s.length();i++){
				switch(s.charAt(i)){
				case 'a':
				case 'A':
					a++;
					break;
				case 'c':
				case 'C':
					c++;
					break;
				case 'g':
				case 'G':
					g++;
					break;
				case 't':
				case 'T':
					t++;
					break;
				}
			}
			
			PrintWriter saida = new PrintWriter(new File("saida1.txt"));
			saida.printf("%d %d %d %d",a,c,g,t);
			saida.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
