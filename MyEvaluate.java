public class MyEvaluate {

	public MyEvaluate() {
	}

	public boolean startEval(Node n) {
		if (n.getType()==4) {
			return true;
		}
		else if (n.getType()==5) {
			if (n.getLeft().getType()==4) {
				Node left = n.getLeft();
				Node right = n.getRight();
				if ((left.getValue().equals("PLUS")) || (left.getValue().equals("MINUS")) || (left.getValue().equals("TIMES")) || (left.getValue().equals("QUOTIENT")) || (left.getValue().equals("REMAINDER"))) {
					if ((right.getLeft() == null) || (right.getRight() == null)) {
						// Error info
					}
					startEval(right.getLeft());
					startEval(right.getRight());
					int result;
					if ((right.getLeft().getSubType()==0) && (right.getRight().getSubType()==0)) {
						switch (left.getValue()):
							case "PLUS": result = right.getLeft().getIntValue() + right.getRight().getIntValue();
							case "MINUS": result = right.getLeft().getIntValue() - right.getRight().getIntValue();
							case "TIMES": result = right.getLeft().getIntValue() * right.getRight().getIntValue();
							case "QUOTIENT": result = right.getLeft().getIntValue() / right.getRight().getIntValue();
							case "REMAINDER": result = right.getLeft().getIntValue() % right.getRight().getIntValue();
					}
					else {
						// Error; Wrong Type;

					}
					n.setValue(String.valueOf(result));
					n.setLeft(null);
					n.setRight(null);
					n.setType(4);
					n.setSubType(0);
				}
				else if ((left.getValue().equals("ATOM")) || (left.getValue().equals("INT")) || (left.getValue().equals("NULL"))) {
					String result;
					if (right==null) {
					}
					startEval(right);
					if ((right.getType()==4) && (left.getValue().equals("ATOM"))) {
						result="T";
					}
					else if ((right.getSubType()==0) && (left.getValue().equals("INT"))) {
						result="T";
					}
					else if ((right.getValue().equals("NIL")) && (left.getValue().equals("NULL"))) {
						result="T";
					}
					n.setValue(result);
					n.setLeft(null);
					n.setRight(null);
					n.setType(4);
				}
				else if ((left.getValue().equals("GREATER")) || (left.getValue().equals("LESS")) || (left.getValue().equals("EQUAL")))  {
					String result;
					if ((right.getLeft() == null) || (right.getRight() == null)) {
						// Error info
					}
					startEval(right.getLeft());
					startEval(right.getRight());
					if ((right.getLeft().getSubType()==0) && (right.getRight().getSubType()==0)) {
						switch (left.getValue()):
							case "GREATER": result = right.getLeft().getIntValue()>right.getRight().getIntValue()?T,NIL;
							case "LESS": result = right.getLeft().getIntValue()<right.getRight().getIntValue()?T,NIL;
							case "EQUAL": result = right.getLeft().getIntValue()==right.getRight().getIntValue()?T,NIL;
					}
					else {
						// Error; Wrong Type;
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
					n = n.getRight();
				}
		}
		return true;
	}
}
