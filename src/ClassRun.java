import java.awt.EventQueue;

/*
 *   Práca ktorú ponúkate ma napĺňa.
 *   Rád čelím výzvam.
 *   Chcem sa učiť a rásť v odbore. 
 *   Podieľať sa na vývoji nových aj existujúcich programov.
 *   Práve pracujem na projekte "Bankový systém". 
 *   Pre klienta banky program komunikuje cez Rest Api ( prihlásenie,  prijímanie a odosielanie dát). 
 *   Pre zamestnanca  banky program komunikuje cez SQL ( prihlásenie,  prijímanie a odosielanie dát). 
 *   Ako klient banky môže vykonávať prevody medzi účtami cez formulár alebo QR kódom, prijímať a posielať správy, zobrať si úver, atď..... 
 *   A ako zamestnanec podľa pozície, admin má prístup na spravovanie celej aplikácie, manažér spravuje len určenú časť ( pozíciu ), napríklad schvaľovať úvery klientov,  vytvárať nové ponuky k kartám ( kreditným, debetným ,atď. ), úverom ( bežný úver , hypotéka) alebo sporiacim účtom(terminovaný vklad, investičné sporenie).   
 */

import UIDesign.MainWindow;

public class ClassRun {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}