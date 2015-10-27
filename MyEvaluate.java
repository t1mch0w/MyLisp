public class MyEvaluate {

	public MyEvaluate() {
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

	public boolean startEval(Node n) {
		if (n.getType()==4) {
			return true;
		}
		else if (n.getType()==5) {
			if (CAR(n).getType()==4) {
				Node left = CAR(n);
				Node right = CDR(n);
				if ((left.getValue().equals("PLUS")) || (left.getValue().equals("MINUS")) || (left.getValue().equals("TIMES")) || (left.getValue().equals("QUOTIENT")) || (left.getValue().equals("REMAINDER"))) {
					if (!CDDR(CDR(n)).getValue().equals("NIL")) {
						// Error info
						System.out.println("ERROR: " + left.getValue() + " with Wrong Number of Arguments.");
						System.exit(-1);
					}
					leftArg = CADR(n);
					rightArg = CADR(CDR(n));
					startEval(leftArg);
					startEval(rightArg);
					int result;
					if ((leftArg.getSubType()==0) && (rightArg.getSubType()==0)) {
						switch (left.getValue()):
							case "PLUS": result = leftArg.getIntValue() + irightArg.getIntValue();
							case "MINUS": result = leftArg.getIntValue() - irightArg.getIntValue();
							case "TIMES": result = leftArg.getIntValue() * irightArg.getIntValue();
							case "QUOTIENT": result = leftArg.getIntValue() / irightArg.getIntValue();
							case "REMAINDER": result = leftArg.getIntValue() % irightArg.getIntValue();
					}
					else {
						// Error; Wrong Type;
						System.out.println("ERROR: " + left.getValue() + " with Wrong Types of Arguments.");
						System.exit(-1);
					}
					n.setValue(String.valueOf(result));
					n.setLeft(null);
					n.setRight(null);
					n.setType(4);
					n.setSubType(0);
				}
				else if ((left.getValue().equals("ATOM")) || (left.getValue().equals("INT")) || (left.getValue().equals("NULL"))) {
					String result;
					leftArg = CADR(n);
					rightArg = CDDR(n);
					if (rightArg.getValue.equals("NIL")) {
						// Error info
						System.out.println("ERROR: " + left.getValue() + " with Wrong Number of Arguments.");
						System.exit(-1);
					}
					startEval(leftArg);
					if ((leftArg.getType()==4) && (left.getValue().equals("ATOM"))) {
						result="T";
					}
					else if ((leftArg.getSubType()==0) && (left.getValue().equals("INT"))) {
						result="T";
					}
					else if ((leftArg.getValue().equals("NIL")) && (left.getValue().equals("NULL"))) {
						result="T";
					}
					else {
						result="NIL";
					}
					n.setValue(result);
					n.setLeft(null);
					n.setRight(null);
					n.setType(4);
				}
				else if ((left.getValue().equals("GREATER")) || (left.getValue().equals("LESS")) || (left.getValue().equals("EQUAL")))  {
					String result;
					if (!CDDR(n).getValue().equals("NIL")) {
						// Error info
						System.out.println("ERROR: " + left.getValue() + " with Wrong Number of Arguments.");
						System.exit(-1);
					}
					leftArg = CADR(n);
					rightArg = CADR(CDR(n));
					startEval(leftArg);
					startEval(rightArg);

					if ((leftArg.getSubType()==0) && (rightArg.getSubType()==0)) {
						switch (left.getValue()):
							case "GREATER": result = leftArg.getIntValue()>rightArg.getIntValue()?T,NIL;
							case "LESS": result = leftArg.getIntValue()<rightArg.getIntValue()?T,NIL;
							case "EQUAL": result = leftArg.getIntValue()==rightArg.getIntValue()?T,NIL;
					}
					else {
						// Error info
						System.out.println("ERROR: " + left.getValue() + " with Wrong Number of Arguments.");
						System.exit(-1);
					}
					n.setValue(result);
					n.setLeft(null);
					n.setRight(null);
					n.setType(4);
				}
				else if (left.getValue().equals("CAR")) {

					if ((right == 5) && (right.getLeft() != null)) {
						n = right.getLeft();
					}
				}
				else if (left.getValue().equals("CDR")) {
					if ((right == 5) && (right.getRight() != null)) {
						left = right.getRight();
					}
				}
				else if (left.getValue().equals("CONS")) {
					String result;
					if ((right.getLeft() == null) || (right.getRight() == null)) {
						// Error info
					}
					startEval(right.getLeft());
					startEval(right.getRight());
					n = right;
				}
				else if (left.getValue().equals("COND")) {
					if (right.getLeft().getType() != 5) {
						// Error info
					}
					Node condNode;
					int stop = 0;
					while (1) {
						if ((right.getLeft()!=null) && (right.getRight()!=null)) {
							if (right.getLeft().getLeft()!=5) {
								condNode = right;
								stop = 1;
							}
							condNode = right.getLeft();
							startEval(condNode.getLeft());
							if (condNode.getLeft().getValue().equals("T")) {
								startEval(condNode.getRight());
								n = condNode.getRight();
								break;
							}
							else {
								right = right.getRight();
							}
							if (stop == 1) {
								n.setValue("NIL");
								n.setLeft(null);
								n.setRight(null);
								n.setType(4);
								break;
							}
						}
						else {
							// Error info
						}
					}
				}
				else if (left.getValue().equals("QUOTE")) {
					if (!CDDR(n).getValue().equals("NIL")) {
						// Error info
						System.out.println("ERROR: " + left.getValue() + " with Wrong Number of Arguments.");
						System.exit(-1);
					}
					else {
						n = CADR(n);
					}
				}
		}
		return true;
	}
}
