package topt;

import java.util.Scanner;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.jfree.ui.RefineryUtilities;

public class Solution {

	public static void main(String[] args) {

		int modulation = 16;
		double sigma1;
		double sigma2;
		double step;
		

		Scanner sc = new Scanner(System.in);
		boolean flaga = true;
		while (flaga) {
			System.out.println("Wybierz pierwsze odchylenie standardowe:");
			sigma1 = Double.parseDouble(sc.nextLine());
			System.out.println("Wybierz drugie odchylenie standardowe:");
			sigma2 = Double.parseDouble(sc.nextLine());
			System.out.println("Wybierz krok:");
			step = Double.parseDouble(sc.nextLine());
			liczPstwo(sigma1, sigma2, step, modulation);
			System.out.println("Wcisnij 1 aby kontynuowaæ, 0 aby wyjsc");
			int tmp = Integer.parseInt(sc.nextLine());
			if (tmp == 0)
				flaga = false;
		}
	}
	
	
	public static void liczPstwo(double _sigma1, double _sigma2, double _step, int _modulation)
	{
		int N = (int) Math.sqrt(_modulation);
		double mx, my; // wartosc oczekiwana
		int x1, x2, y1, y2; // granice calkowania
		NormalDistribution d;
		double constelation[][] = new double[N][N]; // tablica na Pstwa ze sybmol zdekoduje w innym sektorze niz w tym w ktorym jest max
		double PerrorX, PerrorY; // zmienne do calkowania
		double sum;// zmienna do sumowania pstwienstw ze wpadnie do innego
		// sektora
		double MER[]=new double[(int) ((_sigma2-_sigma1)/_step +1)];
		double licznik[]=new double[(int) ((_sigma2-_sigma1)/_step +1)];
		double mianownik[]=new double[(int) ((_sigma2-_sigma1)/_step +1)];
		double BER[]=new double[(int) ((_sigma2-_sigma1)/_step +1)];
		double symbolError;
		double bitsError;
		int bitsPerSymbol;
		int bitsInModulation;
		int cnt=0;
		
		for(double f=_sigma1; _sigma1<=_sigma2; _sigma1+=_step)
		{
			System.out.println("Liczymy dla sigmy: "+_sigma1);
		
		//Liczymy BER
		// petla zeby ustawiac wierzcholek rozkladu w srodku kazdego z sektorow
		for (int j = 0; j < N; j++)
			for (int i = 0; i < N; i++) {
				// ustawiamy wartosc oczekiwana (przesuwanie wierzcholka w rozne
				// sektory konstelacji)
				mx = i + 0.5;
				my = j + 0.5;
			//	System.out.println("Wartosc oczekiwana po X:" + mx + ". Wartosc oczekiwana po Y:" + my + ".");

				// Policzmy blad ze symbol nie trafi do danego wybranego
				// sektora, czyli suma 15 prawdopodobienstw ze trafi do innego sektora
				sum = 0;
				for (int y = 0; y <N; y++)
					// petla po wszystkich sektorach
					for (int x = 0; x <N; x++) {

					//	System.out.println("Liczymy pstwo bledu dla sektora: "+ (y * N + x));
						x1 = x;// granice calkowania, czyli granice sektora
						x2 = x + 1;
						y1 = y;
						y2 = y + 1;
				//		System.out.println("granice calkowania po x: " + x1	+ " " + x2);
				//		System.out.println("granice calkowania po y: " + y1	+ " " + y2);

						// policzmy pstwo po x -> P(x1<mx<x2)
						d = new NormalDistribution(mx, _sigma1); // wartosc oczekiwana x
						PerrorX = d.cumulativeProbability(x2) - d.cumulativeProbability(x1);
				//		System.out.println("pstwo bledu po X:" +PerrorX);

						// policzmy pstwo po y P(y1<my<y2)
						d = new NormalDistribution(my, _sigma1);
						PerrorY = d.cumulativeProbability(y2) - d.cumulativeProbability(y1);
				//		System.out.println("pstwo bledu po Y:"+ PerrorY); 

						if (!(y == j & x == i)) // jesli nie jestesmy w sektorze w ktorym powinien wpasc symbol
						{
				//			System.out.println("Pstwo ze symbol wpadnie do "+ (y * N + x)+ " sektora zamiast do sektora "+ (j * N + i) + " to: " + PerrorY * PerrorX	+ "\n");
							sum += PerrorY * PerrorX;
						}

						if (x == (N - 1) & y == (N - 1)) { //jesli jestesmy w ostatnim sektorze
							constelation[i][j] = sum;
				//			System.out.println("Pstwo bledu dla " + (j * N + i)	+ " sektora to: " + sum + "\n\n");
						}
					}

			}
	/*	for (int y = 0; y < _N; y++)
			for (int x = 0; x < _N; x++)
				System.out.println("Pstwo bledu dla sektora " + (y * _N + x)+ " to: " + constelation[x][y]*100);		
	*/	
		BER[cnt] = 0;
		for (int y = 0; y < N; y++)
			for (int x = 0; x < N; x++)
				BER[cnt] += constelation[x][y]/_modulation; //bo okresla ile symboli(bitów) odbierzemy w danej konstelacji niepoprawnie, suma po jednostkowych bledach(dla kazdego sektoru) *1/wartosciowosc modulacji
		
		
		//policzmy MER
		
		for (int j = 0; j < N; j++)
			for (int i = 0; i < N; i++) {
				mx = i + 0.5;
				my = j + 0.5;
		
			licznik[cnt] = licznik[cnt]+ (Math.pow(mx,2) + Math.pow(my,2));
			mianownik[cnt] = _modulation*2*Math.pow(_sigma1,2);
			}
		MER[cnt]= licznik[cnt]/mianownik[cnt];
		System.out.println("Dla modulacji: "+_modulation+"QAM BER to: "+BER[cnt]+ " a MER to: "+MER[cnt]);
		cnt++;
	}
		
	
		//wykreslmy wykres
		XYSeriesDemo chart = new XYSeriesDemo("BER-MER", MER,BER, _modulation);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		

	}
	
	
	}



