/*
 * Author:	 			Fang Zhou
 * Date:					9/23/2015
 * Description:		This is the main class of this project
*/

public class Project {
	
	public static void main (String [ ] args) {
		MyScanner ms = new MyScanner();
		MyEvaluate me = new MyEvaluate();
		MyParser mp = new MyParser(ms, me);
		if (!ms.scan()) {
			return;
		}
		if (!mp.parseStart()) {
			return;
		}
	}
}
