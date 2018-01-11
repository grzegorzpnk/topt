package topt;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Solution {

	public static void main(String[] args) {
		int modulation = 64;
		int N = (int) Math.sqrt(modulation);
		double mx, my; // wartosc oczekiwana
		int x1, x2, y1, y2; // granice calkowania
		NormalDistribution d;
		double constelation[][] = new double[N][N]; // tablica na Pstwa ze sybmol zdekoduje w innym sektorze niz w tym w ktorym jest max
		double PerrorX, PerrorY; // zmienne do calkowania
		double sum;// zmienna do sumowania pstwienstw ze wpadnie do innego
					// sektora
		double sigma = 0.1;
		double MER;
		double BER;
		double symbolError;
		double bitsError;
		int bitsPerSymbol;
		int bitsInModulation;
		
		// petla zeby ustawiac wierzcholek rozkladu w srodku kazdego z sektorow
		for (int j = 0; j < N; j++)
			for (int i = 0; i < N; i++) {
				// ustawiamy wartosc oczekiwana (przesuwanie wierzcholka w rozne
				// sektory konstelacji)
				mx = i + 0.5;
				my = j + 0.5;
				System.out.println("Wartosc oczekiwana po X:" + mx + ". Wartosc oczekiwana po Y:" + my + ".");

				// Policzmy blad ze symbol nie trafi do danego wybranego
				// sektora, czyli suma 15 prawdopodobienstw ze trafi do innego sektora
				sum = 0;
				for (int y = 0; y < N; y++)
					// petla po wszystkich sektorach
					for (int x = 0; x < N; x++) {

						System.out.println("Liczymy pstwo bledu dla sektora: "+ (y * N + x));
						x1 = x;// granice calkowania, czyli granice sektora
						x2 = x + 1;
						y1 = y;
						y2 = y + 1;
						System.out.println("granice calkowania po x: " + x1	+ " " + x2);
						System.out.println("granice calkowania po y: " + y1	+ " " + y2);

						// policzmy pstwo po x -> P(x1<mx<x2)
						d = new NormalDistribution(mx, sigma); // wartosc oczekiwana x
						PerrorX = d.cumulativeProbability(x2) - d.cumulativeProbability(x1);
						System.out.println("pstwo bledu po X:" +PerrorX);

						// policzmy pstwo po y P(y1<my<y2)
						d = new NormalDistribution(my, sigma);
						PerrorY = d.cumulativeProbability(y2) - d.cumulativeProbability(y1);
						System.out.println("pstwo bledu po Y:"+ PerrorY); 

						if (!(y == j & x == i)) // jesli nie jestesmy w sektorze w ktorym powinien wpasc symbol
						{
							System.out.println("Pstwo ze symbol wpadnie do "+ (y * N + x)+ " sektora zamiast do sektora "+ (j * N + i) + " to: " + PerrorY * PerrorX	+ "\n");
							sum += PerrorY * PerrorX;
						}

						if (x == (N - 1) & y == (N - 1)) { //jesli jestesmy w ostatnim sektorze
							constelation[i][j] = sum;
							System.out.println("Pstwo bledu dla " + (y * N + x)	+ " sektora to: " + sum + "\n\n");
						}
					}

			}
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				System.out.println("Pstwo bledu dla sektora " + (y * N + x)+ " to: " + constelation[x][y]*100);		
		MER = 0;
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				MER += constelation[x][y]/modulation; //bo okresla ile symboli odbierzemy w danej konstelacji niepoprawnie, suma po jednostkowych bledach(dla kazdego sektoru) *1/wartosciowosc modulacji
		System.out.println("Prawdopodobienstwo przeklamania symbolu (MER) dla modulacji: "+modulation+"QAM to: "+MER);
	
		symbolError = MER*modulation;//MER*ilosc symboli w modulacji = ilosc przeklamanych symboli
		System.out.println("Ilosc przeklamanych symboli: "+symbolError); 
		
		bitsPerSymbol = (int) (Math.log(modulation)/Math.log(2));
		bitsError = symbolError*bitsPerSymbol;
		System.out.println("Ilosc przeklamanych bitów: "+bitsError);//ilosc symboli przeklamanych*ile bitow na symbol = ilosb bitow przeklamanych
		
		bitsInModulation = bitsPerSymbol * modulation;
		BER = bitsError/bitsInModulation;
		System.out.println("Prawdopodobienstwo przeklamania bitów (BER) dla modulacji: "+modulation+"QAM to: "+BER);


	}

}
