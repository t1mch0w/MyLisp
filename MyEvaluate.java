import java.util.*;

public class MyEvaluate {

	ArrayList<String> p2list = new ArrayList<String>(Arrays.asList("T", "NIL", "CAR", "CDR", "CONS", "ATOM", "EQ", "NULL", "INT", "PLUS", "MINUS", "TIMES", "QUOTIENT", "REMAINDER", "LESS", "GREATER", "COND", "QUOTE"));

	public MyEvaluate() {
	}

	public boolean bound(Node x, Map<String, Node> a) {
		return a.containsKey(x.getValue());
	}

	public Node getval(Node x, Map<String, Node> a) {
		return (Node)(a.get(x.getValue()));
	}

	public Map<String, Node> addpairs(Node x, Node y, Map<String, Node> z) {
		while ((x!=null) && (y!=null)) {
			if ((x.getType()==4) && (y.getType()==4)) {
				if ((x.getValue().equals("NIL")) && (y.getValue().equals("NIL"))) {
					break;
				}
				else {
					// Error
					System.out.println("ERROR: Wrong Number of Agruments in a-list & d-list");
					System.exit(-1);
				}
			}
			else if ((CDR(y)==null) || (CDR(x)==null) || ((CDR(x).getType()==4) && (CDR(y).getType()==5)) || ((CDR(x).getType()==5) && (CDR(y).getType()==4)))  {
				// Error
				System.out.println("ERROR: Wrong Number of Agruments in a-list & d-list");
				System.exit(-1);
			}

			if ((CAR(x)==null) || bound(CAR(x), z)) {
				// Error
				System.out.println("ERROR: Same Literal Agruments in a-list & d-list");
				System.exit(-1);
			}
			else {
				if ((CAR(x)==null) || (CAR(y)==null)) {
					// Error
					System.out.println("ERROR: Wrong Number of Agruments in a-list & d-list");
					System.exit(-1);
				}
				z.put(CAR(x).getValue(),CAR(y));
				x = CDR(x);
				y = CDR(y);
				continue;
			}
		}
		return z;
	}

	public Node eval(Node n, Map<String, Node> a, Map<String, Node> d) {
		if (n.getType()==4) {
			if ((n.getValue().equals("NIL")) || (n.getValue().equals("T")) || (n.getSubType() == 0)) {
				return n;
			}
			else if (bound(n, a)) {
				if (getval(n,a).getType()==4) {
					return new Node(4, getval(n, a).getValue());
				}
				else {
					return getval(n, a);
				}
			}
			else {
			// Error info
			System.out.println("ERROR: Unbound Literal " + n.getValue());
			System.exit(-1);
			}
		}
		else if (n.getType()==5) {
			if (CAR(n).getValue().equals("QUOTE")) { 
				if ((CDDR(n)!=null) && (CDDR(n).getValue().equals("NIL"))) {
					return CADR(n);
				}
				else {
					// Error info
					System.out.println("ERROR: Wrong Number of Agruments, " + CAR(n).getValue());
					System.exit(-1);
				}
			}
			else if (CAR(n).getValue().equals("COND")) {
				if (CDR(n).getType()==5) {
					if (checkDouble(CDR(n))) {
						return evcon(CDR(n), a, d);
					}
					else {
						// Error info
						System.out.println("ERROR: Not Two Arguments in (B E) for COND");
						System.exit(-1);
					}
				}
				else {
					// Error info
					System.out.println("ERROR: Argument Is Not A List, " + CDR(n).getValue());
					System.exit(-1);
				}
			}
			else if (CAR(n).getValue().equals("DEFUN")) {
				if ((CDR(n)==null) || (CDDR(n)==null) || (CDR(CDDR(n))==null) || (CDDR(CDDR(n))==null) || (CAR(CDDR(n))==null) || (CADR(n)==null) || (CADR(CDDR(n))==null) || (CADR(n).getType()!=4) || (CADR(n).getSubType()==0)) {
					// Error info
					System.out.println("ERROR: Wrong Arguments in DEFUN");
					System.exit(-1);
				}
				else if (p2list.contains(CADR(n).getValue())) {
					// Error info
					System.out.println("ERROR: Keywords in Function Name, " + CADR(n).getValue());
					System.exit(-1);
				}
				if (CDDR(CDDR(n)).getValue().equals("NIL")) {
					if (!checkAtom(CAR(CDDR(n)))) {
						// Error info
						System.out.println("ERROR: Wrong Type of Arguments, DEFUN");
						System.exit(-1);
					}
					d.put(CADR(n).getValue(), CDDR(n));
					return new Node(4, CADR(n).getValue());
				}
				else {
					// Error info
					System.out.println("ERROR: Not Enough Arguments for DEFUN");
					System.exit(-1);
				}
			}
			else {
					return apply(CAR(n),evlist(CDR(n), a, d), a, d);
					/*
				if (CDR(n).getType() == 5) {
					return apply(CAR(n),evlist(CDR(n), a, d), a, d);
				}
				else {
					// Error info
					System.out.println("ERROR: Argument Is Not A List, " + CAR(n).getValue());
					System.exit(-1);
				}
				*/
			}
		}
		return null;
	}

