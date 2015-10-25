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
					if (!startEval(right.getLeft())) {
						// Error info
					}
					if (!startEval(right.getRight())) {
						// Error info
					}
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
						// Error;
					}
					n.setValue(String.valueOf(result));
					n.setLeft(null);
					n.setRight(null);
					n.setType(4);
					n.setSubType(0);
				}
				else if ((left.getValue().equals("T")) || (left.getValue().equals("NIL"))) {
				}
				else if ((left.getValue().equals()) || (left.getValue().equals())) {
				}
			}
			else {
			}
		}
		return true;
	}
}
