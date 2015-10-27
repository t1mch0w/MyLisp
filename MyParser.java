public class MyParser {
	private MyScanner ms;
	private MyEvaluate me;
	private int pos = 0;
	private int error = 0;

	public MyParser(MyScanner ms, MyEvaluate me) {
		this.ms = ms;
		this.me = me;
	}

	public boolean parseStart() {
		while (ms.hasNext()) {
			Node start = new Node(5);
			if (!parseSexp(start)) {
				error = 1;
				break;
			}
			Node newNode = me.eval(start);
			//Node newNode = start;
			evaluate(newNode);
			printer(newNode, judge(newNode));
			System.out.println();
		}
		if (error!=0) {
			return false;
		}
		return true;
	}

	public void printer(Node n, boolean kind) {
		if (n.getType() == 4) {
			System.out.print(n.getValue());
		}
		else if (n.getType() == 5) {
			if ((n.getList() == true) && kind) {
				if ((n.hasLeft()) && (n.hasRight())) {
					System.out.print("(");
					if (n.getLeft().getType() == 4) {
						System.out.print(n.getLeft().getValue());
						if ((n.getRight().getType() == 4) && (n.getRight().getValue().equals("NIL"))) {
						}
						else {
							System.out.print(" ");
						}
					}
					else {
					  printer(n.getLeft(), kind);
						if ((n.getRight().getType() == 4) && (n.getRight().getValue().equals("NIL"))) {
						}
						else {
					    System.out.print(" ");
						}
					}
					while (n.getRight().hasLeft() && (n.getRight().hasRight())) {
						n = n.getRight();
  					if (n.getLeft().getType() == 4) {
  						System.out.print(n.getLeft().getValue());
  						if ((n.getRight().getType() == 4) && (n.getRight().getValue().equals("NIL"))) {
							}
							else {
								System.out.print(" ");
							}
  					}
  					else {
  					  printer(n.getLeft(),kind);
							if ((n.getRight().getType() == 4) && (n.getRight().getValue().equals("NIL"))) {
							}
							else {
								System.out.print(" ");
							}
  					}
					}
					System.out.print(")");
					if ((n.getRight().getType() == 4) && (n.getRight().getValue().equals("NIL"))) {
					}
					else {
						System.out.print(" ");
					}
				}
				else {
					printer(n.getLeft(), kind);
				}
			}
			else {
  			if ((n.hasLeft()) && (n.hasRight())) {
  				System.out.print("(");
  				printer(n.getLeft(), kind);
  				System.out.print(" . ");
  				printer(n.getRight(), kind);
  				System.out.print(")");
  			}
  			else {
  				printer(n.getLeft(), kind);
  			}
			}
		}
	}

	public boolean judge(Node n) {
		if (n.getType() == 5) {
			return (n.getList() && judge(n.getLeft()) && judge(n.getRight()));
		}
		else {
			return true;
		}
	}
	
	public void evaluate(Node n) {
		if (n.getType() == 4) {
			if (n.getValue().equals("NIL")) {
				n.setList(true);
			}
		}
		else {
			if ((n.hasLeft())&&(n.hasRight())) {
			  evaluate(n.getLeft());
			  evaluate(n.getRight());
			  if (n.getRight().getList()) {
			  	n.setList(true);
			  }
			  if ((n.getRight().getList()) && (n.getLeft().getList())) {
				}
			}
			else if (n.getLeft()!=null) {
			  evaluate(n.getLeft());
			}
		}
	}

	public boolean parseSexp(Node n) {
		Node token;
		if (ms.hasNext()) {
			token = ms.getNextToken();
		}
		else {
			return false;
		}
		if (token.getType() == 4) {
			n.setValue(token.getValue());
			n.setSubType(token.getSubType());
			n.setType(token.getType());
			return true;
		}
		else if (token.getType() == 1) {
			Node newLeft = new Node(5);
			n.setLeft(newLeft);
			if (!parseSexp(newLeft)) {
				System.out.println("ERROR: Miss Sexp or Atom element after \"(\"");
				System.exit(-1);
				return false;
			}
			if ((!ms.hasNext()) || (ms.getNextToken().getType() != 3)) {
				System.out.println("ERROR: Miss \".\" element between two Sexps");
				System.exit(-1);
				return false;
			}
			Node newRight = new Node(5);
			n.setRight(newRight);
			if (!parseSexp(newRight)) {
				System.out.println("ERROR: Miss Sexp or Atom element after '.'");
				System.exit(-1);
				return false;
			}
			if ((!ms.hasNext()) || (ms.getNextToken().getType() != 2)) {
				System.out.println("ERROR: Miss \")\" element after the second Sexps");
				System.exit(-1);
				return false;
			}
			return true;
		}
		else {
			return false;
		}
	}
}