	public boolean checkDouble(Node n) {
		if (n.getValue().equals("NIL")) {
			return true;
		}
		else {
			if (((CAAR(n))!=null) && ((CADR(CAR(n)))!=null) && ((CDDR(CAR(n)))!=null) && (CDDR(CAR(n)).getValue().equals("NIL"))) {
				return (true && checkDouble(CDR(n)));
			}
			else {
				return false;
			}
		}
	}

	// evcon function
	public Node evcon (Node x, Map<String, Node> a, Map<String, Node> d) {
		if (x.getValue().equals("NIL")) {
			// Error info
			System.out.println("ERROR: No Final Result in COND");
			System.exit(-1);
		}
		else if ((CDDR(CAR(x))!=null) && (CDDR(CAR(x)).getValue().equals("NIL"))) {
			String y = (eval(CAAR(x), a, d)).getValue();
			if (y.equals("T")) {
				return eval(CADR(CAR(x)), a, d);
			}
			else if (y.equals("NIL")) {
				return evcon(CDR(x), a, d);
			}
			else {
				// Error info
				System.out.println("ERROR: Wrong Condition, COND");
				System.exit(-1);	
			}
		}
		else {
			// Error info
			System.out.println("ERROR: Wrong Number of Arguments, COND");
			System.exit(-1);			
		}
		return null;
	}

	public Node evlist (Node n, Map<String, Node> a, Map<String, Node> d) {
		if (n.getValue().equals("NIL")) {
			return n;
		}
		else {
			Node newNode = new Node(5);
			if (CAR(n)==null) {
				// Error info
				System.out.println("ERROR: eval Input Is Invalid.");
				System.exit(-1);			
			}
			newNode.setLeft(eval(CAR(n), a, d));
			if (CDR(n)==null) {
				// Error info
				System.out.println("ERROR: evList Input Is Invalid.");
				System.exit(-1);			
			}
			newNode.setRight(evlist(CDR(n), a, d));
			return newNode;
		}
	}

