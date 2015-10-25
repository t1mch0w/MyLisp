import java.util.*;
import java.util.regex.*;

public class MyScanner {

	private ArrayList<String> scanList = new ArrayList<String>();
	private String tempStr;
	private String token;

	public boolean scan() {
		Scanner input = new Scanner(System.in);

		if (!input.hasNextLine()) {
			System.out.println("ERROR: This is an empty input.");
			System.exit(-1);
			return false;
		}

		while (input.hasNextLine()) {
			String lineStr = input.nextLine();
			String temp = "";
			for (int i = 0; i < lineStr.length(); i++) {
				String tmp = lineStr.substring(i, i+1);
				if (tmp.equals("(")) {
					if (!temp.equals("")) {
						scanList.add(temp);
					  temp = "";
					}
					scanList.add(tmp);
				}
				else if (tmp.equals(")")) {
					if (!temp.equals("")) {
						scanList.add(temp);
						temp = "";
					}
					scanList.add(tmp);
				}
				else if (tmp.equals(".")) {
					if (!temp.equals("")) {
						scanList.add(temp);
					  temp = "";
					}
					scanList.add(tmp);
				}
				else if ((tmp.equals(" ")) || (tmp.equals("\t"))) {
					if (!temp.equals("")) {
						scanList.add(temp);
					  temp = "";
					}
				}
				else if (((tmp.compareTo("Z") <= 0) && (tmp.compareTo("A") >= 0)) || ((tmp.compareTo("9") <= 0) && (tmp.compareTo("0") >= 0))) {
					temp += tmp;
				}
				else {
					System.out.println("ERROR: " + tmp + " is an illegal characteristic.");
					System.exit(-1);
					return false;					
				}
			}
			if (!temp.equals("")) {
				scanList.add(temp);
				temp = "";
			}

			// Check the bugs
			// Empty file check
	
			// Pattern check
			Pattern pattern = Pattern.compile("[0-9]+[a-zA-Z]+");
			Pattern pattern1 = Pattern.compile("[A-Z]*[a-z]+[A-Z]*");
			for (int i = 0; i < scanList.size(); i++) {
				Matcher matcher = pattern.matcher(scanList.get(i));
				if (matcher.find()) {
					System.out.println("ERROR: " + scanList.get(i) + " contains one or more illegal characters. (It should be a digit.)");
					System.exit(-1);
					return false;
				}
				Matcher matcher1 = pattern1.matcher(scanList.get(i));
				if (matcher1.find()) {
					System.out.println("ERROR: " + scanList.get(i) + " contains one or more down-case characters.");
					System.exit(-1);
					return false;
				}
			}
		}

		while (input.hasNextLine()) {
			tempStr = input.nextLine();
			String[] a = tempStr.split("(?=[(.)])|(?<=[(.)])");
			for (int i = 0; i < a.length; i++) {
				if (a[i].equals(" ")) {
				}
				else {
					scanList.add(a[i].trim());
				}
			}
		}
		
		// test for printing
		//for (int i = 0; i < scanList.size(); i++) {
		//	System.out.print(scanList.get(i) + " ");
		//}


		Pattern pattern = Pattern.compile("[0-9]+[a-zA-z]+");
		for (int i = 0; i < scanList.size(); i++) {
			Matcher matcher = pattern.matcher(scanList.get(i));
			if (matcher.find()) {
				System.out.println("ERROR: " + scanList.get(i) + " contains one or more illegal characters. (It should be a digit.)");
				return false;
			}
		}

		return true;
	}

	int tag = -1;
	public Node getNextToken() {
		tag++;
		switch (scanList.get(tag)) {
			case "(" : return new Node(1, scanList.get(tag)); //OpenParenthesis
			case ")" : return new Node(2, scanList.get(tag)); //ClosingParenthesis
			case "." : return new Node(3, scanList.get(tag)); //Dot
			default	 : return new Node(4, scanList.get(tag)); //Atom
		}
	}

	public boolean hasNext() {
		if (tag == scanList.size() - 1) {
			return false;
		}
		return true;
	}

	public ArrayList<String> getList() {
		return scanList;
	}
}
