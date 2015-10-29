public class MyEvaluate {

	public MyEvaluate() {
	}

	public boolean bound(Node x, Map a) {
		return a.containsKey(x);
	}

	public Node getval(Node x, Map a) {
		return a.get(x);
	}

	public void addpairs(Node x, Node y, Map z) {
		//Error check
		for (;

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

	public Node eval(Node n, Map a, Map d) {
		if (n.getType()==4) {
			if ((n.getValue().equals("NIL")) || (n.getValue().equals("T")) || (n.getSubType() == 0)) {
				return n;
			}
			else if (bound(n, a)) {
				return new Node(4, getevl(n, a));
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
				if (CDDR(CDDR(n)).getType()==5) {
					d.put(CDR(n), CDDR(n));
				}
				else {
					// Error info
					System.out.println("ERROR: Not Two Arguments in (B E) for COND");
					System.exit(-1);
				}
			}
			else {
				if (CDR(n).getType() == 5) {
					return apply(CAR(n),evlist(CDR(n), a, d), a, d);
				}
				else {
					// Error info
					System.out.println("ERROR: Argument Is Not A List, " + CDR(n).getValue());
					System.exit(-1);
				}
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
	public Node evcon (Node x, Map a, Map d) {
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

	public Node evlist (Node n, Map a, Map d) {
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

	public Node apply(Node f, Node x, Map a, Map d) {
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
			addpairs(CAR(getval(f,d)), x, a);
			return eval(CDR(getval(f,d)), a, d);
		}
		//else {
		//	// Error info
		//	System.out.println("ERROR: Wrong API, " + f.getValue());
		//	System.exit(-1);				
		//}
		return null;
	}
}
