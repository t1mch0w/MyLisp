CSE 6341 Project 2

1. Name: Fang Zhou

2. Build Information:
  A. Run 'make' in the project's directory.
  B. Run my project in this way 'java Project < $testfile > $testresult'
     where testfile means the name of test file and testresult means the 
     name of test result.
3. Design Information:
  A. Node.java: This file is used to create a binary tree. We can use
     setLeft() and setRight() functions to set the children. Each node
     has an attribute to point out the type of node, just as '(', ')'.
  B. MyScanner.java: The code in this file is used to accept the input
     from the users. In this part, this class checks the legality of 
     each element in the input. The code also provides an important
     function 'getNextToken', which is called by MyParser class
  C. MyParser.java: MyParser is responsible for building the binary
     tree, evaulating if the node is a list, and printing the results
     to the standard output. It also includes some bugs detection code.
  D. Project.java: Project class contains the main function of the
     project. It also creates some instances of MyParser and MyScanner
     classes.
	E. MyEvaluate.java: This is the Evaluate function in Project 2. This
		 is a class that can be revoked to evalute semantics starting from
		 a Node class. There is a function 'eval' that returns the result
		 in a format of Node class.