	public Node apply(Node f, Node x, Map<String, Node> a, Map<String, Node> d) {
		if ((f.getValue().equals("CAR")) || (f.getValue().equals("CDR"))) {
			if (CDR(x).getValue().equals("NIL")) {
				if ((CAAR(x)!=null) && (f.getValue().equals("CAR"))) {
					return CAAR(x);
				}
				else if ((CDAR(x)!=null) && (f.getValue().equals("CDR"))) {
					return CDAR(x);
				}
				else {
					// Error info
					System.out.println("ERROR: No Enough Arguments, " + f.getValue());
					System.exit(-1);
				}
			}
			else {
				// Error info
				System.out.println("ERROR: Wrong Number of Arguments," + f.getValue());
				System.exit(-1);
			}
		}
		else if (f.getValue().equals("CONS")) {
			if ((CDDR(x)!=null) && (CDDR(x).getValue().equals("NIL"))) {
				Node newNode = new Node(5);
				newNode.setLeft(CAR(x));
				newNode.setRight(CADR(x));
				return newNode;
			}
			else {
				// Error info
				System.out.println("ERROR: Wrong Number of Arguments," + f.getValue());
				System.exit(-1);				
			}
		}
		else if ((f.getValue().equals("ATOM")) || (f.getValue().equals("INT")) || (f.getValue().equals("NULL"))) {
			if ((CDR(x)!=null) && (CDR(x).getValue().equals("NIL"))) {
				if ((f.getValue().equals("ATOM") && (CAR(x).getType()==4))) {
					return new Node(4,"T");
				}
				else if ((f.getValue().equals("INT") && (CAR(x).getSubType()==0))) {
					return new Node(4,"T");
				}
				else if ((f.getValue().equals("NULL") && (CAR(x).getValue().equals("NIL")))) {
					return new Node(4,"T");
				}
				else {
					return new Node(4,"NIL");
				}
			}
			else {
				// Error info
				System.out.println("ERROR: Wrong Number of Arguments," + f.getValue());
				System.exit(-1);						
			}
		}
		else if (f.getValue().equals("EQ")) {
			if ((CDDR(x)!=null) && (CDDR(x).getValue().equals("NIL"))) {
				if ((CAR(x).getType()==4) && (CADR(x).getType()==4)) {
					int result = 0;
					String resultStr = (CAR(x).getValue().equals(CADR(x).getValue()))?"T":"NIL";
					return new Node(4, resultStr);
				}
				else {
					// Error; Wrong Type;
					System.out.println("ERROR: Wrong Types of Arguments, " + f.getValue());
					System.exit(-1);					
				}
			}
			else {
				// Error info
				System.out.println("ERROR: Wrong Number of Arguments, " + f.getValue());
				System.exit(-1);				
			}
		}
		else if ((f.getValue().equals("PLUS")) || (f.getValue().equals("MINUS")) || (f.getValue().equals("TIMES")) || (f.getValue().equals("QUOTIENT")) || (f.getValue().equals("REMAINDER")) || (f.getValue().equals("GREATER")) || (f.getValue().equals("LESS"))) {
			if ((CDDR(x)!=null) && (CDDR(x).getValue().equals("NIL"))) {
				if ((CAR(x).getSubType()==0) && (CADR(x).getSubType()==0)) {
					int result = 0;
					String resultStr = "";
					switch (f.getValue()) {
						case "PLUS": result = CAR(x).getIntValue() + CADR(x).getIntValue(); break;
						case "MINUS": result = CAR(x).getIntValue() - CADR(x).getIntValue(); break;
						case "TIMES": result = CAR(x).getIntValue() * CADR(x).getIntValue(); break;
						case "QUOTIENT": result = CAR(x).getIntValue() / CADR(x).getIntValue(); break;
						case "REMAINDER": result = CAR(x).getIntValue() % CADR(x).getIntValue(); break;
						case "GREATER": resultStr = (CAR(x).getIntValue() > CADR(x).getIntValue())?"T":"NIL"; break;
						case "LESS": resultStr = (CAR(x).getIntValue() < CADR(x).getIntValue())?"T":"NIL"; break;
					}
					if (resultStr.equals("")) {
						return new Node(4,String.valueOf(result));						
					}
					else {
						return new Node(4, resultStr);
					}
				}
				else {
					// Error; Wrong Type;
					System.out.println("ERROR: Wrong Types of Arguments, " + f.getValue());
					System.exit(-1);					
				}
			}
			else {
				// Error info
				System.out.println("ERROR: Wrong Number of Arguments, " + f.getValue());
				System.exit(-1);				
			}
		}
		else {
			if ((getval(f,d)==null) || (CDR(getval(f,d))==null) || (CAR(getval(f,d))==null) || (CADR(getval(f,d))==null) || (CDDR(getval(f,d))==null) || (!CDDR(getval(f,d)).getValue().equals("NIL"))) {
				// Error info
				System.out.println("ERROR: Wrong Number of Arguments, " + f.getValue());
				System.exit(-1);				
			}
			a = new HashMap<String, Node>();
			return eval(CADR(getval(f,d)), addpairs(CAR(getval(f,d)), x, a), d);
		}
		return null;
	}

	public boolean checkAtom(Node n) {
		ArrayList<String> checkList = new ArrayList<String>();
		while (n!=null) {
			if (n.getType()==4) {
				if (n.getValue().equals("NIL")) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if (CAR(n).getType()!=4) {
					return false;
				}
				if (CAR(n).getSubType()==0) {
					return false;
				}
				if (checkList.contains(CAR(n).getValue())) {
					return false;
				}
				if (p2list.contains(CAR(n).getValue())) {
					return false;
				}
				checkList.add(CAR(n).getValue());
				n = CDR(n);
			}
		}
		return true;
	}

	public Node CAR(Node n) {
		return n.getLeft();
	}
	
	public Node CDR(Node n) {
		return n.getRight();
	}
	
	public Node CAAR(Node n) {
		return n.getLeft().getLeft();
	}
	
	public Node CADR(Node n) {
		return n.getRight().getLeft();
	}
	
	public Node CDAR(Node n) {
		return n.getLeft().getRight();
	}
	
	public Node CDDR(Node n) {
		return n.getRight().getRight();
	}
}


